package br.com.doacaoalimentos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.doacaoalimentos.model.Instituicao;

public class InstituicaoDAO {
    public boolean salvar(Instituicao instituicao) {
        String sql = "INSERT INTO instituicoes (nome, endereco) VALUES (?, ?)";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stm.setString(1, instituicao.getNome());
            stm.setString(2, instituicao.getEndereco());
            int linhas = stm.executeUpdate();
            if (linhas > 0) {
                try (ResultSet rs = stm.getGeneratedKeys()) {
                    if (rs.next()) {
                        instituicao.setId(rs.getInt(1));
                    }
                }
            }
            return linhas > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Instituicao> listar() {
        String sql = "SELECT id, nome, endereco FROM instituicoes";
        List<Instituicao> instituicoes = new ArrayList<Instituicao>();
        try (Connection con = Conexao.getConnection();
             PreparedStatement stm = con.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                Instituicao instituicao = new Instituicao();
                instituicao.setId(rs.getInt("id"));
                instituicao.setNome(rs.getString("nome"));
                instituicao.setEndereco(rs.getString("endereco"));
                instituicoes.add(instituicao);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return instituicoes;
    }

    public Instituicao buscarPorId(int id) {
        String sql = "SELECT id, nome, endereco FROM instituicoes WHERE id = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Instituicao instituicao = new Instituicao();
                    instituicao.setId(rs.getInt("id"));
                    instituicao.setNome(rs.getString("nome"));
                    instituicao.setEndereco(rs.getString("endereco"));
                    return instituicao;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean atualizar(Instituicao instituicao) {
        String sql = "UPDATE instituicoes SET nome = ?, endereco = ? WHERE id = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, instituicao.getNome());
            stm.setString(2, instituicao.getEndereco());
            stm.setInt(3, instituicao.getId());
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM instituicoes WHERE id = ?";
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
