package com.devmarllon.price_tracker;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

public class SeleniumScraperTest {

    @Test
    void testarCapturaPichau() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/145.0.0.0 Safari/537.36");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            driver.get("https://www.pichau.com.br/cadeira-gamer-tgt-heron-tc2-preto-e-vermelho-tgt-hrtc-br03");

            WebElement preco = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div[class*='price_vista']")
            ));

            System.out.println("Preço capturado com sucesso: " + preco.getText());
            assertNotNull(preco.getText());

        } catch (Exception e) {
            System.out.println("Erro ao localizar: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }

}
