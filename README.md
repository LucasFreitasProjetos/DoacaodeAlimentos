# DoacaodeAlimentos

Sistema de doações de alimentos desenvolvido para a disciplina de Aplicações para a Internet.

## Estrutura de pastas

- `src/main/java/br/com/doacaoalimentos` - código-fonte Java principal do sistema
- `src/main/webapp` - arquivos web, como `index.jsp` e `WEB-INF/web.xml`
- `src/test/java` - testes unitários
- `target/` - diretório de build gerado pelo Maven (não deve ser enviado ao Git)

## Instruções de execução

1. Abra o terminal na pasta do projeto:
   ```bash
   cd "c:\Users\zezao\OneDrive\Área de Trabalho\doacaoalimentos"
   ```
2. Compile o projeto com Maven:
   ```bash
   mvn clean compile
   ```
3. Execute o aplicativo Java:
   ```bash
   mvn exec:java -Dexec.mainClass="br.com.doacaoalimentos.Main"
   ```

> Se o Maven não estiver configurado, instale-o antes ou use uma IDE Java que suporte projetos Maven.
