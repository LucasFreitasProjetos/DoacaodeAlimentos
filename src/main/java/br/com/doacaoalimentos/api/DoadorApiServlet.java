package br.com.doacaoalimentos.api;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import br.com.doacaoalimentos.controller.DoadorController;
import br.com.doacaoalimentos.model.Doador;

public class DoadorApiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final DoadorController controller = new DoadorController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareJsonResponse(resp);
        Integer id = parseId(req.getPathInfo());
        if (id == null) {
            resp.getWriter().print(JsonUtil.toJson(controller.listar()));
            return;
        }
        Doador doador = controller.buscarPorId(id);
        if (doador == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Doador não encontrado");
            return;
        }
        resp.getWriter().print(JsonUtil.toJson(doador));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareJsonResponse(resp);
        String body = JsonUtil.readBody(req);
        Doador doador = new Doador();
        doador.setNome(JsonUtil.getString(body, "nome"));
        doador.setEmail(JsonUtil.getString(body, "email"));

        if (doador.getNome() == null || doador.getNome().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nome é obrigatório");
            return;
        }

        if (!controller.cadastrar(doador)) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao cadastrar doador");
            return;
        }

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().print(JsonUtil.toJson(doador));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareJsonResponse(resp);
        Integer id = parseId(req.getPathInfo());
        if (id == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do doador é obrigatório na URL");
            return;
        }

        String body = JsonUtil.readBody(req);
        Doador doador = controller.buscarPorId(id);
        if (doador == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Doador não encontrado");
            return;
        }

        String nome = JsonUtil.getString(body, "nome");
        String email = JsonUtil.getString(body, "email");
        if (nome != null) {
            doador.setNome(nome);
        }
        if (email != null) {
            doador.setEmail(email);
        }

        if (!controller.atualizar(doador)) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao atualizar doador");
            return;
        }

        resp.getWriter().print(JsonUtil.toJson(doador));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareJsonResponse(resp);
        Integer id = parseId(req.getPathInfo());
        if (id == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do doador é obrigatório na URL");
            return;
        }

        if (!controller.excluir(id)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Doador não encontrado");
            return;
        }

        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    private Integer parseId(String pathInfo) {
        if (pathInfo == null || pathInfo.length() <= 1) {
            return null;
        }
        try {
            return Integer.parseInt(pathInfo.substring(1));
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private void prepareJsonResponse(HttpServletResponse resp) {
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareJsonResponse(resp);
    }
}
