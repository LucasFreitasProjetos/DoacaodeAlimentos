<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.doacaoalimentos.controller.DoacaoController" %>
<%@ page import="br.com.doacaoalimentos.controller.DoadorController" %>
<%@ page import="br.com.doacaoalimentos.controller.InstituicaoController" %>
<%@ page import="br.com.doacaoalimentos.model.Doacao" %>
<%@ page import="br.com.doacaoalimentos.model.Doador" %>
<%@ page import="br.com.doacaoalimentos.model.Instituicao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.net.URLEncoder" %>
<%! private String encode(String value) { try { return URLEncoder.encode(value, "UTF-8"); } catch (Exception e) { return value; } } %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Doação - Sistema de Doações</title>
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
        <div class="form-container">
            <h2 class="form-title">✏️ Editar Doação</h2>

            <%
                int id = 0;
                Doacao doacao = null;
                String idParam = request.getParameter("id");
                
                try {
                    if (idParam != null && !idParam.isEmpty()) {
                        id = Integer.parseInt(idParam);
                        DoacaoController controller = new DoacaoController();
                        doacao = controller.buscarPorId(id);
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect("listar.jsp?mensagem=" + encode("ID inválido") + "&tipo=danger");
                    return;
                }
                
                if (doacao == null) {
                    response.sendRedirect("listar.jsp?mensagem=" + encode("Doação não encontrada") + "&tipo=danger");
                    return;
                }
                
                // Processar formulário se enviado
                if (request.getMethod().equals("POST")) {
                    try {
                        int doadorId = Integer.parseInt(request.getParameter("doadorId"));
                        int instituicaoId = Integer.parseInt(request.getParameter("instituicaoId"));
                        String descricao = request.getParameter("descricao");
                        String dataStr = request.getParameter("dataDoacao");
                        
                        if (doadorId > 0 && instituicaoId > 0 && descricao != null && !descricao.isEmpty() && dataStr != null && !dataStr.isEmpty()) {
                            doacao.setDoadorId(doadorId);
                            doacao.setInstituicaoId(instituicaoId);
                            doacao.setDescricao(descricao);
                            doacao.setDataDoacao(Date.valueOf(dataStr));
                            
                            DoacaoController controller = new DoacaoController();
                            if (controller.atualizar(doacao)) {
                                response.sendRedirect("listar.jsp?mensagem=" + encode("Doação atualizada com sucesso") + "&tipo=success");
                                return;
                            } else {
            %>
                                <div class="alert alert-danger">Erro ao atualizar doação. Tente novamente.</div>
            <%
                            }
                        } else {
            %>
                            <div class="alert alert-warning">Por favor, preencha todos os campos.</div>
            <%
                        }
                    } catch (Exception e) {
            %>
                        <div class="alert alert-danger">Erro: <%=e.getMessage()%></div>
            <%
                    }
                }
                
                DoadorController doadorController = new DoadorController();
                InstituicaoController instituicaoController = new InstituicaoController();
                List<Doador> doadores = doadorController.listar();
                List<Instituicao> instituicoes = instituicaoController.listar();
            %>

            <form method="POST">
                <div class="form-group">
                    <label for="doadorId">Doador *</label>
                    <select id="doadorId" name="doadorId" required>
                        <option value="">-- Selecione um doador --</option>
                        <% if (doadores != null) {
                            for (Doador d : doadores) { %>
                                <option value="<%=d.getId()%>" <%=d.getId() == doacao.getDoadorId() ? "selected" : ""%>><%=d.getNome()%></option>
                        <%  }
                        } %>
                    </select>
                </div>

                <div class="form-group">
                    <label for="instituicaoId">Instituição Beneficente *</label>
                    <select id="instituicaoId" name="instituicaoId" required>
                        <option value="">-- Selecione uma instituição --</option>
                        <% if (instituicoes != null) {
                            for (Instituicao i : instituicoes) { %>
                                <option value="<%=i.getId()%>" <%=i.getId() == doacao.getInstituicaoId() ? "selected" : ""%>><%=i.getNome()%></option>
                        <%  }
                        } %>
                    </select>
                </div>

                <div class="form-group">
                    <label for="descricao">Descrição da Doação *</label>
                    <textarea id="descricao" name="descricao" required placeholder="Descreva o que será doado"><%=doacao.getDescricao()%></textarea>
                </div>

                <div class="form-group">
                    <label for="dataDoacao">Data da Doação *</label>
                    <input type="date" id="dataDoacao" name="dataDoacao" required value="<%=doacao.getDataDoacao()%>">
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
