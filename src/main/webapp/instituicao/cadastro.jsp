<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.doacaoalimentos.controller.InstituicaoController" %>
<%@ page import="br.com.doacaoalimentos.model.Instituicao" %>
<%@ page import="java.net.URLEncoder" %>
<%! private String encode(String value) { try { return URLEncoder.encode(value, "UTF-8"); } catch (Exception e) { return value; } } %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nova Instituição - Sistema de Doações</title>
    <link rel="stylesheet" href="../css/estilo.css">
</head>
<body>
    <nav class="navbar">
        <div class="container">
            <div class="logo">🍎 Doação de Alimentos</div>
            <ul class="nav-menu">
                <li><a href="../index.jsp">Home</a></li>
                <li><a href="../doador/listar.jsp">Doadores</a></li>
                <li><a href="listar.jsp" class="active">Instituições</a></li>
                <li><a href="../doacao/listar.jsp">Doações</a></li>
            </ul>
        </div>
    </nav>

    <div class="container">
        <div class="form-container">
            <h2 class="form-title">➕ Nova Instituição</h2>

            <%
                // Processar formulário se enviado
                if (request.getMethod().equals("POST")) {
                    String nome = request.getParameter("nome");
                    String endereco = request.getParameter("endereco");
                    
                    if (nome != null && !nome.isEmpty() && endereco != null && !endereco.isEmpty()) {
                        Instituicao instituicao = new Instituicao();
                        instituicao.setNome(nome);
                        instituicao.setEndereco(endereco);
                        
                        InstituicaoController controller = new InstituicaoController();
                        if (controller.cadastrar(instituicao)) {
                            response.sendRedirect("listar.jsp?mensagem=" + encode("Instituição cadastrada com sucesso") + "&tipo=success");
                            return;
                        } else {
            %>
                            <div class="alert alert-danger">Erro ao cadastrar instituição. Tente novamente.</div>
            <%
                        }
                    } else {
            %>
                        <div class="alert alert-warning">Por favor, preencha todos os campos.</div>
            <%
                    }
                }
            %>

            <form method="POST">
                <div class="form-group">
                    <label for="nome">Nome *</label>
                    <input type="text" id="nome" name="nome" required placeholder="Digite o nome da instituição">
                </div>

                <div class="form-group">
                    <label for="endereco">Endereço *</label>
                    <input type="text" id="endereco" name="endereco" required placeholder="Digite o endereço">
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">💾 Salvar</button>
                    <a href="listar.jsp" class="btn btn-secondary">❌ Cancelar</a>
                </div>
            </form>
        </div>
    </div>

    <footer class="footer">
        <p>&copy; 2026 Sistema de Doação de Alimentos. Todos os direitos reservados.</p>
    </footer>
</body>
</html>
