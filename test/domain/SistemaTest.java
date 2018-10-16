package domain;

import domain.Alimento;
import domain.Sistema;
import domain.Usuario;
import domain.Enums;
import domain.AlimentoIngerido;
import domain.ParProfesionalConsulta;
import domain.PlanAlimentacion;
import domain.Consulta;
import java.util.ArrayList;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;
import domain.Profesional;
import java.io.*;
import java.text.ParseException;
import java.util.*;

public class SistemaTest {

    public SistemaTest() {
    }

    @Test
    public void testGetListaUsuarios() {
        Sistema unSistema = new Sistema();
        ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
        Usuario unUsuario = new Usuario();
        unUsuario.setNombres("Juan");
        unUsuario.setApellidos("Lopez");
        unSistema.setListaUsuarios(listaUsuarios);
        assertEquals(listaUsuarios, unSistema.getListaUsuarios());
    }

    @Test
    public void testGetListaProfesionales() {
        Sistema unSistema = new Sistema();
        ArrayList<Profesional> listaProfesionales = new ArrayList<Profesional>();
        Profesional unProfesional = new Profesional();
        unProfesional.setNombres("Juan");
        unProfesional.setApellidos("Lopez");
        unSistema.setListaProfesionales(listaProfesionales);
        assertEquals(listaProfesionales, unSistema.getListaProfesionales());
    }

    @Test
    public void testGetListaAlimentos() {
        Sistema unSistema = new Sistema();
        ArrayList<Alimento> listaAlimentos = new ArrayList<Alimento>();
        Alimento unAlimento = new Alimento();
        unAlimento.setNombre("Frutilla");
        unSistema.setListaAlimentos(listaAlimentos);
        assertEquals(listaAlimentos, unSistema.getListaAlimentos());
    }

    @Test
    public void testAgregarUsuario() {
        Sistema unSistema = new Sistema();
        Usuario unUsuario = new Usuario();
        unUsuario.setNombres("Juan");
        unUsuario.setApellidos("Lopez");
        int resultadoEsperado = unSistema.getListaUsuarios().size() + 1;
        unSistema.agregarUsuario(unUsuario);
        int resultadoObtenido = unSistema.getListaUsuarios().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testRegistrarUsuarioNombreVacio() {
        Sistema unSistema = new Sistema();
        int resultadoEsperado = unSistema.getListaUsuarios().size();
        String nombres = "";
        String apellidos = "Medina";
        String nacionalidad = "Uruguay";
        boolean preferencias[] = new boolean[5];
        boolean restricciones[] = new boolean[5];
        Date nacimiento = new Date();
        String descripcion = "Intolerante a la lactosa";
        unSistema.registrarUsuario(nombres, apellidos, nacionalidad, preferencias, restricciones, nacimiento, descripcion, null);
        int resultadoObtenido = unSistema.getListaUsuarios().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testRegistrarUsuarioApellidoVacio() {
        Sistema unSistema = new Sistema();
        int resultadoEsperado = unSistema.getListaUsuarios().size();
        String nombres = "Juan";
        String apellidos = "";
        String nacionalidad = "Uruguay";
        boolean preferencias[] = new boolean[5];
        boolean restricciones[] = new boolean[5];
        Date nacimiento = new Date();
        String descripcion = "Intolerante a la lactosa";
        unSistema.registrarUsuario(nombres, apellidos, nacionalidad, preferencias, restricciones, nacimiento, descripcion, null);
        int resultadoObtenido = unSistema.getListaUsuarios().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testRegistrarUsuarioNacionalidadVacia() {
        Sistema unSistema = new Sistema();
        int resultadoEsperado = unSistema.getListaUsuarios().size();
        String nombres = "Juan";
        String apellidos = "Medina";
        String nacionalidad = "";
        boolean preferencias[] = new boolean[5];
        boolean restricciones[] = new boolean[5];
        Date nacimiento = new Date();
        String descripcion = "Intolerante a la lactosa";
        unSistema.registrarUsuario(nombres, apellidos, nacionalidad, preferencias, restricciones, nacimiento, descripcion, null);
        int resultadoObtenido = unSistema.getListaUsuarios().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testRegistrarUsuarioDescripcionVacia() {
        Sistema unSistema = new Sistema();
        int resultadoEsperado = unSistema.getListaUsuarios().size() + 1;
        String nombres = "Juan";
        String apellidos = "Medina";
        String nacionalidad = "Uruguay";
        boolean preferencias[] = new boolean[5];
        boolean restricciones[] = new boolean[5];
        Date nacimiento = new Date();
        String descripcion = "";
        unSistema.registrarUsuario(nombres, apellidos, nacionalidad, preferencias, restricciones, nacimiento, descripcion, null);
        int resultadoObtenido = unSistema.getListaUsuarios().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testRegistrarUsuarioYaExistente() {
        Sistema unSistema = new Sistema();
        String nombres = "Juan";
        String apellidos = "Medina";
        String nacionalidad = "Uruguay";
        boolean preferencias[] = new boolean[5];
        boolean restricciones[] = new boolean[5];
        Date fechaNacimiento;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1981);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 12);
        fechaNacimiento = cal.getTime();
        String descripcion = "";
        unSistema.registrarUsuario(nombres, apellidos, nacionalidad, preferencias, restricciones, fechaNacimiento, descripcion, null);
        int resultadoEsperado = unSistema.getListaUsuarios().size();
        unSistema.registrarUsuario(nombres, apellidos, nacionalidad, preferencias, restricciones, fechaNacimiento, descripcion, null);
        int resultadoObtenido = unSistema.getListaUsuarios().size();
        assertEquals(resultadoEsperado, resultadoObtenido);

    }

    @Test
    public void testRegistrarUsuarioCorrecto() {
        Sistema unSistema = new Sistema();
        int resultadoEsperado = unSistema.getListaUsuarios().size() + 1;
        String nombres = "Juan";
        String apellidos = "Medina";
        String nacionalidad = "Uruguay";
        boolean preferencias[] = new boolean[5];
        boolean restricciones[] = new boolean[5];
        Date nacimiento = new Date();
        String descripcion = "Intolerante a la lactosa";
        unSistema.registrarUsuario(nombres, apellidos, nacionalidad, preferencias, restricciones, nacimiento, descripcion, null);
        int resultadoObtenido = unSistema.getListaUsuarios().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    //--------------------------------------------------------------------------------------------------------------
    @Test
    public void testAgregarProfesional() {
        Sistema unSistema = new Sistema();
        int resultadoEsperado = unSistema.getListaProfesionales().size() + 1;
        Profesional unProfesional = new Profesional();
        unSistema.agregarProfesional(unProfesional);
        int resultadoObtenido = unSistema.getListaProfesionales().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testRegistrarProfesionalNombreVacio() {
        Sistema unSistema = new Sistema();
        int resultadoEsperado = unSistema.getListaUsuarios().size();
        String nombres = "";
        String apellidos = "Leivas";
        String titulo = "Lic. en Nutrición";
        String paisObtencionTitulo = "Uruguay";

        Date fechaNacimiento;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1981);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 12);
        fechaNacimiento = cal.getTime();

        Date fechaGraduacion;
        Calendar calBis = Calendar.getInstance();
        calBis.set(Calendar.YEAR, 2011);
        calBis.set(Calendar.DAY_OF_MONTH, 3);
        calBis.set(Calendar.MONTH, 12);
        fechaGraduacion = calBis.getTime();

        unSistema.registrarProfesional(nombres, apellidos, titulo, paisObtencionTitulo, fechaNacimiento, fechaGraduacion, null);
        int resultadoObtenido = unSistema.getListaUsuarios().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testRegistrarProfesionalNombreConNumero() {
        Sistema unSistema = new Sistema();
        int resultadoEsperado = unSistema.getListaUsuarios().size();
        String nombres = "PAb0";
        String apellidos = "Leivas";
        String titulo = "Lic. en Nutrición";
        String paisObtencionTitulo = "Uruguay";

        Date fechaNacimiento;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1981);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 12);
        fechaNacimiento = cal.getTime();

        Date fechaGraduacion;
        Calendar calBis = Calendar.getInstance();
        calBis.set(Calendar.YEAR, 2011);
        calBis.set(Calendar.DAY_OF_MONTH, 3);
        calBis.set(Calendar.MONTH, 12);
        fechaGraduacion = calBis.getTime();

        unSistema.registrarProfesional(nombres, apellidos, titulo, paisObtencionTitulo, fechaNacimiento, fechaGraduacion, null);
        int resultadoObtenido = unSistema.getListaUsuarios().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testRegistrarProfesionalApellidoVacio() {
        Sistema unSistema = new Sistema();
        int resultadoEsperado = unSistema.getListaUsuarios().size();
        String nombres = "Juan";
        String apellidos = "";
        String titulo = "Lic. en Nutrición";
        String paisObtencionTitulo = "Uruguay";

        Date fechaNacimiento;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1981);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 12);
        fechaNacimiento = cal.getTime();

        Date fechaGraduacion;
        Calendar calBis = Calendar.getInstance();
        calBis.set(Calendar.YEAR, 2011);
        calBis.set(Calendar.DAY_OF_MONTH, 3);
        calBis.set(Calendar.MONTH, 12);
        fechaGraduacion = calBis.getTime();
        unSistema.registrarProfesional(nombres, apellidos, titulo, paisObtencionTitulo, fechaNacimiento, fechaGraduacion, null);
        int resultadoObtenido = unSistema.getListaUsuarios().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testRegistrarProfesionalApellidoConNumero() {
        Sistema unSistema = new Sistema();
        int resultadoEsperado = unSistema.getListaUsuarios().size();
        String nombres = "Juan";
        String apellidos = "L0pez";
        String titulo = "Lic. en Nutrición";
        String paisObtencionTitulo = "Uruguay";

        Date fechaNacimiento;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1981);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 12);
        fechaNacimiento = cal.getTime();

        Date fechaGraduacion;
        Calendar calBis = Calendar.getInstance();
        calBis.set(Calendar.YEAR, 2011);
        calBis.set(Calendar.DAY_OF_MONTH, 3);
        calBis.set(Calendar.MONTH, 12);
        fechaGraduacion = calBis.getTime();
        unSistema.registrarProfesional(nombres, apellidos, titulo, paisObtencionTitulo, fechaNacimiento, fechaGraduacion, null);
        int resultadoObtenido = unSistema.getListaUsuarios().size();
        assertEquals(resultadoEsperado, resultadoObtenido);

    }

