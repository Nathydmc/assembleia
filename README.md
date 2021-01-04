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
