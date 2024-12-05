# Primeiro estágio: Construir a aplicação Java
FROM ubuntu:latest AS build

# Atualizar e instalar as dependências necessárias
RUN apt-get update && apt-get install -y openjdk-17-jdk maven

# Copiar os arquivos de construção para o contêiner
WORKDIR /app
COPY . .

# Compilar a aplicação
RUN mvn clean install

# Segundo estágio: Imagem final
FROM openjdk:17-jdk-slim

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo JAR da primeira etapa para a imagem final
COPY --from=build /app/target/*.jar /app/app.jar

# Expor a porta da aplicação
EXPOSE 8082

# Comando de entrada
ENTRYPOINT ["java", "-jar", "app.jar"]
