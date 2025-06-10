# Estágio 1: Build da aplicação com Maven
# Usamos uma imagem que já vem com Maven e Java (JDK) 17
FROM maven:3.8.5-openjdk-17 AS build

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia primeiro o pom.xml para aproveitar o cache de dependências do Docker
COPY pom.xml .

# Baixa todas as dependências do projeto
RUN mvn dependency:go-offline

# Copia todo o código-fonte do seu projeto para dentro do contêiner
COPY src ./src

# Executa o build do Maven para gerar o arquivo .jar
# O -DskipTests pula a execução dos testes para acelerar o build no deploy
RUN mvn clean package -DskipTests


# Estágio 2: Execução da aplicação
# Usamos uma imagem leve, que contém apenas o Java (JRE) para rodar a aplicação
FROM eclipse-temurin:17-jre-focal

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo .jar que foi gerado no estágio anterior (build)
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080 (porta padrão do Spring Boot)
EXPOSE 8080

# Comando para iniciar a aplicação quando o contêiner rodar
ENTRYPOINT ["java", "-jar", "app.jar"]