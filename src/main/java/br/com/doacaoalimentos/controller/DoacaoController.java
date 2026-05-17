package br.com.doacaoalimentos.controller;

import java.util.List;

import br.com.doacaoalimentos.dao.DoacaoDao;
import br.com.doacaoalimentos.dao.DoadorDAO;
import br.com.doacaoalimentos.dao.InstituicaoDAO;
import br.com.doacaoalimentos.model.Doacao;
import br.com.doacaoalimentos.model.Doador;
import br.com.doacaoalimentos.model.Instituicao;

public class DoacaoController {
    private DoacaoDao dao = new DoacaoDao();
    private DoadorDAO doadorDao = new DoadorDAO();
    private InstituicaoDAO instituicaoDao = new InstituicaoDAO();

    public boolean cadastrar(Doacao doacao) {
        return dao.salvar(doacao);
    }

    public List<Doacao> listar() {
        return dao.listar();
    }

    public Doacao buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public boolean atualizar(Doacao doacao) {
        return dao.atualizar(doacao);
    }

    public boolean excluir(int id) {
        return dao.excluir(id);
    }

    public List<Doador> listarDoadores() {
        return doadorDao.listar();
    }

    public List<Instituicao> listarInstituicoes() {
        return instituicaoDao.listar();
    }
}
