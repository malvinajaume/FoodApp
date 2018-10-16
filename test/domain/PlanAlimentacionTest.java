package domain;

import domain.Usuario;
import domain.PlanAlimentacion;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlanAlimentacionTest {

    public PlanAlimentacionTest() {
    }

    @Test
    public void testGetUsuario() {
        PlanAlimentacion unPlan = new PlanAlimentacion();
        Usuario unUsuario = new Usuario();
        unUsuario.setNombres("Juan");
        unPlan.setUsuario(unUsuario);
        assertEquals(unUsuario, unPlan.getUsuario());

    }

    @Test
    public void testGetPeso() {
        PlanAlimentacion unPlan = new PlanAlimentacion();
        int peso = 62;
        unPlan.setPeso(peso);
        assertEquals(peso, unPlan.getPeso());
    }

    @Test
    public void testGetAltura() {
        PlanAlimentacion unPlan = new PlanAlimentacion();
        int altura = 168;
        unPlan.setAltura(altura);
        assertEquals(altura, unPlan.getAltura());
    }

    @Test
    public void testGetHorasDeActividad() {
        PlanAlimentacion unPlan = new PlanAlimentacion();
        int horas = 1;
        unPlan.setHorasDeActividad(horas);
        assertEquals(horas, unPlan.getHorasDeActividad());
    }

    @Test
    public void testGetDetalle() {
        PlanAlimentacion unPlan = new PlanAlimentacion();
        String detalle = "Soy alergico a las frutillas";
        unPlan.setDetalle(detalle);
        assertEquals(detalle, unPlan.getDetalle());
    }

    @Test
    public void testIsEstado() {
        PlanAlimentacion unPlan = new PlanAlimentacion();
        boolean estado = true;
        unPlan.setEstado(estado);
        assertTrue(unPlan.isEstado());
    }

    @Test
    public void testGetAlimentosDiaSemana() {
        PlanAlimentacion unPlan = new PlanAlimentacion();
        String alimentosDiaSemana[][] = new String[4][8];
        alimentosDiaSemana[3][7] = "Tarta de jamón y queso";
        unPlan.setAlimentosDiaSemana(alimentosDiaSemana);
        assertArrayEquals(alimentosDiaSemana, unPlan.getAlimentosDiaSemana());

    }

    @Test
    public void testGetDesayuno() {
        PlanAlimentacion unPlan = new PlanAlimentacion();
        String desayuno[] = new String[7];
        desayuno[1] = "Tostadas con yogurt";
        unPlan.setDesayuno(desayuno);
        assertArrayEquals(desayuno, unPlan.getDesayuno());
    }

    @Test
    public void testGetAlmuerzo() {
        PlanAlimentacion unPlan = new PlanAlimentacion();
        String almuerzo[] = new String[7];
        almuerzo[0] = "Milanesa de carne con puré de zapallo";
        unPlan.setAlmuerzo(almuerzo);
        assertArrayEquals(almuerzo, unPlan.getAlmuerzo());
    }

    @Test
    public void testGetMerienda() {
        PlanAlimentacion unPlan = new PlanAlimentacion();
        String merienda[] = new String[7];
        merienda[0] = "Licuado de frutas con tostadas";
        unPlan.setMerienda(merienda);
        assertArrayEquals(merienda, unPlan.getMerienda());
    }

    @Test
    public void testGetCena() {
        PlanAlimentacion unPlan = new PlanAlimentacion();
        String cena[] = new String[7];
        cena[0] = "Ensalada cesar";
        unPlan.setCena(cena);
        assertArrayEquals(cena, unPlan.getCena());
    }

}
