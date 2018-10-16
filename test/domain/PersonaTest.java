package domain;

import domain.Persona;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import domain.Sistema;
import java.util.Date;

public class PersonaTest {

    private Sistema unSistema;

    public PersonaTest() {
    }

    @Before
    public void setUp() {
        unSistema = new Sistema();
    }

    @Test
    public void testRegistrarUsuarioNombreVacio() {
        int tamanoInicial = unSistema.getListaUsuarios().size();
        int resultadoEsperado = tamanoInicial;
        String nombres = "Pepe";
        String apellidos = "Rodríguez";
        Date nacimiento = new Date();
        //unSistema.r

        //unSistema.registrarUsuario(nombres, apellidos, nacionalidad, preferencias, restricciones, nacimiento, descripcion);
        int resutladoObtenido = unSistema.getListaUsuarios().size();

        assertEquals(resultadoEsperado, resutladoObtenido);

    }

    @Test
    public void testGetNombres() {
        Persona unaPersona = new Persona();
        String nombre = "Edison Roberto";
        unaPersona.setNombres(nombre);
        assertEquals(nombre,unaPersona.getNombres());
    }

    @Test
    public void testGetApellidos() {
        Persona unaPersona = new Persona();
        String apellido = "Cavani";
        unaPersona.setApellidos(apellido);
        assertEquals(apellido,unaPersona.getApellidos());
    }

    @Test
    public void testGetNacimiento() {
        Persona unaPersona = new Persona();
        Date fechaActual = new Date();
        unaPersona.setNacimiento(fechaActual);
        assertEquals(fechaActual,unaPersona.getNacimiento());
    }


    @Test
    public void testToString() {
        Persona unaPersona = new Persona();
        String nombre = "Edison Roberto";
        String apellido = "Cavani";
        unaPersona.setNombres(nombre);
        unaPersona.setApellidos(apellido);
        String resultadoEsperado = nombre+apellido;
        assertEquals(resultadoEsperado, unaPersona.toString());
    }
}