    @Test
    public void testRegistrarProfesionalTituloVacia() {
        Sistema unSistema = new Sistema();
        int resultadoEsperado = unSistema.getListaUsuarios().size();
        String nombres = "Juan";
        String apellidos = "Saaveedra";
        String titulo = "";
        String paisObtencionTitulo = "Uruguay";
        Date fechaNacimiento;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1981);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 12);
        fechaNacimiento = cal.getTime();

        Date fechaGraduacion;
        Calendar calBis = Calendar.getInstance();
        calBis.set(Calendar.YEAR, 2011);
        calBis.set(Calendar.DAY_OF_MONTH, 3);
        calBis.set(Calendar.MONTH, 12);
        fechaGraduacion = calBis.getTime();
        unSistema.registrarProfesional(nombres, apellidos, titulo, paisObtencionTitulo, fechaNacimiento, fechaGraduacion, null);
        int resultadoObtenido = unSistema.getListaUsuarios().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testRegistrarProfesionalTituloConNumero() {
        Sistema unSistema = new Sistema();
        int resultadoEsperado = unSistema.getListaUsuarios().size();
        String nombres = "Juan";
        String apellidos = "Saaveedra";
        String titulo = "Lic. en nutrici0n";
        String paisObtencionTitulo = "Uruguay";
        Date fechaNacimiento;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1981);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 12);
        fechaNacimiento = cal.getTime();

        Date fechaGraduacion;
        Calendar calBis = Calendar.getInstance();
        calBis.set(Calendar.YEAR, 2011);
        calBis.set(Calendar.DAY_OF_MONTH, 3);
        calBis.set(Calendar.MONTH, 12);
        fechaGraduacion = calBis.getTime();
        unSistema.registrarProfesional(nombres, apellidos, titulo, paisObtencionTitulo, fechaNacimiento, fechaGraduacion, null);
        int resultadoObtenido = unSistema.getListaUsuarios().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testRegistrarProfesionalPaisObtencionVacio() {
        Sistema unSistema = new Sistema();
        int resultadoEsperado = unSistema.getListaProfesionales().size();
        String nombres = "Juan";
        String apellidos = "Leivas";
        String titulo = "Lic. en Nutrición";
        String paisObtencionTitulo = " ";
        Date fechaNacimiento;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1981);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 12);
        fechaNacimiento = cal.getTime();

        Date fechaGraduacion;
        Calendar calBis = Calendar.getInstance();
        calBis.set(Calendar.YEAR, 2011);
        calBis.set(Calendar.DAY_OF_MONTH, 3);
        calBis.set(Calendar.MONTH, 12);
        fechaGraduacion = calBis.getTime();
        unSistema.registrarProfesional(nombres, apellidos, titulo, paisObtencionTitulo, fechaNacimiento, fechaGraduacion, null);
        int resultadoObtenido = unSistema.getListaProfesionales().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testRegistrarProfesionalPaisObtencionConNumero() {
        Sistema unSistema = new Sistema();
        int resultadoEsperado = unSistema.getListaUsuarios().size();
        String nombres = "Juan";
        String apellidos = " ";
        String titulo = "Lic. en Nutrición";
        String paisObtencionTitulo = "Urugua7";
        Date fechaNacimiento;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1981);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 12);
        fechaNacimiento = cal.getTime();

        Date fechaGraduacion;
        Calendar calBis = Calendar.getInstance();
        calBis.set(Calendar.YEAR, 2011);
        calBis.set(Calendar.DAY_OF_MONTH, 3);
        calBis.set(Calendar.MONTH, 12);
        fechaGraduacion = calBis.getTime();
        unSistema.registrarProfesional(nombres, apellidos, titulo, paisObtencionTitulo, fechaNacimiento, fechaGraduacion, null);
        int resultadoObtenido = unSistema.getListaUsuarios().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testRegistrarProfesionalFechaNacimientoInvalida() {
        Sistema unSistema = new Sistema();
        int resultadoEsperado = unSistema.getListaUsuarios().size();
        String nombres = "Juan";
        String apellidos = "";
        String titulo = "Lic. en Nutrición";
        String paisObtencionTitulo = "";
        Date fechaNacimiento = new Date();

        Date fechaGraduacion;
        Calendar calBis = Calendar.getInstance();
        calBis.set(Calendar.YEAR, 2011);
        calBis.set(Calendar.DAY_OF_MONTH, 3);
        calBis.set(Calendar.MONTH, 12);
        fechaGraduacion = calBis.getTime();
        unSistema.registrarProfesional(nombres, apellidos, titulo, paisObtencionTitulo, fechaNacimiento, fechaGraduacion, null);
        int resultadoObtenido = unSistema.getListaUsuarios().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testRegistrarProfesionalFechaNacimientoValida() {
        Sistema unSistema = new Sistema();
        int resultadoEsperado = unSistema.getListaUsuarios().size();
        String nombres = "Juan";
        String apellidos = "";
        String titulo = "Lic. en Nutrición";
        String paisObtencionTitulo = "";
        Date fechaNacimiento;
        Calendar calN = Calendar.getInstance();
        calN.set(Calendar.YEAR, 2011);
        calN.set(Calendar.DAY_OF_MONTH, 3);
        calN.set(Calendar.MONTH, 12);
        fechaNacimiento = calN.getTime();

        Date fechaGraduacion;
        Calendar calBis = Calendar.getInstance();
        calBis.set(Calendar.YEAR, 2011);
        calBis.set(Calendar.DAY_OF_MONTH, 3);
        calBis.set(Calendar.MONTH, 12);
        fechaGraduacion = calBis.getTime();
        unSistema.registrarProfesional(nombres, apellidos, titulo, paisObtencionTitulo, fechaNacimiento, fechaGraduacion, null);
        int resultadoObtenido = unSistema.getListaUsuarios().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testRegistrarProfesionalFechaGraduacionInvalida() {
        Sistema unSistema = new Sistema();
        int resultadoEsperado = unSistema.getListaUsuarios().size();
        String nombres = "Juan";
        String apellidos = "";
        String titulo = "Lic. en Nutrición";
        String paisObtencionTitulo = "";
        Date fecha = new Date();
        Date fechaNacimiento;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1981);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 12);
        fechaNacimiento = cal.getTime();

        Date fechaGraduacion = new Date();
        unSistema.registrarProfesional(nombres, apellidos, titulo, paisObtencionTitulo, fechaNacimiento, fechaGraduacion, null);
        int resultadoObtenido = unSistema.getListaUsuarios().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testRegistrarProfesionalYaExistente() {
        Sistema unSistema = new Sistema();
        Profesional unProfesional = new Profesional();
        unProfesional.setNombres("Juan");
        unProfesional.setApellidos("Leivas");
        unProfesional.setTitulo("Lic. en Nutrición");
        unProfesional.setPaisObtencionTitulo("Uruguay");

        Date fechaNacimiento;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1981);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 12);
        fechaNacimiento = cal.getTime();
        unProfesional.setNacimiento(fechaNacimiento);

        Date fechaGraduacion;
        Calendar calBis = Calendar.getInstance();
        calBis.set(Calendar.YEAR, 2011);
        calBis.set(Calendar.DAY_OF_MONTH, 3);
        calBis.set(Calendar.MONTH, 12);
        fechaGraduacion = calBis.getTime();
        unProfesional.setGraduacion(fechaGraduacion);
        unSistema.agregarProfesional(unProfesional);

        // unSistema.registrarProfesional(nombres, apellidos, titulo, paisObtencionTitulo, fechaNacimiento, fechaGraduacion, null);
        int resultadoEsperado = unSistema.getListaProfesionales().size();
        unSistema.registrarProfesional("Juan", "Leivas", "Lic. en Nutrición", "Uruguay", fechaNacimiento, fechaGraduacion, null);
        int resultadoObtenido = unSistema.getListaProfesionales().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testRegistrarProfesionalCorrecto() throws ParseException {
        Sistema unSistema = new Sistema();
        int resultadoEsperado = unSistema.getListaProfesionales().size() + 1;
        String nombres = "Juan";
        String apellidos = "Leivas";
        String titulo = "Lic. en Nutrición";
        String paisObtencionTitulo = "Uruguay";

        Date fechaNacimiento;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1981);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 12);
        fechaNacimiento = cal.getTime();

        Date fechaGraduacion;
        Calendar calBis = Calendar.getInstance();
        calBis.set(Calendar.YEAR, 2011);
        calBis.set(Calendar.DAY_OF_MONTH, 3);
        calBis.set(Calendar.MONTH, 12);
        fechaGraduacion = calBis.getTime();

        unSistema.registrarProfesional(nombres, apellidos, titulo, paisObtencionTitulo, fechaNacimiento, fechaGraduacion, null);
        int resultadoObtenido = unSistema.getListaProfesionales().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }
