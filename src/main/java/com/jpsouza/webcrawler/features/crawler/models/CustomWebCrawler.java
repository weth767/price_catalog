package com.jpsouza.webcrawler.features.crawler.models;

import com.jpsouza.webcrawler.features.crawler.repositories.ProductRepository;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Set;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class CustomWebCrawler extends WebCrawler {
    private static final Pattern FILTERS = Pattern.compile("");
    private final ProductRepository repository;

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches();
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println("URL: " + url);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            extractProductInformation(html);
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            System.out.println("Text length: " + text.length());
            System.out.println("Html length: " + html.length());
            System.out.println("Number of outgoing links: " + links.size());
        }
    }

    private void extractProductInformation(String html) {
        Document doc = Jsoup.parse(html);
        Element divElement = doc.select("div.dynamic-carousel__item-container").first();
        if (divElement == null) {
            return;
        }
        Element imageElement = doc.select("img.dynamic-carousel__img").first();
        Element titleElement = doc.select("h3.dynamic-carousel__title").first();
        Element priceElement = doc.select("span.dynamic-carousel__price").first();
        if (priceElement != null && imageElement != null && titleElement != null) {
            Element realPriceElement = priceElement.selectFirst("span");
            Element centPriceElement = priceElement.selectFirst("sup");
            String realPrice = (realPriceElement != null) ? realPriceElement.text() : "";
            String centPrice = (centPriceElement != null) ? centPriceElement.text() : "";
            Product product = new Product();
            product.title = titleElement.text();
            product.imageUrl = imageElement.text();
            product.price = realPrice + "," + centPrice;
            repository.saveAndFlush(product);
        }
    }
}
