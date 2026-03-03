### 📚 Mercado Livro — Backend

Projeto backend desenvolvido com Spring Boot + Kotlin para fins de estudo.
A aplicação servirá como base para uma API de gerenciamento de livros, usuários e autenticação.

### 🚀 Tecnologias utilizadas

- Java 17
- Kotlin 1.9.25
- Spring Boot 3.4.5
- MySQL & Flyway (Migrations)
- JSON Web Token (JJWT)

### 🔐 Configuração de Segurança (Obrigatório)

Para que a autenticação funcione, você deve configurar uma chave mestra no seu `application.yml` ou `application.properties`.

1. Localize a propriedade `jwt.secret`.
2. Insira uma frase com pelo menos <b>64 caracteres</b> (512 bits) para atender aos requisitos do algoritmo HS512.

### 🛠️ Como criar seu primeiro Usuário Admin

Como a API nasce protegida, siga estes passos para ter acesso total:

1. Criar o usuário via Postman
  - Faça um POST para http://localhost:8080/customers com o corpo:

````bash
    {
      "name": "Admin Teste",
      "email": "admin@email.com",
      "password": "123"
    }
````

2. Promover a ADMIN no MySQL
- Abra seu terminal SQL ou (DBeaver/Workbanch) e execute os comandos abaixo para dar permissões totais ao usuário:

````bash 

USE mercadolivro;

-- 1. Verifique o ID do usuário criado
SELECT id, name, email FROM customer;

-- 2. Insira a role ADMIN para esse ID (exemplo com ID 1)
INSERT INTO customer_roles (customer_id, role) VALUES (1, 'ADMIN');
````
3. Obter o Token de Acesso
  -  Faça um POST para http://localhost:8080/login com o e-mail e senha criados.
   O Token JWT estará no Header da resposta, no campo Authorization.


### 🔍 Queries úteis para verificação

Use estas consultas para monitorar seu banco de dados:
- Verificar todos os usuários e suas roles:

````bash 
SELECT c.id, c.name, c.email, r.role 
FROM customer c 
LEFT JOIN customer_roles r ON c.id = r.customer_id;
````

- Verificar livros cadastrados:
````bash
SELECT * FROM book;
````

### 📖 Documentação da API:

Com o projeto rodando, acesse:
- `http://localhost:8080/swagger-ui/index.html` para visualizar e testar todos os endpoints interativamente.


