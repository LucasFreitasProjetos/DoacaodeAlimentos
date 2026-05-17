<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.doacaoalimentos.controller.InstituicaoController" %>
<%@ page import="br.com.doacaoalimentos.model.Instituicao" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Instituições - Sistema de Doações</title>
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
        <div class="page-header">
            <h1 class="page-title">🏛️ Gerenciar Instituições</h1>
            <a href="cadastro.jsp" class="btn btn-primary">➕ Nova Instituição</a>
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
            
            InstituicaoController controller = new InstituicaoController();
            List<Instituicao> instituicoes = controller.listar();
        %>

        <% if (instituicoes != null && !instituicoes.isEmpty()) { %>
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>#ID</th>
                            <th>Nome</th>
                            <th>Endereço</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Instituicao instituicao : instituicoes) { %>
                            <tr>
                                <td>#<%=instituicao.getId()%></td>
                                <td><%=instituicao.getNome()%></td>
                                <td><%=instituicao.getEndereco()%></td>
                                <td>
                                    <div class="action-buttons">
                                        <a href="editar.jsp?id=<%=instituicao.getId()%>" class="btn btn-secondary btn-small">✏️ Editar</a>
                                        <a href="javascript:void(0);" onclick="confirmarExclusao(<%=instituicao.getId()%>)" class="btn btn-danger btn-small">🗑️ Excluir</a>
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
                <p>Nenhuma instituição cadastrada ainda</p>
                <a href="cadastro.jsp" class="btn btn-primary" style="margin-top: 1rem;">Cadastrar Primeira Instituição</a>
            </div>
        <% } %>
    </div>

    <footer class="footer">
        <p>&copy; 2026 Sistema de Doação de Alimentos. Todos os direitos reservados.</p>
    </footer>

    <script>
        function confirmarExclusao(id) {
            if (confirm('Tem certeza que deseja excluir esta instituição?')) {
                window.location.href = 'excluir.jsp?id=' + id;
            }
        }
    </script>
</body>
</html>
