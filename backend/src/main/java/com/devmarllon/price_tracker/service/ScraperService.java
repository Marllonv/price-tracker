package com.devmarllon.price_tracker.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class ScraperService {

    public Double getPrice(String url, String containerSelector, String mainSelector, String decimalSelector) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Language", "pt-BR,pt;q=0.9")
                    .header("Referer", "https://www.google.com/")
                    .header("Connection", "keep-alive")
                    .header("Upgrade-Insecure-Requests", "1")
                    .header("Sec-Fetch-Dest", "document")
                    .header("Sec-Fetch-Mode", "navigate")
                    .header("Sec-Fetch-Site", "cross-site")
                    .timeout(15000)
                    .get();

            Element container = (containerSelector != null && !containerSelector.isEmpty())
                    ? doc.selectFirst(containerSelector)
                    : doc;

            if (container == null) {
                return null;
            }

            Element mainElement = container.selectFirst(mainSelector);
            if (mainElement == null) return null;

            String priceText = mainElement.text();

            if (decimalSelector != null && !decimalSelector.isEmpty()) {
                Element decimalElement = container.selectFirst(decimalSelector);
                if (decimalElement != null) {
                    priceText = priceText + "," + decimalElement.text();
                }
            }

            return cleanPrice(priceText);

        } catch (IOException e) {
            System.err.println("Erro ao acessar a URL: " + e.getMessage());
            return null;
        }
    }

    private Double cleanPrice(String price) {
        String cleaned = price.replaceAll("[^0-9,.]", "")
                .replace(".", "")
                .replace(",", ".");
        return Double.parseDouble(cleaned);
    }
}