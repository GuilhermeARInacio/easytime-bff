# Easytime - Sistema de Marcação de Ponto (BFF)

## Descrição do Projeto
Easytime é um sistema de marcação de ponto desenvolvido com uma arquitetura **BFF (Back For Front)**. Este projeto é responsável por gerenciar a autenticação de usuários e fornecer um **token JWT** para acesso seguro às funcionalidades do sistema.

O projeto foi desenvolvido em **Java** utilizando o framework **Spring Boot** e implementa autenticação e segurança com **Spring Security**.

## Funcionalidades
- **Autenticação de Usuário**:
    - Rota de login que recebe as credenciais (usuário e senha).
    - Validação de senha com regras específicas.
- **CRUD de Usuário**:
    - Rota de cadastro de um novo usuário, deve ser feito por um usuario já cadastrado.
    - Rota de listagem de todos os usuários cadastrados.
    - Rota que retorna um usuário de acordo com o ID informado.
    - Rota para excluir um usuário de acordo com o ID informado.
    - Validação de campos com regras específicas.
- **Troca de senha**:
    - Rota para enviar um código de validação para um email válido.
    - Rota para validar o código enviado e permitir a troca de senha.
- **Batimento de ponto**:
    - Rota de batimento de ponto, onde o usuário pode registrar um pedido de registro de ponto no sistema.
- **Consulta de ponto**:
    - Rota de consulta de ponto, onde o usuário pode informar login e um periodo de tempo para visualizar os pontos registrados no periodo.
- **Alteração de registro de ponto**:
    - Rota de alteração de registro de ponto, onde o usuário informa o login e o ID do registro e então registra um pedido de alteração no sistema.
      -**Aprovar pedido de ponto**
    - Rota para aprovar um pedido de ponto pendente, onde o usuário administrador informa o ID do pedido e aprova o ponto.
      -**Reprovar pedido de ponto**
    - Rota para reprovar um pedido de ponto pendente, onde o usuário administrador informa o ID do pedido e reprova o ponto.
    - Rota para remover um registro de ponto, onde o usuário admin informa o ID do ponto a ser removido.
      -**Listar pontos**
    - Rota para listar todos os pontos registrados no sistema.
    - Rota para listar todos os pedidos pendentes.
    - Rota para listar todos os pedidos registrados no sistema.

## Tecnologias Utilizadas
- **Java 17**
- **Spring Boot**
- **Spring Security**
- **Maven** (gerenciador de dependências)
- **JWT** (JSON Web Token)

## Estrutura do Projeto
- `src/main/java/easytime/bff/api/controller`: Contém os controladores REST, como o controlador de autenticação.
- `src/main/java/easytime/bff/api/dto`: Contém os DTOs (Data Transfer Objects) utilizados para transferência de dados.
- `src/main/java/easytime/bff/api/service`: Contém as regras de negócio e serviços.
- `src/main/java/easytime/bff/api/validacoes`: Contém as validações específicas, como validação de senha.
- `src/main/java/easytime/bff/api/infra`: Contém os componentes de infraestrutura, como configuração de segurança e JWT.
- `src/main/java/easytime/bff/api/model`: Contém os modelos de dados, como o modelo de usuário.
- `src/main/java/easytime/bff/api/util`: Contém utilitários e classes auxiliares.

## Endpoints

### Health
**POST** `/health`

- **Descrição**: Apenas para verificar se o projeto está rodando.
- **Response**:
    - **200 OK**: Retorna uma mensagem de boas vindas
      ```json
      "Bem-vindo ao EasyTime!"
      ```

### Login
**POST** `/login`

- **Descrição**: Realiza a autenticação do usuário e retorna um token JWT.
- **Request Body**:
  ```json
  {
    "login": "string",
    "senha": "string"
  }
  ```
- **Response**:
    - **200 OK**: Retorna o token JWT.
      ```json
      {
        "token": "string"
      }
      ```
    - **400 Bad Request**: Retorna uma mensagem de erro caso as credenciais sejam inválidas.

