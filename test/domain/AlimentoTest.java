package domain;

import domain.Alimento;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import domain.Sistema;

public class AlimentoTest {

    private Sistema unSistema;

    public AlimentoTest() {
    }

    @Before
    public void setUp() {
        unSistema = new Sistema();
    }

    @Test
    public void testRegistrarAlimentoNombreVacio() {
        int tamanoInicial = unSistema.getListaAlimentos().size();
        int resultadoEsperado = tamanoInicial;
        String nombre = "";
        String tipo = "Fruta";
        int[] nutrientesPrincipales = new int[5];
        unSistema.registrarAlimento(nombre, tipo, nutrientesPrincipales);
        int resutladoObtenido = unSistema.getListaAlimentos().size();
        assertEquals(resultadoEsperado, resutladoObtenido);

    }

    @Test
    public void testRegistrarAlimentoApellidoVacio() {
        int tamanoInicial = unSistema.getListaAlimentos().size();
        int resultadoEsperado = tamanoInicial;
        String nombre = "Manzana";
        String tipo = "";
        int[] nutrientesPrincipales = new int[5];
        unSistema.registrarAlimento(nombre, tipo, nutrientesPrincipales);
        int resutladoObtenido = unSistema.getListaAlimentos().size();
        assertEquals(resultadoEsperado, resutladoObtenido);

    }

    @Test
    public void testRegistrarAlimentoCorrecto() {
        int tamanoInicial = unSistema.getListaAlimentos().size();
        int resultadoEsperado = tamanoInicial + 1;
        String nombre = "Manzana";
        String tipo = "Fruta";
        int[] nutrientesPrincipales = new int[5];
        unSistema.registrarAlimento(nombre, tipo, nutrientesPrincipales);
        int resutladoObtenido = unSistema.getListaAlimentos().size();
        assertEquals(resultadoEsperado, resutladoObtenido);

    }

    @Test
    public void testGetNombre() {
        Alimento unAlimento = new Alimento();
        String nombre = "Mandarina";
        unAlimento.setNombre(nombre);

        assertEquals(unAlimento.getNombre(), nombre);
    }


    @Test
    public void testGetTipo() {
        Alimento unAlimento = new Alimento();
        String tipo = "Fruta";
        unAlimento.setTipo(tipo);

        assertEquals(unAlimento.getTipo(), tipo);
    }


    @Test
    public void testGetNutrientesPrincipales() {
        Alimento unAlimento = new Alimento();
        int nutrientes[] = new int[5];
        nutrientes[0] = 30;
        unAlimento.setNutrientesPrincipales(nutrientes);

        assertEquals(unAlimento.getNutrientesPrincipales()[0], nutrientes[0]);
    }

    @Test
    public void testToStrig(){
        Alimento unAlimento = new Alimento();
        String nombre = "Naranja";
        unAlimento.setNombre(nombre);
        
        assertEquals(unAlimento.toString(),nombre);
    }
 

}