//--------------------------------------------------------------------------------------------------------------

    @Test
    public void testAgregarAlimento() {
        Sistema unSistema = new Sistema();
        int resultadoEsperado = unSistema.getListaAlimentos().size() + 1;
        Alimento unAlimento = new Alimento();
        unSistema.agregarAlimento(unAlimento);
        int resultadoObtenido = unSistema.getListaAlimentos().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testRegistrarAlimentoNombreVacio() {
        Sistema unSistema = new Sistema();
        int resultadoEsperado = unSistema.getListaAlimentos().size();
        String nombre = "";
        String tipo = "Fruta";
        int nutrientesPrincipales[] = new int[5];
        unSistema.registrarAlimento(nombre, tipo, nutrientesPrincipales);
        int resultadoObtenido = unSistema.getListaAlimentos().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testRegistrarAlimentoYaExistente() {
        Sistema unSistema = new Sistema();
        int resultadoEsperado = unSistema.getListaAlimentos().size() + 1;
        String nombre = "Frutilla";
        String tipo = "Fruta";
        int nutrientesPrincipales[] = new int[5];
        unSistema.registrarAlimento(nombre, tipo, nutrientesPrincipales);
        String otroTipo = "Verdura";
        unSistema.registrarAlimento(nombre, otroTipo, nutrientesPrincipales);
        int resultadoObtenido = unSistema.getListaAlimentos().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testRegistrarAlimentoTipoVacio() {
        Sistema unSistema = new Sistema();
        int resultadoEsperado = unSistema.getListaAlimentos().size();
        String nombre = "Frutilla";
        String tipo = "";
        int nutrientesPrincipales[] = new int[5];
        unSistema.registrarAlimento(nombre, tipo, nutrientesPrincipales);
        int resultadoObtenido = unSistema.getListaAlimentos().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testObtenerUsuarioNoExiste() {
        Sistema unSistema = new Sistema();
        Usuario unUsuario = new Usuario();
        String nombres = "Juan";
        unUsuario.setNombres(nombres);
        String apellidos = "Medina";
        unUsuario.setApellidos(apellidos);
        unUsuario.setNacionalidad("Uruguay");
        boolean preferencias[] = new boolean[5];
        unUsuario.setPreferencias(preferencias);
        boolean restricciones[] = new boolean[5];
        unUsuario.setRestricciones(restricciones);
        Date nacimiento;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1981);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 12);
        nacimiento = cal.getTime();
        unUsuario.setNacimiento(nacimiento);
        unUsuario.setDescripcion("Intolerante a la lactosa");

        unSistema.agregarUsuario(unUsuario);
        assertEquals(null, unSistema.obtenerUsuario("Pedro"));
    }

    @Test
    public void testObtenerUsuarioCorrecto() {
        Sistema unSistema = new Sistema();
        Usuario unUsuario = new Usuario();
        String nombres = "Juan";
        unUsuario.setNombres(nombres);
        String apellidos = "Medina";
        unUsuario.setApellidos(apellidos);
        unUsuario.setNacionalidad("Uruguay");
        boolean preferencias[] = new boolean[5];
        unUsuario.setPreferencias(preferencias);
        boolean restricciones[] = new boolean[5];
        unUsuario.setRestricciones(restricciones);
        Date nacimiento;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1981);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 12);
        nacimiento = cal.getTime();
        unUsuario.setNacimiento(nacimiento);
        unUsuario.setDescripcion("Intolerante a la lactosa");

        unSistema.agregarUsuario(unUsuario);
        assertEquals(unUsuario, unSistema.obtenerUsuario(nombres + " " + apellidos));
    }

    @Test
    public void testObtenerProfesionalCorrecto() {
        Sistema unSistema = new Sistema();
        Profesional unProfesional = new Profesional();
        String nombres = "Pepe";
        unProfesional.setNombres(nombres);
        unProfesional.setApellidos("Leivas");
        unProfesional.setTitulo("Lic. en Nutrición");
        unProfesional.setPaisObtencionTitulo("Uruguay");
        Date fechaNacimiento;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1981);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 12);
        fechaNacimiento = cal.getTime();
        unProfesional.setNacimiento(fechaNacimiento);

        Date fechaGraduacion;
        Calendar calBis = Calendar.getInstance();
        calBis.set(Calendar.YEAR, 2011);
        calBis.set(Calendar.DAY_OF_MONTH, 3);
        calBis.set(Calendar.MONTH, 12);
        fechaGraduacion = calBis.getTime();
        unProfesional.setGraduacion(fechaGraduacion);
        unSistema.agregarProfesional(unProfesional);
        String nombreCompleto = "Pepe Leivas";
        assertEquals(unProfesional, unSistema.obtenerProfesional(nombreCompleto));
    }
