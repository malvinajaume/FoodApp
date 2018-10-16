package domain;


public class Alimento {

    private String nombre;
    private String tipo;
    private int nutrientesPrincipales[] = new int[5];

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int[] getNutrientesPrincipales() {
        return nutrientesPrincipales;
    }

    public void setNutrientesPrincipales(int[] nutrientesPrincipales) {
        this.nutrientesPrincipales = nutrientesPrincipales;
    }

    @Override
    public String toString() {
        return this.getNombre();
    }
}
