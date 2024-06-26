package com.jpsouza.webcrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class ProducerServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProducerServiceApplication.class, args);
		/*
		 * KafkaProducer kafkaProducer = new KafkaProducer();
		 * try {
		 * Thread.sleep(5000);
		 * } catch (InterruptedException e) {
		 * Thread.currentThread().interrupt();
		 * }
		 * LinkProductService productService = new LinkProductService(kafkaProducer);
		 * List<String> schemaDomains = new ArrayList<String>(Arrays.asList(
		 * "https://www.kabum.com.br/produto/238671/console-playstation-5-sony-ssd-825gb-controle-sem-fio-dualsense-com-midia-fisica-branco-1214a",
		 * "https://www.mercadolivre.com.br/notebook-samsung-galaxy-book2-i5-1235u-windows-11-home-8gb-256gb-ssd-grafite/p/MLB27983182?pdp_filters=deal%3AMLB779362-1#polycard_client=homes-korribanSearchPromotions&searchVariation=MLB27983182&position=3&search_layout=grid&type=product&tracking_id=c0dc6288-3872-4776-a8f0-3e52c32fe849&c_id=/home/promotions-recommendations/element&c_uid=c9ad6546-d0cc-4ef0-93f7-2b31581c7f60",
		 * "https://www.magazineluiza.com.br/smartphone-samsung-galaxy-a14-128gb-preto-4g-octa-core-4gb-ram-66-cam-tripla-selfie-13mp-dual-chip/p/236721400/te/galx",
		 * "https://www.pontofrio.com.br/smart-tv-50-uhd-4k-samsung-50cu7700-processador-crystal-4k-samsung-gaming-hub-visual-livre-de-cabos-tela-sem-limites-alexa-built-in/p/55060824",
		 * "https://www.americanas.com.br/produto/7247587982/bicicleta-ksw-aro-29-cambios-shimano-24-marchas-freio-disco-hidraulico-com-suspensao-led?pfm_carac=bicicleta-29-aluminum-raider-21-marchas-freio-a-disco-com-suspensao&pfm_index=1&pfm_page=search&pfm_pos=grid&pfm_type=search_page&offerId=640ab3c4401db3b86b03cbcf&tamanho=15&cor=Azul%20Claro&condition=NEW",
		 * "https://www.netshoes.com.br/bicicleta-aro-29-gt-sprint-mx7-21v-freio-disco-mtb-aluminio-preto+cinza-QNQ-0040-172",
		 * "https://www.extra.com.br/bicicleta-29-aluminum-raider-21-marchas-freio-a-disco-com-suspensao-1553027063/p/1553026886",
		 * "https://www.casasbahia.com.br/bicicleta-dropp-z3-aro-29-cambios-shimano-21-marchas-freio-a-disco-mecanico-com-suspensao/p/1542822452",
		 * "https://www.brastemp.com.br/geladeira-brastemp-frost-free-side-inverse-3-portas-a----554-litros-cor-inox-bro85ak/p?idsku=326031112",
		 * "https://www.webcontinental.com.br/refrigerador-brastemp-side-inverse-3-portas-frost-free-554-litros-inox-bro85ak/p",
		 * "https://www.angeloni.com.br/eletro/geladeira---refrigerador-brastemp-inverter-frost-free--bro85-554-litros-evox-133462/p?idsku=133365"
		 * ));
		 * productService.verifyProductPageConditions(
		 * schemaDomains.get(6));
		 */
	}
}
