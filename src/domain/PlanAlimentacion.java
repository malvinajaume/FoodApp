package domain;


public class PlanAlimentacion {

    private Usuario usuario;
    private int peso;
    private int altura;
    private int horasDeActividad;
    private String detalle;
    private boolean estado;// true corresponde a emitido, false a pendiente
    private String alimentosDiaSemana[][] = new String[6][7];
    private String desayuno[] = new String[8];
    private String almuerzo[] = new String[8];
    private String merienda[] = new String[8];
    private String cena[] = new String[8];

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getHorasDeActividad() {
        return horasDeActividad;
    }

    public void setHorasDeActividad(int horasDeActividad) {
        this.horasDeActividad = horasDeActividad;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String[][] getAlimentosDiaSemana() {
        return alimentosDiaSemana;
    }

    public void setAlimentosDiaSemana(String[][] alimentosDiaSemana) {
        this.alimentosDiaSemana = alimentosDiaSemana;
    }

    public String[] getDesayuno() {
        return desayuno;
    }

    public void setDesayuno(String[] desayuno) {
        this.desayuno = desayuno;
    }

    public String[] getAlmuerzo() {
        return almuerzo;
    }

    public void setAlmuerzo(String[] almuerzo) {
        this.almuerzo = almuerzo;
    }

    public String[] getMerienda() {
        return merienda;
    }

    public void setMerienda(String[] merienda) {
        this.merienda = merienda;
    }

    public String[] getCena() {
        return cena;
    }

    public void setCena(String[] cena) {
        this.cena = cena;
    }
}
