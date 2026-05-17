package br.com.doacaoalimentos.controller;

import java.util.List;

import br.com.doacaoalimentos.dao.InstituicaoDAO;
import br.com.doacaoalimentos.model.Instituicao;

public class InstituicaoController {
    private InstituicaoDAO dao = new InstituicaoDAO();

    public boolean cadastrar(Instituicao instituicao) {
        return dao.salvar(instituicao);
    }

    public List<Instituicao> listar() {
        return dao.listar();
    }

    public Instituicao buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public boolean atualizar(Instituicao instituicao) {
        return dao.atualizar(instituicao);
    }

    public boolean excluir(int id) {
        return dao.excluir(id);
    }
}
