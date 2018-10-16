package domain;

import domain.Usuario;
import domain.Consulta;
import domain.Profesional;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import domain.Sistema;
import java.util.Date;
import domain.Enums;

public class ConsultaTest {

    private Sistema unSistema;

    public ConsultaTest() {
    }

    @Before
    public void setUp() {
        unSistema = new Sistema();
    }
    //  public ArrayList<Consulta> obtenerConsultadDeProfesional(){

    // }
    @Test
    public void testAgregarConsultaProfNombreVacio() {
        //Creo el profesional

//    private ArrayList<Consulta> listaConsultas = new ArrayList<Consulta>();
//    private ArrayList<PlanAlimentacion> listaSolicitudesDePlanes = new ArrayList<PlanAlimentacion>();
        Profesional unProfesional = new Profesional();
        unProfesional.setNombres("Pepe");
        unProfesional.setApellidos("Grillo");
        unProfesional.setNacimiento(new Date());
        unProfesional.setTitulo("Lic. en Nutricion");
        unProfesional.setPaisObtencionTitulo("Uruguay");
        unProfesional.setGraduacion(new Date());

        //Creo al usuario
        Usuario unUsuario = new Usuario();
        unUsuario.setNombres("Alberto");
        unUsuario.setApellidos("Arzuaga");
        unUsuario.setNacionalidad("Uruguay");

        int tamanoInicial = unSistema.getListaUsuarios().size();
        int resultadoEsperado = tamanoInicial;

        String nombre = "";
        String descripcion = "Hola quiero que me haga una dieta";
        Usuario usuarioDeConsulta = null;
        Enums.MotivoConsulta motivo = Enums.MotivoConsulta.ALIMENTOSINGERIDOS;
        unSistema.agregarConsultaProf(nombre, descripcion, usuarioDeConsulta, motivo);

        int resutladoObtenido = unSistema.getListaUsuarios().size();
        assertEquals(resultadoEsperado, resutladoObtenido);
    }

    @Test
    public void testAgregarConsultaProfDescripcionVacia() {
        int tamanoInicial = unSistema.getListaUsuarios().size();
        int resultadoEsperado = tamanoInicial;
        String nombre = "";
        String descripcion = "";
        Usuario usuarioDeConsulta = null;
        Enums.MotivoConsulta motivo = Enums.MotivoConsulta.ALIMENTOSINGERIDOS;
        unSistema.agregarConsultaProf(descripcion, descripcion, usuarioDeConsulta, motivo);
        int resutladoObtenido = unSistema.getListaUsuarios().size();
        assertEquals(resultadoEsperado, resutladoObtenido);
    }

    @Test
    public void testAgregarConsultaProfCorrecto() {
        int tamanoInicial = unSistema.getListaUsuarios().size();
        int resultadoEsperado = tamanoInicial;
        String nombre = "";
        String descripcion = "";
        Usuario usuarioDeConsulta = null;
        Enums.MotivoConsulta motivo = Enums.MotivoConsulta.ALIMENTOSINGERIDOS;
        unSistema.agregarConsultaProf(descripcion, descripcion, usuarioDeConsulta, motivo);
        int resutladoObtenido = unSistema.getListaUsuarios().size();
        assertEquals(resultadoEsperado, resutladoObtenido);
    }

    @Test
    public void testGetRespuesta() {
        Consulta unaConsulta = new Consulta();
        String respuesta = "Sustituye las dos mandarinas de la mañana por una banana";
        unaConsulta.setRespuesta(respuesta);
        assertEquals(respuesta, unaConsulta.getRespuesta());
    }

    @Test
    public void testGetFecha() {
        Consulta unaConsulta = new Consulta();
        Date fechaConsulta = new Date();
        unaConsulta.setFecha(fechaConsulta);
        assertEquals(fechaConsulta, unaConsulta.getFecha());
    }

    @Test
    public void testIsEstado() {
        Consulta unaConsulta = new Consulta();
        boolean estado = true;
        unaConsulta.setEstado(estado);
        assertTrue(unaConsulta.isEstado());
    }

    @Test
    public void testGetMotivo() {
        Consulta unaConsulta = new Consulta();
        Enums.MotivoConsulta motivo = Enums.MotivoConsulta.ALIMENTOSINGERIR;
        unaConsulta.setMotivo(motivo);
        assertEquals(motivo, unaConsulta.getMotivo());
    }

    @Test
    public void testGetDescripcion() {
        Consulta unaConsulta = new Consulta();
        String descripcion = "Las mandarinas a la mañana no me caen bien";
        unaConsulta.setDescripcion(descripcion);
        assertEquals(descripcion, unaConsulta.getDescripcion());
    }

    @Test
    public void testGetUsuarioDeConsulta() {
        Consulta unaConsulta = new Consulta();
        Usuario unUsuario = new Usuario();
        unUsuario.setNombres("Juan");
        unaConsulta.setUsuarioDeConsulta(unUsuario);
        assertEquals(unUsuario, unaConsulta.getUsuarioDeConsulta());
    }
}