package br.com.doacaoalimentos.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.doacaoalimentos.model.Doacao;

public class DoacaoDao {
    public boolean salvar(Doacao doacao) {
        String sql = "INSERT INTO doacoes (doador_id, instituicao_id, descricao, data_doacao) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stm.setInt(1, doacao.getDoadorId());
            stm.setInt(2, doacao.getInstituicaoId());
            stm.setString(3, doacao.getDescricao());
            stm.setDate(4, doacao.getDataDoacao());
            int linhas = stm.executeUpdate();
            if (linhas > 0) {
                try (ResultSet rs = stm.getGeneratedKeys()) {
                    if (rs.next()) {
                        doacao.setId(rs.getInt(1));
                    }
                }
            }
            return linhas > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Doacao> listar() {
        String sql = "SELECT id, doador_id, instituicao_id, descricao, data_doacao FROM doacoes";
        List<Doacao> doacoes = new ArrayList<Doacao>();
        try (Connection con = Conexao.getConnection();
             PreparedStatement stm = con.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                Doacao doacao = new Doacao();
                doacao.setId(rs.getInt("id"));
                doacao.setDoadorId(rs.getInt("doador_id"));
                doacao.setInstituicaoId(rs.getInt("instituicao_id"));
                doacao.setDescricao(rs.getString("descricao"));
                doacao.setDataDoacao(rs.getDate("data_doacao"));
                doacoes.add(doacao);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return doacoes;
    }

    public Doacao buscarPorId(int id) {
        String sql = "SELECT id, doador_id, instituicao_id, descricao, data_doacao FROM doacoes WHERE id = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Doacao doacao = new Doacao();
                    doacao.setId(rs.getInt("id"));
                    doacao.setDoadorId(rs.getInt("doador_id"));
                    doacao.setInstituicaoId(rs.getInt("instituicao_id"));
                    doacao.setDescricao(rs.getString("descricao"));
                    doacao.setDataDoacao(rs.getDate("data_doacao"));
                    return doacao;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean atualizar(Doacao doacao) {
        String sql = "UPDATE doacoes SET doador_id = ?, instituicao_id = ?, descricao = ?, data_doacao = ? WHERE id = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, doacao.getDoadorId());
            stm.setInt(2, doacao.getInstituicaoId());
            stm.setString(3, doacao.getDescricao());
            stm.setDate(4, doacao.getDataDoacao());
            stm.setInt(5, doacao.getId());
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM doacoes WHERE id = ?";
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
