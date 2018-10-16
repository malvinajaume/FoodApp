package domain;

import java.io.*;
import java.util.*;

import javax.swing.Icon;

public class Sistema extends Observable implements Serializable {

    
    public ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
    public ArrayList<Profesional> listaProfesionales = new ArrayList<Profesional>();
    public ArrayList<Alimento> listaAlimentos = new ArrayList<Alimento>();

    public ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public ArrayList<Profesional> getListaProfesionales() {
        return listaProfesionales;
    }

    public void setListaProfesionales(ArrayList<Profesional> listaProfesionales) {
        this.listaProfesionales = listaProfesionales;
    }

    public ArrayList<Alimento> getListaAlimentos() {
        return listaAlimentos;
    }

    public void setListaAlimentos(ArrayList<Alimento> listaAlimentos) {
        this.listaAlimentos = listaAlimentos;
    }

    public void agregarUsuario(Usuario usuario) {
        this.getListaUsuarios().add(usuario);
    }

    public boolean registrarUsuario(String nombre, String apellido, String nacionalidad, boolean[] preferencias, boolean[] restricciones, Date nacimiento, String descripcion, Icon icono) {

        boolean existeApellido = false;
        boolean usuarioRegistrado = false;

        Usuario usuario = new Usuario();

        for (int i = 0; i < getListaUsuarios().size(); i++) {
            if (getListaUsuarios().get(i).getApellidos().equals(apellido)) {
                existeApellido = true;
            }
        }

        if (!existeApellido
                && validarCampoTxtNoEsVacio(nombre)
                && validarStringSinNumero(nombre)
                && validarCampoTxtNoEsVacio(apellido)
                && validarStringSinNumero(apellido)
                && validarCampoTxtNoEsVacio(nacionalidad)
                && validarStringSinNumero(nacionalidad)
                && validarFecha(nacimiento, 0)) {

            usuario.setNombres(nombre);
            usuario.setApellidos(apellido);
            usuario.setPreferencias(preferencias);
            usuario.setNacionalidad(nacionalidad);
            usuario.setRestricciones(restricciones);
            usuario.setNacimiento(nacimiento);
            usuario.setDescripcion(descripcion);
            usuario.setAvatar(icono);

            agregarUsuario(usuario);
            usuarioRegistrado = true;
        }
        return usuarioRegistrado;
    }

    public void agregarProfesional(Profesional profesional) {
        getListaProfesionales().add(profesional);
    }

    public void registrarProfesional(String nombre, String apellido, String titulo, String paisObtencionTitulo, Date fechaNacimiento, Date fechaGraduacion, Icon avatar) {

        boolean existeApellido = false;

        Profesional profesional = new Profesional();

        for (int i = 0; i < getListaProfesionales().size(); i++) {
            if (getListaProfesionales().get(i).getApellidos().equals(apellido)) {
                existeApellido = true;
            }
        }

        if (!existeApellido
                && validarCampoTxtNoEsVacio(nombre)
                && validarStringSinNumero(nombre)
                && validarCampoTxtNoEsVacio(apellido)
                && validarStringSinNumero(apellido)
                && validarCampoTxtNoEsVacio(titulo)
                && validarStringSinNumero(titulo)
                && validarCampoTxtNoEsVacio(paisObtencionTitulo)
                && validarStringSinNumero(paisObtencionTitulo)
                && validarFecha(fechaNacimiento, 18)) {

            profesional.setNombres(nombre);
            profesional.setApellidos(apellido);
            profesional.setTitulo(titulo);
            profesional.setPaisObtencionTitulo(paisObtencionTitulo);
            profesional.setNacimiento(fechaNacimiento);
            profesional.setGraduacion(fechaGraduacion);
            profesional.setAvatar(avatar);

            agregarProfesional(profesional);
        }
    }

    public void agregarAlimento(Alimento alimento) {
        getListaAlimentos().add(alimento);
    }

    public void registrarAlimento(String nombre, String tipo, int[] nutrientesPrincipales) {

        boolean existeAlimento = false;
        Alimento alimento = new Alimento();

        for (int i = 0; i < getListaAlimentos().size(); i++) {
            if (getListaAlimentos().get(i).getNombre().equals(nombre)) {
                existeAlimento = true;
            }
        }

        if (!existeAlimento
                && validarCampoTxtNoEsVacio(nombre)
                && validarStringSinNumero(nombre)
                && validarCampoTxtNoEsVacio(tipo)
                && validarStringSinNumero(tipo)) {

            alimento.setNombre(nombre);
            alimento.setTipo(tipo);
            alimento.setNutrientesPrincipales(nutrientesPrincipales);
            agregarAlimento(alimento);
        }
    }

