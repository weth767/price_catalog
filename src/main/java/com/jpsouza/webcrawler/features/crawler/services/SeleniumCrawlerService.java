package com.jpsouza.webcrawler.features.crawler.services;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SeleniumCrawlerService {
    WebDriver driver;

    public void startCrawler(int crawlersNumber, Set<String> urls) {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        for (String url : urls) {
            driver.get(url);
            List<WebElement> productContainers = driver.findElements(new By.ByXPath("//div[@class=\"slick-slide slick-active\"]"));
            for (WebElement productContainer : productContainers) {
                Actions action = new Actions(driver);
                action.moveToElement(productContainer).perform();
                WebElement linkElement = productContainer.findElement(new By.ByXPath("//a[@class=\"ui-item__link\"]"));
                WebElement image = productContainer.findElement(new By.ByXPath("//img[@class=\"ui-item__image\"]"));
                WebElement price = productContainer.findElement(new By.ByXPath("//div[@class=\"ui-item__price-and-discount-wrapper\"]"));
                WebElement priceInstallments = productContainer.findElement(new By.ByXPath("//span[@class=\"ui-item__installments\"]"));
                WebElement title = productContainer.findElement(new By.ByXPath("//p[@class=\"ui-item__title\"]"));
                System.out.println(title.getText());
                System.out.println(price.getText());
                System.out.println(priceInstallments.getText());
                System.out.println(image.getAttribute("src"));
                System.out.println(linkElement.getAttribute("href"));
                action.release();
                return;
            }

        }
        // Close the WebDriver and release resources
        //driver.quit();
    }

    public void stopCrawler() {
    }
}
