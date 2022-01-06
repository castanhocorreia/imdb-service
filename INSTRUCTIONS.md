# imdb-service

Uma API que o site IMDb irá consultar para exibir seu conteúdo para o desafio da ioasys

## Requisitos

- Docker version 20 ou superior
- docker-compose version 1 ou superior

### **Sem utilizar o Docker**:

- Java 11/OpenJDK 11 ou superior
- PostgreSQL 14+

Requisitos adicionais:

- Portas 8080 e 5432 liberadas (ou mudar as configurações relacionadas no `application.yml`)

## Instalação

Para clonar essa aplicação, rode o seguinte comando:

```bash
git clone https://github.com/castanhocorreia/imdb-service.git
```

## Uso

No diretório do projeto clonado, rode o seguinte comando:

```bash
./mvnw clean install 
```

Em seguida:

```bash
docker-compose up -d
```

### **Sem utilizar o Docker**:

Você precisará de uma conexão com um banco de dados PostgreSQL de nome `imdb`, que esteja rodando na porta 5432 e
alterar o valor de `spring.datasource.url` para apontar ao localhost no `application.yml`.

Cumprindo esses requisitos, rode os comandos:

```bash
./mvnw clean install 
```

Em seguida:

```bash
./mvnw spring-boot:run
```

## Informações

- Há uma coleção do Postman na raiz do projeto, com diversas requisições úteis para testar o uso da API
- Todos os endpoints, com exceção de `/auth/**` (para login e cadastro) demandam autenticação e autorização
- O segundo script do Flyway popula o banco de dados com alguns usuários, pessoas e um filme