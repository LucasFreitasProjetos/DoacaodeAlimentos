# Doação de Alimentos

Aplicação Java web com API REST para gerenciar doações de alimentos — projeto didático com interface web e endpoints JSON.

## Visão geral
- **Tipo**: Aplicação Java WAR com Servlets + API REST JSON
- **Build**: Maven
- **Framework**: Servlet 3.1 + JSP
- **API**: REST com JSON (sem framework externo)
- **Front-end**: HTML5 + JavaScript + CSS3

## Funcionalidades
- ✅ API REST com 3 recursos principais: doadores, instituições e doações
- ✅ Interface web simples para CRUD via navegador
- ✅ Camada DAO com MySQL
- ✅ Validação básica de entrada
- ✅ Mensagens de erro/sucesso no front-end

## Pré-requisitos
- Java JDK 21 (recomendado) ou JDK 17+
- Maven 3.6+
- Tomcat 8.5+ (exemplo: `C:\xampp\tomcat`)
- MySQL 5.7+ com banco `banco` criado
- Navegador moderno (Chrome, Firefox, Edge, Safari)

## Configuração do Banco de Dados

Crie o banco `banco` no MySQL com as tabelas:

```sql
CREATE DATABASE banco CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE banco;

CREATE TABLE doadores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

CREATE TABLE instituicoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    endereco VARCHAR(255) NOT NULL
);

CREATE TABLE doacoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    doador_id INT NOT NULL,
    instituicao_id INT NOT NULL,
    descricao TEXT NOT NULL,
    data_doacao DATE NOT NULL,
    FOREIGN KEY (doador_id) REFERENCES doadores(id),
    FOREIGN KEY (instituicao_id) REFERENCES instituicoes(id)
);

-- Tabela de usuários para autenticação JWT (NOVO)
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Usuários de teste
INSERT INTO usuarios (email, senha) VALUES 
('teste@exemplo.com', '123456'),
('admin@exemplo.com', 'senha123');
```

## Como Rodar

### 1. Build do projeto
```bash
cd "c:\Users\zezao\OneDrive\Área de Trabalho\doacaoalimentos"
mvn clean package -DskipTests
```

### 2. Deploy no Tomcat (XAMPP exemplo)
```bash
# Copiar WAR para Tomcat
copy target\doacaoalimentos.war C:\xampp\tomcat\webapps\

# (Opcional) Remover versão explodida antiga
rmdir /s /q C:\xampp\tomcat\webapps\doacaoalimentos
```

### 3. Iniciar Tomcat
```bash
# Se Tomcat está rodando, parar
C:\xampp\tomcat\bin\shutdown.bat

# Iniciar
C:\xampp\tomcat\bin\startup.bat
```

### 4. Acessar aplicação
- **Home**: `http://localhost:8080/doacaoalimentos`
- **Front-end Simples**: `http://localhost:8080/doacaoalimentos/app.html`
- **API REST**: `http://localhost:8080/doacaoalimentos/api/doador` (ex.)

## Endpoints da API REST

### Autenticação JWT (NOVO - v1.1)
- `POST /api/login` — autenticar e obter token
  - Body: `{"email":"teste@exemplo.com", "senha":"123456"}`
  - Response: `{"token":"eyJ...", "email":"teste@exemplo.com"}`
- Todas as outras rotas requerem header: `Authorization: Bearer SEU_TOKEN`
- Se token não for fornecido ou inválido, retorna: `{"erro":"Acesso não autorizado"}`

### Doadores
- `GET /api/doador` — listar todos
- `GET /api/doador/{id}` — buscar por ID
- `POST /api/doador` — criar (body: `{"nome":"...", "email":"..."}`)
- `PUT /api/doador/{id}` — atualizar
- `DELETE /api/doador/{id}` — excluir

### Instituições
- `GET /api/instituicao` — listar todos
- `GET /api/instituicao/{id}` — buscar por ID
- `POST /api/instituicao` — criar (body: `{"nome":"...", "endereco":"..."}`)
- `PUT /api/instituicao/{id}` — atualizar
- `DELETE /api/instituicao/{id}` — excluir

