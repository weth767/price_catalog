#!make

clean_install:
	@echo "executing maven clean install"
	@mvnw clean install

run_clean_install: clean_install docker_up spring_run

docker_up:
	@echo "executing docker compose up detached"
	@cd docker && docker-compose up -d

spring_run:
	@echo "executing spring application"
	@mvnw spring-boot:run