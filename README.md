# User Auth API

API REST com autenticação via JWT desenvolvida com Spring Boot.

## Tecnologias
- Java 25 + Spring Boot 3.4.3
- Spring Security
- JWT (jjwt 0.11.5)
- Spring Data JPA + H2
- BCrypt

## Endpoints

| Método | Rota | Acesso | Descrição |
|--------|------|--------|-----------|
| POST | /auth/register | Público | Cadastrar usuário |
| POST | /auth/login | Público | Login, retorna token JWT |
| GET | /users/me | Autenticado | Dados do usuário logado |
| GET | /users | ADMIN | Listar todos os usuários |
| PUT | /users/{id} | Autenticado | Atualizar usuário |
| DELETE | /users/{id} | ADMIN | Deletar usuário |

## Como usar
```bash
git clone https://github.com/higoronevesdev/user-auth-api.git
cd user-auth-api
./mvnw spring-boot:run
```

## Autor
Higor Oliveira — [GitHub](https://github.com/higoronevesdev)
