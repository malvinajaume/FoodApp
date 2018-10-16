package domain;

import java.util.Date;

public class Persona {
    private String nombres;
    private String apellidos;
    private Date nacimiento;

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }
    
    @Override
    public String toString() {
        return this.getNombres() + this.getApellidos();
    }
}

