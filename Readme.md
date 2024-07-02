# Price Catalog Project

## This is a final college project that search products prices and compare to last prices stored

### Make sure you have Docker and Docker Compose installed on your system to run databases and Kafka containers

### Its necessary, install too, Angular 17 and Java 17 to run Frontend and Backend Microservices

### To run this project you can to use VSCode, using their launch configuration or using Make

### To run all microservices will have a different command

* Docker containers:
`make docker-compose`
* Eureka:
`make run_eureka`
* Gateway:
`make run_gateway`
* User serivce:
`make run_user`
* Collector:
`make run_collector`
* Producer:
`make run_producer`
* Classifier:
`make run_classifier`
* Consumer:
`make run_consumer`

### The order to run is

```text
1º - Docker containers
2º - Eureka
3º - Gateway
4º - User service
5º - Collector service
6º - Producer service
7ª - Classifier service
8º - Consumer service
```

### In case if is necessary use VSCode launch, follow down files descriptions

### [launch.json]

```json
{
  "configurations": [
    {
      "type": "java",
      "name": "Eureka service",
      "request": "launch",
      "mainClass": "com.jpsouza.webcrawler.eureka_service.EurekaServiceApplication",
      "args": ["--server.port=8761"],
      "vmArgs": ["-Dspring.profiles.active=dev"]
    },
    {
      "type": "java",
      "name": "Gateway service",
      "request": "launch",
      "mainClass": "com.jpsouza.webcrawler.gateway_service.GatewayServiceApplication",
      "args": ["--server.port=8080"],
      "vmArgs": ["-Dspring.profiles.active=dev"]
    },
    {
      "type": "java",
      "name": "User service",
      "request": "launch",
      "mainClass": "com.jpsouza.webcrawler.UserServiceApplication",
      "args": ["--server.port=8085"],
      "vmArgs": ["-Dspring.profiles.active=dev"]
    },
    {
      "type": "java",
      "name": "Collector service",
      "request": "launch",
      "mainClass": "com.jpsouza.webcrawler.CollectorServiceApplication",
      "args": ["--server.port=8081"],
      "vmArgs": ["-Dspring.profiles.active=dev"]
    },
    {
      "type": "java",
      "name": "Producer service",
      "request": "launch",
      "mainClass": "com.jpsouza.webcrawler.ProducerServiceApplication",
      "args": ["--server.port=8082"],
      "vmArgs": ["-Dspring.profiles.active=dev"]
    },
    {
      "type": "java",
      "name": "Classifier service",
      "request": "launch",
      "mainClass": "com.jpsouza.webcrawler.ClassifierServiceApplication",
      "args": ["--server.port=8084"],
      "vmArgs": ["-Dspring.profiles.active=dev"]
    },
    {
      "type": "java",
      "name": "Consumer service",
      "request": "launch",
      "mainClass": "com.jpsouza.webcrawler.ConsumerServiceApplication",
      "args": ["--server.port=8083"],
      "vmArgs": ["-Dspring.profiles.active=dev"]
    },
    {
      "type": "node-terminal",
      "name": "Docker compose up detached",
      "command": "cd docker && docker-compose up -d",
      "request": "launch"
    },
    {
      "type": "node-terminal",
      "name": "Frontend",
      "command": "cd price_catalog_frontend && npm start",
      "request": "launch"
    }
  ]
}
```

### [Optional] Some configurations(settings.json) to set in VSCode to run Java projects

```json
{
  "java.compile.nullAnalysis.mode": "automatic",
  "java.configuration.updateBuildConfiguration": "automatic",
  "java.debug.settings.onBuildFailureProceed": true,
  "java.debug.settings.showQualifiedNames": true
}
```

### **Observation:** used Ngrok to tunnel gateway and simulates a real application, if you don't want to use, feel free to change variables in the frontend environment
