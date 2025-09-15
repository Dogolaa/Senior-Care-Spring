# Estágio 1: Build com Maven
# Usamos uma imagem que já contém o Maven e o JDK.
FROM maven:3.9-eclipse-temurin-21 AS build

# Define o diretório de trabalho dentro do container.
WORKDIR /app

# Copia o pom.xml primeiro para aproveitar o cache de camadas do Docker.
# As dependências só serão baixadas novamente se o pom.xml mudar.
COPY pom.xml .

# Baixa todas as dependências do projeto.
RUN mvn dependency:go-offline

# Copia todo o código-fonte do projeto.
COPY src ./src

# Executa o build do Maven para compilar o código e empacotar em um .jar.
# O -DskipTests pula a execução dos testes durante o build da imagem.
RUN mvn package -DskipTests

# Estágio 2: Execução (Runtime)
# Usamos uma imagem leve, apenas com o Java Runtime Environment (JRE), para executar a aplicação.
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copia o arquivo .jar gerado no estágio de build para a imagem final.
# O nome do .jar é definido pelo <artifactId> e <version> no seu pom.xml.
COPY --from=build /app/target/Senior-Care-Spring-1.0-SNAPSHOT.jar app.jar

# Expõe a porta que a aplicação Spring Boot usa por padrão.
EXPOSE 8080

# Comando para iniciar a aplicação quando o container for executado.
ENTRYPOINT ["java", "-jar", "app.jar"]