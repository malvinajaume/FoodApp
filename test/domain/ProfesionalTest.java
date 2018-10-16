package domain;

import domain.PlanAlimentacion;
import domain.Consulta;
import domain.Profesional;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import domain.Sistema;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.Icon;

public class ProfesionalTest {

    private Sistema unSistema;

    public ProfesionalTest() {
    }

    @Before
    public void setUp() {
        unSistema = new Sistema();
    }

    @Test
    public void testRegistrarProfesionalNombreVacio() {
        int tamanoInicial = unSistema.getListaProfesionales().size();
        int resultadoEsperado = tamanoInicial;

        String nombres = "";
        String apellidos = "Rodríguez";
        Date fechaNacimiento = new Date();
        String titulo = "Lic. en Nutrición";
        String paisObtencionTitulo = "Uruguay";
        Date fechaGraduacion = new Date();

        unSistema.registrarProfesional(nombres, apellidos, titulo, paisObtencionTitulo, fechaNacimiento, fechaGraduacion, null);

        int resutladoObtenido = unSistema.getListaProfesionales().size();

        assertEquals(resultadoEsperado, resutladoObtenido);

    }

    @Test
    public void testGetTitulo() {
        Profesional unProfesional = new Profesional();
        String titulo = "Licenciado en Nutrición";
        unProfesional.setTitulo(titulo);
        assertEquals(titulo, unProfesional.getTitulo());

    }

    @Test
    public void testGetPaisObtencionTitulo() {
        Profesional unProfesional = new Profesional();
        String paisObtencionTitulo = "Uruguay";
        unProfesional.setPaisObtencionTitulo(paisObtencionTitulo);
        assertEquals(paisObtencionTitulo, unProfesional.getPaisObtencionTitulo());
    }

    @Test
    public void testGetGraduacion() {
        Profesional unProfesional = new Profesional();
        Date graduacion = new Date();
        unProfesional.setGraduacion(graduacion);
        assertEquals(graduacion, unProfesional.getGraduacion());
    }

    @Test
    public void testGetListaConsultas() {
        Profesional unProfesional = new Profesional();
        ArrayList<Consulta> listaConsultas = new ArrayList<Consulta>();
        Consulta unaConsulta = new Consulta();
        unaConsulta.setDescripcion("El queso me dio alergia");
        listaConsultas.add(unaConsulta);
        unProfesional.setListaConsultas(listaConsultas);
        assertEquals(listaConsultas, unProfesional.getListaConsultas());
    }

    @Test
    public void testGetListaSolicitudesDePlanes() {
        Profesional unProfesional = new Profesional();
        ArrayList<PlanAlimentacion> listaPlanes = new ArrayList<PlanAlimentacion>();
        PlanAlimentacion unPlan = new PlanAlimentacion();
        unPlan.setPeso(62);
        listaPlanes.add(unPlan);
        unProfesional.setListaSolicitudesDePlanes(listaPlanes);
        assertEquals(listaPlanes, unProfesional.getListaSolicitudesDePlanes());
    }

    @Test
    public void testAvatar(){
        Profesional unProfesional = new Profesional();
        Icon avatar = null;
        unProfesional.setAvatar(avatar);
        assertEquals(avatar,unProfesional.getAvatar());
    }
    @Test
    public void testToString() {
        Profesional unProfesional = new Profesional();
        String nombre ="María Elena";
        String apellido = "Fuseneco";
        unProfesional.setNombres(nombre);
        unProfesional.setApellidos(apellido);
        String resultadoEsperado = nombre+apellido;
        assertEquals(resultadoEsperado, unProfesional.toString());
    }

}