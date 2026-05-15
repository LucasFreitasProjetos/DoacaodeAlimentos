package br.com.doacaoalimentos.model;

public class Instituicao {
    private int id;
    private String nome;
    private String endereco;

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Instituição{id=" + id + ", nome='" + nome + "', endereco='" + endereco + "'}";
    }
}