//--------------------------------------------------------------------------------------------------------------

    @Test
    public void testAgregarConsulta() {
        Sistema unSistema = new Sistema();
        Profesional unProfesional = new Profesional();
        Consulta unaConsulta = new Consulta();
        unSistema.agregarConsulta(unProfesional, unaConsulta);
        assertTrue(unProfesional.getListaConsultas().contains(unaConsulta));
    }

    @Test(expected = NullPointerException.class)
    public void testAgregarConsultaProfNombreVacio() {
        Sistema unSistema = new Sistema();
        Profesional unProfesional = new Profesional();
        unProfesional.setNombres("Pepe");
        unProfesional.setApellidos("Leivas");
        unProfesional.setTitulo("Lic. en Nutrición");
        unProfesional.setPaisObtencionTitulo("Uruguay");
        Date nacimiento;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1981);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 12);
        nacimiento = cal.getTime();
        unProfesional.setNacimiento(nacimiento);
        Date fechaGraduacion;
        Calendar calBis = Calendar.getInstance();
        calBis.set(Calendar.YEAR, 2011);
        calBis.set(Calendar.DAY_OF_MONTH, 3);
        calBis.set(Calendar.MONTH, 12);
        fechaGraduacion = calBis.getTime();
        unProfesional.setGraduacion(fechaGraduacion);
        unSistema.agregarProfesional(unProfesional);

        String nombreCompleto = " ";
        String descripcion = "Por que alimento puedo sustituir el pollo?";
        Usuario unUsuario = new Usuario();
        Enums.MotivoConsulta motivo = Enums.MotivoConsulta.ALIMENTOSINGERIDOS;
        int resultadoEsperado = unProfesional.getListaConsultas().size() + 1;
        unSistema.agregarConsultaProf(nombreCompleto, descripcion, unUsuario, motivo);
        int resultadoObtenido = unProfesional.getListaConsultas().size();
        assertEquals(resultadoEsperado, resultadoObtenido);

    }

    @Test
    public void testAgregarConsultaProDescripcionVacia() {
        Sistema unSistema = new Sistema();
        Profesional unProfesional = new Profesional();
        String nombreProfesional = "Pepe";
        String descripcion = "";
        Usuario unUsuario = new Usuario();
        Enums.MotivoConsulta motivo = Enums.MotivoConsulta.ALIMENTOSINGERIDOS;
        unSistema.agregarConsultaProf(nombreProfesional, descripcion, unUsuario, motivo);
        assertEquals(0, unProfesional.getListaConsultas().size());

    }

    @Test
    public void testAgregarConsultaProfCorrecto() {
        Sistema unSistema = new Sistema();
        Profesional unProfesional = new Profesional();
        unProfesional.setNombres("Pepe");
        unProfesional.setApellidos("Leivas");
        unProfesional.setTitulo("Lic. en Nutrición");
        unProfesional.setPaisObtencionTitulo("Uruguay");
        Date fechaNacimiento;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1981);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 12);
        fechaNacimiento = cal.getTime();
        unProfesional.setNacimiento(fechaNacimiento);
        Date fechaGraduacion;
        Calendar calBis = Calendar.getInstance();
        calBis.set(Calendar.YEAR, 2011);
        calBis.set(Calendar.DAY_OF_MONTH, 3);
        calBis.set(Calendar.MONTH, 12);
        fechaGraduacion = calBis.getTime();
        unProfesional.setGraduacion(fechaGraduacion);
        unSistema.agregarProfesional(unProfesional);

        Usuario unUsuario = new Usuario();
        unUsuario.setNombres("Pedro");
        unUsuario.setApellidos("Medina");
        unUsuario.setNacionalidad("Uruguay");
        boolean preferencias[] = new boolean[5];
        unUsuario.setPreferencias(preferencias);
        boolean restricciones[] = new boolean[5];
        unUsuario.setRestricciones(restricciones);
        Date nacimientoUsuario;
        Calendar calUsuario = Calendar.getInstance();
        calUsuario.set(Calendar.YEAR, 1981);
        calUsuario.set(Calendar.DAY_OF_MONTH, 3);
        calUsuario.set(Calendar.MONTH, 12);
        nacimientoUsuario = calUsuario.getTime();
        unUsuario.setNacimiento(nacimientoUsuario);
        unUsuario.setDescripcion("Intolerante a la lactosa");
        unSistema.agregarUsuario(unUsuario);

        int resultadoEsperado = unProfesional.getListaConsultas().size() + 1;
        String descripcionConsulta = "Por que alimento puedo sustituir el pollo?";
        Enums.MotivoConsulta motivo = Enums.MotivoConsulta.ALIMENTOSINGERIDOS;
        unSistema.agregarConsultaProf("Pepe Leivas", descripcionConsulta, unUsuario, motivo);
        int resultadoObtenido = unProfesional.getListaConsultas().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