### Cadastro de Usuário
**PUT** `/users/create`
- **Descrição**: Cadastra um novo usuário.
  - **Request Body**:
    ```json
    {
          "nome": "string",
          "email": "string",
          "login": "string",
          "password": "string",
          "sector": "string",
          "jobTitle": "string",
          "role": "string",
          "isActive": "boolean"
    }
    ```
    - **Response**:
        - **201 Created**: Retorna o usuário cadastrado.
          ```json
          {
            "id": 1,
            "nome": "string",
            "login": "string",
            "email": "string",
            "password": "string",
            "sector": "string",
            "jobTitle": "string",
            "role": "string",
            "isActive": "boolean",
            "creationDate": "datetime",
            "updateDate": "datetime",
            "lastLogin": "datetime",
            "appointments": null,
            "shifts": null,
            "timeLogs": null,
            "absences": null,
            "overtimes": null,
            "auditLogs": null
          }
          ```
        - **400 Bad Request**: Retorna uma mensagem de erro caso os dados sejam inválidos.
        - **401 Unauthorized**: Retorna uma mensagem de erro caso o token esteja inválido ou vazio.

### Listagem de Usuário
**GET** `/users/list`
- **Descrição**: Lista todos os usuários cadastrados.
    - **Response**:
        - **200 OK**: Retorna uma lista de usuários.
          ```json
            [
              {
                "id": 1,
                "nome": "string",
                "login": "string",
                "email": "string",
                "sector": "string",
                "jobTitle": "string",
                "role": "string",
                "isActive": "boolean"
              },
              {
                "id": 2,
                "nome": "string",
                "login": "string",
                "email": "string",
                "sector": "string",
                "jobTitle": "string",
                "role": "string",
                "isActive": "boolean"
              }
            ]
          ```
        - **400 Bad Request**: Retorna uma mensagem de erro caso não haja usuários cadastrados.
        - **401 Unauthorized**: Retorna uma mensagem de erro caso o token esteja inválido ou vazio.

### Usuário por ID
**GET** `/users/getById/{id}`
- **Descrição**: Retorna o usuário de acordo com o ID informado.
    - **Response**:
      - **200 OK**: Retorna um usuário.
        ```json
            {
              "id": 1,
              "nome": "string",
              "login": "string",
              "email": "string",
              "sector": "string",
              "jobTitle": "string",
              "role": "string",
              "isActive": "boolean"
            }
        ```
      - **400 Bad Request**: Retorna uma mensagem de erro caso não haja um usuário com o ID informado.
      - **401 Unauthorized**: Retorna uma mensagem de erro caso o token esteja inválido ou vazio.

### Deletar Usuário por ID
**GET** `/users/delete/{id}`
- **Descrição**: Deleta um usuário de acordo com o ID informado.
    - **Response**:
        - **200 OK**: Retorna uma mensagem de sucesso.
        - **400 Bad Request**: Retorna uma mensagem de erro caso não haja um usuário com o ID informado.
        - **401 Unauthorized**: Retorna uma mensagem de erro caso o token esteja inválido ou vazio.

### Envio de Código de Validação
**POST** `/senha/enviar-codigo`
- **Descrição**: Envia um código de validação para o email informado.
    - **Response**:
        - **200 OK**: Retorna uma mensagem de sucesso.
        - **400 Bad Request**: Retorna uma mensagem de erro caso o campo não tenha sido informado.
        - **401 Unauthorized**: Retorna uma mensagem de erro caso o token esteja inválido ou vazio.

### Redefinição de senha
**POST** `/senha/redefinir`
- **Descrição**: Redefine a senha do usuário.
    - **Response**:
        - **200 OK**: Retorna uma mensagem de sucesso.
        - **400 Bad Request**: Retorna uma mensagem de erro caso um dos campos não tenha sido informado.
        - **401 Unauthorized**: Retorna uma mensagem de erro caso o token esteja inválido ou vazio.

### Batimento de ponto
**POST** `/ponto`
- **Descrição**: Realiza o batimento de ponto do usuário.

      - **Response**:
          - **200 OK**: Retorna o ponto batido.
            ```json
            {
                "login": "string",
                "data": "2025-05-14",
                "horarioBatida": "12:00:00",
                "status": "PENDENTE"
            }
            ```
          - **400 Bad Request**: Retorna uma mensagem de erro quando o usuário não é válido ou não existe.
          - **401 Unauthorized**: Retorna uma mensagem de erro quando o usuário não está autenticado.