    public Usuario obtenerUsuario(String nombreApellidoUsuarioBuscado) {
        String nombreUsuario;

        for (int i = 0; i < this.getListaUsuarios().size(); i++) {
            nombreUsuario = this.getListaUsuarios().get(i).toString();
            if (nombreUsuario.equals(nombreApellidoUsuarioBuscado)) {
                return this.getListaUsuarios().get(i);
            }
        }
        return null;
    }

    public Profesional obtenerProfesional(String nombreApellidoProfesionalBuscado) {
        String nombreProfesional = "";
        Profesional profesionalBuscado = null;
        for (int i = 0; i < this.getListaProfesionales().size(); i++) {
            nombreProfesional = this.getListaProfesionales().get(i).toString();
            if (nombreProfesional.equals(nombreApellidoProfesionalBuscado)) {
                profesionalBuscado = this.getListaProfesionales().get(i);
            }
        }
        return profesionalBuscado;
    }

    public void agregarConsulta(Profesional profesional, Consulta consulta) {
        profesional.getListaConsultas().add(consulta);
    }

    public void agregarConsultaProf(String nombre, String descripcion, Usuario usuario, Enums.MotivoConsulta motivo) {
        Profesional profesionalBuscado = null;

        if (validarCampoTxtNoEsVacio(nombre)
                && validarStringSinNumero(nombre)
                && validarCampoTxtNoEsVacio(descripcion)) {

            Consulta nuevaConsulta = new Consulta();
            nuevaConsulta.setUsuarioDeConsulta(usuario);
            nuevaConsulta.setDescripcion(descripcion);
            nuevaConsulta.setMotivo(motivo);
            nuevaConsulta.setEstado(false);
            nuevaConsulta.setFecha(new Date());
            profesionalBuscado = convertirStringNombreProfesionalEnProfesional(nombre);
            profesionalBuscado.getListaConsultas().add(nuevaConsulta);
        }
    }

    public void agregarAlimentoUsuario(Usuario usuario, String nombreAlimento, Date fechaDeConsumoAlimento) {
        for (int i = 0; i < this.getListaAlimentos().size(); i++) {
            String alimento = this.getListaAlimentos().get(i).toString();
            if (alimento.equals(nombreAlimento)) {
                AlimentoIngerido alimentoIngerido = new AlimentoIngerido();
                alimentoIngerido.setFecha(fechaDeConsumoAlimento);
                alimentoIngerido.setAlimentoIngeridoUsuario(this.getListaAlimentos().get(i));
                usuario.getAlimentosIngeridos().add(alimentoIngerido);
            }
        }
    }

    public void borrarAlimentoUsuario(Usuario usuario, String alimentoBorrar) {
        for (int i = 0; i < usuario.getAlimentosIngeridos().size(); i++) {
            String alimentoBuscado = usuario.getAlimentosIngeridos().get(i).getAlimentoIngeridoUsuario().toString();
            if (alimentoBorrar.equals(alimentoBuscado)) {
                usuario.getAlimentosIngeridos().remove(i);
            }
        }
    }

    public String[][] planAlimentacionUsuario(String nombreApellidoUsuario) {
        String[][] matrizDelPlan = new String[4][8];
        matrizDelPlan[0][0] = "Desayuno";
        matrizDelPlan[1][0] = "Almuerzo";
        matrizDelPlan[2][0] = "Merienda";
        matrizDelPlan[3][0] = "Cena";

        for (int i = 0; i < this.getListaProfesionales().size(); i++) {
            for (int j = 0; j < this.getListaProfesionales().get(i).getListaSolicitudesDePlanes().size(); j++) {
                String usuarioBuscado = this.getListaProfesionales().get(i).getListaSolicitudesDePlanes().get(j).getUsuario().toString();
                if (nombreApellidoUsuario.equals(usuarioBuscado)) {

                    PlanAlimentacion planDelUsuario = this.getListaProfesionales().get(i).getListaSolicitudesDePlanes().get(j);
                    for (int k = 1; k < planDelUsuario.getDesayuno().length; k++) {
                        matrizDelPlan[0][k] = planDelUsuario.getDesayuno()[k];
                        matrizDelPlan[1][k] = planDelUsuario.getAlmuerzo()[k];
                        matrizDelPlan[2][k] = planDelUsuario.getMerienda()[k];
                        matrizDelPlan[3][k] = planDelUsuario.getCena()[k];
                    }

                }
            }
        }
        return matrizDelPlan;
    }

