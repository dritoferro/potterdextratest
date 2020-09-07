# Dextra Challenge

[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=dritoferro_potterdextratest)](https://sonarcloud.io/dashboard?id=dritoferro_potterdextratest)

[![Build Status](https://travis-ci.org/dritoferro/potterdextratest.svg?branch=master)](https://travis-ci.org/dritoferro/potterdextratest)

##### Este repositório contém uma API para prover dados dos personagens de Harry Potter.
***

A url de produção atualmente é esta: https://potter-dextratest.herokuapp.com/api/v1

Observação: A resposta para a primeira request pode demorar um pouco mais no ambiente de produção, pois a infra é "on-demand".  
***
#### Install:
Será necessário ter uma chave de acesso deste parceiro: [Harry Potter API](https://www.potterapi.com/) 

Para executar este projeto com o docker-compose, caso deseje, basta definir as váriaveis de ambiente criando um arquivo `.env` que contenha as chaves do arquivo `.env.example`.

Na pasta "postman" estão os arquivos com os endpoints disponíveis e de variáveis de ambiente.   

Para facilitar em alguns passos, está na pasta do projeto o script `marauders-map.sh` (Mapa do Maroto) que facilita o fluxo de startup do projeto, passando pelas etapas de build e executando comandos de docker-compose.

***

#### Tech:

* Spring
    * Spring Data
    * Spring Security
    * JWT
    * OpenFeign
    * Hystrix
* Kotlin
* Gradle
* PostgreSQL
* Redis
* Swagger
* Docker
* GitFlow
* Sonarcloud
* Travis
* Jenkins
* Kubernetes
* GitHub
    * Issues
    * Project
    * Milestone
    * Pull Requests
* Mockito

***

O Redis foi implementado com o intuito de servir como cache para as "houses", desta forma evitando consultar o parceiro a todo momento. Este cache possui uma validade de 1 dia e a informação é renovada quando uma nova requisição for feita para uma determinada "house".

***

No endpoint de atualização do cache das "houses" é necessário que o usuário tenha nível ADMIN e para simular tal comportamento, basta criar um usuário que possua "admin" no campo email, exemplo: "teste-admin@email.com". 

***

Devido a detalhes técnicos, a documentação não está disponível no ambiente de produção, no entanto, basta executar o docker-compose que a mesma poderá ser acessada pela url: http://localhost:9595/api/swagger
