# Easytime - Sistema de Marcação de Ponto (BFF)

## Descrição do Projeto
Easytime é um sistema de marcação de ponto desenvolvido com uma arquitetura **BFF (Back For Front)**. Este projeto é responsável por gerenciar a autenticação de usuários e fornecer um **token JWT** para acesso seguro às funcionalidades do sistema.

O projeto foi desenvolvido em **Java** utilizando o framework **Spring Boot** e implementa autenticação e segurança com **Spring Security**.

## Funcionalidades
- **Autenticação de Usuário**:
    - Rota de login que recebe as credenciais (usuário e senha).
    - Validação de senha com regras específicas.
- **CRUD de Usuários**:
    - Rota de cadastro de um novo usuário, deve ser feito por um usuario ja cadastrado.
    - Rota de listagem de todos os usuários cadastrados.
    - Rota que retorna um usuário de acordo com o ID informado.
    - Rota para excluir um usuário de acordo com o ID informado.
    - Validação de campos com regras específicas.
- **Troca de senha**:
    - Rota para enviar um código de validação para um email válido.
    - Rota para validar o código enviado e permitir a troca de senha.

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

### Health
**POST** `/health`

- **Descrição**: Apenas para verificar se o projeto está rodando.
- **Response**:
    - **200 OK**: Retorna uma mensagem de boas vindas
      ```json
      "Bem-vindo ao EasyTime!"
      ```

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