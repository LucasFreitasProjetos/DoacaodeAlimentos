package br.com.doacaoalimentos.model;

import java.sql.Date;

public class Doacao {
    private int id;
    private int doadorId;
    private int instituicaoId;
    private String descricao;
    private Date dataDoacao;

    public int getId() {
        return id;
    }

    public int getDoadorId() {
        return doadorId;
    }

    public int getInstituicaoId() {
        return instituicaoId;
    }

    public String getDescricao() {
        return descricao;
    }

    public Date getDataDoacao() {
        return dataDoacao;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDoadorId(int doadorId) {
        this.doadorId = doadorId;
    }

    public void setInstituicaoId(int instituicaoId) {
        this.instituicaoId = instituicaoId;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDataDoacao(Date dataDoacao) {
        this.dataDoacao = dataDoacao;
    }

    @Override
    public String toString() {
        return "Doacao{id=" + id + ", doadorId=" + doadorId + ", instituicaoId=" + instituicaoId + ", descricao='" + descricao + "', dataDoacao=" + dataDoacao + "}";
    }
}
