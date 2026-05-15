package br.com.doacaoalimentos.model;

public class Doador {
    private int id;
    private String nome;
    private String email;

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Doador{id=" + id + ", nome='" + nome + "', email='" + email + "'}";
    }
}
