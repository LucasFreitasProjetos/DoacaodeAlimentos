package br.com.doacaoalimentos.api;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.doacaoalimentos.controller.DoacaoController;
import br.com.doacaoalimentos.model.Doacao;

public class DoacaoApiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final DoacaoController controller = new DoacaoController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareJsonResponse(resp);
        Integer id = parseId(req.getPathInfo());
        if (id == null) {
            resp.getWriter().print(JsonUtil.toJsonDoacoes(controller.listar()));
            return;
        }
        Doacao doacao = controller.buscarPorId(id);
        if (doacao == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Doação não encontrada");
            return;
        }
        resp.getWriter().print(JsonUtil.toJson(doacao));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareJsonResponse(resp);
        String body = JsonUtil.readBody(req);
        Doacao doacao = new Doacao();
        doacao.setDoadorId(JsonUtil.getInt(body, "doadorId") != null ? JsonUtil.getInt(body, "doadorId") : 0);
        doacao.setInstituicaoId(JsonUtil.getInt(body, "instituicaoId") != null ? JsonUtil.getInt(body, "instituicaoId") : 0);
        doacao.setDescricao(JsonUtil.getString(body, "descricao"));
        String data = JsonUtil.getString(body, "dataDoacao");
        if (data != null && !data.isEmpty()) {
            doacao.setDataDoacao(Date.valueOf(data));
        }

        if (doacao.getDoadorId() <= 0 || doacao.getInstituicaoId() <= 0) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "doadorId e instituicaoId são obrigatórios");
            return;
        }

        if (!controller.cadastrar(doacao)) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao cadastrar doação");
            return;
        }

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().print(JsonUtil.toJson(doacao));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareJsonResponse(resp);
        Integer id = parseId(req.getPathInfo());
        if (id == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID da doação é obrigatório na URL");
            return;
        }

        String body = JsonUtil.readBody(req);
        Doacao doacao = controller.buscarPorId(id);
        if (doacao == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Doação não encontrada");
            return;
        }

        Integer doadorId = JsonUtil.getInt(body, "doadorId");
        Integer instituicaoId = JsonUtil.getInt(body, "instituicaoId");
        String descricao = JsonUtil.getString(body, "descricao");
        String data = JsonUtil.getString(body, "dataDoacao");

        if (doadorId != null) {
            doacao.setDoadorId(doadorId);
        }
        if (instituicaoId != null) {
            doacao.setInstituicaoId(instituicaoId);
        }
        if (descricao != null) {
            doacao.setDescricao(descricao);
        }
        if (data != null && !data.isEmpty()) {
            doacao.setDataDoacao(Date.valueOf(data));
        }

        if (!controller.atualizar(doacao)) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao atualizar doação");
            return;
        }

        resp.getWriter().print(JsonUtil.toJson(doacao));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareJsonResponse(resp);
        Integer id = parseId(req.getPathInfo());
        if (id == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID da doação é obrigatório na URL");
            return;
        }

        if (!controller.excluir(id)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Doação não encontrada");
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