//--------------------------------------------------------------------------------------------------------------
    @Test
    public void testAgregarAlimentoUsuario() {
        Sistema unSistema = new Sistema();
        Alimento unAlimento = new Alimento();
        String nombreAlimento = "Frutilla";
        unAlimento.setNombre(nombreAlimento);
        unAlimento.setTipo("Fruta");
        int nutrientesPrincipales[] = new int[5];
        unAlimento.setNutrientesPrincipales(nutrientesPrincipales);
        unSistema.agregarAlimento(unAlimento);

        Date fechaDeConsumoAlimento;
        Calendar calAlimento = Calendar.getInstance();
        calAlimento.set(Calendar.YEAR, 1981);
        calAlimento.set(Calendar.DAY_OF_MONTH, 3);
        calAlimento.set(Calendar.MONTH, 12);
        fechaDeConsumoAlimento = calAlimento.getTime();

        Usuario unUsuario = new Usuario();
        unUsuario.setNombres("Juan");
        unUsuario.setApellidos("Medina");
        unUsuario.setNacionalidad("Uruguay");
        boolean preferencias[] = new boolean[5];
        unUsuario.setPreferencias(preferencias);
        boolean restricciones[] = new boolean[5];
        unUsuario.setRestricciones(restricciones);
        Date nacimiento;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1981);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 12);
        nacimiento = cal.getTime();

        unUsuario.setNacimiento(nacimiento);
        unUsuario.setDescripcion("Intolerante a la lactosa");
        unSistema.agregarUsuario(unUsuario);

        AlimentoIngerido unAlimentoIngerido = new AlimentoIngerido();
        unAlimentoIngerido.setAlimentoIngeridoUsuario(unAlimento);
        unAlimentoIngerido.setFecha(fechaDeConsumoAlimento);
        int resultadoEsperado = unUsuario.getAlimentosIngeridos().size() + 1;
        unSistema.agregarAlimentoUsuario(unUsuario, nombreAlimento, fechaDeConsumoAlimento);
        int resultadoObtenido = unUsuario.getAlimentosIngeridos().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testBorrarAlimentoUsuario() {
        Sistema unSistema = new Sistema();
        Usuario unUsuario = new Usuario();
        unUsuario.setNombres("Juan");
        unUsuario.setApellidos("Medina");
        unUsuario.setNacionalidad("Uruguay");
        boolean preferencias[] = new boolean[5];
        unUsuario.setPreferencias(preferencias);
        boolean restricciones[] = new boolean[5];
        unUsuario.setRestricciones(restricciones);
        Date nacimiento;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1981);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 12);
        nacimiento = cal.getTime();
        unUsuario.setNacimiento(nacimiento);
        unUsuario.setDescripcion("Intolerante a la lactosa");
        unSistema.agregarUsuario(unUsuario);

        Alimento unAlimento = new Alimento();
        unAlimento.setNombre("Frutilla");
        unAlimento.setTipo("Fruta");
        int nutrientesPrincipales[] = new int[5];
        unAlimento.setNutrientesPrincipales(nutrientesPrincipales);

        ArrayList<AlimentoIngerido> lista = new ArrayList<AlimentoIngerido>();
        unUsuario.setAlimentosIngeridos(lista);
        AlimentoIngerido unAlimentoIngerido = new AlimentoIngerido();
        unAlimentoIngerido.setAlimentoIngeridoUsuario(unAlimento);
        unAlimentoIngerido.setFecha(new Date());
        lista.add(unAlimentoIngerido);
        unSistema.agregarAlimentoUsuario(unUsuario, unAlimento.getNombre(), new Date());
        int resultadoEsperado = unUsuario.getAlimentosIngeridos().size() - 1;
        unSistema.borrarAlimentoUsuario(unUsuario, "Frutilla");
        int resultadoObtenido = unUsuario.getAlimentosIngeridos().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testAgregarSolicitud() {
        Sistema unSistema = new Sistema();
        Profesional unProfesional = new Profesional();
        unProfesional.setNombres("Pepe");
        unProfesional.setApellidos("Leivas");
        unProfesional.setTitulo("Lic. en Nutrición");
        unProfesional.setPaisObtencionTitulo("Uruguay");
        Date fechaNacimiento;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1981);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 12);
        fechaNacimiento = cal.getTime();
        unProfesional.setNacimiento(fechaNacimiento);

        Date fechaGraduacion;
        Calendar calBis = Calendar.getInstance();
        calBis.set(Calendar.YEAR, 2011);
        calBis.set(Calendar.DAY_OF_MONTH, 3);
        calBis.set(Calendar.MONTH, 12);
        fechaGraduacion = calBis.getTime();
        unProfesional.setGraduacion(fechaGraduacion);
        unSistema.agregarProfesional(unProfesional);
        PlanAlimentacion unPlan = new PlanAlimentacion();
        unSistema.agregarSolicitud(unProfesional, unPlan);
        assertTrue(unProfesional.getListaSolicitudesDePlanes().contains(unPlan));
    }

    @Test
    public void testConvertirStringNombreProfesionalEnProfesional() {
        Sistema unSistema = new Sistema();
        Profesional unProfesional = new Profesional();
        String nombreProfesional = "Pepe";
        unProfesional.setNombres(nombreProfesional);
        String apellidoProfesional = "Leivas";
        unProfesional.setApellidos(apellidoProfesional);
        unProfesional.setTitulo("Lic. en Nutrición");
        unProfesional.setPaisObtencionTitulo("Uruguay");
        Date fechaNacimiento;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1981);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 12);
        fechaNacimiento = cal.getTime();
        unProfesional.setNacimiento(fechaNacimiento);

        Date fechaGraduacion;
        Calendar calBis = Calendar.getInstance();
        calBis.set(Calendar.YEAR, 2011);
        calBis.set(Calendar.DAY_OF_MONTH, 3);
        calBis.set(Calendar.MONTH, 12);
        fechaGraduacion = calBis.getTime();
        unProfesional.setGraduacion(fechaGraduacion);
        unSistema.agregarProfesional(unProfesional);

        String nombreCompleto = nombreProfesional + " " + apellidoProfesional;

        Profesional resultadoObtenido = unSistema.convertirStringNombreProfesionalEnProfesional(nombreCompleto);

        assertEquals(unProfesional, resultadoObtenido);
    }

    @Test
    public void testSolicitarPlanUsuarioNoExiste() {
        Sistema unSistema = new Sistema();
        Usuario unUsuario = new Usuario();
        Profesional unProfesional = new Profesional();
        int peso = 60;
        int altura = 156;
        int horasDeActividad = 2;
        String detalles = "";
        unSistema.solicitarPlan(unUsuario, unProfesional, peso, altura, horasDeActividad, detalles);
        assertEquals(0, unProfesional.getListaSolicitudesDePlanes().size());
    }

    @Test
    public void testSolicitarPlanUsuarioSiExiste() {
        Sistema unSistema = new Sistema();
        Usuario unUsuario = new Usuario();
        unUsuario.setNombres("Juan");
        unUsuario.setApellidos("Medina");
        unUsuario.setNacionalidad("Uruguay");
        boolean preferencias[] = new boolean[5];
        unUsuario.setPreferencias(preferencias);
        boolean restricciones[] = new boolean[5];
        unUsuario.setRestricciones(restricciones);
        Date nacimiento;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1981);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 12);
        nacimiento = cal.getTime();
        unUsuario.setNacimiento(nacimiento);
        unUsuario.setDescripcion("Intolerante a la lactosa");
        unSistema.agregarUsuario(unUsuario);

        Profesional unProfesional = new Profesional();
        unProfesional.setNombres("Pepe");
        unProfesional.setApellidos("Leivas");
        unProfesional.setTitulo("Lic. en Nutrición");
        unProfesional.setPaisObtencionTitulo("Uruguay");
        Date nacimientoProf;
        Calendar calProf = Calendar.getInstance();
        calProf.set(Calendar.YEAR, 1981);
        calProf.set(Calendar.DAY_OF_MONTH, 3);
        calProf.set(Calendar.MONTH, 12);
        nacimientoProf = calProf.getTime();
        unProfesional.setNacimiento(nacimientoProf);
        Date fechaGraduacion;
        Calendar calBis = Calendar.getInstance();
        calBis.set(Calendar.YEAR, 2011);
        calBis.set(Calendar.DAY_OF_MONTH, 3);
        calBis.set(Calendar.MONTH, 12);
        fechaGraduacion = calBis.getTime();
        unProfesional.setGraduacion(fechaGraduacion);
        unSistema.agregarProfesional(unProfesional);

        int peso = 60;
        int altura = 156;
        int horasDeActividad = 2;
        String detalles = "Quisiera mantener mi peso acutal";
        int resultadoEsperado = unProfesional.getListaSolicitudesDePlanes().size() + 1;
        unSistema.solicitarPlan(unUsuario, unProfesional, peso, altura, horasDeActividad, detalles);
        int resultadoObtenido = unProfesional.getListaSolicitudesDePlanes().size();
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    public void testValidarCampoTxtNoEsVacioSiEs() {
        Sistema unSistema = new Sistema();
        String texto = "";
        assertTrue(unSistema.validarStringSinNumero(texto));
    }

    @Test
    public void testValidarCampoTxtNoEsVacioNoEs() {
        Sistema unSistema = new Sistema();
        String texto = "Texto de prueba";
        assertTrue(unSistema.validarStringSinNumero(texto));
    }

    @Test
    public void testValidarStringSinNumeroVacio() {
        Sistema unSistema = new Sistema();
        String texto = "";
        assertTrue(unSistema.validarStringSinNumero(texto));
    }

    @Test
    public void testValidarStringSinNumeroConNumeros() {
        Sistema unSistema = new Sistema();
        String texto = "Text0 d3 pru3ba";
        assertFalse(unSistema.validarStringSinNumero(texto));
    }

    @Test
    public void testValidarStringSinNumeroSinNumero() {
        Sistema unSistema = new Sistema();
        String texto = "Texto de prueba";
        assertTrue(unSistema.validarStringSinNumero(texto));
    }

    @Test
    public void testValidarFecha() {
        Sistema unSistema = new Sistema();
        Date fecha = new Date();
        assertFalse(unSistema.validarFecha(fecha, 0));
    }

    @Test
    public void testValidarPesoNegativo() {
        Sistema unSistema = new Sistema();
        int peso = -3;
        assertFalse(unSistema.validarPeso(peso));
    }

    @Test
    public void testValidarPesoCero() {
        Sistema unSistema = new Sistema();
        int peso = 0;
        assertFalse(unSistema.validarPeso(peso));
    }

    @Test
    public void testValidarPesoPositivo() {
        Sistema unSistema = new Sistema();
        int peso = 60;
        assertTrue(unSistema.validarPeso(peso));
    }

    @Test
    public void testValidarPesoMuyGrande() {
        Sistema unSistema = new Sistema();
        int peso = 600;
        assertFalse(unSistema.validarPeso(peso));
    }

    @Test
    public void testValidarAltura() {
        Sistema unSistema = new Sistema();
        int horas = -3;
        assertFalse(unSistema.validarAltura(horas));
    }

    @Test
    public void testValidarCero() {
        Sistema unSistema = new Sistema();
        int horas = 0;
        assertFalse(unSistema.validarAltura(horas));
    }

    @Test
    public void testValidarAlturaPositiva() {
        Sistema unSistema = new Sistema();
        int horas = 168;
        assertTrue(unSistema.validarAltura(horas));
    }

    @Test
    public void testValidarAlturaMuyGrande() {
        Sistema unSistema = new Sistema();
        int horas = 251;
        assertFalse(unSistema.validarAltura(horas));
    }

    @Test
    public void testValidarHorasNegativa() {
        Sistema unSistema = new Sistema();
        int horas = -3;
        assertFalse(unSistema.validarHoras(horas));
    }

    @Test
    public void testValidarHorasCero() {
        Sistema unSistema = new Sistema();
        int horas = 0;
        assertFalse(unSistema.validarHoras(horas));
    }

    @Test
    public void testValidarHorasPositiva() {
        Sistema unSistema = new Sistema();
        int horas = 4;
        assertTrue(unSistema.validarHoras(horas));
    }

    @Test
    public void testValidarHorasSePasaDeUnDia() {
        Sistema unSistema = new Sistema();
        int horas = 25;
        assertFalse(unSistema.validarHoras(horas));
    }

    @Test
    public void testValidarUsuarioAgregado() {
        Sistema unSistema = new Sistema();
        Usuario unUsuario = new Usuario();
        unUsuario.setNombres("Juan");
        unUsuario.setApellidos("Lopez");
        unSistema.agregarUsuario(unUsuario);
        assertTrue(unSistema.validarUsuario(unUsuario));
    }

    @Test
    public void testValidarUsuarioSinAgregar() {
        Sistema unSistema = new Sistema();
        Usuario unUsuario = new Usuario();
        unUsuario.setNombres("Juan");
        unUsuario.setApellidos("Lopez");
        assertFalse(unSistema.validarUsuario(unUsuario));
    }

    @Test
    public void testValidarProfesionalAgregado() {
        Sistema unSistema = new Sistema();
        Profesional unProfesional = new Profesional();
        unProfesional.setNombres("Juan");
        unProfesional.setApellidos("Lopez");
        unSistema.agregarProfesional(unProfesional);
        assertTrue(unSistema.validarProfesional(unProfesional));
    }

    @Test
    public void testValidarProfesionalSinAgregar() {
        Sistema unSistema = new Sistema();
        Profesional unProfesional = new Profesional();
        unProfesional.setNombres("Juan");
        unProfesional.setApellidos("Lopez");
        assertFalse(unSistema.validarProfesional(unProfesional));
    }

    @Test
    public void testPlanAlimentacionUsuario() {
        Sistema unSistema = new Sistema();
        Usuario unUsuario = new Usuario();
        unUsuario.setNombres("Juan");
        unUsuario.setApellidos("Medina");
        unUsuario.setNacionalidad("Uruguay");
        boolean preferencias[] = new boolean[5];
        unUsuario.setPreferencias(preferencias);
        boolean restricciones[] = new boolean[5];
        unUsuario.setRestricciones(restricciones);
        Date nacimiento;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1981);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 12);
        nacimiento = cal.getTime();
        unUsuario.setNacimiento(nacimiento);
        unUsuario.setDescripcion("Intolerante a la lactosa");
        unSistema.agregarUsuario(unUsuario);
        String nombreCompleto = unUsuario.toString();

        Profesional unProfesional = new Profesional();
        unProfesional.setNombres("Juan");
        unProfesional.setApellidos("Leivas");
        unProfesional.setTitulo("Lic. en Nutrición");
        unProfesional.setPaisObtencionTitulo("Uruguay");
        Date nacimientoProf;
        Calendar calProf = Calendar.getInstance();
        calProf.set(Calendar.YEAR, 1981);
        calProf.set(Calendar.DAY_OF_MONTH, 3);
        calProf.set(Calendar.MONTH, 12);
        nacimientoProf = calProf.getTime();
        unProfesional.setNacimiento(nacimientoProf);

        Date graduacion;
        Calendar calG = Calendar.getInstance();
        calG.set(Calendar.YEAR, 1981);
        calG.set(Calendar.DAY_OF_MONTH, 3);
        calG.set(Calendar.MONTH, 12);
        graduacion = calG.getTime();
        unProfesional.setGraduacion(graduacion);
        unSistema.agregarProfesional(unProfesional);

        String[][] matrizEsperada = new String[4][8];
        String esperado = matrizEsperada[1][1] = "Licuado con tostadas";

        PlanAlimentacion unPlan = new PlanAlimentacion();
        String desayuno[] = new String[8];
        desayuno[1] = "Licuado con tostadas";
        desayuno[2] = "Licuado con tostadas";
        desayuno[3] = "Licuado con tostadas";
        desayuno[4] = "Licuado con tostadas";
        desayuno[5] = "Licuado con tostadas";
        desayuno[6] = "Licuado con tostadas";
        desayuno[7] = "Licuado con tostadas";
        unPlan.setDesayuno(desayuno);
        unPlan.setAlmuerzo(desayuno);
        unPlan.setMerienda(desayuno);
        unPlan.setCena(desayuno);
        unPlan.setUsuario(unUsuario);
        ArrayList<PlanAlimentacion> lista = new ArrayList<PlanAlimentacion>();
        lista.add(unPlan);
        unProfesional.setListaSolicitudesDePlanes(lista);
        unSistema.agregarSolicitud(unProfesional, unPlan);
        String obtenido = unSistema.planAlimentacionUsuario(nombreCompleto)[1][1];

        assertEquals(esperado, obtenido);
    }

    @Test
    public void testTodasConsultasDeUnUsuario() {
        Sistema unSistema = new Sistema();
        Usuario unUsuario = new Usuario();
        unUsuario.setNombres("Juan");
        unUsuario.setApellidos("Medina");
        unUsuario.setNacionalidad("Uruguay");
        boolean preferencias[] = new boolean[5];
        unUsuario.setPreferencias(preferencias);
        boolean restricciones[] = new boolean[5];
        unUsuario.setRestricciones(restricciones);
        Date nacimiento;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1981);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 12);
        nacimiento = cal.getTime();
        unUsuario.setNacimiento(nacimiento);
        unUsuario.setDescripcion("Intolerante a la lactosa");
        unSistema.agregarUsuario(unUsuario);

        Profesional unProfesional = new Profesional();
        unProfesional.setNombres("Pepe");
        unProfesional.setApellidos("Leivas");
        unProfesional.setTitulo("Lic. en Nutrición");
        unProfesional.setPaisObtencionTitulo("Uruguay");
        Date nacimientoP;
        Calendar calP = Calendar.getInstance();
        calP.set(Calendar.YEAR, 1981);
        calP.set(Calendar.DAY_OF_MONTH, 3);
        calP.set(Calendar.MONTH, 12);
        nacimientoP = calP.getTime();
        unProfesional.setNacimiento(nacimientoP);
        Date fechaGraduacion;
        Calendar calBis = Calendar.getInstance();
        calBis.set(Calendar.YEAR, 2011);
        calBis.set(Calendar.DAY_OF_MONTH, 3);
        calBis.set(Calendar.MONTH, 12);
        fechaGraduacion = calBis.getTime();
        unProfesional.setGraduacion(fechaGraduacion);
        unSistema.agregarProfesional(unProfesional);

        Consulta unaConsulta = new Consulta();
        unaConsulta.setDescripcion("Puedo sustituir la leche de vaca por leche de cabra?");
        unaConsulta.setEstado(true);
        unaConsulta.setFecha(new Date());
        unaConsulta.setMotivo(Enums.MotivoConsulta.OTROS);
        unaConsulta.setRespuesta("No");
        unaConsulta.setUsuarioDeConsulta(unUsuario);
        unSistema.agregarConsulta(unProfesional, unaConsulta);

        ArrayList<ParProfesionalConsulta> listaEsperada = new ArrayList<ParProfesionalConsulta>();
        ParProfesionalConsulta unPar = new ParProfesionalConsulta();
        unPar.setProfesional(unProfesional);
        unPar.setConsulta(unaConsulta);
        listaEsperada.add(unPar);
        String nombreCompleto = unUsuario.toString();
        assertEquals(listaEsperada, unSistema.todasConsultasDeUnUsuario(nombreCompleto));
    }

    @Test
    public void testObtenerEstadoPlanAlimentacionDadoNombreUsuario() {
        Sistema unSistema = new Sistema();

        Usuario unUsuario = new Usuario();
        unUsuario.setNombres("Juan");
        unUsuario.setApellidos("Medina");
        unUsuario.setNacionalidad("Uruguay");
        boolean preferencias[] = new boolean[5];
        unUsuario.setPreferencias(preferencias);
        boolean restricciones[] = new boolean[5];
        unUsuario.setRestricciones(restricciones);
        Date nacimiento;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1981);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 12);
        nacimiento = cal.getTime();
        unUsuario.setNacimiento(nacimiento);
        unUsuario.setDescripcion("Intolerante a la lactosa");
        unSistema.agregarUsuario(unUsuario);
        String nombreCompleto = unUsuario.toString();

        Profesional unProfesional = new Profesional();
        unProfesional.setNombres("Juan");
        unProfesional.setApellidos("Leivas");
        unProfesional.setTitulo("Lic. en Nutrición");
        unProfesional.setPaisObtencionTitulo("Uruguay");
        Date nacimientoProf;
        Calendar calProf = Calendar.getInstance();
        calProf.set(Calendar.YEAR, 1981);
        calProf.set(Calendar.DAY_OF_MONTH, 3);
        calProf.set(Calendar.MONTH, 12);
        nacimientoProf = calProf.getTime();
        unProfesional.setNacimiento(nacimientoProf);

        Date graduacion;
        Calendar calG = Calendar.getInstance();
        calG.set(Calendar.YEAR, 1981);
        calG.set(Calendar.DAY_OF_MONTH, 3);
        calG.set(Calendar.MONTH, 12);
        graduacion = calG.getTime();
        unProfesional.setGraduacion(graduacion);
        unSistema.agregarProfesional(unProfesional);

        PlanAlimentacion unPlan = new PlanAlimentacion();
        String desayuno[] = new String[8];
        desayuno[1] = "Licuado con tostadas";
        desayuno[2] = "Licuado con tostadas";
        desayuno[3] = "Licuado con tostadas";
        desayuno[4] = "Licuado con tostadas";
        desayuno[5] = "Licuado con tostadas";
        desayuno[6] = "Licuado con tostadas";
        desayuno[7] = "Licuado con tostadas";
        unPlan.setDesayuno(desayuno);
        unPlan.setAlmuerzo(desayuno);
        unPlan.setMerienda(desayuno);
        unPlan.setCena(desayuno);
        unPlan.setUsuario(unUsuario);
        unPlan.setEstado(false);
        ArrayList<PlanAlimentacion> lista = new ArrayList<PlanAlimentacion>();
        lista.add(unPlan);
        unProfesional.setListaSolicitudesDePlanes(lista);
        unSistema.agregarSolicitud(unProfesional, unPlan);

        boolean resultadoObtenido = unSistema.obtenerEstadoPlanAlimentacionDadoNombreUsuario(nombreCompleto);
        assertTrue(resultadoObtenido);

    }
}
