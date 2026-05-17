<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.doacaoalimentos.controller.DoacaoController" %>
<%@ page import="br.com.doacaoalimentos.controller.DoadorController" %>
<%@ page import="br.com.doacaoalimentos.controller.InstituicaoController" %>
<%@ page import="br.com.doacaoalimentos.model.Doacao" %>
<%@ page import="br.com.doacaoalimentos.model.Doador" %>
<%@ page import="br.com.doacaoalimentos.model.Instituicao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Doações - Sistema de Doações</title>
    <link rel="stylesheet" href="../css/estilo.css">
</head>
<body>
    <nav class="navbar">
        <div class="container">
            <div class="logo">🍎 Doação de Alimentos</div>
            <ul class="nav-menu">
                <li><a href="../index.jsp">Home</a></li>
                <li><a href="../doador/listar.jsp">Doadores</a></li>
                <li><a href="../instituicao/listar.jsp">Instituições</a></li>
                <li><a href="listar.jsp" class="active">Doações</a></li>
            </ul>
        </div>
    </nav>

    <div class="container">
        <div class="page-header">
            <h1 class="page-title">📦 Gerenciar Doações</h1>
            <a href="cadastro.jsp" class="btn btn-primary">➕ Nova Doação</a>
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
            
            DoacaoController doacaoController = new DoacaoController();
            DoadorController doadorController = new DoadorController();
            InstituicaoController instituicaoController = new InstituicaoController();
            
            List<Doacao> doacoes = doacaoController.listar();
            List<Doador> doadores = doadorController.listar();
            List<Instituicao> instituicoes = instituicaoController.listar();
            
            // Criar mapas para referência rápida
            Map<Integer, Doador> doadorMap = new HashMap<>();
            if (doadores != null) {
                for (Doador d : doadores) {
                    doadorMap.put(d.getId(), d);
                }
            }
            
            Map<Integer, Instituicao> instituicaoMap = new HashMap<>();
            if (instituicoes != null) {
                for (Instituicao i : instituicoes) {
                    instituicaoMap.put(i.getId(), i);
                }
            }
        %>

        <% if (doacoes != null && !doacoes.isEmpty()) { %>
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>#ID</th>
                            <th>Doador</th>
                            <th>Instituição</th>
                            <th>Descrição</th>
                            <th>Data</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Doacao doacao : doacoes) { 
                            Doador doador = doadorMap.get(doacao.getDoadorId());
                            Instituicao instituicao = instituicaoMap.get(doacao.getInstituicaoId());
                        %>
                            <tr>
                                <td>#<%=doacao.getId()%></td>
                                <td><%=doador != null ? doador.getNome() : "N/A"%></td>
                                <td><%=instituicao != null ? instituicao.getNome() : "N/A"%></td>
                                <td><%=doacao.getDescricao()%></td>
                                <td><%=doacao.getDataDoacao()%></td>
                                <td>
                                    <div class="action-buttons">
                                        <a href="editar.jsp?id=<%=doacao.getId()%>" class="btn btn-secondary btn-small">✏️ Editar</a>
                                        <a href="javascript:void(0);" onclick="confirmarExclusao(<%=doacao.getId()%>)" class="btn btn-danger btn-small">🗑️ Excluir</a>
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
                <p>Nenhuma doação cadastrada ainda</p>
                <a href="cadastro.jsp" class="btn btn-primary" style="margin-top: 1rem;">Registrar Primeira Doação</a>
            </div>
        <% } %>
    </div>

    <footer class="footer">
        <p>&copy; 2026 Sistema de Doação de Alimentos. Todos os direitos reservados.</p>
    </footer>

    <script>
        function confirmarExclusao(id) {
            if (confirm('Tem certeza que deseja excluir esta doação?')) {
                window.location.href = 'excluir.jsp?id=' + id;
            }
        }
    </script>
</body>
</html>