### Doações
- `GET /api/doacao` — listar todos
- `GET /api/doacao/{id}` — buscar por ID
- `POST /api/doacao` — criar (body: `{"doadorId":1, "instituicaoId":1, "descricao":"...", "dataDoacao":"2026-06-08"}`)
- `PUT /api/doacao/{id}` — atualizar
- `DELETE /api/doacao/{id}` — excluir

## Como Testar

### Via cURL (API REST com JWT)
```bash
# 1. Fazer login e obter token
TOKEN=$(curl -s -X POST "http://localhost:8080/doacaoalimentos/api/login" \
  -H "Content-Type: application/json" \
  -d "{\"email\":\"teste@exemplo.com\", \"senha\":\"123456\"}" | grep -o '"token":"[^"]*' | cut -d'"' -f4)

echo "Token obtido: $TOKEN"

# 2. Usar o token para listar doadores
curl -X GET "http://localhost:8080/doacaoalimentos/api/doador" \
  -H "Authorization: Bearer $TOKEN"

# 3. Criar doador com token
curl -X POST "http://localhost:8080/doacaoalimentos/api/doador" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d "{\"nome\":\"João\", \"email\":\"joao@exemplo.com\"}"

# 4. Tentar acesso sem token (será negado)
curl -X GET "http://localhost:8080/doacaoalimentos/api/doador"
# Resposta: {"erro": "Acesso não autorizado - Token não fornecido"}
```

### Via Navegador (Front-end Web)
1. Acesse: `http://localhost:8080/doacaoalimentos/app.html`
2. Clique em "Listar Doadores", "Listar Instituições", "Listar Doações"
3. Preencha os formulários e clique em "Cadastrar"

### Via Postman ou Insomnia
1. **POST /api/login** (sem autenticação)
   - URL: `http://localhost:8080/doacaoalimentos/api/login`
   - Body: `{"email":"teste@exemplo.com", "senha":"123456"}`
   - Copia o token da resposta

2. **GET /api/doador** (com token)
   - URL: `http://localhost:8080/doacaoalimentos/api/doador`
   - Header: `Authorization: Bearer SEU_TOKEN_AQUI`
   - Clique em Send

## Estrutura do Projeto

```
doacaoalimentos/
├── pom.xml                                   # Configuração Maven
├── README.md                                 # Este arquivo
├── schema_jwt.sql                            # Script SQL para tabela de usuários
├── src/
│   ├── main/
│   │   ├── java/br/com/doacaoalimentos/
│   │   │   ├── Main.java                    # Entrada principal (CLI)
│   │   │   ├── api/
│   │   │   │   ├── JsonUtil.java            # Utilitário para JSON (sem libs)
│   │   │   │   ├── LoginApiServlet.java     # REST para login e geração de JWT (NOVO)
│   │   │   │   ├── DoadorApiServlet.java    # REST para doadores
│   │   │   │   ├── InstituicaoApiServlet.java # REST para instituições
│   │   │   │   └── DoacaoApiServlet.java    # REST para doações
│   │   │   ├── security/
│   │   │   │   ├── JwtUtil.java             # Utilitário para JWT (NOVO)
│   │   │   │   └── JwtFilter.java           # Filtro para validar tokens (NOVO)
│   │   │   ├── controller/
│   │   │   │   ├── DoadorController.java
│   │   │   │   ├── InstituicaoController.java
│   │   │   │   └── DoacaoController.java
│   │   │   ├── dao/
│   │   │   │   ├── Conexao.java             # Gerenciador de conexão MySQL
│   │   │   │   ├── DoadorDAO.java
│   │   │   │   ├── InstituicaoDAO.java
│   │   │   │   ├── DoacaoDao.java
│   │   │   │   └── UsuarioDAO.java          # DAO para usuários (NOVO)
│   │   │   ├── model/
│   │   │   │   ├── Doador.java
│   │   │   │   ├── Instituicao.java
│   │   │   │   ├── Doacao.java
│   │   │   │   └── Usuario.java             # Modelo de usuário (NOVO)
│   │   │   └── view/
│   │   │       └── Menu.java                # Menu CLI
│   │   └── webapp/
│   │       ├── index.html                   # Home page (HTML)
│   │       ├── app.html                     # Front-end simples (API Dashboard)
│   │       ├── app.js                       # JavaScript para API
│   │       ├── css/
│   │       │   └── estilo.css
│   │       ├── js/
│   │       │   └── api.js                       # Funções comuns da API
│   │       ├── doador/                      # Páginas HTML de Doadores
│   │       ├── instituicao/                 # Páginas HTML de Instituições
│   │       ├── doacao/                      # Páginas HTML de Doações
│   │       └── WEB-INF/
│   │           └── web.xml                  # Mapeamento de servlets e filtros
│   └── test/
│       └── java/                            # Testes (alguns desatualizados)
└── target/
    └── doacaoalimentos.war                  # Artefato gerado (deploy)
```

