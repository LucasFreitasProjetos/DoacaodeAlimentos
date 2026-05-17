<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.doacaoalimentos.controller.DoadorController" %>
<%@ page import="br.com.doacaoalimentos.model.Doador" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Doadores - Sistema de Doações</title>
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
        <div class="page-header">
            <h1 class="page-title">👥 Gerenciar Doadores</h1>
            <a href="cadastro.jsp" class="btn btn-primary">➕ Novo Doador</a>
        </div>

        <%
            // Obter mensagens de sessão
            String mensagem = request.getParameter("mensagem");
            String tipo = request.getParameter("tipo");
            
            if (mensagem != null) {
                String classe = "alert-" + (tipo != null ? tipo : "info");
        %>
            <div class="alert <%=classe%>"><%=mensagem%></div>
        <%
            }
            
            DoadorController controller = new DoadorController();
            List<Doador> doadores = controller.listar();
        %>

        <% if (doadores != null && !doadores.isEmpty()) { %>
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>#ID</th>
                            <th>Nome</th>
                            <th>Email</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Doador doador : doadores) { %>
                            <tr>
                                <td>#<%=doador.getId()%></td>
                                <td><%=doador.getNome()%></td>
                                <td><%=doador.getEmail()%></td>
                                <td>
                                    <div class="action-buttons">
                                        <a href="editar.jsp?id=<%=doador.getId()%>" class="btn btn-secondary btn-small">✏️ Editar</a>
                                        <a href="javascript:void(0);" onclick="confirmarExclusao(<%=doador.getId()%>)" class="btn btn-danger btn-small">🗑️ Excluir</a>
                                    </div>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        <% } else { %>
            <div class="empty-state">
                <div class="empty-state-icon">📭</div>
                <p>Nenhum doador cadastrado ainda</p>
                <a href="cadastro.jsp" class="btn btn-primary" style="margin-top: 1rem;">Cadastrar Primeiro Doador</a>
            </div>
        <% } %>
    </div>

    <footer class="footer">
        <p>&copy; 2026 Sistema de Doação de Alimentos. Todos os direitos reservados.</p>
    </footer>

    <script>
        function confirmarExclusao(id) {
            if (confirm('Tem certeza que deseja excluir este doador?')) {
                window.location.href = 'excluir.jsp?id=' + id;
            }
        }
    </script>
</body>
</html>