### Deletar registro de ponto
**DELETE** `/ponto/{id}`
- **Descrição**: Usuário admin deleta o registro de ponto de acordo com o ID informado.
    - **Response**:
        - **200 OK**: Retorna uma mensagem de sucesso.
        - **400 Bad Request**: Retorna uma mensagem de erro caso o ID informado não exista.
        - **401 Unauthorized**: Retorna uma mensagem de erro caso o token esteja inválido ou vazio.

### Consulta de ponto
**POST** `/ponto/consulta`
- **Descrição**: Lista os registros de pontos de um usuario especifico em um periodo de tempo.
    - **Request Body**:
      ```json
      {
          "dtInicio": "2025-01-01",
          "dtFinal": "2025-01-10"
      }
      ```
        - **Response**:
            - **200 OK**: Retorna uma lista de pontos.
              ```json
              [
                  {
                      "id": 1,
                      "login": "João Silva",
                      "data": "2025-01-02",
                      "horasTrabalhadas": "1",
                      "entrada1": "08:02:37",
                      "saida1": "12:01:23",
                      "entrada2": "13:00:00",
                      "saida2": "17:01:05",
                      "entrada3": null,
                      "saida3": null,
                      "status": "PENDENTE"
                  },
                  {
                      "id": 2,
                      "login": "João Silva",
                      "data": "2025-01-03",
                      "horasTrabalhadas": "1",
                      "entrada1": "08:00:08",
                      "saida1": "12:17:59",
                      "entrada2": "13:01:10",
                      "saida2": "17:10:02",
                      "entrada3": null,
                      "saida3": null,
                      "status": "PENDENTE"
                  }
              ]
              ```
            - **404 Bad Request**: Retorna uma mensagem de erro quando não há registros no período.
            - **401 Unauthorized**: Retorna uma mensagem de erro quando o usuário não está autenticado ou login não existe.

### Alterar registro de ponto
**PUT** `/ponto/alterar`
- **Descrição**: Realiza a alteração do ponto do usuário.
    - **Request Body**:
      ```json
      {
          "idPonto": 1,
          "data": "2025-01-01",
          "entrada1": "08:00:00",
          "saida1": "12:00:00",
          "entrada2": "13:00:00",
          "saida2": "17:00:00",
          "entrada3": null,
          "saida3": null
      }
      ```
        - **Response**:
            - **200 OK**: Retorna mensagem de sucesso da ação.
            - - **404 Not Found**: Retorna uma mensagem de erro quando os campos estão inválidos.
            - **400 Bad Request**: Retorna uma mensagem de erro quando os campos estão inválidos.
            - **401 Unauthorized**: Retorna uma mensagem de erro quando o usuário não está autenticado.

### Listar pontos

**GET** `/ponto`

- **Descrição**: Retorna uma lista com todos os pontos registrados.

#### Response

- **200 OK**: Retorna lista com pontos.
    ```json
    [
        {
            "id": 1,
            "data": "2025-01-02",
            "horasTrabalhadas": "1",
            "entrada1": "08:02:37",
            "saida1": "12:01:23",
            "entrada2": "13:00:00",
            "saida2": "17:01:05",
            "entrada3": null,
            "saida3": null,
            "status": "PENDENTE"
        },
        {
            "id": 2,
            "data": "2025-01-03",
            "horasTrabalhadas": "1",
            "entrada1": "08:00:08",
            "saida1": "12:17:59",
            "entrada2": "13:01:10",
            "saida2": "17:10:02",
            "entrada3": null,
            "saida3": null,
            "status": "PENDENTE"
        }
    ]
    ```
- **404 Not Found**: Nenhum ponto encontrado.
- **500 Internal Server Error**: Um erro inesperado aconteceu.

### Lista os pedidos pendentes
**GET** `/pedidos/pendentes`

- **Descrição**: Retorna uma lista com os pedidos pendentes.

