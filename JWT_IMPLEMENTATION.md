# Autenticação JWT - Implementação Completa

## ✅ O que foi implementado

### 1. Dependências Maven
- JJWT 0.11.5 (JSON Web Token)
- Adicionadas ao `pom.xml`:
  - `jjwt-api`
  - `jjwt-impl`
  - `jjwt-jackson`

### 2. Segurança (Novo pacote `security`)

**JwtUtil.java** - Utilitário para gerar e validar tokens
- `generateToken(email)` - Cria token JWT com validade de 24 horas
- `validateToken(token)` - Valida e extrai email do token
- `getEmailFromToken(token)` - Alias para validateToken

**JwtFilter.java** - Filtro para validar tokens em todas requisições
- Aplica em: `/api/*` (exceto `/api/login`)
- Extrai token do header `Authorization: Bearer TOKEN`
- Retorna 401 se token inválido ou ausente

### 3. Modelo de Usuário

**Usuario.java** (Nova classe em `model`)
- Propriedades: id, email, senha
- Getters e setters padrão

### 4. Acesso a Dados

**UsuarioDAO.java** (Nova classe em `dao`)
- `autenticar(email, senha)` - Valida credenciais
- `buscarPorEmail(email)` - Busca usuário
- `inserir(usuario)` - Cria novo usuário

### 5. Endpoint de Login

**LoginApiServlet.java** (Nova classe em `api`)
- `POST /api/login`
- Body: `{"email":"teste@exemplo.com", "senha":"123456"}`
- Response: `{"token":"eyJ...", "email":"teste@exemplo.com"}`
- Retorna 401 se credenciais inválidas

### 6. Banco de Dados

**Tabela `usuarios`** (criada em MySQL)
```sql
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**Usuários de teste inseridos:**
- Email: `teste@exemplo.com`, Senha: `123456`
- Email: `admin@exemplo.com`, Senha: `senha123`

## 📋 Fluxo de Uso

### 1. Fazer Login
```bash
curl -X POST "http://localhost:8080/doacaoalimentos/api/login" \
  -H "Content-Type: application/json" \
  -d "{\"email\":\"teste@exemplo.com\", \"senha\":\"123456\"}"
```
Resposta:
```json
{"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", "email": "teste@exemplo.com"}
```

### 2. Usar Token para Acessar API
```bash
curl -X GET "http://localhost:8080/doacaoalimentos/api/doador" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

### 3. Tentar Sem Token (Será Rejeitado)
```bash
curl -X GET "http://localhost:8080/doacaoalimentos/api/doador"
# Resposta: {"erro": "Acesso não autorizado - Token não fornecido"}
```

## 🔐 Segurança

- Token válido por **24 horas**
- Chave secreta: `minha_chave_secreta_super_longa_para_jwt_autenticacao_2026`
- Algoritmo: **HS256** (HMAC SHA-256)
- Token armazenado no header `Authorization: Bearer TOKEN`

## 📦 Build e Deploy

```bash
# Build
mvn clean package -Dmaven.test.skip=true

# Deploy
cp target/doacaoalimentos.war C:\xampp\tomcat\webapps\
```

## ⚠️ Próximas Melhorias

1. **Hash de Senha**: Usar BCrypt ou Argon2 em produção
2. **Refresh Token**: Implementar renovação de token
3. **Rate Limiting**: Limitar tentativas de login
4. **Auditoria**: Log de acessos e tentativas falhadas
5. **HTTPS**: Usar SSL/TLS em produção

## 📁 Arquivos Criados/Modificados

- ✅ `pom.xml` - Adicionadas dependências JWT
- ✅ `src/main/java/br/com/doacaoalimentos/security/JwtUtil.java` - NOVO
- ✅ `src/main/java/br/com/doacaoalimentos/security/JwtFilter.java` - NOVO
- ✅ `src/main/java/br/com/doacaoalimentos/model/Usuario.java` - NOVO
- ✅ `src/main/java/br/com/doacaoalimentos/dao/UsuarioDAO.java` - NOVO
- ✅ `src/main/java/br/com/doacaoalimentos/api/LoginApiServlet.java` - NOVO
- ✅ `README.md` - Atualizado com documentação JWT
- ✅ `schema_jwt.sql` - Script SQL para criação de tabela
