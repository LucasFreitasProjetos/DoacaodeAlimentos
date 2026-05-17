package br.com.doacaoalimentos.test;

import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.Date;
import br.com.doacaoalimentos.model.Doacao;

public class DoacaoTest {

    @Test
    public void testConstructorAndGetters() {
        Doacao doacao = new Doacao("Arroz", "Alimento seco", 10);

        assertEquals("Arroz", doacao.getDescricao());
        assertEquals("Alimento seco", doacao.getTipoAlimento());
        assertEquals(10, doacao.getQuantidade());
    }

    @Test
    public void testSettersAndGetters() {
        Doacao doacao = new Doacao();
        Date data = Date.valueOf("2026-05-12");

        doacao.setId(42);
        doacao.setDoadorId(7);
        doacao.setInstituicaoId(3);
        doacao.setDescricao("Feijão");
        doacao.setTipoAlimento("Grãos");
        doacao.setQuantidade(20);
        doacao.setStatus("Pendente");
        doacao.setDataDoacao(data);

        assertEquals(42, doacao.getId());
        assertEquals(7, doacao.getDoadorId());
        assertEquals(3, doacao.getInstituicaoId());
        assertEquals("Feijão", doacao.getDescricao());
        assertEquals("Grãos", doacao.getTipoAlimento());
        assertEquals(20, doacao.getQuantidade());
        assertEquals("Pendente", doacao.getStatus());
        assertEquals(data, doacao.getDataDoacao());
    }
}