#### **Response**:
- **200 OK**: Retorna uma lista de pedidos pendentes.
  ```json
  [
      {
          "id": 1,
          "login": "João Silva",
          "dataPedido": "02/01/2025",
          "tipoPedido": "ALTERACAO",
          "status": "PENDENTE"
      },
      {
          "id": 2,
          "login": "Maria Oliveira",
          "dataPedido": "02/01/2025",
          "tipoPedido": "REGISTRO",
          "status": "PENDENTE"
      }
  ]
- **500 Internal Server Error**: Um erro inesperado aconteceu.

### Aprova um pedido de ponto
**POST** `/aprovar/{idPedido}`

- **Descrição**: Aprova um pedido de ponto com base no ID fornecido.

#### **Parâmetros**:
- **Path Parameter**:
    - `idPedido` (Integer): ID do pedido a ser aprovado.
- **Header**:
    - `Authorization` (String): Token de autenticação no formato `Bearer <seu-token-aqui>`.

#### **Response**:
- **200 OK**: Retorna uma mensagem confirmando que o pedido foi aprovado com sucesso.
  ```json
  {
      "message": "Ponto aprovado com sucesso."
  }

- **404 Not Found**: Nenhum ponto encontrado.
- **400 Bad Requesst**: O usuário passou algum dado inválido.
- **401 UNAUTHORIZED**: O usuário não está autenticado ou o token é inválido, ou o usuário não possui permissão para essa ação.
- **500 Internal Server Error**: Um erro inesperado aconteceu.

### Reprova um pedido de ponto
**POST** `/reprovar/{idPedido}`

- **Descrição**: Permite que um usuário administrador reprove um pedido de ponto com base no ID fornecido.

#### **Parâmetros**:
- **Path Parameter**:
    - `idPedido` (Integer): ID do pedido a ser reprovado.
- **Header**:
    - `Authorization` (String): Token de autenticação no formato `Bearer <seu-token-aqui>`.

#### **Response**:
- **200 OK**: Retorna uma mensagem confirmando que o pedido foi reprovado com sucesso.
  ```json
  {
      "message": "Ponto reprovado com sucesso."
  }
- **404 Not Found**: Nenhum ponto encontrado.
- **400 Bad Requesst**: O usuário passou algum dado inválido.
- **401 UNAUTHORIZED**: O usuário não está autenticado ou o token é inválido, ou o usuário não possui permissão para essa ação.
- **500 Internal Server Error**: Um erro inesperado aconteceu.

### Lista todos os pedidos
**GET** `/pedidos`

- **Descrição**: Retorna uma lista com todos os pedidos registrados no sistema.

#### **Response**:
- **200 OK**: Retorna uma lista com todos os pedidos.
  ```json
  [
      {
          "id": 1,
          "login": "João Silva",
          "dataPedido": "02/01/2025",
          "tipoPedido": "ALTERACAO",
          "status": "PENDENTE"
      },
      {
          "id": 2,
          "login": "Maria Oliveira",
          "dataPedido": "02/01/2025",
          "tipoPedido": "REGISTRO",
          "status": "PENDENTE"
      }
  ]
- **500 Internal Server Error**: Um erro inesperado aconteceu.

## Regras de Validação de Senha
- Não pode estar vazia ou em branco.
- Deve ter no mínimo 8 caracteres.
- Deve conter pelo menos:
    - Uma letra.
    - Um número.
    - Um caractere especial (ex.: `!@#$%^&*()`).

## Como Executar o Projeto
1. Certifique-se de ter o **Java 17** e o **Maven** instalados.
2. Clone o repositório:
   ```bash
   git clone <URL_DO_REPOSITORIO>
   ```
3. Navegue até o diretório do projeto:
   ```bash
   cd easytime
   ```
4. Execute o projeto:
   ```bash
   mvn spring-boot:run
   ```
5. O projeto estará disponível em: `http://localhost:8081`.

## Configuração do JWT

Certifique-se de configurar as propriedades do JWT em um arquivo `.env`:

```properties

JWT_SECRET=seuSegredoJWT

```

## Testes
Para executar os testes, utilize o comando:
```bash
mvn test
```