import org.junit.Test;
import static org.junit.Assert.*;

public class DoadorTest {

    @Test
    public void testSettersAndGetters() {
        Doador doador = new Doador();

        doador.setId(5);
        doador.setNome("Maria");
        doador.setEmail("maria@example.com");
        doador.setTelefone("(11) 99999-9999");

        assertEquals(5, doador.getId());
        assertEquals("Maria", doador.getNome());
        assertEquals("maria@example.com", doador.getEmail());
        assertEquals("(11) 99999-9999", doador.getTelefone());
    }
}
