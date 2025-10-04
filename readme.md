# Senior Care - Gestão de Saúde para ILPIs

## 1\. Sobre o Projeto

Este projeto é o backend de uma aplicação **Software as a Service (SaaS)**, especialmente projetado para a gestão da
saúde em **Instituições de Longa Permanência para Idosos (ILPIs)**. A solução visa oferecer uma plataforma integrada e
segura para otimizar a administração de medicamentos, organizar as rotinas de cuidado e fortalecer a comunicação com os
familiares dos residentes.

Este repositório contém a API RESTful construída em Java com Spring Boot, servindo como a fundação para as aplicações
front-end (Web e Mobile).

## 2\. Tecnologias Utilizadas

- **Backend:** Java 21+, Spring Boot 3, Spring Web, Spring Data JPA, Spring Security
- **Banco de Dados:** PostgreSQL
- **Migrações de Banco:** Flyway
- **Cache:** Redis
- **Mensageria (Planejado):** RabbitMQ
- **Documentação da API:** Springdoc-openapi (Swagger)
- **Containerização:** Docker e Docker Compose
- **Build Tool:** Maven

## 3\. Arquitetura

A arquitetura deste projeto foi cuidadosamente escolhida para gerenciar a complexidade do domínio de negócio e garantir
escalabilidade e manutenibilidade. A aplicação é um **Monolito Modular** que segue os princípios da **Arquitetura
Limpa (Clean Architecture)** e da **Arquitetura Hexagonal**, utilizando os padrões **DDD (Domain-Driven Design)** e *
*CQRS**.

### Monolito Modular

O código é organizado em módulos que representam **Contextos Delimitados (Bounded Contexts)** do negócio:

- `identityaccess`: Gerencia usuários, autenticação, permissões e funcionários.
- `health`: (Planejado) Gerenciará prontuários, medicamentos e sinais vitais.
- `communication`: (Planejado) Cuidará da comunicação com familiares.
- `shared`: Contém código reutilizável por todos os outros módulos.

### Arquitetura Limpa & Hexagonal (Ports & Adapters)

Dentro de cada módulo, as camadas são organizadas para seguir a **Regra da Dependência**, onde as dependências apontam
sempre para o centro, protegendo a lógica de negócio de detalhes de tecnologia.

- **`domain` (O Coração / Hexágono):** Contém as entidades de negócio puras (ex: `User`, `Role`), Value Objects e as
  interfaces dos repositórios (`IUserRepository`). Esta camada não conhece Spring, JPA ou qualquer outra tecnologia
  externa.
- **`application` (Casos de Uso):** Orquestra as entidades de domínio para executar as funcionalidades do sistema.
  Contém os `Commands`, `Queries` e seus `Handlers`.
- **`infrastructure` (Adaptadores de Saída):** Implementa os "detalhes". É aqui que fica o código de acesso ao banco de
  dados (implementações de repositórios com Spring Data JPA), a configuração do cache com Redis e, futuramente, a
  comunicação com o RabbitMQ.
- **`api` (Adaptadores de Entrada):** Expõe a aplicação para o mundo exterior através de uma API REST, com `Controllers`
  e DTOs.

### CQRS (Command Query Responsibility Segregation)

Separamos a responsabilidade de escrita (operações que alteram dados) da responsabilidade de leitura.

- **Commands:** Representam uma intenção de mudar o estado do sistema (ex: `CreateUserCommand`). São processados por
  `CommandHandlers`.
- **Queries:** Representam uma intenção de ler dados (ex: `FindAllUsersQuery`). São processados por `QueryHandlers`.

Essa separação nos permite otimizar cada caminho de forma independente, por exemplo, usando um cache de alta performance
como o **Redis** apenas para as operações de leitura.

## 4\. Como Executar o Projeto

### Pré-requisitos

- JDK 21+
- Maven 3.9+
- Docker e Docker Compose

### Opção 1: Ambiente Híbrido (Recomendado para Desenvolvimento)

1. **Inicie a infraestrutura (Banco e Cache):**
   ```bash
   docker compose up -d db redis
   ```
2. **Execute a Aplicação:** Abra o projeto na sua IDE (IntelliJ) e execute a classe principal `SeniorCare.java` com o
   perfil `local` ativado.

### Opção 2: Tudo via Docker

1. **Construa o projeto com Maven:**
   ```bash
   ./mvnw clean package
   ```
2. **Suba todos os containers:**
   ```bash
   docker compose up --build
   ```

## 5\. Endpoints da API

Com a aplicação rodando, a documentação interativa da API (Swagger UI) pode ser acessada em:

[http://localhost:8080/swagger-ui.html](https://www.google.com/search?q=http://localhost:8080/swagger-ui.html)