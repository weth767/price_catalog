package com.jpsouza.webcrawler.services;

import com.jpsouza.webcrawler.models.Product;
import com.jpsouza.webcrawler.repositories.ProductRepository;
import com.jpsouza.webcrawler.utils.FormatUtils;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private void saveProduct(String description, BigDecimal price, String url) {
        Product product = new Product();
        product.setDescription(description);
        productRepository.save(product);
    }

    /**
     * Método que vai verificar quando é uma página de produto e filtrar as informações desse produto para salvar na
     * nova tabela
     */
    public void verifyProductPageConditions(Document document, String url) {
        //Por enquanto, focando em resolver usando somente o site da Kabum
        Elements pricesElements = document.select("[class*='Price'], [class*='price'], [class*='Preco'], [class*='preco']");
        Elements h1Elements = document
                .getElementsByTag("h1");
       /* Elements descriptionElements = document
                .select("[class*='description'], [class*='Description'], [class*='Descricao'], [class*='descricao']");
        Elements titleElements = document
                .select("[class*='title'], [class*='Title'], [class*='Titulo'], [class*='titulo']");*/
        if (pricesElements.isEmpty() || h1Elements.isEmpty()) {
            return;
        }
        Optional<BigDecimal> minimumPrice = Arrays.stream(pricesElements.text().split(" "))
                .filter((price) -> !price.equals("R$")).collect(Collectors.toList())
                .stream()
                .map((price) -> FormatUtils
                        .convertFormattedStringToBigDecimal(price, new Locale("PT", "BR")))
                .collect(Collectors.toList()).stream().min(BigDecimal::compareTo);
        if (minimumPrice.isEmpty()) {
            return;
        }
        this.saveProduct(h1Elements.text(), minimumPrice.get(), url);
    }
}
