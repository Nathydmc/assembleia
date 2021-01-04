# Projeto: Assembleia
## 1. Sobre
Projeto criado para auxiliar a votação de Pautas em uma Assembléia.

## 2. Aplicação na nuvem
1. URL: https://assembleia-dev.herokuapp.com/

## 3. Dependências (local)
1. Java 8
2. SGBD MariaDB ou similar

## 4. Como rodar (local)
1. Confira o arquivo **application.properties** para realizar a conexão com o banco de dados
2. Clone o projeto através da URL: https://github.com/Nathydmc/assembleia.git
3. Acesse a pasta onde encontra-se o projeto através do comando: `cd assembleia`
4. Rode o comando: `mvn clean install`
5. Inicie a aplicação através do comando: `mvn spring-boot:run`

## 5. Rodar testes
* Rode o comando: `mvn test`

## 6. Endpoints
 - Cadastro de Associado\
**POST** /voto\
**Parâmetros:** \
cpf (tipo: query, obrigatório: sim)\
nome (tipo: query, obrigatório: sim)\
**Exemplo:** /associado?cpf=35339898016&nome=Jose\
**Responses:**\
200 - OK - Associado cadastrado com sucesso.\
406 - NOT ACCEPTABLE - Já existe usuário com Cpf cadastrado no sistema.

- Cadastro de Pauta\
**POST** /pauta\
**Parâmetros:** \
assunto (tipo: query, obrigatório: sim) \
**Exemplo:** /pauta?assunto=Assunto da pauta\
**Responses:**\
200 - OK - Pauta cadastrada com sucesso.

- Votação em uma Pauta\
**POST** /voto\
**Parâmetros:** \
pautaId (tipo: query, obrigatório: sim) \
associadoId (tipo: query, obrigatório: sim)\
voto (tipo: query, obrigatório: sim, valores aceitos: true/false)\
**Exemplo:** /voto?pautaId=25&associadoId=11&voto=false\
**Responses:**\
200 - OK - Voto cadastrada com sucesso.\
404 - NOT FOUND - Pauta ou associado não encontrado.\
406 - NOT ACCEPTABLE - Votação da pauta já foi encerrada.

- Consulta de resultado da votação da Pauta\
**GET** /pauta/{pautaId}\
**Parâmetros:** \
pautaId (tipo: path, obrigatório: sim) \
**Exemplo:**/pauta/1\
**Responses:**
200 - OK - Consulta realizada com sucesso.\
404 - NOT FOUND - Pauta não encontrada.

## 7. Versionamento da API
A sugestão para o versionamento da API seria adicionar a versão na URI, de forma a manter as funcionalidades antigas e disponibilizar as novas através da troca de versão, não impactando os consumidores de versões anteriores da aplicação. Por exemplo: https://assembleia-dev.herokuapp.com/v1/associado e https://assembleia-dev.herokuapp.com/v2/associado.

## 8. Considerações

- O código foi organizado em pacotes conforme responsabilidade das classes, de forma a facilitar o entendimento;
- Toda a camada de serviço foi coberta por testes unitários e também foram feitos testes funcionais através da ferramenta Postman, como formas de evitar bugs e garantir a qualidade da aplicação;
- Optou-se pela utilização do Spring Boot para facilitar o processo de configuração do projeto, promovendo a otimização do tempo disponível para a realização do desafio;
- O Heroku foi escolhido pela simplicidade e facilidade provida em disponibilizar aplicações na nuvem e também por ser gratuito;
- Foi criada uma exceção personalizada (BusinessException), utilizada exclusivamente para erros que infringem regras de negócio, assim como ExceptionHandlers, proporcionando respostas personalizadas que fazem mais sentido ao usuário. 

## 9. Melhorias a serem feitas
- Implementação de testes de integração entre camadas através da utilização do Cucumber e BD H2;
- Utilização de filas para disponibilizar os resultados das votações às aplicações interessadas em consumi-los;
- Testes de performance para evitar comportamento indesejado da aplicação ao receber várias requisições ao mesmo tempo.
