/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.ParProfesionalConsulta;
import domain.Consulta;
import domain.Profesional;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author
 */
public class ParProfesionalConsultaTest {

    public ParProfesionalConsultaTest() {
    }

    @Test
    public void testGetProfesional() {
        ParProfesionalConsulta unPar = new ParProfesionalConsulta();
        Profesional unProfesional = new Profesional();
        unProfesional.setNombres("Pepe");
        unPar.setProfesional(unProfesional);
        assertEquals(unProfesional, unPar.getProfesional());
    }

    @Test
    public void testGetConsulta() {
        ParProfesionalConsulta unPar = new ParProfesionalConsulta();
        Consulta unaConsulta = new Consulta();
        unaConsulta.setEstado(true);
        unPar.setConsulta(unaConsulta);
        assertEquals(unaConsulta, unPar.getConsulta());
    }

}