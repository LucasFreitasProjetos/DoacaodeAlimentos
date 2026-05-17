<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.doacaoalimentos.controller.DoadorController" %>
<%@ page import="br.com.doacaoalimentos.model.Doador" %>
<%@ page import="java.net.URLEncoder" %>
<%! private String encode(String value) { try { return URLEncoder.encode(value, "UTF-8"); } catch (Exception e) { return value; } } %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Doador - Sistema de Doações</title>
    <link rel="stylesheet" href="../css/estilo.css">
</head>
<body>
    <nav class="navbar">
        <div class="container">
            <div class="logo">🍎 Doação de Alimentos</div>
            <ul class="nav-menu">
                <li><a href="../index.jsp">Home</a></li>
                <li><a href="listar.jsp" class="active">Doadores</a></li>
                <li><a href="../instituicao/listar.jsp">Instituições</a></li>
                <li><a href="../doacao/listar.jsp">Doações</a></li>
            </ul>
        </div>
    </nav>

    <div class="container">
        <div class="form-container">
            <h2 class="form-title">✏️ Editar Doador</h2>

            <%
                int id = 0;
                Doador doador = null;
                String idParam = request.getParameter("id");
                
                try {
                    if (idParam != null && !idParam.isEmpty()) {
                        id = Integer.parseInt(idParam);
                        DoadorController controller = new DoadorController();
                        doador = controller.buscarPorId(id);
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect("listar.jsp?mensagem=" + encode("ID inválido") + "&tipo=danger");
                    return;
                }
                
                if (doador == null) {
                    response.sendRedirect("listar.jsp?mensagem=" + encode("Doador não encontrado") + "&tipo=danger");
                    return;
                }
                
                // Processar formulário se enviado
                if (request.getMethod().equals("POST")) {
                    String nome = request.getParameter("nome");
                    String email = request.getParameter("email");
                    
                    if (nome != null && !nome.isEmpty() && email != null && !email.isEmpty()) {
                        doador.setNome(nome);
                        doador.setEmail(email);
                        
                        DoadorController controller = new DoadorController();
                        if (controller.atualizar(doador)) {
                            response.sendRedirect("listar.jsp?mensagem=" + encode("Doador atualizado com sucesso") + "&tipo=success");
                            return;
                        } else {
            %>
                            <div class="alert alert-danger">Erro ao atualizar doador. Tente novamente.</div>
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
                    <input type="text" id="nome" name="nome" required value="<%=doador.getNome()%>" placeholder="Digite o nome completo">
                </div>

                <div class="form-group">
                    <label for="email">Email *</label>
                    <input type="email" id="email" name="email" required value="<%=doador.getEmail()%>" placeholder="Digite o email">
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">💾 Salvar Alterações</button>
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
