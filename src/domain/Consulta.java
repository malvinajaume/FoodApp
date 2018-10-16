package domain;

import java.util.Date;

public class Consulta {

    private Usuario usuarioDeConsulta;
    private String descripcion;
    private Enums.MotivoConsulta motivo;
    private boolean estado;
    private Date fecha;
    private String respuesta;

    public Usuario getUsuarioDeConsulta() {
        return usuarioDeConsulta;
    }

    public void setUsuarioDeConsulta(Usuario usuarioDeConsulta) {
        this.usuarioDeConsulta = usuarioDeConsulta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Enums.MotivoConsulta getMotivo() {
        return motivo;
    }

    public void setMotivo(Enums.MotivoConsulta motivo) {
        this.motivo = motivo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
