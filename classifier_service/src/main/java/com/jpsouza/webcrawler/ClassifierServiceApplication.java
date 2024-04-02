package com.jpsouza.webcrawler;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.jpsouza.webcrawler.callables.OntologyCallable;
import com.jpsouza.webcrawler.configuration.MongoConfig;
import com.jpsouza.webcrawler.dtos.PossibleProductDTO;
import com.jpsouza.webcrawler.enums.SimilarityMethod;
import com.jpsouza.webcrawler.models.Product;

@SpringBootApplication
@EnableFeignClients
@EnableMongoRepositories
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class ClassifierServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ClassifierServiceApplication.class, args);
		MongoTemplate template = new MongoConfig().mongoTemplate();
		PossibleProductDTO possibleProductDTO = new PossibleProductDTO();
		possibleProductDTO.setDescription("Refrigerante Antarctica Garrafa 2 5l");
		Callable<Product> callable = new OntologyCallable(possibleProductDTO, SimilarityMethod.COSINE, template);
		Future<Product> future = Executors.newFixedThreadPool(1).submit(callable);
		try {
			Product product = future.get();
			System.out.println(product);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}