    public ArrayList<ParProfesionalConsulta> todasConsultasDeUnUsuario(String nombreApellidoUsuarioBuscado) {

        ArrayList<ParProfesionalConsulta> retorno = new ArrayList<ParProfesionalConsulta>();

        for (int i = 0; i < this.getListaProfesionales().size(); i++) {
            for (int j = 0; j < this.getListaProfesionales().get(i).getListaConsultas().size(); j++) {
                String nombreUsuarioDeConsulta = this.getListaProfesionales().get(i).getListaConsultas().get(j).getUsuarioDeConsulta().toString();
                if (nombreUsuarioDeConsulta.equals(nombreApellidoUsuarioBuscado)) {
                    ParProfesionalConsulta par = new ParProfesionalConsulta();
                    par.setProfesional(this.getListaProfesionales().get(i));
                    par.setConsulta(this.getListaProfesionales().get(i).getListaConsultas().get(j));
                    retorno.add(par);
                }
            }
        }
        return retorno;
    }


    public void agregarSolicitud(Profesional profesional, PlanAlimentacion plan) {
        for (int i = 0; i < this.getListaProfesionales().size(); i++) {
            if (this.getListaProfesionales().get(i).equals(profesional)) {
                this.getListaProfesionales().get(i).getListaSolicitudesDePlanes().add(plan);
            }
        }
    }

    public Profesional convertirStringNombreProfesionalEnProfesional(String nombreApellidoProfesional) {
        Profesional retorno = null;
        for (int i = 0; i < this.getListaProfesionales().size(); i++) {
            String nombre = this.getListaProfesionales().get(i).toString();
            if (nombre.equals(nombreApellidoProfesional)) {
                retorno = this.getListaProfesionales().get(i);
            }
        }
        return retorno;
    }

    public void solicitarPlan(Usuario usuario, Profesional profesional, int peso, int altura, int horasDeActividad, String detalles) {
        PlanAlimentacion nuevaSolicitud = new PlanAlimentacion();

        if (validarUsuario(usuario)
                && validarProfesional(profesional)
                && validarPeso(peso)
                && validarAltura(altura)
                && validarHoras(horasDeActividad)) {

            nuevaSolicitud.setAltura(altura);
            nuevaSolicitud.setEstado(false);
            nuevaSolicitud.setHorasDeActividad(horasDeActividad);
            nuevaSolicitud.setPeso(peso);
            nuevaSolicitud.setUsuario(usuario);
            nuevaSolicitud.setDetalle(detalles);

            agregarSolicitud(profesional, nuevaSolicitud);
        }
    }

    //FUNCIONES PARA VALIDAR CAMPOS
    public boolean validarCampoTxtNoEsVacio(String campo) {
        boolean esCampoValido = false;

        if (!campo.equals("")) {
            esCampoValido = true;
        }
        return esCampoValido;
    }

    public boolean validarStringSinNumero(String s) {
        boolean esCampoValido = true;

        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                esCampoValido = false;
            }
        }
        return esCampoValido;
    }

    public boolean validarFecha(Date fechaValidar, int mayorIgualDe) {
        boolean esMayor = false;

        Date fechaActual = new Date();

        Calendar calFechaActual = Calendar.getInstance();

        calFechaActual.setTime(fechaActual);
        int anioFechaActual = calFechaActual.get(Calendar.YEAR);

        Calendar calValidar = Calendar.getInstance();
        calValidar.setTime(fechaValidar);
        int anioValidar = calValidar.get(Calendar.YEAR);

        if (anioFechaActual - anioValidar >= mayorIgualDe) {
            esMayor = true;
        }
        return esMayor;
    }

    public boolean validarPeso(int peso) {
        boolean esCampoValido = true;
        if (peso <= 0 || peso > 500) {
            esCampoValido = false;
        }
        return esCampoValido;
    }

    public boolean validarAltura(int altura) {
        boolean esCampoValido = true;
        if (altura <= 0 || altura > 250) {
            esCampoValido = false;
        }
        return esCampoValido;
    }

    public boolean validarHoras(int horas) {
        boolean esCampoValido = true;
        if (horas <= 0 || horas > 24) {
            esCampoValido = false;
        }
        return esCampoValido;
    }

    public boolean validarUsuario(Usuario usuario) {
        return this.getListaUsuarios().contains(usuario);
    }

    public boolean validarProfesional(Profesional profesional) {
        return this.getListaProfesionales().contains(profesional);
    }

    public boolean obtenerEstadoPlanAlimentacionDadoNombreUsuario(String nombreAlimentoUsuario) {
        boolean estado = false;

        for (int i = 0; i < this.getListaProfesionales().size(); i++) {
            for (int j = 0; j < this.getListaProfesionales().get(i).getListaSolicitudesDePlanes().size(); j++) {
                String usuarioBuscado = this.getListaProfesionales().get(i).getListaSolicitudesDePlanes().get(j).getUsuario() + "";
                if (nombreAlimentoUsuario.equals(usuarioBuscado)) {

                    PlanAlimentacion planDelUsuario = this.getListaProfesionales().get(i).getListaSolicitudesDePlanes().get(j);
                    estado = planDelUsuario.isEstado();
                }
            }
        }
        return estado;
    }
}
