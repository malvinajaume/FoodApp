package domain;

import domain.Alimento;
import domain.Usuario;
import domain.AlimentoIngerido;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import domain.Sistema;
import java.util.Date;
import java.util.ArrayList;

public class AlimentoIngeridoTest {

    private Sistema unSistema;
//falta caso string con numero

    @Before
    public void setUp() {
        unSistema = new Sistema();
        Alimento unAlimento = new Alimento();
        unAlimento.setNombre("Banana");
        int nutrientes[] = new int[5];
        nutrientes[0] = 20;
        unAlimento.setNutrientesPrincipales(nutrientes);
        unAlimento.setTipo("Fruta");

    }

    @Test
    public void testAlimentoIngeridoIncorrecto() {

    }

    @Test
    public void testAlimentoIngeridoCorrecto() {
        //Creo el alimento
        Alimento unAlimento = new Alimento();
        unAlimento.setNombre("Banana");
        int nutrientes[] = new int[5];
        nutrientes[0] = 20;
        unAlimento.setNutrientesPrincipales(nutrientes);
        unAlimento.setTipo("Fruta");

        //Creo el usuario
        Usuario unUsuario = new Usuario();
        unUsuario.setNombres("Juan");
        unUsuario.setNacionalidad("Uruguay");
        unUsuario.setApellidos("Lutz");

//        unUsuario.setAlimentosIngeridos(new ArrayList<>());

        //Creo el alimento ingerido 
        AlimentoIngerido alimentoIngerido = new AlimentoIngerido();
        alimentoIngerido.setAlimentoIngeridoUsuario(unAlimento);
        Date fechaDeHoy = new Date();
        alimentoIngerido.setFecha(fechaDeHoy);

        int cantAlimentosIngeridosInicial = unUsuario.getAlimentosIngeridos().size();
        int resultadoEsperado = cantAlimentosIngeridosInicial + 2;

        unUsuario.getAlimentosIngeridos().add(alimentoIngerido);
        unUsuario.getAlimentosIngeridos().add(alimentoIngerido);

        int resultadoObtenido = unUsuario.getAlimentosIngeridos().size();

        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testGetFecha() {
        AlimentoIngerido unAlimentoIngerido = new AlimentoIngerido();
        Date fechaActual = new Date();
        unAlimentoIngerido.setFecha(fechaActual);

        assertEquals(fechaActual, unAlimentoIngerido.getFecha());
    }

    @Test
    public void testGetAlimentoIngeridoUsuario() {
        Alimento unAlimento = new Alimento();
        String nombreAlimento = "Frutilla";
        unAlimento.setNombre(nombreAlimento);
        AlimentoIngerido unAlimentoIngerido = new AlimentoIngerido();
        unAlimentoIngerido.setAlimentoIngeridoUsuario(unAlimento);

        assertEquals(unAlimento,unAlimentoIngerido.getAlimentoIngeridoUsuario());
    }

    @Test
    public void testToString() {
        Usuario unUsuario = new Usuario();
        Alimento unAlimento = new Alimento();
        String nombreAlimento = "Frutilla";
        unAlimento.setNombre(nombreAlimento);
        ArrayList<AlimentoIngerido> listaIngeridos = new ArrayList<AlimentoIngerido>();
        AlimentoIngerido unAlimentoIngerido = new AlimentoIngerido();
        unAlimentoIngerido.setAlimentoIngeridoUsuario(unAlimento);
        listaIngeridos.add(unAlimentoIngerido);
        unUsuario.setAlimentosIngeridos(listaIngeridos);

        assertEquals(nombreAlimento, unUsuario.getAlimentosIngeridos().get(0).toString());
    }
}