package domain;

import domain.Enums;
import domain.Consulta;
import domain.Enums.MotivoConsulta;
import org.junit.Test;
import static org.junit.Assert.*;

public class EnumsTest {

    public EnumsTest() {
    }

    @Test
    public void testSomeMethod() {
    }

    @Test
    public void testNutrientesPrincipalesAntioxidantesValueOfAntioxidantes() {
        assertEquals("ANTIOXIDANTES", Enums.NutrientesPrincipales.valueOf("ANTIOXIDANTES").toString());
    }

    @Test
    public void testNutrientesPrincipalesAntioxidantesValueOfCarbohidratos() {
        assertEquals("CARBOHIDRATOS", Enums.NutrientesPrincipales.valueOf("CARBOHIDRATOS").toString());
    }

    @Test
    public void testNutrientesPrincipalesAntioxidantesValueOfMinerales() {
        assertEquals("MINERALES", Enums.NutrientesPrincipales.valueOf("MINERALES").toString());
    }

    @Test
    public void testNutrientesPrincipalesAntioxidantesValueOfProteinas() {
        assertEquals("PROTEINAS", Enums.NutrientesPrincipales.valueOf("PROTEINAS").toString());
    }

    @Test
    public void testNutrientesPrincipalesAntioxidantesValueOfVitaminas() {
        assertEquals("VITAMINAS", Enums.NutrientesPrincipales.valueOf("VITAMINAS").toString());
    }

    @Test
    public void testNutrientesPrincipalesAntioxidantes() {
        assertEquals("ANTIOXIDANTES", Enums.NutrientesPrincipales.ANTIOXIDANTES.toString());
    }

    @Test
    public void testNutrientesPrincipalesCarbohidratos() {
        assertEquals("CARBOHIDRATOS", Enums.NutrientesPrincipales.CARBOHIDRATOS.toString());
    }

    @Test
    public void testNutrientesPrincipalesMinerales() {
        assertEquals("MINERALES", Enums.NutrientesPrincipales.MINERALES.toString());
    }

    @Test
    public void testNutrientesPrincipalesProteinas() {
        assertEquals("PROTEINAS", Enums.NutrientesPrincipales.PROTEINAS.toString());
    }

    @Test
    public void testNutrientesPrincipalesVitaminas() {
        assertEquals("VITAMINAS", Enums.NutrientesPrincipales.VITAMINAS.toString());
    }

    @Test
    public void testNutrientesPrincipales() {
        Enums.MotivoConsulta unEnum = Enums.MotivoConsulta.ALIMENTOSINGERIR;
        assertEquals(unEnum, Enums.MotivoConsulta.ALIMENTOSINGERIR);
    }

    @Test
    public void testMotivoConsulta() {
        Consulta unaConsulta = new Consulta();
        Enums.MotivoConsulta unEnum = Enums.MotivoConsulta.ALIMENTOSINGERIR;
        unaConsulta.setMotivo(unEnum);
        assertEquals(unEnum, Enums.MotivoConsulta.ALIMENTOSINGERIR);
    }

    @Test
    public void testMotivoConsultaValueOfAlimentosIngerir() {
        assertEquals("ALIMENTOSINGERIR", Enums.MotivoConsulta.valueOf("ALIMENTOSINGERIR").toString());
    }

    @Test
    public void testMotivoConsultaValueOfAlimentosIngeridos() {
        assertEquals("ALIMENTOSINGERIDOS", Enums.MotivoConsulta.valueOf("ALIMENTOSINGERIDOS").toString());
    }

    @Test
    public void testMotivoConsultaValueOfAlimentosOtros() {
        assertEquals("OTROS", Enums.MotivoConsulta.valueOf("OTROS").toString());
    }

    @Test
    public void testMotivoConsultaAlimentosIngerir() {
        assertEquals("ALIMENTOSINGERIR", Enums.MotivoConsulta.ALIMENTOSINGERIR.toString());
    }

    @Test
    public void testMotivoConsultaAlimentosIngeridos() {
        assertEquals("ALIMENTOSINGERIDOS", Enums.MotivoConsulta.ALIMENTOSINGERIDOS.toString());
    }

    @Test
    public void testMotivoConsultaOtros() {
        assertEquals("OTROS", Enums.MotivoConsulta.OTROS.toString());
    }

}