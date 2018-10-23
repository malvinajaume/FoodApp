package domain;

import java.util.ArrayList;
import javax.swing.Icon;

public class Usuario extends Persona {

    private Icon avatar;
    private String nacionalidad;
    private String descripcion;
    //carnes lacteos frutas verduras otros
    private boolean[] preferencias = new boolean[5];
    //salado dulce lacteos carnes rojas otros
    private boolean[] restricciones = new boolean[5];
    private ArrayList<AlimentoIngerido> alimentosIngeridos = new ArrayList<AlimentoIngerido>();

    private PlanAlimentacion planDeAlimentacion;

    public Icon getAvatar() {
        return avatar;
    }

    public void setAvatar(Icon avatar) {
        this.avatar = avatar;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public boolean[] getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(boolean[] preferencias) {
        this.preferencias = preferencias;
    }

    public boolean[] getRestricciones() {
        return restricciones;
    }

    public void setRestricciones(boolean[] restricciones) {
        this.restricciones = restricciones;
    }

    public ArrayList<AlimentoIngerido> getAlimentosIngeridos() {
        return alimentosIngeridos;
    }

    public void setAlimentosIngeridos(ArrayList<AlimentoIngerido> alimentosIngeridos) {
        this.alimentosIngeridos = alimentosIngeridos;
    }

    public PlanAlimentacion getPlanDeAlimentacion() {
        return planDeAlimentacion;
    }

    public void setPlanDeAlimentacion(PlanAlimentacion planDeAlimentacion) {
        this.planDeAlimentacion = planDeAlimentacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return this.getNombres() + " " + this.getApellidos();
    }
}
