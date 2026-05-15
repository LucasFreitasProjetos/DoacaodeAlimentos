package br.com.doacaoalimentos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.doacaoalimentos.model.Doador;

public class DoadorDAO {
    public boolean salvar(Doador doador) {
        String sql = "INSERT INTO doadores (nome, email) VALUES (?, ?)";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stm.setString(1, doador.getNome());
            stm.setString(2, doador.getEmail());
            int linhas = stm.executeUpdate();
            if (linhas > 0) {
                try (ResultSet rs = stm.getGeneratedKeys()) {
                    if (rs.next()) {
                        doador.setId(rs.getInt(1));
                    }
                }
            }
            return linhas > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Doador> listar() {
        String sql = "SELECT id, nome, email FROM doadores";
        List<Doador> doadores = new ArrayList<Doador>();
        try (Connection con = Conexao.getConnection();
             PreparedStatement stm = con.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                Doador doador = new Doador();
                doador.setId(rs.getInt("id"));
                doador.setNome(rs.getString("nome"));
                doador.setEmail(rs.getString("email"));
                doadores.add(doador);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return doadores;
    }

    public Doador buscarPorId(int id) {
        String sql = "SELECT id, nome, email FROM doadores WHERE id = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Doador doador = new Doador();
                    doador.setId(rs.getInt("id"));
                    doador.setNome(rs.getString("nome"));
                    doador.setEmail(rs.getString("email"));
                    return doador;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean atualizar(Doador doador) {
        String sql = "UPDATE doadores SET nome = ?, email = ? WHERE id = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, doador.getNome());
            stm.setString(2, doador.getEmail());
            stm.setInt(3, doador.getId());
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM doadores WHERE id = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, id);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
