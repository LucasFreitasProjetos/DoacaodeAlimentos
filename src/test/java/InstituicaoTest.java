import org.junit.Test;
import static org.junit.Assert.*;

public class InstituicaoTest {

    @Test
    public void testSettersAndGetters() {
        Instituicao instituicao = new Instituicao();

        instituicao.setId(9);
        instituicao.setNome("Abrigo Esperança");
        instituicao.setEndereco("Rua das Flores, 123");
        instituicao.setTelefone("(21) 88888-8888");

        assertEquals(9, instituicao.getId());
        assertEquals("Abrigo Esperança", instituicao.getNome());
        assertEquals("Rua das Flores, 123", instituicao.getEndereco());
        assertEquals("(21) 88888-8888", instituicao.getTelefone());
    }
}
