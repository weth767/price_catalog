#!make

clean_install_classifier:
	@echo "Executing clean install in Classifier service"
	@cd classifier_service && .\mvnw clean install

clean_install_collector:
	@echo "Executing clean install in Collector service"
	@cd collector_service && .\mvnw clean install

clean_install_consumer:
	@echo "Executing clean install in Consumer service"
	@cd consumer_service && .\mvnw clean install

clean_install_producer:
	@echo "Executing clean install in Producer service"
	@cd producer_service && .\mvnw clean install

clean_install_user:
	@echo "Executing clean install in User service"
	@cd user_service && .\mvnw clean install

clean_install_all:
	@echo "Executing clean install"
	@cd classifier_service && .\mvnw clean install
	@cd collector_service && .\mvnw clean install
	@cd consumer_service && .\mvnw clean install
	@cd producer_service && .\mvnw clean install

docker_compose:
	@echo "Executing docker compose up detached"
	@cd docker && docker-compose up -d

run_classifier:
	@echo "Running Classifier service"
	@cd classifier_service && .\mvnw spring-boot:run

run_collector:
	@echo "Running Collector service"
	@cd collector_service && .\mvnw spring-boot:run

run_consumer:
	@echo "Running Consumer service"
	@cd consumer_service && .\mvnw spring-boot:run

run_producer:
	@echo "Running Producer service"
	@cd producer_service && .\mvnw spring-boot:run

run_user:
	@echo "Running User service"
	@cd user_service && .\mvnw spring-boot:run

run_eureka:
	@echo "Running Eureka service"
	@cd eureka_service && .\mvnw spring-boot:run

run_gateway:
	@echo "Running Gateway service"
	@cd gateway_service && .\mvnw spring-boot:run

run_all:
	@echo "Running all microservices"
	@wt -p "Classifier Service" -d ./classifier_service powershell ".\mvnw spring-boot:run"; -p "Collector Service" -d ./collector_service powershell ".\mvnw spring-boot:run"; -p "Consumer Service" -d ./consumer_service powershell ".\mvnw spring-boot:run"; -p "Producer Service" -d ./producer_service powershell ".\mvnw spring-boot:run"
