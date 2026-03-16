package com.devmarllon.price_tracker.scraper.strategy;

import com.devmarllon.price_tracker.dto.ScrapingResult;
import com.devmarllon.price_tracker.model.StoreConfig;
import com.devmarllon.price_tracker.scraper.Scraper;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.Duration;
import java.util.Collections;

@Component
public class SeleniumScraper implements Scraper {

    @Override
    public ScrapingResult execute(String url, StoreConfig config) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/146.0.0.0 Safari/537.36");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--window-size=1920,1080");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            driver.get(url);
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");

            Thread.sleep(3000);

            WebElement priceEl = wait.until(d -> {
                try {
                    WebElement el = d.findElement(By.cssSelector(config.getMainSelector()));
                    String text = el.getText();
                    if (text != null && text.contains("R$")) return el;

                    return d.findElement(By.xpath("//h4[contains(text(), 'R$')]"));
                } catch (NoSuchElementException | StaleElementReferenceException e) {
                    try {
                        return d.findElement(By.xpath("//*[contains(text(), 'R$')]"));
                    } catch (Exception e2) {
                        return null;
                    }
                }
            });

            String priceText = priceEl.getAttribute("textContent")
                    .replace("\u00a0", " ")
                    .replace("\n", " ")
                    .trim();

            WebElement nameEl = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(config.getNameSelector())));
            WebElement imgEl = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(config.getImageSelector())));

            return new ScrapingResult(
                    cleanPrice(priceText),
                    nameEl.getText().trim(),
                    imgEl.getAttribute("src")
            );

        } catch (Exception e) {
            try {
                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String fileName = "erro_scraping_" + System.currentTimeMillis() + ".png";
                org.apache.commons.io.FileUtils.copyFile(scrFile, new File(fileName));
                System.err.println("Captura de tela salva como: " + fileName);
            } catch (Exception printException) {
                System.err.println("Erro ao tentar salvar captura de tela: " + printException.getMessage());
            }
            throw new RuntimeException("Falha no scraping da URL: " + url + " | Erro: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}