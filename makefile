#!make

ci:
	@echo "executing maven clean install"
	@mvnw clean install
dcu:
	@echo "executing docker compose up detached"
	@cd docker && docker-compose up -d

sr:
	@echo "executing spring application"
	@mvnw spring-boot:run

run: ci dcu sr