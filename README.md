 Doacao de Alimentos

Aplicação web simples para gerenciar doações de alimentos — projeto didático.

**Visão geral**
- Tipo: Aplicação Java web (WAR) usando Maven
- Artefato build: [pom.xml](pom.xml)

**Pré-requisitos**
- Java JDK 17+ (você usa JDK 21)
- Maven 3.6+
- Tomcat 9/10/11 (instalado localmente)
- Banco de dados MySQL (configuração em [src/main/java/br/com/doacaoalimentos/dao/Conexao.java](src/main/java/br/com/doacaoalimentos/dao/Conexao.java))

**Como rodar (modo rápido)**
1. Na raiz do projeto:
```bash
cd "c:/Users/zezao/OneDrive/Área de Trabalho/doacaoalimentos"
```
2. Gerar o WAR (ignorar testes se necessário):
```bash
mvn clean package -DskipTests
```
3. Copiar o WAR gerado para a pasta `webapps` do Tomcat (exemplo local):
```bash
copy target\doacaoalimentos.war C:\tomcat\apache-tomcat-10.1.52\webapps\
```
4. Iniciar (ou reiniciar) o Tomcat:
```bash
C:\tomcat\apache-tomcat-10.1.52\bin\startup.bat
# ou, para reiniciar
C:\tomcat\apache-tomcat-10.1.52\bin\shutdown.bat
C:\tomcat\apache-tomcat-10.1.52\bin\startup.bat
```
5. Acessar no navegador:
```
http://localhost:8080/doacaoalimentos   # ou outra porta configurada (ex.: 8081)
```

Observação: se o Tomcat usar porta diferente (ex.: 8081), ajuste a URL.

**Executando em ambiente de desenvolvimento (IDE)**
- Importe o projeto no IntelliJ/ Eclipse como projeto Maven.
- Configure a conexão com o banco em [src/main/java/br/com/doacaoalimentos/dao/Conexao.java](src/main/java/br/com/doacaoalimentos/dao/Conexao.java).
- Rode como um módulo web no Tomcat integrado da IDE ou gere o WAR e faça deploy manual.

**Testes**
- Executar testes unitários:
```bash
mvn test
```

**Estrutura principal**
- [src/main/java/br/com/doacaoalimentos](src/main/java/br/com/doacaoalimentos) — código Java (model, dao, controller, view)
- [src/main/webapp](src/main/webapp) — JSPs e configuração web
- [src/test/java](src/test/java) — testes

**Problemas comuns**
- Se der erro de conexão ao MySQL, verifique credenciais e URL em [src/main/java/br/com/doacaoalimentos/dao/Conexao.java](src/main/java/br/com/doacaoalimentos/dao/Conexao.java).
- Se a aplicação não subir, confira os logs do Tomcat em `C:\tomcat\apache-tomcat-10.1.52\logs`.

**Contribuição**
- Abra uma issue ou envie pull request com melhorias.

**Licença**
- Projeto educacional — verificar com o autor para uso/redistribuição.

