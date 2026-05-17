package br.com.doacaoalimentos.controller;

import java.util.List;

import br.com.doacaoalimentos.dao.DoadorDAO;
import br.com.doacaoalimentos.model.Doador;

public class DoadorController {
    private DoadorDAO dao = new DoadorDAO();

    public boolean cadastrar(Doador doador) {
        return dao.salvar(doador);
    }

    public List<Doador> listar() {
        return dao.listar();
    }

    public Doador buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public boolean atualizar(Doador doador) {
        return dao.atualizar(doador);
    }

    public boolean excluir(int id) {
        return dao.excluir(id);
    }
}
