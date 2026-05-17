<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.doacaoalimentos.controller.InstituicaoController" %>
<%@ page import="java.net.URLEncoder" %>
<%! private String encode(String value) { try { return URLEncoder.encode(value, "UTF-8"); } catch (Exception e) { return value; } } %>
<%
    String idParam = request.getParameter("id");
    
    if (idParam != null && !idParam.isEmpty()) {
        try {
            int id = Integer.parseInt(idParam);
            InstituicaoController controller = new InstituicaoController();
            
            if (controller.excluir(id)) {
                response.sendRedirect("listar.jsp?mensagem=" + encode("Instituição removida com sucesso") + "&tipo=success");
            } else {
                response.sendRedirect("listar.jsp?mensagem=" + encode("Erro ao remover instituição") + "&tipo=danger");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("listar.jsp?mensagem=" + encode("ID inválido") + "&tipo=danger");
        }
    } else {
        response.sendRedirect("listar.jsp");
    }
%>
