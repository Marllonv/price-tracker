package com.devmarllon.price_tracker.scraper.strategy;

import com.devmarllon.price_tracker.dto.ScrapingResult;
import com.devmarllon.price_tracker.model.StoreConfig;
import com.devmarllon.price_tracker.scraper.Scraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class JsoupScraper implements Scraper {

    @Override
    public ScrapingResult execute(String url, StoreConfig config) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/146.0.0.0 Safari/537.36")
                    .referrer("http://www.google.com")
                    .timeout(15000)
                    .get();


            Element priceEl = doc.selectFirst(config.getMainSelector());
            Element nameEl = doc.selectFirst(config.getNameSelector());
            Element imgEl = doc.selectFirst(config.getImageSelector());

            if (priceEl == null || nameEl == null) {
                throw new RuntimeException("Elemento vital (preço ou nome) não encontrado com os seletores atuais.");
            }

            String priceText = priceEl.text();

            if (config.getDecimalSelector() != null) {
                Element decimalEl = doc.selectFirst(config.getDecimalSelector());
                if (decimalEl != null) {
                    priceText = priceText + "," + decimalEl.text();
                }
            }

            return new ScrapingResult(
                    cleanPrice(priceText),
                    nameEl.text().trim(),
                    imgEl != null ? imgEl.absUrl("src") : ""
            );

        } catch (IOException e) {
            throw new RuntimeException("Erro de conexão Jsoup: " + e.getMessage());
        }
    }
}