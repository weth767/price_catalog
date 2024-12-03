package com.jpsouza.webcrawler.callables;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextQuery;

import com.jpsouza.webcrawler.dtos.PossibleProductDTO;
import com.jpsouza.webcrawler.enums.SimilarityMethod;
import com.jpsouza.webcrawler.models.Product;
import com.jpsouza.webcrawler.services.CosineStrategyService;
import com.jpsouza.webcrawler.services.DamerauLevenshteinStrategyService;
import com.jpsouza.webcrawler.services.JaccardServiceStrategyService;
import com.jpsouza.webcrawler.services.JaroWinklerStrategyService;
import com.jpsouza.webcrawler.services.LevenshteinStrategyService;
import com.jpsouza.webcrawler.services.MongoScoreStrategyService;
import com.jpsouza.webcrawler.services.NGramStrategyService;
import com.jpsouza.webcrawler.services.NeedlemanWunchStrategyService;
import com.jpsouza.webcrawler.services.OntologyStrategyService;
import com.jpsouza.webcrawler.services.SmithWatermanStrategyService;
import com.jpsouza.webcrawler.services.SorensenDiceStrategyService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OntologyCallable implements Callable<Product> {

    private final PossibleProductDTO possibleProduct;
    private final SimilarityMethod similarityMethod;
    @Autowired
    private final MongoTemplate mongoTemplate;
    private final Map<SimilarityMethod, OntologyStrategyService> services = new HashMap<>() {
        {
            put(SimilarityMethod.COSINE, new CosineStrategyService());
            put(SimilarityMethod.JACCARD, new JaccardServiceStrategyService());
            put(SimilarityMethod.JAROWINKLER, new JaroWinklerStrategyService());
            put(SimilarityMethod.DAMERAULEVENSHTEIN, new DamerauLevenshteinStrategyService());
            put(SimilarityMethod.LEVENSHTEIN, new LevenshteinStrategyService());
            put(SimilarityMethod.MONGOSCORE, new MongoScoreStrategyService());
            put(SimilarityMethod.NEEDLEMANWUNCH, new NeedlemanWunchStrategyService());
            put(SimilarityMethod.NGRAM, new NGramStrategyService());
            put(SimilarityMethod.SMITHWATERMAN, new SmithWatermanStrategyService());
            put(SimilarityMethod.SORENSENDICE, new SorensenDiceStrategyService());
        }
    };

    @SuppressWarnings("null")
    @Override
    public Product call() throws Exception {
        Query query;
        if (Objects.isNull(possibleProduct.getName()) && Objects.isNull(possibleProduct.getDescription())) {
            return null;
        }
        if (!services.containsKey(similarityMethod)) {
            return null;
        }
        if (similarityMethod.equals(SimilarityMethod.MONGOSCORE)) {
            query = new TextQuery(Objects.nonNull(possibleProduct.getName()) ? possibleProduct.getName() : possibleProduct.getDescription()).includeScore().sortByScore();
        } else {
            query = new TextQuery(Objects.nonNull(possibleProduct.getName()) ? possibleProduct.getName() : possibleProduct.getDescription());
        }
        List<Product> products = mongoTemplate.find(query, Product.class);
        return services.get(this.similarityMethod).compareSimilarity(products, Objects.nonNull(possibleProduct.getName()) ? possibleProduct.getName() : possibleProduct.getDescription());
    }

}
