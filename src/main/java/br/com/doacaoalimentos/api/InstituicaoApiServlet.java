package br.com.doacaoalimentos.api;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import br.com.doacaoalimentos.controller.InstituicaoController;
import br.com.doacaoalimentos.model.Instituicao;

public class InstituicaoApiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final InstituicaoController controller = new InstituicaoController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareJsonResponse(resp);
        Integer id = parseId(req.getPathInfo());
        if (id == null) {
            resp.getWriter().print(JsonUtil.toJsonInstituicoes(controller.listar()));
            return;
        }
        Instituicao instituicao = controller.buscarPorId(id);
        if (instituicao == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Instituição não encontrada");
            return;
        }
        resp.getWriter().print(JsonUtil.toJson(instituicao));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareJsonResponse(resp);
        String body = JsonUtil.readBody(req);
        Instituicao instituicao = new Instituicao();
        instituicao.setNome(JsonUtil.getString(body, "nome"));
        instituicao.setEndereco(JsonUtil.getString(body, "endereco"));

        if (instituicao.getNome() == null || instituicao.getNome().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nome é obrigatório");
            return;
        }

        if (!controller.cadastrar(instituicao)) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao cadastrar instituição");
            return;
        }

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().print(JsonUtil.toJson(instituicao));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareJsonResponse(resp);
        Integer id = parseId(req.getPathInfo());
        if (id == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID da instituição é obrigatório na URL");
            return;
        }

        String body = JsonUtil.readBody(req);
        Instituicao instituicao = controller.buscarPorId(id);
        if (instituicao == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Instituição não encontrada");
            return;
        }

        String nome = JsonUtil.getString(body, "nome");
        String endereco = JsonUtil.getString(body, "endereco");
        if (nome != null) {
            instituicao.setNome(nome);
        }
        if (endereco != null) {
            instituicao.setEndereco(endereco);
        }

        if (!controller.atualizar(instituicao)) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao atualizar instituição");
            return;
        }

        resp.getWriter().print(JsonUtil.toJson(instituicao));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareJsonResponse(resp);
        Integer id = parseId(req.getPathInfo());
        if (id == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID da instituição é obrigatório na URL");
            return;
        }

        if (!controller.excluir(id)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Instituição não encontrada");
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