## Atualizações Recentes

### Autenticação JWT (v1.1 - NOVO)
- Adicionada camada de segurança com JWT (JSON Web Tokens)
- Endpoint `/api/login` para gerar tokens
- Todas as rotas `/api/*` (exceto login) requerem token válido
- Token deve ser enviado no header: `Authorization: Bearer TOKEN`
- Classe `JwtUtil` para geração e validação de tokens
- Filtro `JwtFilter` que valida token em todas as requisições protegidas
- Tabela `usuarios` no banco de dados para armazenar credenciais

### API REST Adicionada (v1.0)
- Criada camada REST em `src/main/java/br/com/doacaoalimentos/api/`
- Servlets mapeados em `web.xml`:
  - `/api/doador/*`
  - `/api/instituicao/*`
  - `/api/doacao/*`
- JSON serializado manualmente com `JsonUtil.java` (sem Jackson/Gson)
- CORS habilitado nos endpoints

### Front-end Web Simples (v1.0)
- Novo arquivo `src/main/webapp/app.html`
- JavaScript em `src/main/webapp/app.js`
- Funcionalidades:
  - Listagem de doadores, instituições e doações em tabelas
  - Formulários para criar registros
  - Selects auto-populados para doações (evita IDs manuais)
  - Mensagens de validação

## Configuração

### Conexão com Banco
Editar [src/main/java/br/com/doacaoalimentos/dao/Conexao.java](src/main/java/br/com/doacaoalimentos/dao/Conexao.java):
```java
private static final String URL = "jdbc:mysql://localhost:3306/banco?useSSL=false";
private static final String USER = "root";
private static final String PASSWORD = "";
```

### Dependências Maven
Principais dependências:
- `javax.servlet-api:3.1.0` (Servlet API)
- `mysql-connector-java:8.0.33` (Driver MySQL)
- `junit:4.11` (Testes)

## Executando em IDE

### IntelliJ IDEA
1. File → Open → Selecionar pasta do projeto
2. Configure JDK 21 em Project Settings
3. Configure Tomcat em Run → Edit Configurations
4. Rode com Shift+F10

### Eclipse
1. File → Import → Maven → Existing Maven Projects
2. Configure Tomcat em Preferences → Server
3. Right-click no projeto → Run As → Run on Server

## Testes

### Unitários (parcialmente implementados)
```bash
mvn test
```
Nota: Alguns testes podem falhar por desatualização do modelo.

## Troubleshooting

### Erro 404 no navegador
- Verifique se o Tomcat está rodando: `netstat -ano | findstr :8080`
- Confirme a URL: `http://localhost:8080/doacaoalimentos/app.html`
- Redeploy o WAR se necessário

### Erro de conexão com banco
- Verifique credenciais em `Conexao.java`
- Confirme que MySQL está rodando
- Verifique que a tabela `banco` existe: `mysql -u root -e "USE banco; SHOW TABLES;"`

### Tomcat não inicia
- Veja logs: `C:\xampp\tomcat\logs\catalina.out`
- Verifique porta 8080 não está em uso: `netstat -ano | findstr :8080`
- Limpe temp: `rmdir /s /q C:\xampp\tomcat\temp && mkdir C:\xampp\tomcat\temp`

### Testes falham na compilação
- Build sem testes: `mvn package -DskipTests`
- Ou: `mvn package -Dmaven.test.skip=true`

### API retorna 404
- Confirme que o contexto está correto: `/doacaoalimentos`
- Verifique o `web.xml` tem os mapeamentos corretos
- Redeploy o WAR

## Contribuição
- Abra uma issue para relatar bugs
- Envie pull request com melhorias

## Licença
Projeto educacional — uso livre para fins de aprendizado.

## Autor
Desenvolvido como projeto didático em 2026.
