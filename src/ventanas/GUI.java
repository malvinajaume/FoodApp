package ventanas;

import com.toedter.calendar.JDateChooser;
import domain.Consulta;
import domain.Enums;
import domain.ParProfesionalConsulta;
import domain.PlanAlimentacion;
import domain.Profesional;
import domain.Sistema;
import domain.Usuario;
import fuentes.CustomFont;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;

public class GUI extends javax.swing.JFrame {

    /**
     * Creates new form GUI
     */
    Sistema sistema = new Sistema();
    Usuario usuario = new Usuario();
    Profesional profesional = new Profesional();

    public GUI() {
        initComponents();
        setLocationRelativeTo(null);
        cargarIconoDeVentana();
        limpiarVentanas();
        cargarBoxPaisesRegProfUsuarioYRol();

        CardLayout cl = (CardLayout) panelLateral.getLayout();
        cl.show(panelLateral, "panelBotonesInicio");

    }

    public void cargarBoxPaisesRegProfUsuarioYRol() {
        boxNacionalidadUsuario.addItem("Seleccione...");
        for (int i = 0; i < nacionalidad.length; i++) {
            boxNacionalidadUsuario.addItem(nacionalidad[i]);
        }

        boxPaisTitProf.addItem("Seleccione...");
        for (int i = 0; i < nacionalidad.length; i++) {
            boxPaisTitProf.addItem(nacionalidad[i]);
        }

        boxRolInicSec.addItem("Seleccione...");
        boxRolInicSec.addItem("Profesional");
        boxRolInicSec.addItem("Usuario");
    }

    public void cargarBoxProfesionalesConsultaProfUsuario() {
        boxNombreProfesional.removeAllItems();
        boxNombreProfesional.addItem("Seleccione...");
        for (int i = 0; i < sistema.getListaProfesionales().size(); i++) {
            boxNombreProfesional.addItem(sistema.getListaProfesionales().get(i).getNombres() + " " + sistema.getListaProfesionales().get(i).getApellidos());
        }
    }

    public void cargarIconoDeVentana() {
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/icon.png"));
        setIconImage(icon);
    }

    public void cambiarLogoLabelDesdeArchivo(JLabel labelCambiarIcono) {
        JFileChooser jf = new JFileChooser();
        FileNameExtensionFilter filtroExtension = new FileNameExtensionFilter("jpg, png, gif & jpeg", "jpg", "png", "gif", "jpeg");
        jf.setFileFilter(filtroExtension);
        jf.setCurrentDirectory(new File("Fotos"));
        int opcion = jf.showOpenDialog(this);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            String rutaIcono = jf.getSelectedFile().getAbsolutePath();
            cargarIconoDeLabelAPartirDeStringRuta(rutaIcono, labelCambiarIcono);
        }
    }

    public void cargarIconoDeLabelAPartirDeStringRuta(String rutaIcono, JLabel labelCambiarIcono) {
        labelCambiarIcono.setIcon(new ImageIcon(rutaIcono));
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(rutaIcono).getImage().getScaledInstance(labelCambiarIcono.getWidth(), labelCambiarIcono.getHeight(), Image.SCALE_DEFAULT));
        labelCambiarIcono.setIcon(imageIcon);
    }

    public void mostrarTablaAlimentosIngeridosUsuarios() {
        int largoListaAlimentos = usuario.getAlimentosIngeridos().size();
        String matrizDatos[][] = new String[largoListaAlimentos][2];

        for (int i = 0; i < largoListaAlimentos; i++) {
            matrizDatos[i][0] = usuario.getAlimentosIngeridos().get(i).getAlimentoIngeridoUsuario() + "";
            matrizDatos[i][1] = new SimpleDateFormat("dd-MM-yyyy").format(usuario.getAlimentosIngeridos().get(i).getFecha()) + "";
            tablaAlimentosIngeridosUsuario.changeSelection(i, 1, false, false);
        }

        tablaAlimentosIngeridosUsuario.setModel(new javax.swing.table.DefaultTableModel(
                matrizDatos,
                new String[]{
                    "Alimentos", "Fecha"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

    }

    public boolean validarFormUsuario() {
        boolean esFormValido = false;

        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String paisSeleccionado = (String) boxNacionalidadUsuario.getSelectedItem();

        if (validarCampoTxtNoEsVacio(nombre)) {
            lbCheckNombreErrorUsuario.setVisible(false);
            lbCheckNombreBienUsuario.setVisible(true);
            esFormValido = true;
        } else {
            lbCheckNombreBienUsuario.setVisible(false);
            lbCheckNombreErrorUsuario.setVisible(true);
        }

        if (validarCampoTxtNoEsVacio(apellido)) {
            lbCheckApellidoErrorUsuario.setVisible(false);
            lbCheckApellidoBienUsuario.setVisible(true);
            esFormValido &= true;
        } else {
            lbCheckApellidoBienUsuario.setVisible(false);
            lbCheckApellidoErrorUsuario.setVisible(true);
            esFormValido = false;
        }

        if (validarBoxDistintoSeleccione(paisSeleccionado)) {
            boxCheckNacionalidadErrorUsuario.setVisible(false);
            boxCheckNacionalidadBienUsuario.setVisible(true);
            esFormValido &= true;
        } else {
            boxCheckNacionalidadBienUsuario.setVisible(false);
            boxCheckNacionalidadErrorUsuario.setVisible(true);
            esFormValido = false;
        }

        if (validarChooserFechaNoEsVacio(dcNacimientoUsuario)) {
            lbCheckDchNacimientoErrorUsuario.setVisible(false);
            lbCheckDchNacimientoBienUsuario.setVisible(true);
            esFormValido &= true;
        } else {
            lbCheckDchNacimientoBienUsuario.setVisible(false);
            lbCheckDchNacimientoErrorUsuario.setVisible(true);
            esFormValido = false;
        }

        return esFormValido;
    }

    public boolean validarFormProfesional() {
        boolean esFormValido = false;

        String nombre = txtNombreProf.getText();
        String apellido = txtApellidoProf.getText();
        String titulo = txtTitulo.getText();
        String paisTitulo = (String) boxPaisTitProf.getSelectedItem();

        if (validarCampoTxtNoEsVacio(nombre)) {
            lbCheckNombreErrorProf.setVisible(false);
            lbCheckNombreBienProf.setVisible(true);
            esFormValido = true;
        } else {
            lbCheckNombreBienProf.setVisible(false);
            lbCheckNombreErrorProf.setVisible(true);
        }

        if (validarCampoTxtNoEsVacio(apellido)) {
            lbCheckApellidoErrorProf.setVisible(false);
            lbCheckApellidoBienProf.setVisible(true);
            esFormValido &= true;
        } else {
            lbCheckApellidoBienProf.setVisible(false);
            lbCheckApellidoErrorProf.setVisible(true);
            esFormValido = false;
        }

        if (validarCampoTxtNoEsVacio(titulo)) {
            lbCheckTituloProfesionaErrorProf.setVisible(false);
            lbCheckTituloProfesionaBienlProf.setVisible(true);
            esFormValido &= true;
        } else {
            lbCheckTituloProfesionaBienlProf.setVisible(false);
            lbCheckTituloProfesionaErrorProf.setVisible(true);
            esFormValido = false;
        }

        if (validarBoxDistintoSeleccione(paisTitulo)) {
            lbCheckPaisTituloErrorProf.setVisible(false);
            lbCheckPaisTituloBienProf.setVisible(true);
            esFormValido &= true;
        } else {
            lbCheckPaisTituloBienProf.setVisible(false);
            lbCheckPaisTituloErrorProf.setVisible(true);
            esFormValido = false;
        }

        if (validarChooserFechaNoEsVacio(dcNacimientoProf)) {

            if (validarFechaMayorIgualDe(dcNacimientoProf.getDate(), 18)) {
                lbCheckNacimientoErrorProf.setVisible(false);
                lbCheckNacimientoBienProf.setVisible(true);
                lbCheckNacimientoMenorEdadProf.setVisible(false);
                esFormValido &= true;
            } else {
                lbCheckNacimientoMenorEdadProf.setVisible(true);
                lbCheckNacimientoBienProf.setVisible(false);
                lbCheckNacimientoErrorProf.setVisible(true);
                esFormValido = false;
            }

        } else {
            lbCheckNacimientoBienProf.setVisible(false);
            lbCheckNacimientoErrorProf.setVisible(true);
            esFormValido = false;
        }

        if (validarChooserFechaNoEsVacio(dcGraduacionProf)) {

            if (validarFechasNoDesfasadas(dcGraduacionProf.getDate(), dcNacimientoProf.getDate())) {
                lbCheckGraduacionErrorProf.setVisible(false);
                lbCheckGraduacionBienProf.setVisible(true);
                lbCheckGraduacionFechaDesfasadaProf.setVisible(false);
                esFormValido &= true;
            } else {
                lbCheckGraduacionBienProf.setVisible(false);
                lbCheckGraduacionErrorProf.setVisible(true);
                lbCheckGraduacionFechaDesfasadaProf.setVisible(true);
                esFormValido = false;
            }

        } else {
            lbCheckGraduacionBienProf.setVisible(false);
            lbCheckGraduacionErrorProf.setVisible(true);
            esFormValido = false;
        }

        return esFormValido;
    }

    public boolean validarFormAlimento() {
        boolean esFormValido = false;
        String nombre = txtNombreAlimentos.getText();

        if (validarCampoTxtNoEsVacio(nombre)) {
            lbCheckNombreErrorAlimentos.setVisible(false);
            lbCheckNombreBienAlimentos.setVisible(true);
            esFormValido = true;
        } else {
            lbCheckNombreBienAlimentos.setVisible(false);
            lbCheckNombreErrorAlimentos.setVisible(true);
        }

        if (rBtnFruta.isSelected()
                || rBtnCereal.isSelected()
                || rBtnLegumbre.isSelected()
                || rBtnOtros.isSelected()) {

            lbTipoError.setVisible(false);
            esFormValido &= true;
        } else {
            lbTipoError.setVisible(true);
            esFormValido = false;
        }

        if (checkBoxNutProteinas.isSelected()) {
            if (validarCampoTxtNoEsVacio(txtProporcionProteinas.getText()) && validarRangoCampoInt(txtProporcionProteinas)) {
                lbCheckNutProteinasError.setVisible(false);
                lbCheckNutProteinasBien.setVisible(true);
            } else {
                lbCheckNutProteinasBien.setVisible(false);
                lbCheckNutProteinasError.setVisible(true);
                esFormValido = false;
            }
        }

        if (checkBoxNutCarbohidratos.isSelected()) {
            if (validarCampoTxtNoEsVacio(txtProporcionCarbohidratos.getText()) && validarRangoCampoInt(txtProporcionCarbohidratos)) {
                lbCheckNutCarbohidratosError.setVisible(false);
                lbCheckNutCarbohidratosBien.setVisible(true);
            } else {
                lbCheckNutCarbohidratosBien.setVisible(false);
                lbCheckNutCarbohidratosError.setVisible(true);
                esFormValido = false;
            }
        }

        if (checkBoxNutVitaminas.isSelected()) {
            if (validarCampoTxtNoEsVacio(txtProporcionVitaminas.getText()) && validarRangoCampoInt(txtProporcionVitaminas)) {
                lbCheckNutVitaminasError.setVisible(false);
                lbCheckNutVitaminasBien.setVisible(true);
            } else {
                lbCheckNutVitaminasBien.setVisible(false);
                lbCheckNutVitaminasError.setVisible(true);
                esFormValido = false;
            }
        }

        if (checkBoxNutMinerales.isSelected()) {
            if (validarCampoTxtNoEsVacio(txtProporcionMinerales.getText()) && validarRangoCampoInt(txtProporcionMinerales)) {
                lbCheckNutMineralesError.setVisible(false);
                lbCheckNutMineralesBien.setVisible(true);
            } else {
                lbCheckNutMineralesBien.setVisible(false);
                lbCheckNutMineralesError.setVisible(true);
                esFormValido = false;
            }
        }

        if (checkBoxNutAntioxidante.isSelected()) {
            if (validarCampoTxtNoEsVacio(txtProporcionAntioxidante.getText()) && validarRangoCampoInt(txtProporcionAntioxidante)) {
                lbCheckNutAntioxidanteError.setVisible(false);
                lbCheckNutAntioxidanteBien.setVisible(true);
            } else {
                lbCheckNutAntioxidanteBien.setVisible(false);
                lbCheckNutAntioxidanteError.setVisible(true);
                esFormValido = false;
            }
        }

        return esFormValido;
    }

    public boolean validarRangoCampoInt(JTextField campo) {
        int valor = Integer.parseInt(campo.getText());

        return (valor > 0 && valor < 101);
    }

    public boolean validarCampoTxtNoEsVacio(String campo) {
        boolean esCampoValido = false;

        if (!campo.equals("")) {
            esCampoValido = true;
        }

        return esCampoValido;
    }

    public boolean validarBoxDistintoSeleccione(String seleccion) {
        boolean esCampoValido = false;

        if (!seleccion.equals("Seleccione...")) {
            esCampoValido = true;
        }

        return esCampoValido;
    }

    public boolean validarChooserFechaNoEsVacio(JDateChooser dateChooserValidar) {
        return (dateChooserValidar.getDate() != null);
    }

    public boolean validarFechaMayorIgualDe(Date fechaValidar, int mayorIgualDe) {
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

    public boolean validarFechasNoDesfasadas(Date fechaSeEsperaMayor, Date fechaSeEsperaMenor) {
        return fechaSeEsperaMayor.compareTo(fechaSeEsperaMenor) > 0;
    }

    public boolean validarFormSolicitud() {
        boolean esFormValido = false;

        String profesional = (String) boxNombreProfesionalSolicitud.getSelectedItem();
        String peso = txtPesoSolicitud.getText();
        String altura = txtAlturaSolicitud.getText();
        String horasDeActividad = txtHorasDeActividadSolicitud.getText();
        String detalles = txtDetallesSolicitud.getText();

        if (!profesional.equals("Seleccione...")) {
            lbCkeckBoxProfesionalBienSolicitud.setVisible(true);
            lbCheckBoxProfesionalErrorSolicitud.setVisible(false);
            esFormValido = true;
        } else {
            lbCkeckBoxProfesionalBienSolicitud.setVisible(false);
            lbCheckBoxProfesionalErrorSolicitud.setVisible(true);
        }

        if (validarCampoTxtNoEsVacio(peso)) {
            if (validarPeso(Integer.parseInt(peso))) {
                lbCkeckBoxPesoBienSolicitud.setVisible(true);
                lbCheckBoxPesoErrorSolicitud.setVisible(false);
                lbPesoPlanAlimentacionError.setVisible(false);
                esFormValido &= true;
            } else {
                lbCkeckBoxPesoBienSolicitud.setVisible(false);
                lbCheckBoxPesoErrorSolicitud.setVisible(true);
                lbPesoPlanAlimentacionError.setVisible(true);
                esFormValido = false;
            }

        } else {
            lbCkeckBoxPesoBienSolicitud.setVisible(false);
            lbCheckBoxPesoErrorSolicitud.setVisible(true);
            esFormValido = false;
        }

        if (validarCampoTxtNoEsVacio(altura)) {
            if (validarAltura(Integer.parseInt(altura))) {
                lbCkeckBoxAlturaBienSolicitud.setVisible(true);
                lbCheckBoxAlturaErrorSolicitud.setVisible(false);
                lbAlturaPlanAlimentacionError.setVisible(false);
                esFormValido &= true;
            } else {
                lbCkeckBoxAlturaBienSolicitud.setVisible(false);
                lbCheckBoxAlturaErrorSolicitud.setVisible(true);
                lbAlturaPlanAlimentacionError.setVisible(true);
                esFormValido = false;
            }
        } else {
            lbCkeckBoxAlturaBienSolicitud.setVisible(false);
            lbCheckBoxAlturaErrorSolicitud.setVisible(true);
            esFormValido = false;
        }

        if (validarCampoTxtNoEsVacio(horasDeActividad)) {
            if (validarHoras(Integer.parseInt(horasDeActividad))) {
                lbCkeckBoxHorasDeActividadBienSolicitud.setVisible(true);
                lbCheckBoxHorasDeActividadErrorSolicitud.setVisible(false);
                lbHorasActFisicaPlanAlimentacionError.setVisible(false);
                esFormValido &= true;
            } else {
                lbCkeckBoxHorasDeActividadBienSolicitud.setVisible(false);
                lbCheckBoxHorasDeActividadErrorSolicitud.setVisible(true);
                lbHorasActFisicaPlanAlimentacionError.setVisible(true);
                esFormValido = false;
            }
        } else {
            lbCkeckBoxHorasDeActividadBienSolicitud.setVisible(false);
            lbCheckBoxHorasDeActividadErrorSolicitud.setVisible(true);
            esFormValido = false;
        }

        if (validarCampoTxtNoEsVacio(detalles)) {
            lbDetallesPlanAlimentacionError.setVisible(false);
            esFormValido &= true;
        } else {
            lbDetallesPlanAlimentacionError.setVisible(true);
            esFormValido = false;
        }
        return esFormValido;
    }

    public void limpiarVentanas() {
        limpiarVentanaPricipal();
        limpiarVentanaUsuario();
        limpiarVentanaProfesional();
        limpiarFormRegistroAlimentoConsumidoUsuario();
        limpiarFormConsultaProfesionalUsuario();
    }

    public void limpiarFormInicSec() {
        boxRolInicSec.setSelectedItem("Seleccione...");
        boxNombreInicSec.setSelectedItem("Seleccione...");
        boxNombreInicSec.setEnabled(false);
        lbRolError.setVisible(false);
        lbNombreInicSecError.setVisible(false);
    }

    public void limpiarVentanaInicSec() {
        pBRegUsuario.setBackground(Color.GRAY);
        pBRegProfesional.setBackground(Color.GRAY);

        lbRolError.setVisible(false);
        lbNombreInicSecError.setVisible(false);
    }

    public void limpiarVentanaPricipal() {
        pBRegUsuario.setBackground(Color.GRAY);
        pBRegProfesional.setBackground(Color.GRAY);

        limpiarFormUsuario();
        limpiarFormProfesional();
    }

    public void limpiarVentanaUsuario() {
        pBEditarDatosUsuario.setBackground(Color.GRAY);
        pBRegAlimentosIngeridosUsuario.setBackground(Color.GRAY);
        pBRegConsultaProfUsuario.setBackground(Color.GRAY);
        pBRegPlanAlimentacionUsuario.setBackground(Color.GRAY);

        limpiarFormUsuario();
    }

    public void limpiarVentanaProfesional() {
        pBEditarDatosProf.setBackground(Color.GRAY);
        pBRegAlimentosProf.setBackground(Color.GRAY);
        pBConsultasRecibidasProf.setBackground(Color.GRAY);
        pBPlanAlimentacionProf.setBackground(Color.GRAY);

        limpiarFormProfesional();
        limpiarFormAlimento();
        limpiarFormConsultasRecibidasProf();

        limpiarFormSolicitud();
    }

    public void limpiarFormUsuario() {
        lbRegistroUsuario.setText("Registrar Usuario");

        lbCheckNombreErrorUsuario.setVisible(false);
        lbCheckApellidoErrorUsuario.setVisible(false);
        boxCheckNacionalidadErrorUsuario.setVisible(false);
        boxCheckNacionalidadErrorUsuario.setVisible(false);
        lbCheckDchNacimientoErrorUsuario.setVisible(false);

        lbCheckNombreBienUsuario.setVisible(false);
        lbCheckApellidoBienUsuario.setVisible(false);
        boxCheckNacionalidadBienUsuario.setVisible(false);
        boxCheckNacionalidadBienUsuario.setVisible(false);
        lbCheckDchNacimientoBienUsuario.setVisible(false);

        lbCheckRegistroUsuarioRepetido.setVisible(false);
        lbCheckRegistroUsuarioExitoso.setVisible(false);

        txtNombre.setText("");
        txtApellido.setText("");
        txtDescripcionUsuario.setText("");

        checkboxPrefCarnes.setSelected(false);
        checkboxPrefLacteos.setSelected(false);
        checkboxPrefFrutas.setSelected(false);
        checkboxPrefVerduras.setSelected(false);
        checkboxPrefOtros.setSelected(false);

        checkboxResSalado.setSelected(false);
        checkboxResDulce.setSelected(false);
        checkboxResLacteos.setSelected(false);
        checkboxResCarnesRojas.setSelected(false);
        checkboxResOtros.setSelected(false);

        boxNacionalidadUsuario.setSelectedItem("Seleccione...");

        lbAvatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/avatar.png")));

        dcNacimientoUsuario.setDate(null);

        //Boton de edición de datos, solo se muestra cuando el usuario 
        //ya regristrado ingresa desea modificar sus datos
        btnEditarUsuario.setVisible(false);
        btnRegistrarUsuario.setVisible(true);
    }

    public void limpiarFormProfesional() {

        lbCheckNombreErrorProf.setVisible(false);
        lbCheckApellidoErrorProf.setVisible(false);
        lbCheckTituloProfesionaErrorProf.setVisible(false);
        lbCheckPaisTituloErrorProf.setVisible(false);
        lbCheckNacimientoErrorProf.setVisible(false);
        lbCheckGraduacionErrorProf.setVisible(false);

        lbCheckNombreBienProf.setVisible(false);
        lbCheckApellidoBienProf.setVisible(false);
        lbCheckTituloProfesionaBienlProf.setVisible(false);
        lbCheckPaisTituloBienProf.setVisible(false);
        lbCheckNacimientoBienProf.setVisible(false);
        lbCheckGraduacionBienProf.setVisible(false);

        lbCheckNacimientoMenorEdadProf.setVisible(false);
        lbCheckGraduacionFechaDesfasadaProf.setVisible(false);

        txtNombreProf.setText("");
        txtApellidoProf.setText("");
        txtTitulo.setText("");

        boxPaisTitProf.setSelectedItem("Seleccione...");
        lbAvatarProf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/avatar.png")));

        dcNacimientoProf.setDate(null);
        dcGraduacionProf.setDate(null);

        btnRegistrarProfesional.setVisible(true);
        btnEditarProfesional.setVisible(false);
    }

    public void limpiarFormAlimento() {

        lbCheckNombreBienAlimentos.setVisible(false);
        lbCheckNombreErrorAlimentos.setVisible(false);
        lbTipoError.setVisible(false);

        lbCheckNombreErrorAlimentos.setVisible(false);
        txtNombreAlimentos.setText("");
        rbtnGrupRegAlimentos.clearSelection();

        txtProporcionProteinas.setText("");
        txtProporcionCarbohidratos.setText("");
        txtProporcionVitaminas.setText("");
        txtProporcionMinerales.setText("");
        txtProporcionAntioxidante.setText("");

        txtProporcionProteinas.setEnabled(false);
        txtProporcionCarbohidratos.setEnabled(false);
        txtProporcionVitaminas.setEnabled(false);
        txtProporcionMinerales.setEnabled(false);
        txtProporcionAntioxidante.setEnabled(false);

        checkBoxNutProteinas.setSelected(false);
        checkBoxNutCarbohidratos.setSelected(false);
        checkBoxNutVitaminas.setSelected(false);
        checkBoxNutMinerales.setSelected(false);
        checkBoxNutAntioxidante.setSelected(false);

        lbCheckNutProteinasError.setVisible(false);
        lbCheckNutCarbohidratosError.setVisible(false);
        lbCheckNutVitaminasError.setVisible(false);
        lbCheckNutMineralesError.setVisible(false);
        lbCheckNutAntioxidanteError.setVisible(false);

        lbCheckNutProteinasBien.setVisible(false);
        lbCheckNutCarbohidratosBien.setVisible(false);
        lbCheckNutVitaminasBien.setVisible(false);
        lbCheckNutMineralesBien.setVisible(false);
        lbCheckNutAntioxidanteBien.setVisible(false);

    }

    public void limpiarFormConsultasRecibidasProf() {
        lbResponderConsultasRecibidasError.setVisible(false);
        txtResponderUsuarioConsultaRecibidaProf.setText("");
        txtDescripcionUsuarioConsultaRecibidaProf.setText("");
        mostrarTablaConsultaProfesional(tablaConsultasRecibidas);
        tablaConsultasRecibidas.clearSelection();
    }

    public void limpiarFormConsultaProfesionalUsuario() {
        lbMotivoError.setVisible(false);
        lbCheckProfErrorConsProfUsuario.setVisible(false);
        lbCheckProfBienConsProfUsuario.setVisible(false);
        lbDescripcionError.setVisible(false);

        boxNombreProfesional.setSelectedItem("Seleccione...");
        rbtnGrupConsultaProfMotivo.clearSelection();
        txtDescripcionConsultaProf.setText("");

        cargarBoxProfesionalesConsultaProfUsuario();
    }

    public void limpiarFormRegistroAlimentoConsumidoUsuario() {
        boxAlimentoConsumidoUsuario.setSelectedItem("Seleccione...");
        dcAlimentoConsumidoUsuario.setDate(null);

        lbCheckBoxNombreAlimentoConsumidoErrorUsuario.setVisible(false);
        lbCheckBoxNombreAlimentoConsumidoBienUsuario.setVisible(false);
        lbCheckBoxFechaAlimentoConsumidoErrorUsuario.setVisible(false);
        lbCheckBoxFechaAlimentoConsumidoBienUsuario.setVisible(false);

        mostrarTablaAlimentosIngeridosUsuarios();
    }

    public void limpiarFormSolicitud() {
        boxNombreProfesionalSolicitud.setSelectedItem("Seleccione...");
        txtPesoSolicitud.setText("");
        txtAlturaSolicitud.setText("");
        txtHorasDeActividadSolicitud.setText("");
        txtDetallesSolicitud.setText("");

        lbCheckBoxProfesionalErrorSolicitud.setVisible(false);
        lbCheckBoxPesoErrorSolicitud.setVisible(false);
        lbCheckBoxAlturaErrorSolicitud.setVisible(false);
        lbCheckBoxHorasDeActividadErrorSolicitud.setVisible(false);
        lbDetallesPlanAlimentacionError.setVisible(false);

        lbCkeckBoxProfesionalBienSolicitud.setVisible(false);
        lbCkeckBoxPesoBienSolicitud.setVisible(false);
        lbCkeckBoxAlturaBienSolicitud.setVisible(false);
        lbCkeckBoxHorasDeActividadBienSolicitud.setVisible(false);

        lbPesoPlanAlimentacionError.setVisible(false);
        lbAlturaPlanAlimentacionError.setVisible(false);
        lbHorasActFisicaPlanAlimentacionError.setVisible(false);
    }

    public void cerrarSesion() {
        limpiarVentanaUsuario();
        limpiarVentanaProfesional();

        CardLayout clContenido = (CardLayout) panelContenido.getLayout();
        clContenido.show(panelContenido, "panelInicio");

        CardLayout clBotones = (CardLayout) panelLateral.getLayout();
        clBotones.show(panelLateral, "panelBotonesInicio");

        this.usuario = null;
        this.profesional = null;
    }

    public String obtenerTipoAlimento() {
        String tipo = "";

        if (rBtnFruta.isSelected()) {
            tipo = "Fruta";
        }
        if (rBtnCereal.isSelected()) {
            tipo = "Cereal";
        }
        if (rBtnLegumbre.isSelected()) {
            tipo = "Legumbre";
        }
        if (rBtnOtros.isSelected()) {
            tipo = "Otros";
        }

        return tipo;
    }

    public int[] obtenerNutrientesPrincipales() {
        int retorno[] = new int[5];

        if (checkBoxNutProteinas.isSelected()) {
            retorno[0] = Integer.parseInt(txtProporcionProteinas.getText());
        }

        if (checkBoxNutCarbohidratos.isSelected()) {
            retorno[1] = Integer.parseInt(txtProporcionCarbohidratos.getText());
        }

        if (checkBoxNutVitaminas.isSelected()) {
            retorno[2] = Integer.parseInt(txtProporcionVitaminas.getText());
        }

        if (checkBoxNutMinerales.isSelected()) {
            retorno[3] = Integer.parseInt(txtProporcionMinerales.getText());
        }

        if (checkBoxNutAntioxidante.isSelected()) {
            retorno[4] = Integer.parseInt(txtProporcionAntioxidante.getText());
        }

        return retorno;
    }

    public Enums.MotivoConsulta obtenerMotivoConsulta() {
        Enums.MotivoConsulta retorno = Enums.MotivoConsulta.OTROS;

        if (rbtnAliIng.isSelected()) {
            retorno = Enums.MotivoConsulta.ALIMENTOSINGERIDOS;
        }

        if (rbtnAliIng.isSelected()) {
            retorno = Enums.MotivoConsulta.ALIMENTOSINGERIDOS;
        }

        if (rbtnAliIgerir.isSelected()) {
            retorno = Enums.MotivoConsulta.ALIMENTOSINGERIR;
        }
        return retorno;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rbtnGrupRegAlimentos = new javax.swing.ButtonGroup();
        jLabel5 = new javax.swing.JLabel();
        rbtnGrupConsultaProfMotivo = new javax.swing.ButtonGroup();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        panelPrincipal = new javax.swing.JPanel();
        panelBarraSuperiorVentana = new javax.swing.JPanel();
        lbMinimizar = new javax.swing.JLabel();
        lbCerrar = new javax.swing.JLabel();
        panelLateral = new javax.swing.JPanel();
        panelBotonesUsuario = new javax.swing.JPanel();
        pBRegConsultaProfUsuario = new javax.swing.JPanel();
        lbRegConsultaProf = new javax.swing.JLabel();
        lbLogoRegConsultaProf = new javax.swing.JLabel();
        pBRegPlanAlimentacionUsuario = new javax.swing.JPanel();
        lbRegPlanAlimentacion = new javax.swing.JLabel();
        lbLogoRegPlanAlimentacion = new javax.swing.JLabel();
        pBRegAlimentosIngeridosUsuario = new javax.swing.JPanel();
        lbRegAlimentosIngeridos = new javax.swing.JLabel();
        lbLogoRegAlimentosIngeridos = new javax.swing.JLabel();
        pBEditarDatosUsuario = new javax.swing.JPanel();
        lbRegPlanAlimentacion1 = new javax.swing.JLabel();
        lbLogoRegPlanAlimentacion1 = new javax.swing.JLabel();
        pBYaSalirUsuario = new javax.swing.JPanel();
        lbYaEstoyReg1 = new javax.swing.JLabel();
        pBVolverInicioUsuario = new javax.swing.JPanel();
        lbAvatarInicSecUsuario = new javax.swing.JLabel();
        lbMostrarNombreUsuario = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        panelBotonesInicial = new javax.swing.JPanel();
        pBRegProfesional = new javax.swing.JPanel();
        lbRegProf = new javax.swing.JLabel();
        lbLogoRegProf = new javax.swing.JLabel();
        pBRegUsuario = new javax.swing.JPanel();
        lbRegUsuario = new javax.swing.JLabel();
        lbLogoRegUsuario = new javax.swing.JLabel();
        pBYaEstoyReg = new javax.swing.JPanel();
        lbYaEstoyReg = new javax.swing.JLabel();
        lbLogoPrincipal = new javax.swing.JLabel();
        panelBotonesProfesional = new javax.swing.JPanel();
        pBRegAlimentosProf = new javax.swing.JPanel();
        lbRegAlimentos1 = new javax.swing.JLabel();
        lbLogoRegAlimentos1 = new javax.swing.JLabel();
        pBEditarDatosProf = new javax.swing.JPanel();
        lbRegAlimentos2 = new javax.swing.JLabel();
        lbLogoRegAlimentos2 = new javax.swing.JLabel();
        pBConsultasRecibidasProf = new javax.swing.JPanel();
        lbRegAlimentos3 = new javax.swing.JLabel();
        lbLogoRegAlimentos3 = new javax.swing.JLabel();
        pBPlanAlimentacionProf = new javax.swing.JPanel();
        lbRegAlimentos4 = new javax.swing.JLabel();
        lbLogoRegAlimentos4 = new javax.swing.JLabel();
        pBSalirProf = new javax.swing.JPanel();
        lbYaEstoyReg2 = new javax.swing.JLabel();
        pBVolverInicioProf = new javax.swing.JPanel();
        lbAvatarInicSecProfesional = new javax.swing.JLabel();
        lbMostrarNombreProfesional = new javax.swing.JLabel();
        panelContenido = new javax.swing.JPanel();
        PInicio = new javax.swing.JPanel();
        PConsultaProfUsuario = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcionConsultaProf = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        rbtnAliIng = new javax.swing.JRadioButton();
        rbtnAliIgerir = new javax.swing.JRadioButton();
        rbtnAliOtros = new javax.swing.JRadioButton();
        btnGuardarUsuario1 = new javax.swing.JButton();
        lbRegistroUsuario2 = new javax.swing.JLabel();
        boxNombreProfesional = new javax.swing.JComboBox<>();
        lbNombre1 = new javax.swing.JLabel();
        lbNombre2 = new javax.swing.JLabel();
        lbNombre3 = new javax.swing.JLabel();
        lbCheckProfErrorConsProfUsuario = new javax.swing.JLabel();
        lbCheckProfBienConsProfUsuario = new javax.swing.JLabel();
        lbDescripcionError = new javax.swing.JLabel();
        lbMotivoError = new javax.swing.JLabel();
        jScrollPane15 = new javax.swing.JScrollPane();
        tablaConsultasUsuario = new javax.swing.JTable();
        lbNombre11 = new javax.swing.JLabel();
        lbNombre12 = new javax.swing.JLabel();
        jScrollPane16 = new javax.swing.JScrollPane();
        txtPreguntaUsuarioConsultaUsuario = new javax.swing.JTextArea();
        jScrollPane17 = new javax.swing.JScrollPane();
        txtRespuestaUsuarioConsultaUsuario = new javax.swing.JTextArea();
        lbNombre13 = new javax.swing.JLabel();
        PRegUsuario = new javax.swing.JPanel();
        lbRegistroUsuario = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lbNombre = new javax.swing.JLabel();
        lbApellido = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        boxNacionalidadUsuario = new javax.swing.JComboBox<>();
        lbNacionalidad = new javax.swing.JLabel();
        lbNacimiento = new javax.swing.JLabel();
        lbPreferencias = new javax.swing.JLabel();
        checkboxResOtros = new javax.swing.JCheckBox();
        checkboxResCarnesRojas = new javax.swing.JCheckBox();
        checkboxPrefLacteos = new javax.swing.JCheckBox();
        checkboxPrefFrutas = new javax.swing.JCheckBox();
        checkboxPrefVerduras = new javax.swing.JCheckBox();
        lbDescripcion = new javax.swing.JLabel();
        checkboxResSalado = new javax.swing.JCheckBox();
        checkboxResDulce = new javax.swing.JCheckBox();
        checkboxResLacteos = new javax.swing.JCheckBox();
        checkboxPrefOtros = new javax.swing.JCheckBox();
        checkboxPrefCarnes = new javax.swing.JCheckBox();
        lbRestricciones = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcionUsuario = new javax.swing.JTextArea();
        lbAvatar = new javax.swing.JLabel();
        btnRegistrarUsuario = new javax.swing.JButton();
        lbCheckApellidoErrorUsuario = new javax.swing.JLabel();
        lbCheckDchNacimientoErrorUsuario = new javax.swing.JLabel();
        lbCheckNombreErrorUsuario = new javax.swing.JLabel();
        boxCheckNacionalidadErrorUsuario = new javax.swing.JLabel();
        lbCheckApellidoBienUsuario = new javax.swing.JLabel();
        lbCheckNombreBienUsuario = new javax.swing.JLabel();
        boxCheckNacionalidadBienUsuario = new javax.swing.JLabel();
        lbCheckDchNacimientoBienUsuario = new javax.swing.JLabel();
        dcNacimientoUsuario = new com.toedter.calendar.JDateChooser();
        btnEditarUsuario = new javax.swing.JButton();
        lbCheckRegistroUsuarioExitoso = new javax.swing.JLabel();
        lbCheckRegistroUsuarioRepetido = new javax.swing.JLabel();
        PRegProfesional = new javax.swing.JPanel();
        lbRegistroProfesional = new javax.swing.JLabel();
        lbAvatarProf = new javax.swing.JLabel();
        lbNombreProf = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        lbApellidoProf = new javax.swing.JLabel();
        txtApellidoProf = new javax.swing.JTextField();
        boxPaisTitProf = new javax.swing.JComboBox<>();
        lbTituloProf = new javax.swing.JLabel();
        lbNacimientoProf = new javax.swing.JLabel();
        lbFechaGraduacionProf1 = new javax.swing.JLabel();
        txtNombreProf = new javax.swing.JTextField();
        lbTituloProf1 = new javax.swing.JLabel();
        btnRegistrarProfesional = new javax.swing.JButton();
        lbCheckNombreErrorProf = new javax.swing.JLabel();
        lbCheckApellidoErrorProf = new javax.swing.JLabel();
        lbCheckTituloProfesionaErrorProf = new javax.swing.JLabel();
        lbCheckPaisTituloErrorProf = new javax.swing.JLabel();
        lbCheckNacimientoErrorProf = new javax.swing.JLabel();
        lbCheckGraduacionErrorProf = new javax.swing.JLabel();
        lbCheckPaisTituloBienProf = new javax.swing.JLabel();
        lbCheckNombreBienProf = new javax.swing.JLabel();
        lbCheckApellidoBienProf = new javax.swing.JLabel();
        lbCheckTituloProfesionaBienlProf = new javax.swing.JLabel();
        lbCheckGraduacionBienProf = new javax.swing.JLabel();
        lbCheckNacimientoBienProf = new javax.swing.JLabel();
        dcNacimientoProf = new com.toedter.calendar.JDateChooser();
        dcGraduacionProf = new com.toedter.calendar.JDateChooser();
        btnEditarProfesional = new javax.swing.JButton();
        lbCheckNacimientoMenorEdadProf = new javax.swing.JLabel();
        lbCheckGraduacionFechaDesfasadaProf = new javax.swing.JLabel();
        PRegAlimentosProfesional = new javax.swing.JPanel();
        lbRegistroAlimento = new javax.swing.JLabel();
        lbTipo = new javax.swing.JLabel();
        rBtnOtros = new javax.swing.JRadioButton();
        rBtnFruta = new javax.swing.JRadioButton();
        rBtnCereal = new javax.swing.JRadioButton();
        rBtnLegumbre = new javax.swing.JRadioButton();
        lbNombreAlimentos1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        lbCheckNombreErrorAlimentos = new javax.swing.JLabel();
        lbCheckNombreBienAlimentos = new javax.swing.JLabel();
        lbTipoError = new javax.swing.JLabel();
        lbAvatarAlimento = new javax.swing.JLabel();
        checkBoxNutProteinas = new javax.swing.JCheckBox();
        checkBoxNutCarbohidratos = new javax.swing.JCheckBox();
        checkBoxNutVitaminas = new javax.swing.JCheckBox();
        checkBoxNutMinerales = new javax.swing.JCheckBox();
        lbNombreAlimentos2 = new javax.swing.JLabel();
        lbNombreAlimentos3 = new javax.swing.JLabel();
        txtNombreAlimentos = new javax.swing.JTextField();
        txtProporcionProteinas = new javax.swing.JTextField();
        txtProporcionCarbohidratos = new javax.swing.JTextField();
        txtProporcionVitaminas = new javax.swing.JTextField();
        txtProporcionMinerales = new javax.swing.JTextField();
        checkBoxNutAntioxidante = new javax.swing.JCheckBox();
        txtProporcionAntioxidante = new javax.swing.JTextField();
        lbNombreAlimentos4 = new javax.swing.JLabel();
        lbCheckNutProteinasError = new javax.swing.JLabel();
        lbCheckNutCarbohidratosError = new javax.swing.JLabel();
        lbCheckNutMineralesError = new javax.swing.JLabel();
        lbCheckNutVitaminasError = new javax.swing.JLabel();
        lbCheckNutAntioxidanteError = new javax.swing.JLabel();
        lbCheckNutAntioxidanteBien = new javax.swing.JLabel();
        lbCheckNutProteinasBien = new javax.swing.JLabel();
        lbCheckNutCarbohidratosBien = new javax.swing.JLabel();
        lbCheckNutVitaminasBien = new javax.swing.JLabel();
        lbCheckNutMineralesBien = new javax.swing.JLabel();
        PAlimentosIngeridosUsuario = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaAlimentosIngeridosUsuario = new javax.swing.JTable();
        dcAlimentoConsumidoUsuario = new com.toedter.calendar.JDateChooser();
        btnBorrarAlimentoUsuario = new javax.swing.JButton();
        lbAlimentosIngeridos = new javax.swing.JLabel();
        lbFechaAlimentoIngerido = new javax.swing.JLabel();
        lbNombreAlimentoIngerido1 = new javax.swing.JLabel();
        lbFechaAlimentoIngerido1 = new javax.swing.JLabel();
        boxAlimentoConsumidoUsuario = new javax.swing.JComboBox<>();
        btnAgregarAlimentoUsuario = new javax.swing.JButton();
        lbCheckBoxFechaAlimentoConsumidoErrorUsuario = new javax.swing.JLabel();
        lbCheckBoxNombreAlimentoConsumidoErrorUsuario = new javax.swing.JLabel();
        lbCheckBoxNombreAlimentoConsumidoBienUsuario = new javax.swing.JLabel();
        lbCheckBoxFechaAlimentoConsumidoBienUsuario = new javax.swing.JLabel();
        PSolicitarPlanAlimentacionUsuario = new javax.swing.JPanel();
        lbPlanAlimentacionUsuario = new javax.swing.JLabel();
        boxNombreProfesionalSolicitud = new javax.swing.JComboBox<>();
        lbNombre4 = new javax.swing.JLabel();
        txtPesoSolicitud = new javax.swing.JTextField();
        txtAlturaSolicitud = new javax.swing.JTextField();
        lbNombre5 = new javax.swing.JLabel();
        lbNombre7 = new javax.swing.JLabel();
        txtHorasDeActividadSolicitud = new javax.swing.JTextField();
        lbNombre8 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtDetallesSolicitud = new javax.swing.JTextArea();
        lbNombre9 = new javax.swing.JLabel();
        btnSolicitarPlan = new javax.swing.JButton();
        lbCheckBoxProfesionalErrorSolicitud = new javax.swing.JLabel();
        lbCheckBoxPesoErrorSolicitud = new javax.swing.JLabel();
        lbCheckBoxAlturaErrorSolicitud = new javax.swing.JLabel();
        lbCheckBoxHorasDeActividadErrorSolicitud = new javax.swing.JLabel();
        lbCkeckBoxProfesionalBienSolicitud = new javax.swing.JLabel();
        lbCkeckBoxPesoBienSolicitud = new javax.swing.JLabel();
        lbCkeckBoxAlturaBienSolicitud = new javax.swing.JLabel();
        lbCkeckBoxHorasDeActividadBienSolicitud = new javax.swing.JLabel();
        lbDetallesPlanAlimentacionError = new javax.swing.JLabel();
        lbHorasActFisicaPlanAlimentacionError = new javax.swing.JLabel();
        lbPesoPlanAlimentacionError = new javax.swing.JLabel();
        lbAlturaPlanAlimentacionError = new javax.swing.JLabel();
        PPlanAlimentacionProfesional = new javax.swing.JPanel();
        lbPlanAlimentacionUsuario1 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablaIdearPlanDePlanesProfesional = new javax.swing.JTable();
        lbNombre10 = new javax.swing.JLabel();
        btnEnviarPlan = new javax.swing.JButton();
        panelAmpliarDatosPlanAlimentacion = new javax.swing.JPanel();
        PTablaPlanesAlimentacionSolicitados = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablaSolicitudDePlanesProfesional = new javax.swing.JTable();
        PAmpliarDatosUsuarioPlanesProfesional = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnEnviarPlan2 = new javax.swing.JButton();
        btnVolverATabla = new javax.swing.JButton();
        jScrollPane18 = new javax.swing.JScrollPane();
        txtAmpilarInfoDescripcionPlanAlimProf = new javax.swing.JTextArea();
        jScrollPane19 = new javax.swing.JScrollPane();
        txtAmpilarInfoPreferenciasPlanAlimProf = new javax.swing.JTextArea();
        jScrollPane20 = new javax.swing.JScrollPane();
        txtAmpilarInfoRestriccionesPlanAlimProf = new javax.swing.JTextArea();
        txtAmpilarInfoNombrePlanAlimProf = new javax.swing.JTextField();
        jScrollPane21 = new javax.swing.JScrollPane();
        txtAmpilarInfoDetallesPlanAlimProf = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane22 = new javax.swing.JScrollPane();
        txtAmpilarInfoAliIngPlanAlimProf = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        btnAmpliarInformacion = new javax.swing.JButton();
        boxComidaPlanAliProf = new javax.swing.JComboBox<>();
        boxDiasSemanaPlanAliProf = new javax.swing.JComboBox<>();
        lbNombre14 = new javax.swing.JLabel();
        lbNombre15 = new javax.swing.JLabel();
        txtNombreAlimentoPlanAliProf = new javax.swing.JTextField();
        btnAniadir = new javax.swing.JButton();
        lbNombre16 = new javax.swing.JLabel();
        lbCheckProfErrorAgrDiaPlanAli = new javax.swing.JLabel();
        lbCheckProfErrorAgrComidaPlanAli = new javax.swing.JLabel();
        lbCheckProfErrorNombrePlanAli = new javax.swing.JLabel();
        lbCheckProfBienAgrDiaPlanAli = new javax.swing.JLabel();
        lbCheckProfBienAgrComidaPlanAli = new javax.swing.JLabel();
        lbCheckProfBienNombrePlanAli = new javax.swing.JLabel();
        lbIdearPlanErrorUsuNoSelec = new javax.swing.JLabel();
        lbIdearPlanErrorUsuNoYaEnv = new javax.swing.JLabel();
        PConsultasRecibidasProfesional = new javax.swing.JPanel();
        lbPlanAlimentacionUsuario2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaConsultasRecibidas = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtDescripcionUsuarioConsultaRecibidaProf = new javax.swing.JTextArea();
        lbResponder = new javax.swing.JLabel();
        lbConsultas1 = new javax.swing.JLabel();
        btnResponderConsulta = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtResponderUsuarioConsultaRecibidaProf = new javax.swing.JTextArea();
        lbResponder1 = new javax.swing.JLabel();
        lbResponderConsultasRecibidasError = new javax.swing.JLabel();
        PInicioUsuario = new javax.swing.JPanel();
        lbPlanAlimentacionUsuario3 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tablaPlanAlimentacionUsuario = new javax.swing.JTable();
        lbPlanAlimentacionUsuario4 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tablaAlimentosIngeridosUsuarioInicio = new javax.swing.JTable();
        jScrollPane23 = new javax.swing.JScrollPane();
        tablaConsultasAProfesionalesUsuarioInicio = new javax.swing.JTable();
        lbPlanAlimentacionUsuario5 = new javax.swing.JLabel();
        PInicioProfesional = new javax.swing.JPanel();
        lbRegistroUsuario3 = new javax.swing.JLabel();
        lbRegistroUsuario4 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tablaInicioUltimosPlanesProf = new javax.swing.JTable();
        jScrollPane11 = new javax.swing.JScrollPane();
        tablaInicioUltimasConsutasProf = new javax.swing.JTable();
        PInicSec = new javax.swing.JPanel();
        boxRolInicSec = new javax.swing.JComboBox<>();
        lbRol = new javax.swing.JLabel();
        lbRegistroUsuario1 = new javax.swing.JLabel();
        lbRol1 = new javax.swing.JLabel();
        boxNombreInicSec = new javax.swing.JComboBox<>();
        btnInicSec = new javax.swing.JButton();
        lbNombreInicSecError = new javax.swing.JLabel();
        lbRolError = new javax.swing.JLabel();

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/check.png"))); // NOI18N

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane14.setViewportView(jTable3);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        panelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelBarraSuperiorVentana.setBackground(new java.awt.Color(0, 0, 0));
        panelBarraSuperiorVentana.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelBarraSuperiorVentanaMouseDragged(evt);
            }
        });
        panelBarraSuperiorVentana.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelBarraSuperiorVentanaMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                panelBarraSuperiorVentanaMouseReleased(evt);
            }
        });
        panelBarraSuperiorVentana.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbMinimizar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbMinimizar.setForeground(new java.awt.Color(255, 255, 255));
        lbMinimizar.setText("_");
        lbMinimizar.setToolTipText("");
        lbMinimizar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lbMinimizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbMinimizarMouseClicked(evt);
            }
        });
        panelBarraSuperiorVentana.add(lbMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 0, 10, 30));

        lbCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icons8_Delete_15px_1.png"))); // NOI18N
        lbCerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lbCerrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lbCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbCerrarMouseClicked(evt);
            }
        });
        panelBarraSuperiorVentana.add(lbCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 0, 28, 30));

        panelPrincipal.add(panelBarraSuperiorVentana, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 760, 30));

        panelLateral.setBackground(new java.awt.Color(102, 102, 102));
        panelLateral.setForeground(new java.awt.Color(51, 51, 51));
        panelLateral.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelLateralMouseDragged(evt);
            }
        });
        panelLateral.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelLateralMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                panelLateralMouseReleased(evt);
            }
        });
        panelLateral.setLayout(new java.awt.CardLayout());

        panelBotonesUsuario.setBackground(new java.awt.Color(102, 102, 102));
        panelBotonesUsuario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pBRegConsultaProfUsuario.setBackground(new java.awt.Color(153, 153, 153));
        pBRegConsultaProfUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pBRegConsultaProfUsuario.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pBRegConsultaProfUsuarioMouseDragged(evt);
            }
        });
        pBRegConsultaProfUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pBRegConsultaProfUsuarioFocusGained(evt);
            }
        });
        pBRegConsultaProfUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pBRegConsultaProfUsuarioMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pBRegConsultaProfUsuarioMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pBRegConsultaProfUsuarioMousePressed(evt);
            }
        });

        lbRegConsultaProf.setFont(cf.MyFont(0, 21f));
        lbRegConsultaProf.setForeground(new java.awt.Color(255, 255, 255));
        lbRegConsultaProf.setText("Consulta Profesional");

        lbLogoRegConsultaProf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/consprof.png"))); // NOI18N

        javax.swing.GroupLayout pBRegConsultaProfUsuarioLayout = new javax.swing.GroupLayout(pBRegConsultaProfUsuario);
        pBRegConsultaProfUsuario.setLayout(pBRegConsultaProfUsuarioLayout);
        pBRegConsultaProfUsuarioLayout.setHorizontalGroup(
            pBRegConsultaProfUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pBRegConsultaProfUsuarioLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lbLogoRegConsultaProf, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbRegConsultaProf)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pBRegConsultaProfUsuarioLayout.setVerticalGroup(
            pBRegConsultaProfUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbLogoRegConsultaProf, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(lbRegConsultaProf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelBotonesUsuario.add(pBRegConsultaProfUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 270, -1));

        pBRegPlanAlimentacionUsuario.setBackground(new java.awt.Color(153, 153, 153));
        pBRegPlanAlimentacionUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pBRegPlanAlimentacionUsuario.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pBRegPlanAlimentacionUsuarioMouseDragged(evt);
            }
        });
        pBRegPlanAlimentacionUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pBRegPlanAlimentacionUsuarioFocusGained(evt);
            }
        });
        pBRegPlanAlimentacionUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pBRegPlanAlimentacionUsuarioMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pBRegPlanAlimentacionUsuarioMousePressed(evt);
            }
        });

        lbRegPlanAlimentacion.setFont(cf.MyFont(0, 21f));
        lbRegPlanAlimentacion.setForeground(new java.awt.Color(255, 255, 255));
        lbRegPlanAlimentacion.setText("Plan de Alimentación");

        lbLogoRegPlanAlimentacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cubiertos.png"))); // NOI18N

        javax.swing.GroupLayout pBRegPlanAlimentacionUsuarioLayout = new javax.swing.GroupLayout(pBRegPlanAlimentacionUsuario);
        pBRegPlanAlimentacionUsuario.setLayout(pBRegPlanAlimentacionUsuarioLayout);
        pBRegPlanAlimentacionUsuarioLayout.setHorizontalGroup(
            pBRegPlanAlimentacionUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pBRegPlanAlimentacionUsuarioLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lbLogoRegPlanAlimentacion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbRegPlanAlimentacion)
                .addContainerGap(111, Short.MAX_VALUE))
        );
        pBRegPlanAlimentacionUsuarioLayout.setVerticalGroup(
            pBRegPlanAlimentacionUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbLogoRegPlanAlimentacion, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(lbRegPlanAlimentacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelBotonesUsuario.add(pBRegPlanAlimentacionUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 270, -1));

        pBRegAlimentosIngeridosUsuario.setBackground(new java.awt.Color(153, 153, 153));
        pBRegAlimentosIngeridosUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pBRegAlimentosIngeridosUsuario.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pBRegAlimentosIngeridosUsuarioMouseDragged(evt);
            }
        });
        pBRegAlimentosIngeridosUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pBRegAlimentosIngeridosUsuarioFocusGained(evt);
            }
        });
        pBRegAlimentosIngeridosUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pBRegAlimentosIngeridosUsuarioMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pBRegAlimentosIngeridosUsuarioMousePressed(evt);
            }
        });

        lbRegAlimentosIngeridos.setFont(cf.MyFont(0, 21f));
        lbRegAlimentosIngeridos.setForeground(new java.awt.Color(255, 255, 255));
        lbRegAlimentosIngeridos.setText("Alimentos Ingeridos");

        lbLogoRegAlimentosIngeridos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/vegetables.png"))); // NOI18N

        javax.swing.GroupLayout pBRegAlimentosIngeridosUsuarioLayout = new javax.swing.GroupLayout(pBRegAlimentosIngeridosUsuario);
        pBRegAlimentosIngeridosUsuario.setLayout(pBRegAlimentosIngeridosUsuarioLayout);
        pBRegAlimentosIngeridosUsuarioLayout.setHorizontalGroup(
            pBRegAlimentosIngeridosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pBRegAlimentosIngeridosUsuarioLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lbLogoRegAlimentosIngeridos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbRegAlimentosIngeridos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pBRegAlimentosIngeridosUsuarioLayout.setVerticalGroup(
            pBRegAlimentosIngeridosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbLogoRegAlimentosIngeridos, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(lbRegAlimentosIngeridos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelBotonesUsuario.add(pBRegAlimentosIngeridosUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 270, -1));

        pBEditarDatosUsuario.setBackground(new java.awt.Color(153, 153, 153));
        pBEditarDatosUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pBEditarDatosUsuario.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pBEditarDatosUsuarioMouseDragged(evt);
            }
        });
        pBEditarDatosUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pBEditarDatosUsuarioFocusGained(evt);
            }
        });
        pBEditarDatosUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pBEditarDatosUsuarioMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pBEditarDatosUsuarioMousePressed(evt);
            }
        });

        lbRegPlanAlimentacion1.setFont(cf.MyFont(0, 21f));
        lbRegPlanAlimentacion1.setForeground(new java.awt.Color(255, 255, 255));
        lbRegPlanAlimentacion1.setText("Editar Datos");

        lbLogoRegPlanAlimentacion1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/edit.png"))); // NOI18N

        javax.swing.GroupLayout pBEditarDatosUsuarioLayout = new javax.swing.GroupLayout(pBEditarDatosUsuario);
        pBEditarDatosUsuario.setLayout(pBEditarDatosUsuarioLayout);
        pBEditarDatosUsuarioLayout.setHorizontalGroup(
            pBEditarDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pBEditarDatosUsuarioLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lbLogoRegPlanAlimentacion1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbRegPlanAlimentacion1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pBEditarDatosUsuarioLayout.setVerticalGroup(
            pBEditarDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbLogoRegPlanAlimentacion1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(lbRegPlanAlimentacion1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelBotonesUsuario.add(pBEditarDatosUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 270, -1));

        pBYaSalirUsuario.setBackground(new java.awt.Color(51, 153, 0));
        pBYaSalirUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pBYaSalirUsuario.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pBYaSalirUsuarioMouseDragged(evt);
            }
        });
        pBYaSalirUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pBYaSalirUsuarioFocusGained(evt);
            }
        });
        pBYaSalirUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pBYaSalirUsuarioMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pBYaSalirUsuarioMousePressed(evt);
            }
        });

        lbYaEstoyReg1.setFont(cf.MyFont(0, 21f));
        lbYaEstoyReg1.setForeground(new java.awt.Color(255, 255, 255));
        lbYaEstoyReg1.setText("Salir");

        javax.swing.GroupLayout pBYaSalirUsuarioLayout = new javax.swing.GroupLayout(pBYaSalirUsuario);
        pBYaSalirUsuario.setLayout(pBYaSalirUsuarioLayout);
        pBYaSalirUsuarioLayout.setHorizontalGroup(
            pBYaSalirUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pBYaSalirUsuarioLayout.createSequentialGroup()
                .addContainerGap(137, Short.MAX_VALUE)
                .addComponent(lbYaEstoyReg1)
                .addGap(113, 113, 113))
        );
        pBYaSalirUsuarioLayout.setVerticalGroup(
            pBYaSalirUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbYaEstoyReg1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        panelBotonesUsuario.add(pBYaSalirUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 270, 50));

        pBVolverInicioUsuario.setBackground(new java.awt.Color(102, 102, 102));
        pBVolverInicioUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pBVolverInicioUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pBVolverInicioUsuarioMouseClicked(evt);
            }
        });
        pBVolverInicioUsuario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbAvatarInicSecUsuario.setForeground(new java.awt.Color(240, 240, 240));
        pBVolverInicioUsuario.add(lbAvatarInicSecUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 140, 130));

        lbMostrarNombreUsuario.setFont(cf.MyFont(0, 21f));
        lbMostrarNombreUsuario.setText("Nombre de Usuario");
        lbMostrarNombreUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbMostrarNombreUsuarioMouseClicked(evt);
            }
        });
        pBVolverInicioUsuario.add(lbMostrarNombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 150, 50));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/portada.jpg"))); // NOI18N
        pBVolverInicioUsuario.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 490, 360));
        jLabel8.getAccessibleContext().setAccessibleName("panelinicio");

        panelBotonesUsuario.add(pBVolverInicioUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 190, 160));

        panelLateral.add(panelBotonesUsuario, "panelBotonesUsuario");

        panelBotonesInicial.setBackground(new java.awt.Color(102, 102, 102));
        panelBotonesInicial.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pBRegProfesional.setBackground(new java.awt.Color(153, 153, 153));
        pBRegProfesional.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pBRegProfesional.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pBRegProfesionalMouseDragged(evt);
            }
        });
        pBRegProfesional.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pBRegProfesionalFocusGained(evt);
            }
        });
        pBRegProfesional.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pBRegProfesionalMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pBRegProfesionalMousePressed(evt);
            }
        });

        lbRegProf.setFont(cf.MyFont(0, 21f));
        lbRegProf.setForeground(new java.awt.Color(255, 255, 255));
        lbRegProf.setText("Registrar Profesional");

        lbLogoRegProf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/userprof.png"))); // NOI18N

        javax.swing.GroupLayout pBRegProfesionalLayout = new javax.swing.GroupLayout(pBRegProfesional);
        pBRegProfesional.setLayout(pBRegProfesionalLayout);
        pBRegProfesionalLayout.setHorizontalGroup(
            pBRegProfesionalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pBRegProfesionalLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lbLogoRegProf, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbRegProf)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pBRegProfesionalLayout.setVerticalGroup(
            pBRegProfesionalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbLogoRegProf, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(lbRegProf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelBotonesInicial.add(pBRegProfesional, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 270, -1));

        pBRegUsuario.setBackground(new java.awt.Color(153, 153, 153));
        pBRegUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pBRegUsuario.setFont(cf.MyFont(1, 18)
        );
        pBRegUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pBRegUsuarioMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pBRegUsuarioMousePressed(evt);
            }
        });

        lbRegUsuario.setFont(cf.MyFont(0, 21f)
        );
        lbRegUsuario.setForeground(new java.awt.Color(255, 255, 255));
        lbRegUsuario.setText("Registrar Usuario");

        lbLogoRegUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/user.png"))); // NOI18N

        javax.swing.GroupLayout pBRegUsuarioLayout = new javax.swing.GroupLayout(pBRegUsuario);
        pBRegUsuario.setLayout(pBRegUsuarioLayout);
        pBRegUsuarioLayout.setHorizontalGroup(
            pBRegUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pBRegUsuarioLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lbLogoRegUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbRegUsuario)
                .addContainerGap(123, Short.MAX_VALUE))
        );
        pBRegUsuarioLayout.setVerticalGroup(
            pBRegUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbLogoRegUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbRegUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelBotonesInicial.add(pBRegUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 270, 40));

        pBYaEstoyReg.setBackground(new java.awt.Color(51, 153, 0));
        pBYaEstoyReg.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pBYaEstoyReg.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pBYaEstoyRegMouseDragged(evt);
            }
        });
        pBYaEstoyReg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pBYaEstoyRegFocusGained(evt);
            }
        });
        pBYaEstoyReg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pBYaEstoyRegMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pBYaEstoyRegMousePressed(evt);
            }
        });

        lbYaEstoyReg.setFont(cf.MyFont(0, 21f));
        lbYaEstoyReg.setForeground(new java.awt.Color(255, 255, 255));
        lbYaEstoyReg.setText("<html><center>Ya estoy registrado</center></html>");

        javax.swing.GroupLayout pBYaEstoyRegLayout = new javax.swing.GroupLayout(pBYaEstoyReg);
        pBYaEstoyReg.setLayout(pBYaEstoyRegLayout);
        pBYaEstoyRegLayout.setHorizontalGroup(
            pBYaEstoyRegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pBYaEstoyRegLayout.createSequentialGroup()
                .addContainerGap(134, Short.MAX_VALUE)
                .addComponent(lbYaEstoyReg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
        pBYaEstoyRegLayout.setVerticalGroup(
            pBYaEstoyRegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbYaEstoyReg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        panelBotonesInicial.add(pBYaEstoyReg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 270, 50));

        lbLogoPrincipal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo_1.png"))); // NOI18N
        lbLogoPrincipal.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lbLogoPrincipal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbLogoPrincipalMouseClicked(evt);
            }
        });
        panelBotonesInicial.add(lbLogoPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 150, 40));

        panelLateral.add(panelBotonesInicial, "panelBotonesInicio");

        panelBotonesProfesional.setBackground(new java.awt.Color(102, 102, 102));
        panelBotonesProfesional.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pBRegAlimentosProf.setBackground(new java.awt.Color(153, 153, 153));
        pBRegAlimentosProf.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pBRegAlimentosProf.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pBRegAlimentosProfMouseDragged(evt);
            }
        });
        pBRegAlimentosProf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pBRegAlimentosProfFocusGained(evt);
            }
        });
        pBRegAlimentosProf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pBRegAlimentosProfMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pBRegAlimentosProfMousePressed(evt);
            }
        });

        lbRegAlimentos1.setFont(cf.MyFont(0, 21f));
        lbRegAlimentos1.setForeground(new java.awt.Color(255, 255, 255));
        lbRegAlimentos1.setText("Registrar Alimento");

        lbLogoRegAlimentos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ingeridos.png"))); // NOI18N

        javax.swing.GroupLayout pBRegAlimentosProfLayout = new javax.swing.GroupLayout(pBRegAlimentosProf);
        pBRegAlimentosProf.setLayout(pBRegAlimentosProfLayout);
        pBRegAlimentosProfLayout.setHorizontalGroup(
            pBRegAlimentosProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pBRegAlimentosProfLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lbLogoRegAlimentos1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbRegAlimentos1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pBRegAlimentosProfLayout.setVerticalGroup(
            pBRegAlimentosProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbLogoRegAlimentos1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(lbRegAlimentos1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelBotonesProfesional.add(pBRegAlimentosProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 270, -1));

        pBEditarDatosProf.setBackground(new java.awt.Color(153, 153, 153));
        pBEditarDatosProf.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pBEditarDatosProf.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pBEditarDatosProfMouseDragged(evt);
            }
        });
        pBEditarDatosProf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pBEditarDatosProfFocusGained(evt);
            }
        });
        pBEditarDatosProf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pBEditarDatosProfMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pBEditarDatosProfMousePressed(evt);
            }
        });

        lbRegAlimentos2.setFont(cf.MyFont(0, 21f));
        lbRegAlimentos2.setForeground(new java.awt.Color(255, 255, 255));
        lbRegAlimentos2.setText("Editar Datos");

        lbLogoRegAlimentos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/edit.png"))); // NOI18N

        javax.swing.GroupLayout pBEditarDatosProfLayout = new javax.swing.GroupLayout(pBEditarDatosProf);
        pBEditarDatosProf.setLayout(pBEditarDatosProfLayout);
        pBEditarDatosProfLayout.setHorizontalGroup(
            pBEditarDatosProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pBEditarDatosProfLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lbLogoRegAlimentos2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbRegAlimentos2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pBEditarDatosProfLayout.setVerticalGroup(
            pBEditarDatosProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbLogoRegAlimentos2, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(lbRegAlimentos2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelBotonesProfesional.add(pBEditarDatosProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 270, -1));

        pBConsultasRecibidasProf.setBackground(new java.awt.Color(153, 153, 153));
        pBConsultasRecibidasProf.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pBConsultasRecibidasProf.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pBConsultasRecibidasProfMouseDragged(evt);
            }
        });
        pBConsultasRecibidasProf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pBConsultasRecibidasProfFocusGained(evt);
            }
        });
        pBConsultasRecibidasProf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pBConsultasRecibidasProfMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pBConsultasRecibidasProfMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pBConsultasRecibidasProfMousePressed(evt);
            }
        });

        lbRegAlimentos3.setFont(cf.MyFont(0, 21f));
        lbRegAlimentos3.setForeground(new java.awt.Color(255, 255, 255));
        lbRegAlimentos3.setText("Consultas Recibidas");

        lbLogoRegAlimentos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/letter.png"))); // NOI18N

        javax.swing.GroupLayout pBConsultasRecibidasProfLayout = new javax.swing.GroupLayout(pBConsultasRecibidasProf);
        pBConsultasRecibidasProf.setLayout(pBConsultasRecibidasProfLayout);
        pBConsultasRecibidasProfLayout.setHorizontalGroup(
            pBConsultasRecibidasProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pBConsultasRecibidasProfLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lbLogoRegAlimentos3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbRegAlimentos3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pBConsultasRecibidasProfLayout.setVerticalGroup(
            pBConsultasRecibidasProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbLogoRegAlimentos3, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(lbRegAlimentos3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelBotonesProfesional.add(pBConsultasRecibidasProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 270, -1));

        pBPlanAlimentacionProf.setBackground(new java.awt.Color(153, 153, 153));
        pBPlanAlimentacionProf.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pBPlanAlimentacionProf.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pBPlanAlimentacionProfMouseDragged(evt);
            }
        });
        pBPlanAlimentacionProf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pBPlanAlimentacionProfFocusGained(evt);
            }
        });
        pBPlanAlimentacionProf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pBPlanAlimentacionProfMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pBPlanAlimentacionProfMousePressed(evt);
            }
        });

        lbRegAlimentos4.setFont(cf.MyFont(0, 21f));
        lbRegAlimentos4.setForeground(new java.awt.Color(255, 255, 255));
        lbRegAlimentos4.setText("Plan de Alimentación");

        lbLogoRegAlimentos4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cubiertos.png"))); // NOI18N

        javax.swing.GroupLayout pBPlanAlimentacionProfLayout = new javax.swing.GroupLayout(pBPlanAlimentacionProf);
        pBPlanAlimentacionProf.setLayout(pBPlanAlimentacionProfLayout);
        pBPlanAlimentacionProfLayout.setHorizontalGroup(
            pBPlanAlimentacionProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pBPlanAlimentacionProfLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lbLogoRegAlimentos4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbRegAlimentos4)
                .addContainerGap(105, Short.MAX_VALUE))
        );
        pBPlanAlimentacionProfLayout.setVerticalGroup(
            pBPlanAlimentacionProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbLogoRegAlimentos4, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(lbRegAlimentos4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelBotonesProfesional.add(pBPlanAlimentacionProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 270, -1));

        pBSalirProf.setBackground(new java.awt.Color(51, 153, 0));
        pBSalirProf.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pBSalirProf.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pBSalirProfMouseDragged(evt);
            }
        });
        pBSalirProf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pBSalirProfFocusGained(evt);
            }
        });
        pBSalirProf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pBSalirProfMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pBSalirProfMousePressed(evt);
            }
        });

        lbYaEstoyReg2.setFont(cf.MyFont(0, 21f));
        lbYaEstoyReg2.setForeground(new java.awt.Color(255, 255, 255));
        lbYaEstoyReg2.setText("Salir");

        javax.swing.GroupLayout pBSalirProfLayout = new javax.swing.GroupLayout(pBSalirProf);
        pBSalirProf.setLayout(pBSalirProfLayout);
        pBSalirProfLayout.setHorizontalGroup(
            pBSalirProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pBSalirProfLayout.createSequentialGroup()
                .addContainerGap(137, Short.MAX_VALUE)
                .addComponent(lbYaEstoyReg2)
                .addGap(113, 113, 113))
        );
        pBSalirProfLayout.setVerticalGroup(
            pBSalirProfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbYaEstoyReg2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        panelBotonesProfesional.add(pBSalirProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 270, 50));

        pBVolverInicioProf.setBackground(new java.awt.Color(102, 102, 102));
        pBVolverInicioProf.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pBVolverInicioProf.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pBVolverInicioProf.add(lbAvatarInicSecProfesional, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 150, 120));

        lbMostrarNombreProfesional.setFont(cf.MyFont(0, 21f));
        lbMostrarNombreProfesional.setText("Nombre del profesional");
        pBVolverInicioProf.add(lbMostrarNombreProfesional, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 160, 50));

        panelBotonesProfesional.add(pBVolverInicioProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 190, 160));

        panelLateral.add(panelBotonesProfesional, "panelBotonesProfesional");

        panelPrincipal.add(panelLateral, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 270, 560));

        panelContenido.setBackground(new java.awt.Color(0, 0, 0));
        panelContenido.setLayout(new java.awt.CardLayout());

        PInicio.setBackground(new java.awt.Color(0, 0, 0));
        PInicio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelContenido.add(PInicio, "panelInicio");

        PConsultaProfUsuario.setBackground(new java.awt.Color(0, 0, 0));
        PConsultaProfUsuario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtDescripcionConsultaProf.setColumns(20);
        txtDescripcionConsultaProf.setFont(cf.MyFont(0, 16f));
        txtDescripcionConsultaProf.setRows(5);
        jScrollPane2.setViewportView(txtDescripcionConsultaProf);

        PConsultaProfUsuario.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 330, 110));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(cf.MyFont(0, 20f));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        PConsultaProfUsuario.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        rbtnAliIng.setBackground(new java.awt.Color(0, 0, 0));
        rbtnGrupConsultaProfMotivo.add(rbtnAliIng);
        rbtnAliIng.setFont(cf.MyFont(0, 16f));
        rbtnAliIng.setForeground(new java.awt.Color(255, 255, 255));
        rbtnAliIng.setText("Alimentos Ingeridos");
        rbtnAliIng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnAliIngActionPerformed(evt);
            }
        });
        PConsultaProfUsuario.add(rbtnAliIng, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        rbtnAliIgerir.setBackground(new java.awt.Color(0, 0, 0));
        rbtnGrupConsultaProfMotivo.add(rbtnAliIgerir);
        rbtnAliIgerir.setFont(cf.MyFont(0, 16f));
        rbtnAliIgerir.setForeground(new java.awt.Color(255, 255, 255));
        rbtnAliIgerir.setText("Alimentos a Ingerir");
        PConsultaProfUsuario.add(rbtnAliIgerir, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        rbtnAliOtros.setBackground(new java.awt.Color(0, 0, 0));
        rbtnGrupConsultaProfMotivo.add(rbtnAliOtros);
        rbtnAliOtros.setFont(cf.MyFont(0, 16f));
        rbtnAliOtros.setForeground(new java.awt.Color(255, 255, 255));
        rbtnAliOtros.setText("Otros");
        rbtnAliOtros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnAliOtrosActionPerformed(evt);
            }
        });
        PConsultaProfUsuario.add(rbtnAliOtros, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, -1));

        btnGuardarUsuario1.setBackground(new java.awt.Color(153, 153, 153));
        btnGuardarUsuario1.setFont(cf.MyFont(0, 20f));
        btnGuardarUsuario1.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarUsuario1.setText("Enviar");
        btnGuardarUsuario1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarUsuario1ActionPerformed(evt);
            }
        });
        PConsultaProfUsuario.add(btnGuardarUsuario1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 460, 120, 30));

        lbRegistroUsuario2.setFont(cf.MyFont(1, 30f));
        lbRegistroUsuario2.setForeground(new java.awt.Color(255, 255, 255));
        lbRegistroUsuario2.setText("Consulta Profesional");
        PConsultaProfUsuario.add(lbRegistroUsuario2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 330, 40));

        boxNombreProfesional.setFont(cf.MyFont(0, 20f));
        PConsultaProfUsuario.add(boxNombreProfesional, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 230, 30));

        lbNombre1.setFont(cf.MyFont(0, 20f));
        lbNombre1.setForeground(new java.awt.Color(255, 255, 255));
        lbNombre1.setText("Descripción");
        PConsultaProfUsuario.add(lbNombre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, -1, -1));

        lbNombre2.setFont(cf.MyFont(0, 20f));
        lbNombre2.setForeground(new java.awt.Color(255, 255, 255));
        lbNombre2.setText("Profesional");
        PConsultaProfUsuario.add(lbNombre2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        lbNombre3.setFont(cf.MyFont(0, 20f));
        lbNombre3.setForeground(new java.awt.Color(255, 255, 255));
        lbNombre3.setText("Motivo");
        PConsultaProfUsuario.add(lbNombre3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        lbCheckProfErrorConsProfUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PConsultaProfUsuario.add(lbCheckProfErrorConsProfUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 90, -1, 30));

        lbCheckProfBienConsProfUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PConsultaProfUsuario.add(lbCheckProfBienConsProfUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 90, -1, 30));

        lbDescripcionError.setForeground(new java.awt.Color(255, 0, 0));
        lbDescripcionError.setText("<html>*Escriba en este campo todo<br> lo que considere pertinente</html>");
        PConsultaProfUsuario.add(lbDescripcionError, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 450, -1, -1));

        lbMotivoError.setForeground(new java.awt.Color(255, 0, 0));
        lbMotivoError.setText("<html>*Debe indicar el <br> motivo de la consulta</html>");
        PConsultaProfUsuario.add(lbMotivoError, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 240, -1, -1));

        tablaConsultasUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaConsultasUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaConsultasUsuarioMouseClicked(evt);
            }
        });
        jScrollPane15.setViewportView(tablaConsultasUsuario);

        PConsultaProfUsuario.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 100, 330, 90));

        lbNombre11.setFont(cf.MyFont(0, 20f));
        lbNombre11.setForeground(new java.awt.Color(255, 255, 255));
        lbNombre11.setText("Sus consultas");
        PConsultaProfUsuario.add(lbNombre11, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 60, -1, -1));

        lbNombre12.setFont(cf.MyFont(0, 20f));
        lbNombre12.setForeground(new java.awt.Color(255, 255, 255));
        lbNombre12.setText("Pregunta");
        PConsultaProfUsuario.add(lbNombre12, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 210, -1, -1));

        txtPreguntaUsuarioConsultaUsuario.setEditable(false);
        txtPreguntaUsuarioConsultaUsuario.setColumns(20);
        txtPreguntaUsuarioConsultaUsuario.setFont(cf.MyFont(0, 16f));
        txtPreguntaUsuarioConsultaUsuario.setRows(5);
        jScrollPane16.setViewportView(txtPreguntaUsuarioConsultaUsuario);

        PConsultaProfUsuario.add(jScrollPane16, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 240, 330, 60));

        txtRespuestaUsuarioConsultaUsuario.setEditable(false);
        txtRespuestaUsuarioConsultaUsuario.setColumns(20);
        txtRespuestaUsuarioConsultaUsuario.setFont(cf.MyFont(0, 16f));
        txtRespuestaUsuarioConsultaUsuario.setRows(5);
        jScrollPane17.setViewportView(txtRespuestaUsuarioConsultaUsuario);

        PConsultaProfUsuario.add(jScrollPane17, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 370, 330, 70));

        lbNombre13.setFont(cf.MyFont(0, 20f));
        lbNombre13.setForeground(new java.awt.Color(255, 255, 255));
        lbNombre13.setText("Respuesta");
        PConsultaProfUsuario.add(lbNombre13, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 340, -1, -1));

        panelContenido.add(PConsultaProfUsuario, "panelConsultaProf");
        PConsultaProfUsuario.getAccessibleContext().setAccessibleDescription("");

        PRegUsuario.setBackground(new java.awt.Color(0, 0, 0));
        PRegUsuario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbRegistroUsuario.setFont(cf.MyFont(1, 30f));
        lbRegistroUsuario.setForeground(new java.awt.Color(255, 255, 255));
        lbRegistroUsuario.setText("Registrar Usuario");
        PRegUsuario.add(lbRegistroUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 330, 40));

        txtNombre.setFont(cf.MyFont(0, 20f));
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });
        PRegUsuario.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, 230, 32));

        lbNombre.setFont(cf.MyFont(0, 20f));
        lbNombre.setForeground(new java.awt.Color(255, 255, 255));
        lbNombre.setText("Nombres");
        PRegUsuario.add(lbNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, -1, -1));

        lbApellido.setFont(cf.MyFont(0, 20f));
        lbApellido.setForeground(new java.awt.Color(255, 255, 255));
        lbApellido.setText("Apellidos");
        PRegUsuario.add(lbApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 70, -1, -1));

        txtApellido.setFont(cf.MyFont(0, 20f));
        txtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoActionPerformed(evt);
            }
        });
        txtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoKeyTyped(evt);
            }
        });
        PRegUsuario.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 100, 240, 32));

        boxNacionalidadUsuario.setFont(cf.MyFont(0, 20f));
        PRegUsuario.add(boxNacionalidadUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 230, 30));

        lbNacionalidad.setFont(cf.MyFont(0, 20f));
        lbNacionalidad.setForeground(new java.awt.Color(255, 255, 255));
        lbNacionalidad.setText("Nacionalidad");
        PRegUsuario.add(lbNacionalidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, -1, -1));

        lbNacimiento.setFont(cf.MyFont(0, 20f));
        lbNacimiento.setForeground(new java.awt.Color(255, 255, 255));
        lbNacimiento.setText("Nacimiento");
        PRegUsuario.add(lbNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 150, -1, -1));

        lbPreferencias.setFont(cf.MyFont(0, 20f));
        lbPreferencias.setForeground(new java.awt.Color(255, 255, 255));
        lbPreferencias.setText("Preferencias");
        PRegUsuario.add(lbPreferencias, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, -1, -1));

        checkboxResOtros.setBackground(new java.awt.Color(0, 0, 0));
        checkboxResOtros.setFont(cf.MyFont(0, 16f));
        checkboxResOtros.setForeground(new java.awt.Color(255, 255, 255));
        checkboxResOtros.setText("Otros");
        checkboxResOtros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxResOtrosActionPerformed(evt);
            }
        });
        PRegUsuario.add(checkboxResOtros, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 410, -1, -1));

        checkboxResCarnesRojas.setBackground(new java.awt.Color(0, 0, 0));
        checkboxResCarnesRojas.setFont(cf.MyFont(0, 16f));
        checkboxResCarnesRojas.setForeground(new java.awt.Color(255, 255, 255));
        checkboxResCarnesRojas.setText("Carnes Rojas");
        checkboxResCarnesRojas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxResCarnesRojasActionPerformed(evt);
            }
        });
        PRegUsuario.add(checkboxResCarnesRojas, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 380, -1, -1));

        checkboxPrefLacteos.setBackground(new java.awt.Color(0, 0, 0));
        checkboxPrefLacteos.setFont(cf.MyFont(0, 16f));
        checkboxPrefLacteos.setForeground(new java.awt.Color(255, 255, 255));
        checkboxPrefLacteos.setText("Lacteos");
        PRegUsuario.add(checkboxPrefLacteos, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, -1, -1));

        checkboxPrefFrutas.setBackground(new java.awt.Color(0, 0, 0));
        checkboxPrefFrutas.setFont(cf.MyFont(0, 16f));
        checkboxPrefFrutas.setForeground(new java.awt.Color(255, 255, 255));
        checkboxPrefFrutas.setText("Frutas");
        PRegUsuario.add(checkboxPrefFrutas, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, -1, -1));

        checkboxPrefVerduras.setBackground(new java.awt.Color(0, 0, 0));
        checkboxPrefVerduras.setFont(cf.MyFont(0, 16f));
        checkboxPrefVerduras.setForeground(new java.awt.Color(255, 255, 255));
        checkboxPrefVerduras.setText("Verduras");
        PRegUsuario.add(checkboxPrefVerduras, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, -1, -1));

        lbDescripcion.setFont(cf.MyFont(0, 20f));
        lbDescripcion.setForeground(new java.awt.Color(255, 255, 255));
        lbDescripcion.setText("Descripción");
        PRegUsuario.add(lbDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 260, -1, -1));

        checkboxResSalado.setBackground(new java.awt.Color(0, 0, 0));
        checkboxResSalado.setFont(cf.MyFont(0, 16f));
        checkboxResSalado.setForeground(new java.awt.Color(255, 255, 255));
        checkboxResSalado.setText("Salado");
        checkboxResSalado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxResSaladoActionPerformed(evt);
            }
        });
        PRegUsuario.add(checkboxResSalado, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 290, -1, -1));

        checkboxResDulce.setBackground(new java.awt.Color(0, 0, 0));
        checkboxResDulce.setFont(cf.MyFont(0, 16f));
        checkboxResDulce.setForeground(new java.awt.Color(255, 255, 255));
        checkboxResDulce.setText("Dulce");
        PRegUsuario.add(checkboxResDulce, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 320, -1, -1));

        checkboxResLacteos.setBackground(new java.awt.Color(0, 0, 0));
        checkboxResLacteos.setFont(cf.MyFont(0, 16f));
        checkboxResLacteos.setForeground(new java.awt.Color(255, 255, 255));
        checkboxResLacteos.setText("Lacteos");
        PRegUsuario.add(checkboxResLacteos, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, -1, -1));

        checkboxPrefOtros.setBackground(new java.awt.Color(0, 0, 0));
        checkboxPrefOtros.setFont(cf.MyFont(0, 16f));
        checkboxPrefOtros.setForeground(new java.awt.Color(255, 255, 255));
        checkboxPrefOtros.setText("Otros");
        checkboxPrefOtros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxPrefOtrosActionPerformed(evt);
            }
        });
        PRegUsuario.add(checkboxPrefOtros, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 410, -1, -1));

        checkboxPrefCarnes.setBackground(new java.awt.Color(0, 0, 0));
        checkboxPrefCarnes.setFont(cf.MyFont(0, 16f));
        checkboxPrefCarnes.setForeground(new java.awt.Color(255, 255, 255));
        checkboxPrefCarnes.setText("Carnes");
        checkboxPrefCarnes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxPrefCarnesActionPerformed(evt);
            }
        });
        PRegUsuario.add(checkboxPrefCarnes, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 290, -1, -1));

        lbRestricciones.setFont(cf.MyFont(0, 20f));
        lbRestricciones.setForeground(new java.awt.Color(255, 255, 255));
        lbRestricciones.setText("Restricciones");
        PRegUsuario.add(lbRestricciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 260, -1, -1));

        txtDescripcionUsuario.setColumns(20);
        txtDescripcionUsuario.setFont(cf.MyFont(0, 16f));
        txtDescripcionUsuario.setRows(5);
        jScrollPane1.setViewportView(txtDescripcionUsuario);

        PRegUsuario.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 300, 340, 100));

        lbAvatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/avatar.png"))); // NOI18N
        lbAvatar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lbAvatar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbAvatarMouseClicked(evt);
            }
        });
        PRegUsuario.add(lbAvatar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 120, 140));

        btnRegistrarUsuario.setBackground(new java.awt.Color(153, 153, 153));
        btnRegistrarUsuario.setFont(cf.MyFont(0, 20f));
        btnRegistrarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarUsuario.setText("Registrar");
        btnRegistrarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarUsuarioActionPerformed(evt);
            }
        });
        PRegUsuario.add(btnRegistrarUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 430, 120, 30));

        lbCheckApellidoErrorUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PRegUsuario.add(lbCheckApellidoErrorUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 100, -1, 30));

        lbCheckDchNacimientoErrorUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PRegUsuario.add(lbCheckDchNacimientoErrorUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 180, -1, 30));

        lbCheckNombreErrorUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PRegUsuario.add(lbCheckNombreErrorUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, -1, 30));

        boxCheckNacionalidadErrorUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PRegUsuario.add(boxCheckNacionalidadErrorUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 180, -1, 30));

        lbCheckApellidoBienUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PRegUsuario.add(lbCheckApellidoBienUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 100, -1, 30));

        lbCheckNombreBienUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PRegUsuario.add(lbCheckNombreBienUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, -1, 30));

        boxCheckNacionalidadBienUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PRegUsuario.add(boxCheckNacionalidadBienUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 180, -1, 30));

        lbCheckDchNacimientoBienUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PRegUsuario.add(lbCheckDchNacimientoBienUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 180, -1, 30));

        dcNacimientoUsuario.setFont(cf.MyFont(0, 20f));
        dcNacimientoUsuario.setMaxSelectableDate(new Date());
        dcNacimientoUsuario.setMinSelectableDate(new java.util.Date(-1735676116000L));
        PRegUsuario.add(dcNacimientoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 180, 240, 30));

        btnEditarUsuario.setBackground(new java.awt.Color(153, 153, 153));
        btnEditarUsuario.setFont(cf.MyFont(0, 20f));
        btnEditarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnEditarUsuario.setText("Guardar");
        btnEditarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarUsuarioActionPerformed(evt);
            }
        });
        PRegUsuario.add(btnEditarUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 430, 120, 30));

        lbCheckRegistroUsuarioExitoso.setForeground(new java.awt.Color(51, 204, 0));
        lbCheckRegistroUsuarioExitoso.setText("<html>*Registro exitoso</html>");
        PRegUsuario.add(lbCheckRegistroUsuarioExitoso, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 420, -1, -1));

        lbCheckRegistroUsuarioRepetido.setForeground(new java.awt.Color(255, 0, 0));
        lbCheckRegistroUsuarioRepetido.setText("<html>*Parece que el usuario que <br> intenta registrar ya existe</html>");
        PRegUsuario.add(lbCheckRegistroUsuarioRepetido, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 420, -1, -1));

        panelContenido.add(PRegUsuario, "panelUsuario");

        PRegProfesional.setBackground(new java.awt.Color(0, 0, 0));
        PRegProfesional.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbRegistroProfesional.setFont(cf.MyFont(1, 30f));
        lbRegistroProfesional.setForeground(new java.awt.Color(255, 255, 255));
        lbRegistroProfesional.setText("Registro Profesional");
        PRegProfesional.add(lbRegistroProfesional, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 360, 40));

        lbAvatarProf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/avatar.png"))); // NOI18N
        lbAvatarProf.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lbAvatarProf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbAvatarProfMouseClicked(evt);
            }
        });
        PRegProfesional.add(lbAvatarProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 120, 140));

        lbNombreProf.setFont(cf.MyFont(0, 20f));
        lbNombreProf.setForeground(new java.awt.Color(255, 255, 255));
        lbNombreProf.setText("Nombres");
        PRegProfesional.add(lbNombreProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, -1, -1));

        txtTitulo.setFont(cf.MyFont(0, 20f));
        txtTitulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTituloKeyTyped(evt);
            }
        });
        PRegProfesional.add(txtTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 240, 32));

        lbApellidoProf.setFont(cf.MyFont(0, 20f));
        lbApellidoProf.setForeground(new java.awt.Color(255, 255, 255));
        lbApellidoProf.setText("Apellidos");
        PRegProfesional.add(lbApellidoProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 70, -1, -1));

        txtApellidoProf.setFont(cf.MyFont(0, 20f));
        txtApellidoProf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoProfKeyTyped(evt);
            }
        });
        PRegProfesional.add(txtApellidoProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 100, 240, 32));

        boxPaisTitProf.setFont(cf.MyFont(0, 20f));
        PRegProfesional.add(boxPaisTitProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 240, 30));

        lbTituloProf.setFont(cf.MyFont(0, 20f));
        lbTituloProf.setForeground(new java.awt.Color(255, 255, 255));
        lbTituloProf.setText("País de Obtención del Título");
        PRegProfesional.add(lbTituloProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 320, -1, -1));

        lbNacimientoProf.setFont(cf.MyFont(0, 20f));
        lbNacimientoProf.setForeground(new java.awt.Color(255, 255, 255));
        lbNacimientoProf.setText("Nacimiento");
        PRegProfesional.add(lbNacimientoProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, -1, -1));

        lbFechaGraduacionProf1.setFont(cf.MyFont(0, 20f));
        lbFechaGraduacionProf1.setForeground(new java.awt.Color(255, 255, 255));
        lbFechaGraduacionProf1.setText("Fecha de Graduación");
        PRegProfesional.add(lbFechaGraduacionProf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 240, -1, -1));

        txtNombreProf.setFont(cf.MyFont(0, 20f));
        txtNombreProf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreProfActionPerformed(evt);
            }
        });
        txtNombreProf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreProfKeyTyped(evt);
            }
        });
        PRegProfesional.add(txtNombreProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, 240, 32));

        lbTituloProf1.setFont(cf.MyFont(0, 20f));
        lbTituloProf1.setForeground(new java.awt.Color(255, 255, 255));
        lbTituloProf1.setText("Título Profesional");
        PRegProfesional.add(lbTituloProf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 240, -1, -1));

        btnRegistrarProfesional.setBackground(new java.awt.Color(153, 153, 153));
        btnRegistrarProfesional.setFont(cf.MyFont(0, 20f));
        btnRegistrarProfesional.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarProfesional.setText("Registrar");
        btnRegistrarProfesional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarProfesionalActionPerformed(evt);
            }
        });
        PRegProfesional.add(btnRegistrarProfesional, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 430, 120, 30));

        lbCheckNombreErrorProf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PRegProfesional.add(lbCheckNombreErrorProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 100, -1, 30));

        lbCheckApellidoErrorProf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PRegProfesional.add(lbCheckApellidoErrorProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 100, -1, 30));

        lbCheckTituloProfesionaErrorProf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PRegProfesional.add(lbCheckTituloProfesionaErrorProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 270, -1, 30));

        lbCheckPaisTituloErrorProf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PRegProfesional.add(lbCheckPaisTituloErrorProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 350, -1, 30));

        lbCheckNacimientoErrorProf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PRegProfesional.add(lbCheckNacimientoErrorProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 180, -1, 30));

        lbCheckGraduacionErrorProf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PRegProfesional.add(lbCheckGraduacionErrorProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 270, -1, 30));

        lbCheckPaisTituloBienProf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PRegProfesional.add(lbCheckPaisTituloBienProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 350, -1, 30));

        lbCheckNombreBienProf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PRegProfesional.add(lbCheckNombreBienProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 100, -1, 30));

        lbCheckApellidoBienProf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PRegProfesional.add(lbCheckApellidoBienProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 100, -1, 30));

        lbCheckTituloProfesionaBienlProf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PRegProfesional.add(lbCheckTituloProfesionaBienlProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 270, 20, 30));

        lbCheckGraduacionBienProf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PRegProfesional.add(lbCheckGraduacionBienProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 270, -1, 30));

        lbCheckNacimientoBienProf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PRegProfesional.add(lbCheckNacimientoBienProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 180, -1, 30));

        dcNacimientoProf.setFont(cf.MyFont(0, 20f));
        dcNacimientoProf.setMaxSelectableDate( new Date());
        dcNacimientoProf.setMinSelectableDate(new java.util.Date(-1735676116000L));
        PRegProfesional.add(dcNacimientoProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 240, 30));

        dcGraduacionProf.setFocusable(false);
        dcGraduacionProf.setFont(cf.MyFont(0, 20f));
        dcGraduacionProf.setMaxSelectableDate(new Date());
        dcGraduacionProf.setMinSelectableDate(new java.util.Date(-1420059600000L));
        PRegProfesional.add(dcGraduacionProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 270, 240, 30));

        btnEditarProfesional.setBackground(new java.awt.Color(153, 153, 153));
        btnEditarProfesional.setFont(cf.MyFont(0, 20f));
        btnEditarProfesional.setForeground(new java.awt.Color(255, 255, 255));
        btnEditarProfesional.setText("Guardar");
        btnEditarProfesional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProfesionalActionPerformed(evt);
            }
        });
        PRegProfesional.add(btnEditarProfesional, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 430, 120, 30));

        lbCheckNacimientoMenorEdadProf.setForeground(new java.awt.Color(255, 0, 0));
        lbCheckNacimientoMenorEdadProf.setText("<html>*Debe ser mayor de edad <br> para registrarse como profesional</html>");
        PRegProfesional.add(lbCheckNacimientoMenorEdadProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, -1, -1));

        lbCheckGraduacionFechaDesfasadaProf.setForeground(new java.awt.Color(255, 0, 0));
        lbCheckGraduacionFechaDesfasadaProf.setText("<html>*La fecha de graduación debe ser <br>  mayor que la fecha de nacimiento</html>");
        PRegProfesional.add(lbCheckGraduacionFechaDesfasadaProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 320, -1, -1));

        panelContenido.add(PRegProfesional, "panelProfesional");

        PRegAlimentosProfesional.setBackground(new java.awt.Color(0, 0, 0));
        PRegAlimentosProfesional.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbRegistroAlimento.setFont(cf.MyFont(1, 30f));
        lbRegistroAlimento.setForeground(new java.awt.Color(255, 255, 255));
        lbRegistroAlimento.setText("Registro Alimentos");
        PRegAlimentosProfesional.add(lbRegistroAlimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 510, 40));

        lbTipo.setFont(cf.MyFont(0, 20f));
        lbTipo.setForeground(new java.awt.Color(255, 255, 255));
        lbTipo.setText("Tipo");
        PRegAlimentosProfesional.add(lbTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        rBtnOtros.setBackground(new java.awt.Color(0, 0, 0));
        rbtnGrupRegAlimentos.add(rBtnOtros);
        rBtnOtros.setFont(cf.MyFont(0, 16f));
        rBtnOtros.setForeground(new java.awt.Color(255, 255, 255));
        rBtnOtros.setText("Otros");
        PRegAlimentosProfesional.add(rBtnOtros, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, -1, -1));

        rBtnFruta.setBackground(new java.awt.Color(0, 0, 0));
        rbtnGrupRegAlimentos.add(rBtnFruta);
        rBtnFruta.setFont(cf.MyFont(0, 16f));
        rBtnFruta.setForeground(new java.awt.Color(255, 255, 255));
        rBtnFruta.setText("Fruta");
        PRegAlimentosProfesional.add(rBtnFruta, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, -1));

        rBtnCereal.setBackground(new java.awt.Color(0, 0, 0));
        rbtnGrupRegAlimentos.add(rBtnCereal);
        rBtnCereal.setFont(cf.MyFont(0, 16f));
        rBtnCereal.setForeground(new java.awt.Color(255, 255, 255));
        rBtnCereal.setText("Cereal");
        PRegAlimentosProfesional.add(rBtnCereal, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, -1, -1));

        rBtnLegumbre.setBackground(new java.awt.Color(0, 0, 0));
        rbtnGrupRegAlimentos.add(rBtnLegumbre);
        rBtnLegumbre.setFont(cf.MyFont(0, 16f));
        rBtnLegumbre.setForeground(new java.awt.Color(255, 255, 255));
        rBtnLegumbre.setText("Legumbre");
        PRegAlimentosProfesional.add(rBtnLegumbre, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, -1, -1));

        lbNombreAlimentos1.setFont(cf.MyFont(0, 20f));
        lbNombreAlimentos1.setForeground(new java.awt.Color(255, 255, 255));
        lbNombreAlimentos1.setText("Nombre");
        PRegAlimentosProfesional.add(lbNombreAlimentos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, -1, -1));

        jButton3.setBackground(new java.awt.Color(153, 153, 153));
        jButton3.setFont(cf.MyFont(0, 20f));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Guardar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        PRegAlimentosProfesional.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 420, 120, 30));

        lbCheckNombreErrorAlimentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PRegAlimentosProfesional.add(lbCheckNombreErrorAlimentos, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 100, -1, 30));

        lbCheckNombreBienAlimentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PRegAlimentosProfesional.add(lbCheckNombreBienAlimentos, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 100, -1, 30));

        lbTipoError.setForeground(new java.awt.Color(255, 0, 0));
        lbTipoError.setText("<html>*Debe indicar el <br> tipo del alimento</html>");
        PRegAlimentosProfesional.add(lbTipoError, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, -1, -1));

        lbAvatarAlimento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/avatarFruta.png"))); // NOI18N
        lbAvatarAlimento.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lbAvatarAlimento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbAvatarAlimentoMouseClicked(evt);
            }
        });
        PRegAlimentosProfesional.add(lbAvatarAlimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, 140));

        checkBoxNutProteinas.setBackground(new java.awt.Color(0, 0, 0));
        checkBoxNutProteinas.setFont(cf.MyFont(0, 16f));
        checkBoxNutProteinas.setForeground(new java.awt.Color(255, 255, 255));
        checkBoxNutProteinas.setText("Proteinas");
        checkBoxNutProteinas.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                checkBoxNutProteinasStateChanged(evt);
            }
        });
        PRegAlimentosProfesional.add(checkBoxNutProteinas, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 240, -1, -1));

        checkBoxNutCarbohidratos.setBackground(new java.awt.Color(0, 0, 0));
        checkBoxNutCarbohidratos.setFont(cf.MyFont(0, 16f));
        checkBoxNutCarbohidratos.setForeground(new java.awt.Color(255, 255, 255));
        checkBoxNutCarbohidratos.setText("Carbohidratos");
        checkBoxNutCarbohidratos.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                checkBoxNutCarbohidratosStateChanged(evt);
            }
        });
        PRegAlimentosProfesional.add(checkBoxNutCarbohidratos, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 270, -1, -1));

        checkBoxNutVitaminas.setBackground(new java.awt.Color(0, 0, 0));
        checkBoxNutVitaminas.setFont(cf.MyFont(0, 16f));
        checkBoxNutVitaminas.setForeground(new java.awt.Color(255, 255, 255));
        checkBoxNutVitaminas.setText("Vitaminas");
        checkBoxNutVitaminas.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                checkBoxNutVitaminasStateChanged(evt);
            }
        });
        PRegAlimentosProfesional.add(checkBoxNutVitaminas, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, -1, -1));

        checkBoxNutMinerales.setBackground(new java.awt.Color(0, 0, 0));
        checkBoxNutMinerales.setFont(cf.MyFont(0, 16f));
        checkBoxNutMinerales.setForeground(new java.awt.Color(255, 255, 255));
        checkBoxNutMinerales.setText("Minerales");
        checkBoxNutMinerales.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                checkBoxNutMineralesStateChanged(evt);
            }
        });
        PRegAlimentosProfesional.add(checkBoxNutMinerales, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 330, -1, -1));

        lbNombreAlimentos2.setFont(cf.MyFont(0, 20f));
        lbNombreAlimentos2.setForeground(new java.awt.Color(255, 255, 255));
        lbNombreAlimentos2.setText("Nutrientes Principales");
        PRegAlimentosProfesional.add(lbNombreAlimentos2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, -1, -1));

        lbNombreAlimentos3.setFont(cf.MyFont(0, 20f));
        lbNombreAlimentos3.setForeground(new java.awt.Color(255, 255, 255));
        lbNombreAlimentos3.setText("Proporción %");
        PRegAlimentosProfesional.add(lbNombreAlimentos3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 200, -1, -1));

        txtNombreAlimentos.setFont(cf.MyFont(0, 20f));
        txtNombreAlimentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreAlimentosActionPerformed(evt);
            }
        });
        txtNombreAlimentos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreAlimentosKeyTyped(evt);
            }
        });
        PRegAlimentosProfesional.add(txtNombreAlimentos, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, 240, 32));

        txtProporcionProteinas.setFont(cf.MyFont(0, 20f));
        txtProporcionProteinas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProporcionProteinasActionPerformed(evt);
            }
        });
        txtProporcionProteinas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProporcionProteinasKeyTyped(evt);
            }
        });
        PRegAlimentosProfesional.add(txtProporcionProteinas, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 230, 110, 32));

        txtProporcionCarbohidratos.setFont(cf.MyFont(0, 20f));
        txtProporcionCarbohidratos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProporcionCarbohidratosActionPerformed(evt);
            }
        });
        txtProporcionCarbohidratos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProporcionCarbohidratosKeyTyped(evt);
            }
        });
        PRegAlimentosProfesional.add(txtProporcionCarbohidratos, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 260, 110, 32));

        txtProporcionVitaminas.setFont(cf.MyFont(0, 20f));
        txtProporcionVitaminas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProporcionVitaminasActionPerformed(evt);
            }
        });
        txtProporcionVitaminas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProporcionVitaminasKeyTyped(evt);
            }
        });
        PRegAlimentosProfesional.add(txtProporcionVitaminas, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 290, 110, 32));

        txtProporcionMinerales.setFont(cf.MyFont(0, 20f));
        txtProporcionMinerales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProporcionMineralesActionPerformed(evt);
            }
        });
        txtProporcionMinerales.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProporcionMineralesKeyTyped(evt);
            }
        });
        PRegAlimentosProfesional.add(txtProporcionMinerales, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 320, 110, 32));

        checkBoxNutAntioxidante.setBackground(new java.awt.Color(0, 0, 0));
        checkBoxNutAntioxidante.setFont(cf.MyFont(0, 16f));
        checkBoxNutAntioxidante.setForeground(new java.awt.Color(255, 255, 255));
        checkBoxNutAntioxidante.setText("Antioxidantes");
        checkBoxNutAntioxidante.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                checkBoxNutAntioxidanteStateChanged(evt);
            }
        });
        PRegAlimentosProfesional.add(checkBoxNutAntioxidante, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 360, -1, -1));

        txtProporcionAntioxidante.setFont(cf.MyFont(0, 20f));
        txtProporcionAntioxidante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProporcionAntioxidanteActionPerformed(evt);
            }
        });
        txtProporcionAntioxidante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProporcionAntioxidanteKeyTyped(evt);
            }
        });
        PRegAlimentosProfesional.add(txtProporcionAntioxidante, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 350, 110, 32));

        lbNombreAlimentos4.setFont(cf.MyFont(0, 20f));
        lbNombreAlimentos4.setForeground(new java.awt.Color(255, 255, 255));
        lbNombreAlimentos4.setText("Nutriente");
        PRegAlimentosProfesional.add(lbNombreAlimentos4, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 200, -1, -1));

        lbCheckNutProteinasError.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PRegAlimentosProfesional.add(lbCheckNutProteinasError, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 230, -1, 30));

        lbCheckNutCarbohidratosError.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PRegAlimentosProfesional.add(lbCheckNutCarbohidratosError, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 260, -1, 30));

        lbCheckNutMineralesError.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PRegAlimentosProfesional.add(lbCheckNutMineralesError, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 320, -1, 30));

        lbCheckNutVitaminasError.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PRegAlimentosProfesional.add(lbCheckNutVitaminasError, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 290, -1, 30));

        lbCheckNutAntioxidanteError.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PRegAlimentosProfesional.add(lbCheckNutAntioxidanteError, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 350, -1, 30));

        lbCheckNutAntioxidanteBien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PRegAlimentosProfesional.add(lbCheckNutAntioxidanteBien, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 350, -1, 30));

        lbCheckNutProteinasBien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PRegAlimentosProfesional.add(lbCheckNutProteinasBien, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 230, -1, 30));

        lbCheckNutCarbohidratosBien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PRegAlimentosProfesional.add(lbCheckNutCarbohidratosBien, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 260, -1, 30));

        lbCheckNutVitaminasBien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PRegAlimentosProfesional.add(lbCheckNutVitaminasBien, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 290, -1, 30));

        lbCheckNutMineralesBien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PRegAlimentosProfesional.add(lbCheckNutMineralesBien, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 320, -1, 30));

        panelContenido.add(PRegAlimentosProfesional, "panelAlimento");

        PAlimentosIngeridosUsuario.setBackground(new java.awt.Color(0, 0, 0));
        PAlimentosIngeridosUsuario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tablaAlimentosIngeridosUsuario.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tablaAlimentosIngeridosUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tablaAlimentosIngeridosUsuario);

        PAlimentosIngeridosUsuario.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 520, 140));

        dcAlimentoConsumidoUsuario.setFont(cf.MyFont(0, 20f));
        dcAlimentoConsumidoUsuario.setMaxSelectableDate( new Date());
        dcAlimentoConsumidoUsuario.setMinSelectableDate(new java.util.Date(-1735676116000L));
        PAlimentosIngeridosUsuario.add(dcAlimentoConsumidoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 110, 240, 30));

        btnBorrarAlimentoUsuario.setBackground(new java.awt.Color(153, 153, 153));
        btnBorrarAlimentoUsuario.setFont(cf.MyFont(0, 20f));
        btnBorrarAlimentoUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnBorrarAlimentoUsuario.setText("Eliminar");
        btnBorrarAlimentoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarAlimentoUsuarioActionPerformed(evt);
            }
        });
        PAlimentosIngeridosUsuario.add(btnBorrarAlimentoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 420, 120, 30));

        lbAlimentosIngeridos.setFont(cf.MyFont(1, 30f));
        lbAlimentosIngeridos.setForeground(new java.awt.Color(255, 255, 255));
        lbAlimentosIngeridos.setText("Alimentos Ingeridos");
        PAlimentosIngeridosUsuario.add(lbAlimentosIngeridos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 510, 40));

        lbFechaAlimentoIngerido.setFont(cf.MyFont(0, 20f));
        lbFechaAlimentoIngerido.setForeground(new java.awt.Color(255, 255, 255));
        lbFechaAlimentoIngerido.setText("Alimentos");
        PAlimentosIngeridosUsuario.add(lbFechaAlimentoIngerido, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, -1));

        lbNombreAlimentoIngerido1.setFont(cf.MyFont(0, 20f));
        lbNombreAlimentoIngerido1.setForeground(new java.awt.Color(255, 255, 255));
        lbNombreAlimentoIngerido1.setText("Nombre");
        PAlimentosIngeridosUsuario.add(lbNombreAlimentoIngerido1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        lbFechaAlimentoIngerido1.setFont(cf.MyFont(0, 20f));
        lbFechaAlimentoIngerido1.setForeground(new java.awt.Color(255, 255, 255));
        lbFechaAlimentoIngerido1.setText("Fecha");
        PAlimentosIngeridosUsuario.add(lbFechaAlimentoIngerido1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, -1, -1));

        boxAlimentoConsumidoUsuario.setFont(cf.MyFont(0, 20f));
        PAlimentosIngeridosUsuario.add(boxAlimentoConsumidoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 230, 30));

        btnAgregarAlimentoUsuario.setBackground(new java.awt.Color(153, 153, 153));
        btnAgregarAlimentoUsuario.setFont(cf.MyFont(0, 20f));
        btnAgregarAlimentoUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarAlimentoUsuario.setText("Agregar");
        btnAgregarAlimentoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarAlimentoUsuarioActionPerformed(evt);
            }
        });
        PAlimentosIngeridosUsuario.add(btnAgregarAlimentoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 170, 120, 30));

        lbCheckBoxFechaAlimentoConsumidoErrorUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PAlimentosIngeridosUsuario.add(lbCheckBoxFechaAlimentoConsumidoErrorUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 110, -1, 30));

        lbCheckBoxNombreAlimentoConsumidoErrorUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PAlimentosIngeridosUsuario.add(lbCheckBoxNombreAlimentoConsumidoErrorUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, -1, 30));

        lbCheckBoxNombreAlimentoConsumidoBienUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PAlimentosIngeridosUsuario.add(lbCheckBoxNombreAlimentoConsumidoBienUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, -1, 30));

        lbCheckBoxFechaAlimentoConsumidoBienUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PAlimentosIngeridosUsuario.add(lbCheckBoxFechaAlimentoConsumidoBienUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 110, -1, 30));

        panelContenido.add(PAlimentosIngeridosUsuario, "panelAlimentosIngeridos");

        PSolicitarPlanAlimentacionUsuario.setBackground(new java.awt.Color(0, 0, 0));
        PSolicitarPlanAlimentacionUsuario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbPlanAlimentacionUsuario.setFont(cf.MyFont(1, 30f));
        lbPlanAlimentacionUsuario.setForeground(new java.awt.Color(255, 255, 255));
        lbPlanAlimentacionUsuario.setText("Plan de Alimentación");
        PSolicitarPlanAlimentacionUsuario.add(lbPlanAlimentacionUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 510, 40));

        boxNombreProfesionalSolicitud.setFont(cf.MyFont(0, 20f));
        PSolicitarPlanAlimentacionUsuario.add(boxNombreProfesionalSolicitud, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 230, 30));

        lbNombre4.setFont(cf.MyFont(0, 20f));
        lbNombre4.setForeground(new java.awt.Color(255, 255, 255));
        lbNombre4.setText("Detalles");
        PSolicitarPlanAlimentacionUsuario.add(lbNombre4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, -1));

        txtPesoSolicitud.setFont(cf.MyFont(0, 20f));
        txtPesoSolicitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesoSolicitudActionPerformed(evt);
            }
        });
        txtPesoSolicitud.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPesoSolicitudKeyTyped(evt);
            }
        });
        PSolicitarPlanAlimentacionUsuario.add(txtPesoSolicitud, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 100, 32));

        txtAlturaSolicitud.setFont(cf.MyFont(0, 20f));
        txtAlturaSolicitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlturaSolicitudActionPerformed(evt);
            }
        });
        txtAlturaSolicitud.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAlturaSolicitudKeyTyped(evt);
            }
        });
        PSolicitarPlanAlimentacionUsuario.add(txtAlturaSolicitud, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 210, 100, 32));

        lbNombre5.setFont(cf.MyFont(0, 20f));
        lbNombre5.setForeground(new java.awt.Color(255, 255, 255));
        lbNombre5.setText("Profesional");
        PSolicitarPlanAlimentacionUsuario.add(lbNombre5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        lbNombre7.setFont(cf.MyFont(0, 20f));
        lbNombre7.setForeground(new java.awt.Color(255, 255, 255));
        lbNombre7.setText("<html>Horas actividad <br> física</htmal>");
        PSolicitarPlanAlimentacionUsuario.add(lbNombre7, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 160, -1, -1));

        txtHorasDeActividadSolicitud.setFont(cf.MyFont(0, 20f));
        txtHorasDeActividadSolicitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHorasDeActividadSolicitudActionPerformed(evt);
            }
        });
        txtHorasDeActividadSolicitud.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHorasDeActividadSolicitudKeyTyped(evt);
            }
        });
        PSolicitarPlanAlimentacionUsuario.add(txtHorasDeActividadSolicitud, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 210, 100, 32));

        lbNombre8.setFont(cf.MyFont(0, 20f));
        lbNombre8.setForeground(new java.awt.Color(255, 255, 255));
        lbNombre8.setText("Peso");
        PSolicitarPlanAlimentacionUsuario.add(lbNombre8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        txtDetallesSolicitud.setColumns(20);
        txtDetallesSolicitud.setFont(cf.MyFont(0, 16f));
        txtDetallesSolicitud.setRows(5);
        jScrollPane6.setViewportView(txtDetallesSolicitud);

        PSolicitarPlanAlimentacionUsuario.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 410, 110));

        lbNombre9.setFont(cf.MyFont(0, 20f));
        lbNombre9.setForeground(new java.awt.Color(255, 255, 255));
        lbNombre9.setText("Altura");
        PSolicitarPlanAlimentacionUsuario.add(lbNombre9, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, -1, -1));

        btnSolicitarPlan.setBackground(new java.awt.Color(153, 153, 153));
        btnSolicitarPlan.setFont(cf.MyFont(0, 20f));
        btnSolicitarPlan.setForeground(new java.awt.Color(255, 255, 255));
        btnSolicitarPlan.setText("Enviar");
        btnSolicitarPlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSolicitarPlanActionPerformed(evt);
            }
        });
        PSolicitarPlanAlimentacionUsuario.add(btnSolicitarPlan, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 480, 120, 30));

        lbCheckBoxProfesionalErrorSolicitud.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PSolicitarPlanAlimentacionUsuario.add(lbCheckBoxProfesionalErrorSolicitud, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, -1, 30));

        lbCheckBoxPesoErrorSolicitud.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PSolicitarPlanAlimentacionUsuario.add(lbCheckBoxPesoErrorSolicitud, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, 20, 30));

        lbCheckBoxAlturaErrorSolicitud.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PSolicitarPlanAlimentacionUsuario.add(lbCheckBoxAlturaErrorSolicitud, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 210, -1, 30));

        lbCheckBoxHorasDeActividadErrorSolicitud.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PSolicitarPlanAlimentacionUsuario.add(lbCheckBoxHorasDeActividadErrorSolicitud, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 210, -1, 30));

        lbCkeckBoxProfesionalBienSolicitud.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PSolicitarPlanAlimentacionUsuario.add(lbCkeckBoxProfesionalBienSolicitud, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, -1, 30));

        lbCkeckBoxPesoBienSolicitud.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PSolicitarPlanAlimentacionUsuario.add(lbCkeckBoxPesoBienSolicitud, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, -1, 30));

        lbCkeckBoxAlturaBienSolicitud.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PSolicitarPlanAlimentacionUsuario.add(lbCkeckBoxAlturaBienSolicitud, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 210, -1, 30));

        lbCkeckBoxHorasDeActividadBienSolicitud.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PSolicitarPlanAlimentacionUsuario.add(lbCkeckBoxHorasDeActividadBienSolicitud, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 210, -1, 30));

        lbDetallesPlanAlimentacionError.setForeground(new java.awt.Color(255, 0, 0));
        lbDetallesPlanAlimentacionError.setText("<html>*Debe completar el <br> campo detalles</html>");
        PSolicitarPlanAlimentacionUsuario.add(lbDetallesPlanAlimentacionError, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 340, -1, 40));

        lbHorasActFisicaPlanAlimentacionError.setForeground(new java.awt.Color(255, 0, 0));
        lbHorasActFisicaPlanAlimentacionError.setText("<html>*Las horas de actividad física <br> deben estar entre 0 y 24</html>");
        PSolicitarPlanAlimentacionUsuario.add(lbHorasActFisicaPlanAlimentacionError, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 250, -1, 40));

        lbPesoPlanAlimentacionError.setForeground(new java.awt.Color(255, 0, 0));
        lbPesoPlanAlimentacionError.setText("<html>*El peso debe estar <br> entre 0 y 500 kg</html>");
        PSolicitarPlanAlimentacionUsuario.add(lbPesoPlanAlimentacionError, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, -1, 40));

        lbAlturaPlanAlimentacionError.setForeground(new java.awt.Color(255, 0, 0));
        lbAlturaPlanAlimentacionError.setText("<html>*La altura debe estar <br> entre 0 y 250 cm kg</html>");
        PSolicitarPlanAlimentacionUsuario.add(lbAlturaPlanAlimentacionError, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 250, -1, 40));

        panelContenido.add(PSolicitarPlanAlimentacionUsuario, "panelPlanAlimentacion");

        PPlanAlimentacionProfesional.setBackground(new java.awt.Color(0, 0, 0));
        PPlanAlimentacionProfesional.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbPlanAlimentacionUsuario1.setFont(cf.MyFont(1, 30f));
        lbPlanAlimentacionUsuario1.setForeground(new java.awt.Color(255, 255, 255));
        lbPlanAlimentacionUsuario1.setText("Plan de Alimentación");
        PPlanAlimentacionProfesional.add(lbPlanAlimentacionUsuario1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 510, 40));

        tablaIdearPlanDePlanesProfesional.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Desayuno", null, null, null, null, null, null, null},
                {"Almuerzo", null, null, null, null, null, null, null},
                {"Merienda", null, null, null, null, null, null, null},
                {"Cena", null, null, null, null, null, null, null}
            },
            new String [] {
                "", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sabado", "Domingo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaIdearPlanDePlanesProfesional.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(tablaIdearPlanDePlanesProfesional);

        PPlanAlimentacionProfesional.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 700, 100));

        lbNombre10.setFont(cf.MyFont(0, 18f));
        lbNombre10.setForeground(new java.awt.Color(255, 255, 255));
        lbNombre10.setText("Nombre");
        PPlanAlimentacionProfesional.add(lbNombre10, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 420, -1, -1));

        btnEnviarPlan.setBackground(new java.awt.Color(153, 153, 153));
        btnEnviarPlan.setFont(cf.MyFont(0, 20f));
        btnEnviarPlan.setForeground(new java.awt.Color(255, 255, 255));
        btnEnviarPlan.setText("Enviar");
        btnEnviarPlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarPlanActionPerformed(evt);
            }
        });
        PPlanAlimentacionProfesional.add(btnEnviarPlan, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 480, 120, 30));

        panelAmpliarDatosPlanAlimentacion.setLayout(new java.awt.CardLayout());

        PTablaPlanesAlimentacionSolicitados.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tablaSolicitudDePlanesProfesional.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaSolicitudDePlanesProfesional.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaSolicitudDePlanesProfesionalMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tablaSolicitudDePlanesProfesional);

        PTablaPlanesAlimentacionSolicitados.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 180));

        panelAmpliarDatosPlanAlimentacion.add(PTablaPlanesAlimentacionSolicitados, "panelTablaPlanesAlimentacionSolicitados");

        PAmpliarDatosUsuarioPlanesProfesional.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Detalles");
        PAmpliarDatosUsuarioPlanesProfesional.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, -1, -1));

        jLabel3.setText("Nombre");
        PAmpliarDatosUsuarioPlanesProfesional.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

        jLabel4.setText("Descripción");
        PAmpliarDatosUsuarioPlanesProfesional.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jLabel6.setText("Preferencias");
        PAmpliarDatosUsuarioPlanesProfesional.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, -1, -1));

        btnEnviarPlan2.setText("Amlpiar");
        PAmpliarDatosUsuarioPlanesProfesional.add(btnEnviarPlan2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 230, 80, -1));

        btnVolverATabla.setText("Volver");
        btnVolverATabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverATablaActionPerformed(evt);
            }
        });
        PAmpliarDatosUsuarioPlanesProfesional.add(btnVolverATabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 150, 80, -1));

        txtAmpilarInfoDescripcionPlanAlimProf.setEditable(false);
        txtAmpilarInfoDescripcionPlanAlimProf.setColumns(20);
        txtAmpilarInfoDescripcionPlanAlimProf.setRows(5);
        jScrollPane18.setViewportView(txtAmpilarInfoDescripcionPlanAlimProf);

        PAmpliarDatosUsuarioPlanesProfesional.add(jScrollPane18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 190, 80));

        txtAmpilarInfoPreferenciasPlanAlimProf.setEditable(false);
        txtAmpilarInfoPreferenciasPlanAlimProf.setColumns(20);
        txtAmpilarInfoPreferenciasPlanAlimProf.setRows(5);
        jScrollPane19.setViewportView(txtAmpilarInfoPreferenciasPlanAlimProf);

        PAmpliarDatosUsuarioPlanesProfesional.add(jScrollPane19, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, 90, 130));

        txtAmpilarInfoRestriccionesPlanAlimProf.setEditable(false);
        txtAmpilarInfoRestriccionesPlanAlimProf.setColumns(20);
        txtAmpilarInfoRestriccionesPlanAlimProf.setRows(5);
        jScrollPane20.setViewportView(txtAmpilarInfoRestriccionesPlanAlimProf);

        PAmpliarDatosUsuarioPlanesProfesional.add(jScrollPane20, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, 90, 130));

        txtAmpilarInfoNombrePlanAlimProf.setEditable(false);
        txtAmpilarInfoNombrePlanAlimProf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAmpilarInfoNombrePlanAlimProfActionPerformed(evt);
            }
        });
        PAmpliarDatosUsuarioPlanesProfesional.add(txtAmpilarInfoNombrePlanAlimProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 190, -1));

        txtAmpilarInfoDetallesPlanAlimProf.setEditable(false);
        txtAmpilarInfoDetallesPlanAlimProf.setColumns(20);
        txtAmpilarInfoDetallesPlanAlimProf.setRows(5);
        jScrollPane21.setViewportView(txtAmpilarInfoDetallesPlanAlimProf);

        PAmpliarDatosUsuarioPlanesProfesional.add(jScrollPane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 30, 180, 100));

        jLabel7.setText("<html>Alimentos <br> Ingeridos </html>");
        PAmpliarDatosUsuarioPlanesProfesional.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, -1, -1));

        txtAmpilarInfoAliIngPlanAlimProf.setEditable(false);
        txtAmpilarInfoAliIngPlanAlimProf.setColumns(20);
        txtAmpilarInfoAliIngPlanAlimProf.setRows(5);
        jScrollPane22.setViewportView(txtAmpilarInfoAliIngPlanAlimProf);

        PAmpliarDatosUsuarioPlanesProfesional.add(jScrollPane22, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 30, 90, 130));

        jLabel9.setText("Restricciones");
        PAmpliarDatosUsuarioPlanesProfesional.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, -1, -1));

        panelAmpliarDatosPlanAlimentacion.add(PAmpliarDatosUsuarioPlanesProfesional, "panelAmpliarDatosUsuario");

        PPlanAlimentacionProfesional.add(panelAmpliarDatosPlanAlimentacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 700, 180));
        panelAmpliarDatosPlanAlimentacion.getAccessibleContext().setAccessibleName("");

        btnAmpliarInformacion.setBackground(new java.awt.Color(153, 153, 153));
        btnAmpliarInformacion.setFont(cf.MyFont(0, 20f));
        btnAmpliarInformacion.setForeground(new java.awt.Color(255, 255, 255));
        btnAmpliarInformacion.setText("Amlpiar");
        btnAmpliarInformacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmpliarInformacionActionPerformed(evt);
            }
        });
        PPlanAlimentacionProfesional.add(btnAmpliarInformacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 260, 110, 30));

        boxComidaPlanAliProf.setFont(cf.MyFont(0, 20f));
        boxComidaPlanAliProf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxComidaPlanAliProfActionPerformed(evt);
            }
        });
        PPlanAlimentacionProfesional.add(boxComidaPlanAliProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 440, 180, 30));

        boxDiasSemanaPlanAliProf.setFont(cf.MyFont(0, 20f));
        boxDiasSemanaPlanAliProf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxDiasSemanaPlanAliProfActionPerformed(evt);
            }
        });
        PPlanAlimentacionProfesional.add(boxDiasSemanaPlanAliProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, 170, 30));

        lbNombre14.setFont(cf.MyFont(0, 20f));
        lbNombre14.setForeground(new java.awt.Color(255, 255, 255));
        lbNombre14.setText("Idear plan");
        PPlanAlimentacionProfesional.add(lbNombre14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, -1));

        lbNombre15.setFont(cf.MyFont(0, 18f));
        lbNombre15.setForeground(new java.awt.Color(255, 255, 255));
        lbNombre15.setText("Dia");
        PPlanAlimentacionProfesional.add(lbNombre15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, -1, -1));

        txtNombreAlimentoPlanAliProf.setFont(cf.MyFont(0, 20f));
        txtNombreAlimentoPlanAliProf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreAlimentoPlanAliProfKeyTyped(evt);
            }
        });
        PPlanAlimentacionProfesional.add(txtNombreAlimentoPlanAliProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 440, 170, 30));

        btnAniadir.setBackground(new java.awt.Color(153, 153, 153));
        btnAniadir.setFont(cf.MyFont(0, 20f));
        btnAniadir.setForeground(new java.awt.Color(255, 255, 255));
        btnAniadir.setText("+");
        btnAniadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAniadirActionPerformed(evt);
            }
        });
        PPlanAlimentacionProfesional.add(btnAniadir, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 440, 50, 30));

        lbNombre16.setFont(cf.MyFont(0, 18f));
        lbNombre16.setForeground(new java.awt.Color(255, 255, 255));
        lbNombre16.setText("Comida");
        PPlanAlimentacionProfesional.add(lbNombre16, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 420, -1, -1));

        lbCheckProfErrorAgrDiaPlanAli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PPlanAlimentacionProfesional.add(lbCheckProfErrorAgrDiaPlanAli, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 440, 20, 30));

        lbCheckProfErrorAgrComidaPlanAli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PPlanAlimentacionProfesional.add(lbCheckProfErrorAgrComidaPlanAli, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 440, 20, 30));

        lbCheckProfErrorNombrePlanAli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/error.png"))); // NOI18N
        PPlanAlimentacionProfesional.add(lbCheckProfErrorNombrePlanAli, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 440, 20, 30));

        lbCheckProfBienAgrDiaPlanAli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PPlanAlimentacionProfesional.add(lbCheckProfBienAgrDiaPlanAli, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 440, 20, 30));

        lbCheckProfBienAgrComidaPlanAli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PPlanAlimentacionProfesional.add(lbCheckProfBienAgrComidaPlanAli, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 440, 20, 30));

        lbCheckProfBienNombrePlanAli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        PPlanAlimentacionProfesional.add(lbCheckProfBienNombrePlanAli, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 440, 20, 30));

        lbIdearPlanErrorUsuNoSelec.setForeground(new java.awt.Color(255, 0, 0));
        lbIdearPlanErrorUsuNoSelec.setText("<html>*Debe seleccionar un usuario <br> de la tabla para idear el plan</html>");
        PPlanAlimentacionProfesional.add(lbIdearPlanErrorUsuNoSelec, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 480, 170, 40));

        lbIdearPlanErrorUsuNoYaEnv.setForeground(new java.awt.Color(255, 0, 0));
        lbIdearPlanErrorUsuNoYaEnv.setText("<html>*No puede editar un plan <br> de alimentos que ya fue enviado</html>");
        PPlanAlimentacionProfesional.add(lbIdearPlanErrorUsuNoYaEnv, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 480, 170, 40));

        panelContenido.add(PPlanAlimentacionProfesional, "panelPlanAlimentacionProf");

        PConsultasRecibidasProfesional.setBackground(new java.awt.Color(0, 0, 0));
        PConsultasRecibidasProfesional.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbPlanAlimentacionUsuario2.setFont(cf.MyFont(1, 30f));
        lbPlanAlimentacionUsuario2.setForeground(new java.awt.Color(255, 255, 255));
        lbPlanAlimentacionUsuario2.setText("Consultas Recibidas");
        PConsultasRecibidasProfesional.add(lbPlanAlimentacionUsuario2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 510, 40));

        tablaConsultasRecibidas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Usuario", "Fecha", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaConsultasRecibidas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaConsultasRecibidasMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablaConsultasRecibidas);

        PConsultasRecibidasProfesional.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 700, 190));

        txtDescripcionUsuarioConsultaRecibidaProf.setEditable(false);
        txtDescripcionUsuarioConsultaRecibidaProf.setColumns(20);
        txtDescripcionUsuarioConsultaRecibidaProf.setFont(cf.MyFont(0, 16f));
        txtDescripcionUsuarioConsultaRecibidaProf.setRows(5);
        jScrollPane5.setViewportView(txtDescripcionUsuarioConsultaRecibidaProf);

        PConsultasRecibidasProfesional.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 310, 100));

        lbResponder.setFont(cf.MyFont(0, 20f));
        lbResponder.setForeground(new java.awt.Color(255, 255, 255));
        lbResponder.setText("Responder");
        PConsultasRecibidasProfesional.add(lbResponder, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 290, -1, -1));

        lbConsultas1.setFont(cf.MyFont(0, 20f));
        lbConsultas1.setForeground(new java.awt.Color(255, 255, 255));
        lbConsultas1.setText("Consultas");
        PConsultasRecibidasProfesional.add(lbConsultas1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        btnResponderConsulta.setBackground(new java.awt.Color(153, 153, 153));
        btnResponderConsulta.setFont(cf.MyFont(0, 20f));
        btnResponderConsulta.setForeground(new java.awt.Color(255, 255, 255));
        btnResponderConsulta.setText("Enviar");
        btnResponderConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResponderConsultaActionPerformed(evt);
            }
        });
        PConsultasRecibidasProfesional.add(btnResponderConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 450, 120, 30));

        txtResponderUsuarioConsultaRecibidaProf.setColumns(20);
        txtResponderUsuarioConsultaRecibidaProf.setFont(cf.MyFont(0, 16f));
        txtResponderUsuarioConsultaRecibidaProf.setRows(5);
        jScrollPane7.setViewportView(txtResponderUsuarioConsultaRecibidaProf);

        PConsultasRecibidasProfesional.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 320, 350, 100));

        lbResponder1.setFont(cf.MyFont(0, 20f));
        lbResponder1.setForeground(new java.awt.Color(255, 255, 255));
        lbResponder1.setText("Detalle");
        PConsultasRecibidasProfesional.add(lbResponder1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, -1, -1));

        lbResponderConsultasRecibidasError.setForeground(new java.awt.Color(255, 0, 0));
        lbResponderConsultasRecibidasError.setText("<html>*Escriba en este <br> campo su respuesta</html>");
        PConsultasRecibidasProfesional.add(lbResponderConsultasRecibidasError, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 430, -1, -1));

        panelContenido.add(PConsultasRecibidasProfesional, "panelConsultasRecibidasProf");

        PInicioUsuario.setBackground(new java.awt.Color(0, 0, 0));
        PInicioUsuario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbPlanAlimentacionUsuario3.setFont(cf.MyFont(1, 30f));
        lbPlanAlimentacionUsuario3.setForeground(new java.awt.Color(255, 255, 255));
        lbPlanAlimentacionUsuario3.setText("Sus alimentos ingeridos");
        PInicioUsuario.add(lbPlanAlimentacionUsuario3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 510, 40));

        tablaPlanAlimentacionUsuario.setFont(cf.MyFont(0, 14f));
        tablaPlanAlimentacionUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaPlanAlimentacionUsuario.setGridColor(new java.awt.Color(0, 0, 0));
        jScrollPane12.setViewportView(tablaPlanAlimentacionUsuario);

        PInicioUsuario.add(jScrollPane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 700, 120));

        lbPlanAlimentacionUsuario4.setFont(cf.MyFont(1, 30f));
        lbPlanAlimentacionUsuario4.setForeground(new java.awt.Color(255, 255, 255));
        lbPlanAlimentacionUsuario4.setText("Su plan de alimentación");
        PInicioUsuario.add(lbPlanAlimentacionUsuario4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 510, 40));

        tablaAlimentosIngeridosUsuarioInicio.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tablaAlimentosIngeridosUsuarioInicio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaAlimentosIngeridosUsuarioInicio.setGridColor(new java.awt.Color(0, 0, 0));
        tablaAlimentosIngeridosUsuarioInicio.getTableHeader().setReorderingAllowed(false);
        jScrollPane13.setViewportView(tablaAlimentosIngeridosUsuarioInicio);

        PInicioUsuario.add(jScrollPane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 700, 100));

        tablaConsultasAProfesionalesUsuarioInicio.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tablaConsultasAProfesionalesUsuarioInicio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaConsultasAProfesionalesUsuarioInicio.setGridColor(new java.awt.Color(0, 0, 0));
        jScrollPane23.setViewportView(tablaConsultasAProfesionalesUsuarioInicio);

        PInicioUsuario.add(jScrollPane23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 700, 100));

        lbPlanAlimentacionUsuario5.setFont(cf.MyFont(1, 30f));
        lbPlanAlimentacionUsuario5.setForeground(new java.awt.Color(255, 255, 255));
        lbPlanAlimentacionUsuario5.setText("Sus consultas a profesionales");
        PInicioUsuario.add(lbPlanAlimentacionUsuario5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 510, 40));

        panelContenido.add(PInicioUsuario, "panelInicioUsuario");

        PInicioProfesional.setBackground(new java.awt.Color(0, 0, 0));
        PInicioProfesional.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbRegistroUsuario3.setFont(cf.MyFont(1, 30f));
        lbRegistroUsuario3.setForeground(new java.awt.Color(255, 255, 255));
        lbRegistroUsuario3.setText("Sus consultas");
        PInicioProfesional.add(lbRegistroUsuario3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 570, 40));

        lbRegistroUsuario4.setFont(cf.MyFont(1, 30f));
        lbRegistroUsuario4.setForeground(new java.awt.Color(255, 255, 255));
        lbRegistroUsuario4.setText("Sus solicitudes de planes");
        PInicioProfesional.add(lbRegistroUsuario4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 510, 40));

        tablaInicioUltimosPlanesProf.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Usuario", "Fecha", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane10.setViewportView(tablaInicioUltimosPlanesProf);

        PInicioProfesional.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 700, 170));

        tablaInicioUltimasConsutasProf.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Usuario", "Edad", "Peso (kg)", "Altura (cm)", "Actividad física (h)", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane11.setViewportView(tablaInicioUltimasConsutasProf);

        PInicioProfesional.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 700, 150));

        panelContenido.add(PInicioProfesional, "panelInicioProfesional");

        PInicSec.setBackground(new java.awt.Color(0, 0, 0));
        PInicSec.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        boxRolInicSec.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        boxRolInicSec.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                boxRolInicSecItemStateChanged(evt);
            }
        });
        boxRolInicSec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boxRolInicSecMouseClicked(evt);
            }
        });
        boxRolInicSec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxRolInicSecActionPerformed(evt);
            }
        });
        PInicSec.add(boxRolInicSec, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 160, 260, 30));

        lbRol.setFont(cf.MyFont(0, 20f));
        lbRol.setForeground(new java.awt.Color(255, 255, 255));
        lbRol.setText("Seleccione Rol");
        PInicSec.add(lbRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 120, -1, -1));

        lbRegistroUsuario1.setFont(cf.MyFont(1, 30f));
        lbRegistroUsuario1.setForeground(new java.awt.Color(255, 255, 255));
        lbRegistroUsuario1.setText("Inicio de Sesión");
        PInicSec.add(lbRegistroUsuario1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 350, 40));

        lbRol1.setFont(cf.MyFont(0, 20f));
        lbRol1.setForeground(new java.awt.Color(255, 255, 255));
        lbRol1.setText("Seleccione su Nombre");
        PInicSec.add(lbRol1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 210, -1, -1));

        boxNombreInicSec.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        PInicSec.add(boxNombreInicSec, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 250, 260, 30));

        btnInicSec.setBackground(new java.awt.Color(153, 153, 153));
        btnInicSec.setFont(cf.MyFont(0, 20f));
        btnInicSec.setForeground(new java.awt.Color(255, 255, 255));
        btnInicSec.setText("Ingresar");
        btnInicSec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicSecActionPerformed(evt);
            }
        });
        PInicSec.add(btnInicSec, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 300, 120, 30));

        lbNombreInicSecError.setForeground(new java.awt.Color(255, 0, 0));
        lbNombreInicSecError.setText("<html>*Debe seleccionar su  nombre <br>  para poder iniciar sesión</html>");
        PInicSec.add(lbNombreInicSecError, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 250, -1, -1));

        lbRolError.setForeground(new java.awt.Color(255, 0, 0));
        lbRolError.setText("<html>*Debe seleccionar un rol</html>");
        PInicSec.add(lbRolError, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 160, -1, -1));

        panelContenido.add(PInicSec, "panelYaEstoyReg");

        panelPrincipal.add(panelContenido, new org.netbeans.lib.awtextra.AbsoluteConstraints(269, 30, 770, 530));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 1022, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    int xx;
    int xy;

    private void panelLateralMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLateralMousePressed
        setOpacity((float) 0.8);
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_panelLateralMousePressed

    private void panelLateralMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLateralMouseReleased
        setOpacity((float) 1.0);
    }//GEN-LAST:event_panelLateralMouseReleased

    private void panelLateralMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLateralMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xx, y - xy);
    }//GEN-LAST:event_panelLateralMouseDragged

    private void lbCerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbCerrarMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lbCerrarMouseClicked

    private void pBRegUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBRegUsuarioMouseClicked
        CardLayout cl = (CardLayout) panelContenido.getLayout();
        cl.show(panelContenido, "panelUsuario");

        txtNombre.setEditable(true);
        txtApellido.setEditable(true);

        limpiarVentanaPricipal();
        pBRegUsuario.setBackground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_pBRegUsuarioMouseClicked

    private void pBRegUsuarioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBRegUsuarioMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pBRegUsuarioMousePressed

    private void pBRegProfesionalMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBRegProfesionalMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_pBRegProfesionalMouseDragged

    private void pBRegProfesionalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pBRegProfesionalFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_pBRegProfesionalFocusGained

    private void pBRegProfesionalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBRegProfesionalMouseClicked
        CardLayout cl = (CardLayout) panelContenido.getLayout();
        cl.show(panelContenido, "panelProfesional");

        limpiarVentanaPricipal();
        pBRegProfesional.setBackground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_pBRegProfesionalMouseClicked

    private void pBRegProfesionalMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBRegProfesionalMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pBRegProfesionalMousePressed

    private void lbLogoPrincipalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbLogoPrincipalMouseClicked
        CardLayout cl = (CardLayout) panelContenido.getLayout();
        cl.show(panelContenido, "panelInicio");

        limpiarVentanaPricipal();
    }//GEN-LAST:event_lbLogoPrincipalMouseClicked

    private void lbMinimizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbMinimizarMouseClicked
        setExtendedState(JFrame.CROSSHAIR_CURSOR);
    }//GEN-LAST:event_lbMinimizarMouseClicked

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        char c = evt.getKeyChar();

        if (Character.isDigit(c)) {
            getToolkit().beep();

            evt.consume();

        }
    }//GEN-LAST:event_txtNombreKeyTyped

    private void checkboxResCarnesRojasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxResCarnesRojasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkboxResCarnesRojasActionPerformed

    private void checkboxResOtrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxResOtrosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkboxResOtrosActionPerformed

    private void checkboxResSaladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxResSaladoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkboxResSaladoActionPerformed

    private void checkboxPrefOtrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxPrefOtrosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkboxPrefOtrosActionPerformed

    private void checkboxPrefCarnesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxPrefCarnesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkboxPrefCarnesActionPerformed

    private void panelBarraSuperiorVentanaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelBarraSuperiorVentanaMousePressed
        setOpacity((float) 0.8);
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_panelBarraSuperiorVentanaMousePressed

    private void panelBarraSuperiorVentanaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelBarraSuperiorVentanaMouseReleased
        setOpacity((float) 1.0);
    }//GEN-LAST:event_panelBarraSuperiorVentanaMouseReleased

    private void panelBarraSuperiorVentanaMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelBarraSuperiorVentanaMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xx, y - xy);
    }//GEN-LAST:event_panelBarraSuperiorVentanaMouseDragged


    private void btnRegistrarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarUsuarioActionPerformed
        String nombre;
        String apellidos;
        String nacionalidad;
        String descripcion;
        Icon avatar;

        boolean[] preferencias = new boolean[5];
        boolean[] restricciones = new boolean[5];
        Date nacimiento;

        if (validarFormUsuario()) {

            nombre = txtNombre.getText();
            apellidos = txtApellido.getText();
            nacionalidad = boxNacionalidadUsuario.getSelectedItem() + "";
            nacimiento = dcNacimientoUsuario.getDate();
            descripcion = txtDescripcionUsuario.getText();
            avatar = lbAvatar.getIcon();

            //Preferencias
            if (checkboxPrefCarnes.isSelected()) {
                preferencias[0] = true;
            }
            if (checkboxPrefLacteos.isSelected()) {
                preferencias[1] = true;
            }
            if (checkboxPrefFrutas.isSelected()) {
                preferencias[2] = true;
            }
            if (checkboxPrefVerduras.isSelected()) {
                preferencias[3] = true;
            }
            if (checkboxPrefOtros.isSelected()) {
                preferencias[4] = true;
            }
            //Restricciones
            if (checkboxResSalado.isSelected()) {
                restricciones[0] = true;
            }
            if (checkboxResDulce.isSelected()) {
                restricciones[1] = true;
            }
            if (checkboxResLacteos.isSelected()) {
                restricciones[2] = true;
            }
            if (checkboxResCarnesRojas.isSelected()) {
                restricciones[3] = true;
            }
            if (checkboxResOtros.isSelected()) {
                restricciones[4] = true;
            }

            Icon icono = lbAvatar.getIcon();
            lbAvatarInicSecUsuario.setIcon(profesional.getAvatar());
            boolean registroExitoso = sistema.registrarUsuario(nombre, apellidos, nacionalidad, preferencias, restricciones, nacimiento, descripcion, icono);

            if (registroExitoso) {
                lbCheckRegistroUsuarioExitoso.setVisible(true);
                limpiarFormUsuario();
            } else {
                lbCheckRegistroUsuarioRepetido.setVisible(true);
            }

        }
    }//GEN-LAST:event_btnRegistrarUsuarioActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (validarFormAlimento()) {

            String nombre = txtNombreAlimentos.getText();
            String tipo = obtenerTipoAlimento();
            int nutrientesPrincipales[] = obtenerNutrientesPrincipales();

            sistema.registrarAlimento(nombre, tipo, nutrientesPrincipales);
            limpiarFormAlimento();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void pBRegAlimentosIngeridosUsuarioMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBRegAlimentosIngeridosUsuarioMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_pBRegAlimentosIngeridosUsuarioMouseDragged

    private void pBRegAlimentosIngeridosUsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pBRegAlimentosIngeridosUsuarioFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_pBRegAlimentosIngeridosUsuarioFocusGained

    public void cargarBoxAlimentosIngeridosUsuario() {
        boxAlimentoConsumidoUsuario.removeAllItems();
        boxAlimentoConsumidoUsuario.addItem("Seleccione...");
        for (int i = 0; i < sistema.getListaAlimentos().size(); i++) {
            boxAlimentoConsumidoUsuario.addItem(sistema.getListaAlimentos().get(i) + "");
        }
    }

    private void pBRegAlimentosIngeridosUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBRegAlimentosIngeridosUsuarioMouseClicked
        CardLayout cl = (CardLayout) panelContenido.getLayout();
        cl.show(panelContenido, "panelAlimentosIngeridos");

        limpiarVentanaUsuario();
        pBRegAlimentosIngeridosUsuario.setBackground(Color.LIGHT_GRAY);

        limpiarFormRegistroAlimentoConsumidoUsuario();
        cargarBoxAlimentosIngeridosUsuario();

    }//GEN-LAST:event_pBRegAlimentosIngeridosUsuarioMouseClicked

    private void pBRegAlimentosIngeridosUsuarioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBRegAlimentosIngeridosUsuarioMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pBRegAlimentosIngeridosUsuarioMousePressed

    private void pBRegPlanAlimentacionUsuarioMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBRegPlanAlimentacionUsuarioMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_pBRegPlanAlimentacionUsuarioMouseDragged

    private void pBRegPlanAlimentacionUsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pBRegPlanAlimentacionUsuarioFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_pBRegPlanAlimentacionUsuarioFocusGained

    public void cargarBoxProfesionalesSolicitudDePlan() {
        boxNombreProfesionalSolicitud.removeAllItems();
        boxNombreProfesionalSolicitud.addItem("Seleccione...");
        for (int i = 0; i < sistema.getListaProfesionales().size(); i++) {
            boxNombreProfesionalSolicitud.addItem(sistema.getListaProfesionales().get(i) + "");
        }
    }

    private void pBRegPlanAlimentacionUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBRegPlanAlimentacionUsuarioMouseClicked
        CardLayout cl = (CardLayout) panelContenido.getLayout();
        cl.show(panelContenido, "panelPlanAlimentacion");

        limpiarVentanaUsuario();
        pBRegPlanAlimentacionUsuario.setBackground(Color.LIGHT_GRAY);

        cargarBoxProfesionalesSolicitudDePlan();
    }//GEN-LAST:event_pBRegPlanAlimentacionUsuarioMouseClicked

    private void pBRegPlanAlimentacionUsuarioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBRegPlanAlimentacionUsuarioMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pBRegPlanAlimentacionUsuarioMousePressed

    private void pBRegConsultaProfUsuarioMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBRegConsultaProfUsuarioMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_pBRegConsultaProfUsuarioMouseDragged

    private void pBRegConsultaProfUsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pBRegConsultaProfUsuarioFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_pBRegConsultaProfUsuarioFocusGained

    public void cargarTablaTodasLasConsultasDeUnUsuario(JTable tablaCargar) {
        ArrayList<ParProfesionalConsulta> todasLasConsultas = sistema.todasConsultasDeUnUsuario(usuario + "");
        int largoListaConsultas;

        try {
            largoListaConsultas = todasLasConsultas.size();
        } catch (NullPointerException ex) {
            largoListaConsultas = 0;
        }

        String matrizDatos[][] = new String[largoListaConsultas][3];

        for (int i = 0; i < largoListaConsultas; i++) {

            String estado = "";

            if (todasLasConsultas.get(i).getConsulta().isEstado()) {
                estado = "Cumplido";
            } else {
                estado = "Pendiente";
            }

            matrizDatos[i][0] = todasLasConsultas.get(i).getProfesional() + "";
            matrizDatos[i][1] = new SimpleDateFormat("dd-MMMM-yyyy").format(todasLasConsultas.get(i).getConsulta().getFecha()) + "";
            matrizDatos[i][2] = estado;

            tablaCargar.changeSelection(i, 1, false, false);
        }

        tablaCargar.setModel(new javax.swing.table.DefaultTableModel(
                matrizDatos,
                new String[]{
                    "Profesional", "Fecha", "Estado"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
    }

    private void pBRegConsultaProfUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBRegConsultaProfUsuarioMouseClicked
        CardLayout cl = (CardLayout) panelContenido.getLayout();
        cl.show(panelContenido, "panelConsultaProf");

        limpiarVentanaUsuario();
        pBRegConsultaProfUsuario.setBackground(Color.LIGHT_GRAY);

        cargarBoxProfesionalesConsultaProfUsuario();
        cargarTablaTodasLasConsultasDeUnUsuario(tablaConsultasUsuario);
    }//GEN-LAST:event_pBRegConsultaProfUsuarioMouseClicked

    private void pBRegConsultaProfUsuarioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBRegConsultaProfUsuarioMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pBRegConsultaProfUsuarioMousePressed

    private void lbAvatarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbAvatarMouseClicked
        cambiarLogoLabelDesdeArchivo(lbAvatar);
    }//GEN-LAST:event_lbAvatarMouseClicked

    private void btnRegistrarProfesionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarProfesionalActionPerformed
        if (validarFormProfesional()) {

            String nombres = txtNombreProf.getText();
            String apellidos = txtApellidoProf.getText();
            String titulo = txtTitulo.getText();
            String paisObtencionTitulo = boxPaisTitProf.getSelectedItem() + "";
            Date fechaNacimiento = dcNacimientoProf.getDate();
            Date fechaGraduacion = dcGraduacionProf.getDate();
            Icon avatar = lbAvatarProf.getIcon();

            sistema.registrarProfesional(nombres, apellidos, titulo, paisObtencionTitulo, fechaNacimiento, fechaGraduacion, avatar);

            limpiarFormProfesional();
        }
    }//GEN-LAST:event_btnRegistrarProfesionalActionPerformed

    private void txtNombreProfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProfKeyTyped
        char c = evt.getKeyChar();

        if (Character.isDigit(c)) {
            getToolkit().beep();

            evt.consume();
        }
    }//GEN-LAST:event_txtNombreProfKeyTyped

    private void txtTituloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTituloKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTituloKeyTyped

    private void lbAvatarProfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbAvatarProfMouseClicked
        cambiarLogoLabelDesdeArchivo(lbAvatarProf);
    }//GEN-LAST:event_lbAvatarProfMouseClicked

    private void txtApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyTyped
        char c = evt.getKeyChar();

        if (Character.isDigit(c)) {
            getToolkit().beep();

            evt.consume();

        }
    }//GEN-LAST:event_txtApellidoKeyTyped

    private void txtApellidoProfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoProfKeyTyped
        char c = evt.getKeyChar();

        if (Character.isDigit(c)) {
            getToolkit().beep();

            evt.consume();

        }
    }//GEN-LAST:event_txtApellidoProfKeyTyped

    private void txtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoActionPerformed

    private void lbAvatarAlimentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbAvatarAlimentoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbAvatarAlimentoMouseClicked

    private void btnGuardarUsuario1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarUsuario1ActionPerformed
        String profesional = (String) boxNombreProfesional.getSelectedItem();
        String descripcion = txtDescripcionConsultaProf.getText();
        Enums.MotivoConsulta motivo = obtenerMotivoConsulta();

        boolean esValido = false;

        if (!profesional.equals("Seleccione...")) {
            lbCheckProfErrorConsProfUsuario.setVisible(false);
            esValido = true;
        } else {
            lbCheckProfErrorConsProfUsuario.setVisible(true);
        }

        if (rbtnAliIng.isSelected() || rbtnAliIgerir.isSelected() || rbtnAliOtros.isSelected()) {
            lbMotivoError.setVisible(false);
            esValido &= true;
        } else {
            lbMotivoError.setVisible(true);
            esValido = false;
        }

        if (!descripcion.equals("")) {
            lbDescripcionError.setVisible(false);
            esValido &= true;
        } else {
            lbDescripcionError.setVisible(true);
            esValido = false;
        }

        if (esValido) {
            sistema.agregarConsultaProf(profesional, descripcion, usuario, motivo);
            limpiarFormConsultaProfesionalUsuario();
            cargarVentanaPrincipalUsuario();
            cargarTablaTodasLasConsultasDeUnUsuario(tablaConsultasUsuario);
        }

    }//GEN-LAST:event_btnGuardarUsuario1ActionPerformed

    private void rbtnAliIngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnAliIngActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnAliIngActionPerformed

    private void txtNombreProfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreProfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreProfActionPerformed

    private void txtNombreAlimentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreAlimentosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreAlimentosActionPerformed

    private void txtNombreAlimentosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreAlimentosKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreAlimentosKeyTyped

    private void txtProporcionProteinasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProporcionProteinasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProporcionProteinasActionPerformed

    private void txtProporcionProteinasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProporcionProteinasKeyTyped
        char c = evt.getKeyChar();
        if (c < '0' || c > '9') {
            evt.consume();
        }
    }//GEN-LAST:event_txtProporcionProteinasKeyTyped

    private void txtProporcionCarbohidratosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProporcionCarbohidratosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProporcionCarbohidratosActionPerformed

    private void txtProporcionCarbohidratosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProporcionCarbohidratosKeyTyped
        char c = evt.getKeyChar();
        if (c < '0' || c > '9') {
            evt.consume();
        }
    }//GEN-LAST:event_txtProporcionCarbohidratosKeyTyped

    private void txtProporcionVitaminasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProporcionVitaminasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProporcionVitaminasActionPerformed

    private void txtProporcionVitaminasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProporcionVitaminasKeyTyped
        char c = evt.getKeyChar();
        if (c < '0' || c > '9') {
            evt.consume();
        }
    }//GEN-LAST:event_txtProporcionVitaminasKeyTyped

    private void txtProporcionMineralesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProporcionMineralesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProporcionMineralesActionPerformed

    private void txtProporcionMineralesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProporcionMineralesKeyTyped
        char c = evt.getKeyChar();
        if (c < '0' || c > '9') {
            evt.consume();
        }
    }//GEN-LAST:event_txtProporcionMineralesKeyTyped

    private void txtProporcionAntioxidanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProporcionAntioxidanteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProporcionAntioxidanteActionPerformed

    private void txtProporcionAntioxidanteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProporcionAntioxidanteKeyTyped
        char c = evt.getKeyChar();
        if (c < '0' || c > '9') {
            evt.consume();
        }
    }//GEN-LAST:event_txtProporcionAntioxidanteKeyTyped

    private void pBYaEstoyRegMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBYaEstoyRegMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_pBYaEstoyRegMouseDragged

    private void pBYaEstoyRegFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pBYaEstoyRegFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_pBYaEstoyRegFocusGained

    private void pBYaEstoyRegMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBYaEstoyRegMouseClicked
        CardLayout cl = (CardLayout) panelContenido.getLayout();
        cl.show(panelContenido, "panelYaEstoyReg");

        limpiarVentanaPricipal();
        limpiarVentanaInicSec();
        limpiarFormInicSec();

        boxNombreInicSec.removeAllItems();
        boxNombreInicSec.addItem("Seleccione...");
        // boxNombreInicSec.setEnabled(true);
        for (int i = 0; i < sistema.getListaProfesionales().size(); i++) {
            boxNombreInicSec.addItem(sistema.getListaProfesionales().get(i) + "");
        }
    }//GEN-LAST:event_pBYaEstoyRegMouseClicked

    private void pBYaEstoyRegMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBYaEstoyRegMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pBYaEstoyRegMousePressed

    public void cargarVentanaPrincipalUsuario() {
        lbMostrarNombreUsuario.setText(usuario + "");
        lbAvatarInicSecUsuario.setIcon(usuario.getAvatar());

        int largoListaAlimentos = usuario.getAlimentosIngeridos().size();
        String matrizDatos[][] = new String[largoListaAlimentos][2];

        for (int i = 0; i < largoListaAlimentos; i++) {
            matrizDatos[i][0] = usuario.getAlimentosIngeridos().get(i).getAlimentoIngeridoUsuario() + "";
            matrizDatos[i][1] = new SimpleDateFormat("dd-MMMM-yyyy").format(usuario.getAlimentosIngeridos().get(i).getFecha()) + "";
        }

        tablaAlimentosIngeridosUsuarioInicio.setModel(new javax.swing.table.DefaultTableModel(
                matrizDatos,
                new String[]{
                    "Alimentos", "Fecha"
                }
        ) {

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        });

        if (sistema.obtenerEstadoPlanAlimentacionDadoNombreUsuario(usuario + "")) {
            tablaPlanAlimentacionUsuario.setModel(new javax.swing.table.DefaultTableModel(
                    sistema.planAlimentacionUsuario(usuario + ""),
                    new String[]{
                        "", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"
                    }
            ) {
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            });
        }
        cargarTablaTodasLasConsultasDeUnUsuario(tablaConsultasAProfesionalesUsuarioInicio);
    }

    public void cargarVentanaPrincipalProfesional() {
        lbMostrarNombreProfesional.setText(profesional + "");
        lbAvatarInicSecProfesional.setIcon(profesional.getAvatar());

        mostrarTablaConsultaProfesional(tablaInicioUltimosPlanesProf);
        mostrarTablaSolicitudDePlanesProfesional(tablaInicioUltimasConsutasProf);
        limpiarFormPlanAlimentacion();
    }

    private void btnInicSecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicSecActionPerformed
        String opcion = (String) boxRolInicSec.getSelectedItem();

        if (opcion.equals("Usuario")) {

            lbRolError.setVisible(false);

            String nombre = (String) boxNombreInicSec.getSelectedItem();

            if (!nombre.equals("Seleccione...")) {
                lbNombreInicSecError.setVisible(false);
                usuario = sistema.obtenerUsuario(nombre);

                if (usuario != null) {
                    CardLayout panelLateralUsuario = (CardLayout) panelLateral.getLayout();
                    panelLateralUsuario.show(panelLateral, "panelBotonesUsuario");

                    CardLayout panelContenidoUsuario = (CardLayout) panelContenido.getLayout();
                    panelContenidoUsuario.show(panelContenido, "panelInicioUsuario");

                    cargarVentanaPrincipalUsuario();
                    limpiarVentanaUsuario();
                }
            } else {
                lbNombreInicSecError.setVisible(true);
            }
        }

        if (opcion.equals("Profesional")) {

            lbRolError.setVisible(false);

            String nombre = (String) boxNombreInicSec.getSelectedItem();

            if (!nombre.equals("Seleccione...")) {
                lbNombreInicSecError.setVisible(false);
                profesional = sistema.obtenerProfesional(nombre);

                if (profesional != null) {
                    CardLayout panelLateralProfesional = (CardLayout) panelLateral.getLayout();
                    panelLateralProfesional.show(panelLateral, "panelBotonesProfesional");

                    CardLayout panelContenidoUsuario = (CardLayout) panelContenido.getLayout();
                    panelContenidoUsuario.show(panelContenido, "panelInicioProfesional");

                    cargarVentanaPrincipalProfesional();

                }
            } else {
                lbNombreInicSecError.setVisible(true);
            }
        }

        if (opcion.equals("Seleccione...")) {
            lbRolError.setVisible(true);
        }

    }//GEN-LAST:event_btnInicSecActionPerformed

    private void boxRolInicSecItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_boxRolInicSecItemStateChanged
        if (boxRolInicSec.getSelectedItem().equals("Seleccione...")) {
            boxNombreInicSec.removeAllItems();
            boxNombreInicSec.addItem("Seleccione...");
            boxNombreInicSec.setEnabled(false);
            lbNombreInicSecError.setVisible(false);
        }

        if (boxRolInicSec.getSelectedItem().equals("Profesional")) {
            boxNombreInicSec.removeAllItems();
            boxNombreInicSec.addItem("Seleccione...");
            boxNombreInicSec.setEnabled(true);
            for (int i = 0; i < sistema.getListaProfesionales().size(); i++) {
                boxNombreInicSec.addItem(sistema.getListaProfesionales().get(i) + "");
            }
        }

        if (boxRolInicSec.getSelectedItem().equals("Usuario")) {
            boxNombreInicSec.removeAllItems();
            boxNombreInicSec.addItem("Seleccione...");
            boxNombreInicSec.setEnabled(true);
            for (int i = 0; i < sistema.getListaUsuarios().size(); i++) {
                boxNombreInicSec.addItem(sistema.getListaUsuarios().get(i) + "");
            }
        }
    }//GEN-LAST:event_boxRolInicSecItemStateChanged

    private void boxRolInicSecMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boxRolInicSecMouseClicked
        CardLayout cl = (CardLayout) panelContenido.getLayout();
        cl.show(panelContenido, "panelYaEstoyReg");
    }//GEN-LAST:event_boxRolInicSecMouseClicked

    private void pBRegAlimentosProfMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBRegAlimentosProfMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_pBRegAlimentosProfMouseDragged

    private void pBRegAlimentosProfFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pBRegAlimentosProfFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_pBRegAlimentosProfFocusGained

    private void pBRegAlimentosProfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBRegAlimentosProfMouseClicked
        CardLayout cl = (CardLayout) panelContenido.getLayout();
        cl.show(panelContenido, "panelAlimento");

        limpiarVentanaProfesional();
        pBRegAlimentosProf.setBackground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_pBRegAlimentosProfMouseClicked

    private void pBRegAlimentosProfMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBRegAlimentosProfMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pBRegAlimentosProfMousePressed

    private void pBEditarDatosProfMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBEditarDatosProfMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_pBEditarDatosProfMouseDragged

    private void pBEditarDatosProfFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pBEditarDatosProfFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_pBEditarDatosProfFocusGained

    public void cargarDatosProfesional() {
        txtNombreProf.setText(profesional.getNombres());
        txtApellidoProf.setText(profesional.getApellidos());
        dcNacimientoProf.setDate(profesional.getNacimiento());
        txtTitulo.setText(profesional.getTitulo());
        dcGraduacionProf.setDate(profesional.getNacimiento());
        boxPaisTitProf.setSelectedItem(profesional.getPaisObtencionTitulo());

        btnRegistrarProfesional.setVisible(false);
        btnEditarProfesional.setVisible(true);
    }

    private void pBEditarDatosProfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBEditarDatosProfMouseClicked
        CardLayout cl = (CardLayout) panelContenido.getLayout();
        cl.show(panelContenido, "panelProfesional");

        txtNombreProf.setEditable(false);
        txtApellidoProf.setEditable(false);

        limpiarVentanaProfesional();
        pBEditarDatosProf.setBackground(Color.LIGHT_GRAY);
        cargarDatosProfesional();
    }//GEN-LAST:event_pBEditarDatosProfMouseClicked

    private void pBEditarDatosProfMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBEditarDatosProfMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pBEditarDatosProfMousePressed

    private void pBEditarDatosUsuarioMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBEditarDatosUsuarioMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_pBEditarDatosUsuarioMouseDragged

    private void pBEditarDatosUsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pBEditarDatosUsuarioFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_pBEditarDatosUsuarioFocusGained

    public void cargarDatosUsuario() {
        txtNombre.setText(usuario.getNombres());
        txtApellido.setText(usuario.getApellidos());

        dcNacimientoUsuario.setDate(usuario.getNacimiento());
        boxNacionalidadUsuario.setSelectedItem(usuario.getNacionalidad());
        txtDescripcionUsuario.setText(usuario.getDescripcion());

        //Preferencias
        if (usuario.getPreferencias()[0]) {
            checkboxPrefCarnes.setSelected(true);
        }

        if (usuario.getPreferencias()[1]) {
            checkboxPrefLacteos.setSelected(true);
        }

        if (usuario.getPreferencias()[2]) {
            checkboxPrefFrutas.setSelected(true);
        }

        if (usuario.getPreferencias()[3]) {
            checkboxPrefVerduras.setSelected(true);
        }

        if (usuario.getPreferencias()[4]) {
            checkboxPrefOtros.setSelected(true);
        }

        //Restricciones
        if (usuario.getRestricciones()[0]) {
            checkboxResSalado.setSelected(true);
        }

        if (usuario.getRestricciones()[1]) {
            checkboxResDulce.setSelected(true);
        }

        if (usuario.getRestricciones()[2]) {
            checkboxResLacteos.setSelected(true);
        }

        if (usuario.getRestricciones()[3]) {
            checkboxResCarnesRojas.setSelected(true);
        }

        if (usuario.getRestricciones()[4]) {
            checkboxResOtros.setSelected(true);
        }

        btnRegistrarUsuario.setVisible(false);
        btnEditarUsuario.setVisible(true);
    }

    private void pBEditarDatosUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBEditarDatosUsuarioMouseClicked
        CardLayout cl = (CardLayout) panelContenido.getLayout();
        cl.show(panelContenido, "panelUsuario");

        txtNombre.setEditable(false);
        txtApellido.setEditable(false);

        limpiarVentanaUsuario();
        cargarDatosUsuario();
        pBEditarDatosUsuario.setBackground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_pBEditarDatosUsuarioMouseClicked

    private void pBEditarDatosUsuarioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBEditarDatosUsuarioMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pBEditarDatosUsuarioMousePressed

    private void pBConsultasRecibidasProfMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBConsultasRecibidasProfMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_pBConsultasRecibidasProfMouseDragged

    private void pBConsultasRecibidasProfFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pBConsultasRecibidasProfFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_pBConsultasRecibidasProfFocusGained

    public String obtenerEstado(boolean estado) {
        String retornoEstado = "";
        if (estado) {
            retornoEstado = "Cumplido";
        } else {
            retornoEstado = "Pendiente";
        }
        return retornoEstado;
    }

    public void mostrarTablaConsultaProfesional(JTable tabla) {
        int largoListaConsultas;

        try {
            largoListaConsultas = profesional.getListaConsultas().size();
        } catch (NullPointerException ex) {
            largoListaConsultas = 0;
        }

        String matrizDatos[][] = new String[largoListaConsultas][3];

        for (int i = 0; i < largoListaConsultas; i++) {
            Consulta consulta = profesional.getListaConsultas().get(i);

            matrizDatos[i][0] = consulta.getUsuarioDeConsulta() + "";
            matrizDatos[i][1] = new SimpleDateFormat("dd-MM-yyyy").format(consulta.getFecha()) + "";
            matrizDatos[i][2] = obtenerEstado(consulta.isEstado());

            tabla.changeSelection(i, 1, false, false);
        }

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                matrizDatos,
                new String[]{
                    "Usuario", "Fecha", "Estado"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

    }

    private void pBConsultasRecibidasProfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBConsultasRecibidasProfMouseClicked
        CardLayout cl = (CardLayout) panelContenido.getLayout();
        cl.show(panelContenido, "panelConsultasRecibidasProf");

        limpiarVentanaProfesional();
        pBConsultasRecibidasProf.setBackground(Color.LIGHT_GRAY);

        mostrarTablaConsultaProfesional(tablaConsultasRecibidas);
    }//GEN-LAST:event_pBConsultasRecibidasProfMouseClicked

    private void pBConsultasRecibidasProfMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBConsultasRecibidasProfMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pBConsultasRecibidasProfMousePressed

    private void pBPlanAlimentacionProfMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBPlanAlimentacionProfMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_pBPlanAlimentacionProfMouseDragged

    private void pBPlanAlimentacionProfFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pBPlanAlimentacionProfFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_pBPlanAlimentacionProfFocusGained

    public int obtenerEdadAPartirDeFechaNacimiento(Date fechaNacimiento) {
        Date fechaActual = new Date();

        Calendar calFechaActual = Calendar.getInstance();
        calFechaActual.setTime(fechaActual);
        int anioFechaActual = calFechaActual.get(Calendar.YEAR);

        Calendar calValidar = Calendar.getInstance();
        calValidar.setTime(fechaNacimiento);
        int anioNacimiento = calValidar.get(Calendar.YEAR);

        return (anioFechaActual - anioNacimiento);
    }

    public void mostrarTablaSolicitudDePlanesProfesional(JTable tabla) {
        int largoListaPlanesProfesional;

        try {
            largoListaPlanesProfesional = profesional.getListaSolicitudesDePlanes().size();
        } catch (NullPointerException ex) {
            largoListaPlanesProfesional = 0;
        }

        String matrizDatos[][] = new String[largoListaPlanesProfesional][8];

        for (int i = 0; i < largoListaPlanesProfesional; i++) {
            PlanAlimentacion pAlimentacion = profesional.getListaSolicitudesDePlanes().get(i);

            matrizDatos[i][0] = pAlimentacion.getUsuario() + "";
            matrizDatos[i][1] = obtenerEdadAPartirDeFechaNacimiento(pAlimentacion.getUsuario().getNacimiento()) + "";
            matrizDatos[i][2] = pAlimentacion.getPeso() + "";
            matrizDatos[i][3] = pAlimentacion.getAltura() + "";
            matrizDatos[i][4] = pAlimentacion.getHorasDeActividad() + "";
            matrizDatos[i][5] = obtenerEstado(pAlimentacion.isEstado());

            tabla.changeSelection(i, 1, false, false);
        }

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                matrizDatos,
                new String[]{
                    "Usuario", "Edad", "Peso (Kg)", "Altura (cm)", "Actividad física (h)", "Estado"
                }
        ) {

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;//canEdit[columnIndex];
            }
        });
    }

    public void cargarBoxDiasSemana() {
        boxDiasSemanaPlanAliProf.removeAllItems();
        boxDiasSemanaPlanAliProf.addItem("Seleccione...");
        for (int i = 0; i < diasSemana.length; i++) {
            boxDiasSemanaPlanAliProf.addItem(diasSemana[i]);
        }
    }

    public void cargarBoxPlanAlimentacion() {
        boxComidaPlanAliProf.removeAllItems();
        boxComidaPlanAliProf.addItem("Seleccione...");
        for (int i = 0; i < comidas.length; i++) {
            boxComidaPlanAliProf.addItem(comidas[i]);
        }
    }

    private void pBPlanAlimentacionProfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBPlanAlimentacionProfMouseClicked
        CardLayout cl = (CardLayout) panelContenido.getLayout();
        cl.show(panelContenido, "panelPlanAlimentacionProf");

        limpiarVentanaProfesional();
        pBPlanAlimentacionProf.setBackground(Color.LIGHT_GRAY);

        CardLayout subCl = (CardLayout) panelAmpliarDatosPlanAlimentacion.getLayout();
        subCl.show(panelAmpliarDatosPlanAlimentacion, "panelTablaPlanesAlimentacionSolicitados");

        cargarBoxDiasSemana();
        cargarBoxPlanAlimentacion();

        mostrarTablaSolicitudDePlanesProfesional(tablaSolicitudDePlanesProfesional);
    }//GEN-LAST:event_pBPlanAlimentacionProfMouseClicked

    private void pBPlanAlimentacionProfMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBPlanAlimentacionProfMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pBPlanAlimentacionProfMousePressed

    private void pBYaSalirUsuarioMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBYaSalirUsuarioMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_pBYaSalirUsuarioMouseDragged

    private void pBYaSalirUsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pBYaSalirUsuarioFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_pBYaSalirUsuarioFocusGained

    private void pBYaSalirUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBYaSalirUsuarioMouseClicked
        cerrarSesion();
    }//GEN-LAST:event_pBYaSalirUsuarioMouseClicked

    private void pBYaSalirUsuarioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBYaSalirUsuarioMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pBYaSalirUsuarioMousePressed

    private void pBSalirProfMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBSalirProfMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_pBSalirProfMouseDragged

    private void pBSalirProfFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pBSalirProfFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_pBSalirProfFocusGained

    private void pBSalirProfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBSalirProfMouseClicked
        cerrarSesion();
    }//GEN-LAST:event_pBSalirProfMouseClicked

    private void pBSalirProfMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBSalirProfMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pBSalirProfMousePressed

    private void btnBorrarAlimentoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarAlimentoUsuarioActionPerformed
        int fila = tablaAlimentosIngeridosUsuario.getSelectedRow();
        String filaSeleccionada = (String) tablaAlimentosIngeridosUsuario.getValueAt(fila, 0);
        sistema.borrarAlimentoUsuario(usuario, filaSeleccionada);
        limpiarFormRegistroAlimentoConsumidoUsuario();

    }//GEN-LAST:event_btnBorrarAlimentoUsuarioActionPerformed

    private void pBConsultasRecibidasProfMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBConsultasRecibidasProfMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_pBConsultasRecibidasProfMouseEntered

    private void btnResponderConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResponderConsultaActionPerformed
        String respuestaConsulta = txtResponderUsuarioConsultaRecibidaProf.getText();
        int fila = tablaConsultasRecibidas.getSelectedRow();
        if (validarCampoTxtNoEsVacio(respuestaConsulta)) {
            profesional.getListaConsultas().get(fila).setRespuesta(respuestaConsulta);
            profesional.getListaConsultas().get(fila).setEstado(true);
        } else {
            lbResponderConsultasRecibidasError.setVisible(true);
        }

        mostrarTablaConsultaProfesional(tablaConsultasRecibidas);

    }//GEN-LAST:event_btnResponderConsultaActionPerformed

    private void btnAgregarAlimentoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarAlimentoUsuarioActionPerformed
        String alimento = (String) boxAlimentoConsumidoUsuario.getSelectedItem();

        if (!alimento.equals("Seleccione...")) {
            lbCheckBoxNombreAlimentoConsumidoBienUsuario.setVisible(true);
            lbCheckBoxNombreAlimentoConsumidoErrorUsuario.setVisible(false);
        } else {
            lbCheckBoxNombreAlimentoConsumidoBienUsuario.setVisible(false);
            lbCheckBoxNombreAlimentoConsumidoErrorUsuario.setVisible(true);
        }

        if (validarChooserFechaNoEsVacio(dcAlimentoConsumidoUsuario)) {
            lbCheckBoxFechaAlimentoConsumidoErrorUsuario.setVisible(false);
            lbCheckBoxFechaAlimentoConsumidoErrorUsuario.setVisible(true);
        } else {
            lbCheckBoxFechaAlimentoConsumidoErrorUsuario.setVisible(true);
            lbCheckBoxFechaAlimentoConsumidoErrorUsuario.setVisible(false);
        }

        if (validarChooserFechaNoEsVacio(dcAlimentoConsumidoUsuario) && !alimento.equals("Seleccione...")) {

            Date fechaDeConsumoAlimento = dcAlimentoConsumidoUsuario.getDate();
            sistema.agregarAlimentoUsuario(usuario, alimento, fechaDeConsumoAlimento);

            limpiarFormRegistroAlimentoConsumidoUsuario();
        }
    }//GEN-LAST:event_btnAgregarAlimentoUsuarioActionPerformed

    private void pBRegConsultaProfUsuarioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBRegConsultaProfUsuarioMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_pBRegConsultaProfUsuarioMouseEntered

    private void txtPesoSolicitudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesoSolicitudActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesoSolicitudActionPerformed

    private void txtPesoSolicitudKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesoSolicitudKeyTyped
        char c = evt.getKeyChar();
        if (c < '0' || c > '9') {
            evt.consume();
        }
    }//GEN-LAST:event_txtPesoSolicitudKeyTyped

    private void txtAlturaSolicitudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlturaSolicitudActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAlturaSolicitudActionPerformed

    private void txtAlturaSolicitudKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAlturaSolicitudKeyTyped
        char c = evt.getKeyChar();
        if (c < '0' || c > '9') {
            evt.consume();
        }
    }//GEN-LAST:event_txtAlturaSolicitudKeyTyped

    private void txtHorasDeActividadSolicitudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHorasDeActividadSolicitudActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHorasDeActividadSolicitudActionPerformed

    private void txtHorasDeActividadSolicitudKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHorasDeActividadSolicitudKeyTyped
        char c = evt.getKeyChar();
        if (c < '0' || c > '9') {
            evt.consume();
        }
    }//GEN-LAST:event_txtHorasDeActividadSolicitudKeyTyped

    public boolean validarPeso(int peso) {
        boolean esCampoValido = true;
        if (peso < 0 || peso > 500) {
            esCampoValido = false;
        }
        return esCampoValido;
    }

    public boolean validarAltura(int altura) {
        boolean esCampoValido = true;
        if (altura < 0 || altura > 250) {
            esCampoValido = false;
        }
        return esCampoValido;
    }

    public boolean validarHoras(int horas) {
        boolean esCampoValido = true;
        if (horas < 0 || horas > 24) {
            esCampoValido = false;
        }
        return esCampoValido;
    }

    private void btnSolicitarPlanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSolicitarPlanActionPerformed
        if (validarFormSolicitud()) {

            Profesional profesionalSeleccionado = sistema.convertirStringNombreProfesionalEnProfesional(boxNombreProfesionalSolicitud.getSelectedItem() + "");

            int peso = Integer.parseInt(txtPesoSolicitud.getText());
            int altura = Integer.parseInt(txtAlturaSolicitud.getText());
            int horasDeActividad = Integer.parseInt(txtHorasDeActividadSolicitud.getText());
            String detalles = txtDetallesSolicitud.getText();

            sistema.solicitarPlan(usuario, profesionalSeleccionado, peso, altura, horasDeActividad, detalles);
            limpiarFormSolicitud();
        }
    }//GEN-LAST:event_btnSolicitarPlanActionPerformed

    private void checkBoxNutProteinasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_checkBoxNutProteinasStateChanged
        if (checkBoxNutProteinas.isSelected()) {
            txtProporcionProteinas.setEnabled(true);
        } else {
            txtProporcionProteinas.setEnabled(false);
            txtProporcionProteinas.setText("");
        }
    }//GEN-LAST:event_checkBoxNutProteinasStateChanged

    private void checkBoxNutCarbohidratosStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_checkBoxNutCarbohidratosStateChanged
        if (checkBoxNutCarbohidratos.isSelected()) {
            txtProporcionCarbohidratos.setEnabled(true);
        } else {
            txtProporcionCarbohidratos.setEnabled(false);
            txtProporcionCarbohidratos.setText("");
        }
    }//GEN-LAST:event_checkBoxNutCarbohidratosStateChanged

    private void checkBoxNutVitaminasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_checkBoxNutVitaminasStateChanged
        if (checkBoxNutVitaminas.isSelected()) {
            txtProporcionVitaminas.setEnabled(true);
        } else {
            txtProporcionVitaminas.setEnabled(false);
            txtProporcionVitaminas.setText("");
        }
    }//GEN-LAST:event_checkBoxNutVitaminasStateChanged

    private void checkBoxNutMineralesStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_checkBoxNutMineralesStateChanged
        if (checkBoxNutMinerales.isSelected()) {
            txtProporcionMinerales.setEnabled(true);
        } else {
            txtProporcionMinerales.setEnabled(false);
            txtProporcionMinerales.setText("");
        }
    }//GEN-LAST:event_checkBoxNutMineralesStateChanged

    private void checkBoxNutAntioxidanteStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_checkBoxNutAntioxidanteStateChanged
        if (checkBoxNutAntioxidante.isSelected()) {
            txtProporcionAntioxidante.setEnabled(true);
        } else {
            txtProporcionAntioxidante.setEnabled(false);
            txtProporcionAntioxidante.setText("");
        }
    }//GEN-LAST:event_checkBoxNutAntioxidanteStateChanged

    private void rbtnAliOtrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnAliOtrosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnAliOtrosActionPerformed

    private void btnEditarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarUsuarioActionPerformed
        String nombre;
        String apellidos;
        String nacionalidad;
        String descripcion;

        boolean[] preferencias = new boolean[5];
        boolean[] restricciones = new boolean[5];
        Date nacimiento;

        if (validarFormUsuario()) {

            nombre = txtNombre.getText();
            apellidos = txtApellido.getText();
            nacionalidad = boxNacionalidadUsuario.getSelectedItem() + "";
            nacimiento = dcNacimientoUsuario.getDate();
            descripcion = txtDescripcionUsuario.getText();

            //Preferencias
            if (checkboxPrefCarnes.isSelected()) {
                preferencias[0] = true;
            }
            if (checkboxPrefLacteos.isSelected()) {
                preferencias[1] = true;
            }
            if (checkboxPrefFrutas.isSelected()) {
                preferencias[2] = true;
            }
            if (checkboxPrefVerduras.isSelected()) {
                preferencias[3] = true;
            }
            if (checkboxPrefOtros.isSelected()) {
                preferencias[4] = true;
            }
            //Restricciones
            if (checkboxResSalado.isSelected()) {
                restricciones[0] = true;
            }
            if (checkboxResDulce.isSelected()) {
                restricciones[1] = true;
            }
            if (checkboxResLacteos.isSelected()) {
                restricciones[2] = true;
            }
            if (checkboxResCarnesRojas.isSelected()) {
                restricciones[3] = true;
            }
            if (checkboxResOtros.isSelected()) {
                restricciones[4] = true;
            }

            usuario.setNombres(nombre);
            usuario.setApellidos(apellidos);
            usuario.setNacionalidad(nacionalidad);
            usuario.setNacimiento(nacimiento);
            usuario.setDescripcion(descripcion);
            usuario.setPreferencias(preferencias);
            usuario.setRestricciones(restricciones);
            usuario.setAvatar(lbAvatar.getIcon());
            lbAvatarInicSecUsuario.setIcon(lbAvatar.getIcon());

            limpiarFormUsuario();
            cargarDatosUsuario();
        }
    }//GEN-LAST:event_btnEditarUsuarioActionPerformed

    private void btnEditarProfesionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProfesionalActionPerformed
        if (validarFormProfesional()) {

            String nombres = txtNombreProf.getText();
            String apellidos = txtApellidoProf.getText();
            String titulo = txtTitulo.getText();
            String paisObtencionTitulo = boxPaisTitProf.getSelectedItem() + "";
            Date fechaNacimiento = dcNacimientoProf.getDate();
            Date fechaGraduacion = dcGraduacionProf.getDate();

            profesional.setNombres(nombres);
            profesional.setApellidos(apellidos);
            profesional.setTitulo(titulo);
            profesional.setPaisObtencionTitulo(paisObtencionTitulo);
            profesional.setNacimiento(fechaNacimiento);
            profesional.setGraduacion(fechaGraduacion);
            profesional.setAvatar(lbAvatarProf.getIcon());
            lbAvatarInicSecProfesional.setIcon(lbAvatar.getIcon());

            limpiarFormProfesional();
            cargarDatosProfesional();
        }

    }//GEN-LAST:event_btnEditarProfesionalActionPerformed

    private void lbMostrarNombreUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbMostrarNombreUsuarioMouseClicked

    }//GEN-LAST:event_lbMostrarNombreUsuarioMouseClicked

    private void tablaConsultasRecibidasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaConsultasRecibidasMouseClicked
        int fila = tablaConsultasRecibidas.getSelectedRow();
        txtDescripcionUsuarioConsultaRecibidaProf.setText(profesional.getListaConsultas().get(fila).getDescripcion());

        if (profesional.getListaConsultas().get(fila).isEstado()) {
            txtResponderUsuarioConsultaRecibidaProf.setEditable(false);
        } else {
            txtResponderUsuarioConsultaRecibidaProf.setEditable(true);
        }
    }//GEN-LAST:event_tablaConsultasRecibidasMouseClicked

    private void tablaConsultasUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaConsultasUsuarioMouseClicked
        ArrayList<ParProfesionalConsulta> todasLasConsultas = sistema.todasConsultasDeUnUsuario(usuario + "");
        int fila = tablaConsultasUsuario.getSelectedRow();
        txtPreguntaUsuarioConsultaUsuario.setText(todasLasConsultas.get(fila).getConsulta().getDescripcion());
        txtRespuestaUsuarioConsultaUsuario.setText(todasLasConsultas.get(fila).getConsulta().getRespuesta());
    }//GEN-LAST:event_tablaConsultasUsuarioMouseClicked

    public void limpiarDetallesUsuario() {
        txtAmpilarInfoNombrePlanAlimProf.setText("");
        txtAmpilarInfoDescripcionPlanAlimProf.setText("");
        txtAmpilarInfoPreferenciasPlanAlimProf.setText("");
        txtAmpilarInfoRestriccionesPlanAlimProf.setText("");
        txtAmpilarInfoDetallesPlanAlimProf.setText("");

        CardLayout cl = (CardLayout) panelAmpliarDatosPlanAlimentacion.getLayout();
        cl.show(panelAmpliarDatosPlanAlimentacion, "panelTablaPlanesAlimentacionSolicitados");
    }

    private void btnVolverATablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverATablaActionPerformed
        limpiarDetallesUsuario();

    }//GEN-LAST:event_btnVolverATablaActionPerformed

    private void txtAmpilarInfoNombrePlanAlimProfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAmpilarInfoNombrePlanAlimProfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAmpilarInfoNombrePlanAlimProfActionPerformed

    public String obtenerStringDePreferencias(Usuario usuario) {
        String retorno = "";

        if (usuario.getPreferencias()[0]) {
            retorno += "Carnes \n";
        }

        if (usuario.getPreferencias()[1]) {
            retorno += "Lacteos \n";
        }

        if (usuario.getPreferencias()[2]) {
            retorno += "Frutas \n";
        }

        if (usuario.getPreferencias()[3]) {
            retorno += "Verduras \n";
        }

        if (usuario.getPreferencias()[4]) {
            retorno += "Otros \n";
        }

        return retorno;
    }

    public String obtenerStringDeRestricciones(Usuario usuario) {
        String retorno = "";

        if (usuario.getRestricciones()[0]) {
            retorno += "Salado \n";
        }

        if (usuario.getRestricciones()[1]) {
            retorno += "Dulce \n";
        }

        if (usuario.getRestricciones()[2]) {
            retorno += "Lacteos \n";
        }

        if (usuario.getRestricciones()[3]) {
            retorno += "Carnes Rojas\n";
        }

        if (usuario.getRestricciones()[4]) {
            retorno += "Otros \n";
        }

        return retorno;
    }

    public String obtenerStringAlimentosIngeridos(Usuario usuario) {
        String retorno = "";

        for (int i = 0; i < usuario.getAlimentosIngeridos().size(); i++) {
            retorno += usuario.getAlimentosIngeridos().get(i) + "\n";
        }

        return retorno;
    }

    private void btnAmpliarInformacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmpliarInformacionActionPerformed
        CardLayout cl = (CardLayout) panelAmpliarDatosPlanAlimentacion.getLayout();
        cl.show(panelAmpliarDatosPlanAlimentacion, "panelAmpliarDatosUsuario");

        int filaSeleccionada = tablaSolicitudDePlanesProfesional.getSelectedRow();

        if (filaSeleccionada != -1) {
            PlanAlimentacion planAlimentacion = profesional.getListaSolicitudesDePlanes().get(filaSeleccionada);

            txtAmpilarInfoNombrePlanAlimProf.setText(planAlimentacion.getUsuario() + "");
            txtAmpilarInfoDescripcionPlanAlimProf.setText(planAlimentacion.getUsuario().getDescripcion());
            txtAmpilarInfoPreferenciasPlanAlimProf.setText(obtenerStringDePreferencias(planAlimentacion.getUsuario()));
            txtAmpilarInfoRestriccionesPlanAlimProf.setText(obtenerStringDeRestricciones(planAlimentacion.getUsuario()));
            txtAmpilarInfoDetallesPlanAlimProf.setText(planAlimentacion.getDetalle());
            txtAmpilarInfoAliIngPlanAlimProf.setText(obtenerStringAlimentosIngeridos(planAlimentacion.getUsuario()));
        }
    }//GEN-LAST:event_btnAmpliarInformacionActionPerformed

    public void limpiarTablaSolicitudDePlanesPrfesional() {
        tablaIdearPlanDePlanesProfesional.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {"Desayuno", null, null, null, null, null, null, null},
                    {"Almuerzo", null, null, null, null, null, null, null},
                    {"Merienda", null, null, null, null, null, null, null},
                    {"Cena", null, null, null, null, null, null, null}
                },
                new String[]{
                    "", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sabado", "Domingo"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
    }

    private void btnEnviarPlanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarPlanActionPerformed
        int filaSeleccionada = tablaSolicitudDePlanesProfesional.getSelectedRow();

        if (filaSeleccionada != -1) {
            profesional.getListaSolicitudesDePlanes().get(filaSeleccionada).setEstado(true);
            mostrarTablaSolicitudDePlanesProfesional(tablaSolicitudDePlanesProfesional);
            limpiarTablaSolicitudDePlanesPrfesional();

        }
    }//GEN-LAST:event_btnEnviarPlanActionPerformed

    private void tablaSolicitudDePlanesProfesionalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaSolicitudDePlanesProfesionalMouseClicked

    }//GEN-LAST:event_tablaSolicitudDePlanesProfesionalMouseClicked

    private void txtNombreAlimentoPlanAliProfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreAlimentoPlanAliProfKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreAlimentoPlanAliProfKeyTyped

    public void agregarComidaAlPlan(int posProfesional, int tipoComida, int dia, String alimento) {
        if (tipoComida == 1) {
            profesional.getListaSolicitudesDePlanes().get(posProfesional).getDesayuno()[dia] = alimento;

        }
        if (tipoComida == 2) {
            profesional.getListaSolicitudesDePlanes().get(posProfesional).getAlmuerzo()[dia] = alimento;

        }
        if (tipoComida == 3) {
            profesional.getListaSolicitudesDePlanes().get(posProfesional).getMerienda()[dia] = alimento;

        }
        if (tipoComida == 4) {
            profesional.getListaSolicitudesDePlanes().get(posProfesional).getCena()[dia] = alimento;

        }
    }

    public boolean validarIngresoIndearPlan() {
        boolean esValido = false;

        if (validarBoxDistintoSeleccione(boxDiasSemanaPlanAliProf.getSelectedItem() + "")) {
            lbCheckProfBienAgrDiaPlanAli.setVisible(true);
            lbCheckProfErrorAgrDiaPlanAli.setVisible(false);
            esValido = true;
        } else {
            lbCheckProfBienAgrDiaPlanAli.setVisible(false);
            lbCheckProfErrorAgrDiaPlanAli.setVisible(true);
        }

        if (validarBoxDistintoSeleccione(boxComidaPlanAliProf.getSelectedItem() + "")) {
            lbCheckProfBienAgrComidaPlanAli.setVisible(true);
            lbCheckProfErrorAgrComidaPlanAli.setVisible(false);
            esValido &= true;
        } else {
            lbCheckProfBienAgrComidaPlanAli.setVisible(false);
            lbCheckProfErrorAgrComidaPlanAli.setVisible(true);
            esValido = false;
        }

        if (validarCampoTxtNoEsVacio(txtNombreAlimentoPlanAliProf.getText())) {
            lbCheckProfErrorNombrePlanAli.setVisible(false);
            lbCheckProfBienNombrePlanAli.setVisible(true);
            esValido &= true;
        } else {
            lbCheckProfBienNombrePlanAli.setVisible(false);
            lbCheckProfErrorNombrePlanAli.setVisible(true);
            esValido = false;
        }

        return esValido;
    }

    public void limpiarFormPlanAlimentacion() {
        boxDiasSemanaPlanAliProf.setSelectedItem("Seleccione...");
        boxComidaPlanAliProf.setSelectedItem("Seleccione...");
        txtNombreAlimentoPlanAliProf.setText("");

        lbCheckProfBienAgrDiaPlanAli.setVisible(false);
        lbCheckProfErrorAgrDiaPlanAli.setVisible(false);
        lbCheckProfBienAgrComidaPlanAli.setVisible(false);
        lbCheckProfErrorAgrComidaPlanAli.setVisible(false);
        lbCheckProfErrorNombrePlanAli.setVisible(false);
        lbCheckProfBienNombrePlanAli.setVisible(false);
        lbIdearPlanErrorUsuNoSelec.setVisible(false);

        lbIdearPlanErrorUsuNoYaEnv.setVisible(false);
    }

    private void btnAniadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAniadirActionPerformed

        if (validarIngresoIndearPlan()) {
            int posProfesional = tablaSolicitudDePlanesProfesional.getSelectedRow();
            if (posProfesional != -1) {
                if (!profesional.getListaSolicitudesDePlanes().get(posProfesional).isEstado()) {
                    lbIdearPlanErrorUsuNoYaEnv.setVisible(false);

                    int dia = boxDiasSemanaPlanAliProf.getSelectedIndex();
                    int tipoComida = boxComidaPlanAliProf.getSelectedIndex();
                    String alimento = txtNombreAlimentoPlanAliProf.getText();

                    agregarComidaAlPlan(posProfesional, tipoComida, dia, alimento);
                    mostrarTablaPlanAlimentacion(posProfesional);
                    limpiarFormPlanAlimentacion();
                } else {
                    lbIdearPlanErrorUsuNoYaEnv.setVisible(true);
                }
            } else {
                lbIdearPlanErrorUsuNoSelec.setVisible(true);
            }
        }
    }//GEN-LAST:event_btnAniadirActionPerformed

    public void cargarFila(String matriz[][], int fila, int posProfesional) {
        if (fila == 0) {
            for (int i = 1; i < 8; i++) {
                matriz[fila][i] = profesional.getListaSolicitudesDePlanes().get(posProfesional).getDesayuno()[i];
            }
        }
        if (fila == 1) {
            for (int i = 1; i < 8; i++) {
                matriz[fila][i] = profesional.getListaSolicitudesDePlanes().get(posProfesional).getAlmuerzo()[i];
            }
        }
        if (fila == 2) {
            for (int i = 1; i < 8; i++) {
                matriz[fila][i] = profesional.getListaSolicitudesDePlanes().get(posProfesional).getMerienda()[i];
            }
        }
        if (fila == 3) {
            for (int i = 1; i < 8; i++) {
                matriz[fila][i] = profesional.getListaSolicitudesDePlanes().get(posProfesional).getCena()[i];
            }
        }
    }

    public void mostrarTablaPlanAlimentacion(int posProfesional) {

        String matrizDatos[][] = new String[4][8];

        for (int fila = 0; fila < 4; fila++) {
            cargarFila(matrizDatos, fila, posProfesional);
        }

        matrizDatos[0][0] = "Desayuno";
        matrizDatos[1][0] = "Almuerzo";
        matrizDatos[2][0] = "Merienda";
        matrizDatos[3][0] = "Cena";

        tablaIdearPlanDePlanesProfesional.setModel(new javax.swing.table.DefaultTableModel(
                matrizDatos,
                new String[]{
                    "", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"
                }
        ) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        });
    }

    private void boxComidaPlanAliProfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxComidaPlanAliProfActionPerformed

    }//GEN-LAST:event_boxComidaPlanAliProfActionPerformed

    private void boxDiasSemanaPlanAliProfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxDiasSemanaPlanAliProfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boxDiasSemanaPlanAliProfActionPerformed

    private void pBVolverInicioUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pBVolverInicioUsuarioMouseClicked
        CardLayout panelContenidoUsuario = (CardLayout) panelContenido.getLayout();
        panelContenidoUsuario.show(panelContenido, "panelInicioUsuario");

        cargarVentanaPrincipalUsuario();
    }//GEN-LAST:event_pBVolverInicioUsuarioMouseClicked

    private void boxRolInicSecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxRolInicSecActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boxRolInicSecActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new GUI().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PAlimentosIngeridosUsuario;
    private javax.swing.JPanel PAmpliarDatosUsuarioPlanesProfesional;
    private javax.swing.JPanel PConsultaProfUsuario;
    private javax.swing.JPanel PConsultasRecibidasProfesional;
    private javax.swing.JPanel PInicSec;
    private javax.swing.JPanel PInicio;
    private javax.swing.JPanel PInicioProfesional;
    private javax.swing.JPanel PInicioUsuario;
    private javax.swing.JPanel PPlanAlimentacionProfesional;
    private javax.swing.JPanel PRegAlimentosProfesional;
    private javax.swing.JPanel PRegProfesional;
    private javax.swing.JPanel PRegUsuario;
    private javax.swing.JPanel PSolicitarPlanAlimentacionUsuario;
    private javax.swing.JPanel PTablaPlanesAlimentacionSolicitados;
    private javax.swing.JComboBox<String> boxAlimentoConsumidoUsuario;
    private javax.swing.JLabel boxCheckNacionalidadBienUsuario;
    private javax.swing.JLabel boxCheckNacionalidadErrorUsuario;
    private javax.swing.JComboBox<String> boxComidaPlanAliProf;
    private javax.swing.JComboBox<String> boxDiasSemanaPlanAliProf;
    private javax.swing.JComboBox<String> boxNacionalidadUsuario;
    private javax.swing.JComboBox<String> boxNombreInicSec;
    private javax.swing.JComboBox<String> boxNombreProfesional;
    private javax.swing.JComboBox<String> boxNombreProfesionalSolicitud;
    private javax.swing.JComboBox<String> boxPaisTitProf;
    private javax.swing.JComboBox<String> boxRolInicSec;
    private javax.swing.JButton btnAgregarAlimentoUsuario;
    private javax.swing.JButton btnAmpliarInformacion;
    private javax.swing.JButton btnAniadir;
    private javax.swing.JButton btnBorrarAlimentoUsuario;
    private javax.swing.JButton btnEditarProfesional;
    private javax.swing.JButton btnEditarUsuario;
    private javax.swing.JButton btnEnviarPlan;
    private javax.swing.JButton btnEnviarPlan2;
    private javax.swing.JButton btnGuardarUsuario1;
    private javax.swing.JButton btnInicSec;
    private javax.swing.JButton btnRegistrarProfesional;
    private javax.swing.JButton btnRegistrarUsuario;
    private javax.swing.JButton btnResponderConsulta;
    private javax.swing.JButton btnSolicitarPlan;
    private javax.swing.JButton btnVolverATabla;
    private javax.swing.JCheckBox checkBoxNutAntioxidante;
    private javax.swing.JCheckBox checkBoxNutCarbohidratos;
    private javax.swing.JCheckBox checkBoxNutMinerales;
    private javax.swing.JCheckBox checkBoxNutProteinas;
    private javax.swing.JCheckBox checkBoxNutVitaminas;
    private javax.swing.JCheckBox checkboxPrefCarnes;
    private javax.swing.JCheckBox checkboxPrefFrutas;
    private javax.swing.JCheckBox checkboxPrefLacteos;
    private javax.swing.JCheckBox checkboxPrefOtros;
    private javax.swing.JCheckBox checkboxPrefVerduras;
    private javax.swing.JCheckBox checkboxResCarnesRojas;
    private javax.swing.JCheckBox checkboxResDulce;
    private javax.swing.JCheckBox checkboxResLacteos;
    private javax.swing.JCheckBox checkboxResOtros;
    private javax.swing.JCheckBox checkboxResSalado;
    private com.toedter.calendar.JDateChooser dcAlimentoConsumidoUsuario;
    private com.toedter.calendar.JDateChooser dcGraduacionProf;
    private com.toedter.calendar.JDateChooser dcNacimientoProf;
    private com.toedter.calendar.JDateChooser dcNacimientoUsuario;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable3;
    private javax.swing.JLabel lbAlimentosIngeridos;
    private javax.swing.JLabel lbAlturaPlanAlimentacionError;
    private javax.swing.JLabel lbApellido;
    private javax.swing.JLabel lbApellidoProf;
    private javax.swing.JLabel lbAvatar;
    private javax.swing.JLabel lbAvatarAlimento;
    private javax.swing.JLabel lbAvatarInicSecProfesional;
    private javax.swing.JLabel lbAvatarInicSecUsuario;
    private javax.swing.JLabel lbAvatarProf;
    private javax.swing.JLabel lbCerrar;
    private javax.swing.JLabel lbCheckApellidoBienProf;
    private javax.swing.JLabel lbCheckApellidoBienUsuario;
    private javax.swing.JLabel lbCheckApellidoErrorProf;
    private javax.swing.JLabel lbCheckApellidoErrorUsuario;
    private javax.swing.JLabel lbCheckBoxAlturaErrorSolicitud;
    private javax.swing.JLabel lbCheckBoxFechaAlimentoConsumidoBienUsuario;
    private javax.swing.JLabel lbCheckBoxFechaAlimentoConsumidoErrorUsuario;
    private javax.swing.JLabel lbCheckBoxHorasDeActividadErrorSolicitud;
    private javax.swing.JLabel lbCheckBoxNombreAlimentoConsumidoBienUsuario;
    private javax.swing.JLabel lbCheckBoxNombreAlimentoConsumidoErrorUsuario;
    private javax.swing.JLabel lbCheckBoxPesoErrorSolicitud;
    private javax.swing.JLabel lbCheckBoxProfesionalErrorSolicitud;
    private javax.swing.JLabel lbCheckDchNacimientoBienUsuario;
    private javax.swing.JLabel lbCheckDchNacimientoErrorUsuario;
    private javax.swing.JLabel lbCheckGraduacionBienProf;
    private javax.swing.JLabel lbCheckGraduacionErrorProf;
    private javax.swing.JLabel lbCheckGraduacionFechaDesfasadaProf;
    private javax.swing.JLabel lbCheckNacimientoBienProf;
    private javax.swing.JLabel lbCheckNacimientoErrorProf;
    private javax.swing.JLabel lbCheckNacimientoMenorEdadProf;
    private javax.swing.JLabel lbCheckNombreBienAlimentos;
    private javax.swing.JLabel lbCheckNombreBienProf;
    private javax.swing.JLabel lbCheckNombreBienUsuario;
    private javax.swing.JLabel lbCheckNombreErrorAlimentos;
    private javax.swing.JLabel lbCheckNombreErrorProf;
    private javax.swing.JLabel lbCheckNombreErrorUsuario;
    private javax.swing.JLabel lbCheckNutAntioxidanteBien;
    private javax.swing.JLabel lbCheckNutAntioxidanteError;
    private javax.swing.JLabel lbCheckNutCarbohidratosBien;
    private javax.swing.JLabel lbCheckNutCarbohidratosError;
    private javax.swing.JLabel lbCheckNutMineralesBien;
    private javax.swing.JLabel lbCheckNutMineralesError;
    private javax.swing.JLabel lbCheckNutProteinasBien;
    private javax.swing.JLabel lbCheckNutProteinasError;
    private javax.swing.JLabel lbCheckNutVitaminasBien;
    private javax.swing.JLabel lbCheckNutVitaminasError;
    private javax.swing.JLabel lbCheckPaisTituloBienProf;
    private javax.swing.JLabel lbCheckPaisTituloErrorProf;
    private javax.swing.JLabel lbCheckProfBienAgrComidaPlanAli;
    private javax.swing.JLabel lbCheckProfBienAgrDiaPlanAli;
    private javax.swing.JLabel lbCheckProfBienConsProfUsuario;
    private javax.swing.JLabel lbCheckProfBienNombrePlanAli;
    private javax.swing.JLabel lbCheckProfErrorAgrComidaPlanAli;
    private javax.swing.JLabel lbCheckProfErrorAgrDiaPlanAli;
    private javax.swing.JLabel lbCheckProfErrorConsProfUsuario;
    private javax.swing.JLabel lbCheckProfErrorNombrePlanAli;
    private javax.swing.JLabel lbCheckRegistroUsuarioExitoso;
    private javax.swing.JLabel lbCheckRegistroUsuarioRepetido;
    private javax.swing.JLabel lbCheckTituloProfesionaBienlProf;
    private javax.swing.JLabel lbCheckTituloProfesionaErrorProf;
    private javax.swing.JLabel lbCkeckBoxAlturaBienSolicitud;
    private javax.swing.JLabel lbCkeckBoxHorasDeActividadBienSolicitud;
    private javax.swing.JLabel lbCkeckBoxPesoBienSolicitud;
    private javax.swing.JLabel lbCkeckBoxProfesionalBienSolicitud;
    private javax.swing.JLabel lbConsultas1;
    private javax.swing.JLabel lbDescripcion;
    private javax.swing.JLabel lbDescripcionError;
    private javax.swing.JLabel lbDetallesPlanAlimentacionError;
    private javax.swing.JLabel lbFechaAlimentoIngerido;
    private javax.swing.JLabel lbFechaAlimentoIngerido1;
    private javax.swing.JLabel lbFechaGraduacionProf1;
    private javax.swing.JLabel lbHorasActFisicaPlanAlimentacionError;
    private javax.swing.JLabel lbIdearPlanErrorUsuNoSelec;
    private javax.swing.JLabel lbIdearPlanErrorUsuNoYaEnv;
    private javax.swing.JLabel lbLogoPrincipal;
    private javax.swing.JLabel lbLogoRegAlimentos1;
    private javax.swing.JLabel lbLogoRegAlimentos2;
    private javax.swing.JLabel lbLogoRegAlimentos3;
    private javax.swing.JLabel lbLogoRegAlimentos4;
    private javax.swing.JLabel lbLogoRegAlimentosIngeridos;
    private javax.swing.JLabel lbLogoRegConsultaProf;
    private javax.swing.JLabel lbLogoRegPlanAlimentacion;
    private javax.swing.JLabel lbLogoRegPlanAlimentacion1;
    private javax.swing.JLabel lbLogoRegProf;
    private javax.swing.JLabel lbLogoRegUsuario;
    private javax.swing.JLabel lbMinimizar;
    private javax.swing.JLabel lbMostrarNombreProfesional;
    private javax.swing.JLabel lbMostrarNombreUsuario;
    private javax.swing.JLabel lbMotivoError;
    private javax.swing.JLabel lbNacimiento;
    private javax.swing.JLabel lbNacimientoProf;
    private javax.swing.JLabel lbNacionalidad;
    private javax.swing.JLabel lbNombre;
    private javax.swing.JLabel lbNombre1;
    private javax.swing.JLabel lbNombre10;
    private javax.swing.JLabel lbNombre11;
    private javax.swing.JLabel lbNombre12;
    private javax.swing.JLabel lbNombre13;
    private javax.swing.JLabel lbNombre14;
    private javax.swing.JLabel lbNombre15;
    private javax.swing.JLabel lbNombre16;
    private javax.swing.JLabel lbNombre2;
    private javax.swing.JLabel lbNombre3;
    private javax.swing.JLabel lbNombre4;
    private javax.swing.JLabel lbNombre5;
    private javax.swing.JLabel lbNombre7;
    private javax.swing.JLabel lbNombre8;
    private javax.swing.JLabel lbNombre9;
    private javax.swing.JLabel lbNombreAlimentoIngerido1;
    private javax.swing.JLabel lbNombreAlimentos1;
    private javax.swing.JLabel lbNombreAlimentos2;
    private javax.swing.JLabel lbNombreAlimentos3;
    private javax.swing.JLabel lbNombreAlimentos4;
    private javax.swing.JLabel lbNombreInicSecError;
    private javax.swing.JLabel lbNombreProf;
    private javax.swing.JLabel lbPesoPlanAlimentacionError;
    private javax.swing.JLabel lbPlanAlimentacionUsuario;
    private javax.swing.JLabel lbPlanAlimentacionUsuario1;
    private javax.swing.JLabel lbPlanAlimentacionUsuario2;
    private javax.swing.JLabel lbPlanAlimentacionUsuario3;
    private javax.swing.JLabel lbPlanAlimentacionUsuario4;
    private javax.swing.JLabel lbPlanAlimentacionUsuario5;
    private javax.swing.JLabel lbPreferencias;
    private javax.swing.JLabel lbRegAlimentos1;
    private javax.swing.JLabel lbRegAlimentos2;
    private javax.swing.JLabel lbRegAlimentos3;
    private javax.swing.JLabel lbRegAlimentos4;
    private javax.swing.JLabel lbRegAlimentosIngeridos;
    private javax.swing.JLabel lbRegConsultaProf;
    private javax.swing.JLabel lbRegPlanAlimentacion;
    private javax.swing.JLabel lbRegPlanAlimentacion1;
    private javax.swing.JLabel lbRegProf;
    private javax.swing.JLabel lbRegUsuario;
    private javax.swing.JLabel lbRegistroAlimento;
    private javax.swing.JLabel lbRegistroProfesional;
    private javax.swing.JLabel lbRegistroUsuario;
    private javax.swing.JLabel lbRegistroUsuario1;
    private javax.swing.JLabel lbRegistroUsuario2;
    private javax.swing.JLabel lbRegistroUsuario3;
    private javax.swing.JLabel lbRegistroUsuario4;
    private javax.swing.JLabel lbResponder;
    private javax.swing.JLabel lbResponder1;
    private javax.swing.JLabel lbResponderConsultasRecibidasError;
    private javax.swing.JLabel lbRestricciones;
    private javax.swing.JLabel lbRol;
    private javax.swing.JLabel lbRol1;
    private javax.swing.JLabel lbRolError;
    private javax.swing.JLabel lbTipo;
    private javax.swing.JLabel lbTipoError;
    private javax.swing.JLabel lbTituloProf;
    private javax.swing.JLabel lbTituloProf1;
    private javax.swing.JLabel lbYaEstoyReg;
    private javax.swing.JLabel lbYaEstoyReg1;
    private javax.swing.JLabel lbYaEstoyReg2;
    private javax.swing.JPanel pBConsultasRecibidasProf;
    private javax.swing.JPanel pBEditarDatosProf;
    private javax.swing.JPanel pBEditarDatosUsuario;
    private javax.swing.JPanel pBPlanAlimentacionProf;
    private javax.swing.JPanel pBRegAlimentosIngeridosUsuario;
    private javax.swing.JPanel pBRegAlimentosProf;
    private javax.swing.JPanel pBRegConsultaProfUsuario;
    private javax.swing.JPanel pBRegPlanAlimentacionUsuario;
    private javax.swing.JPanel pBRegProfesional;
    private javax.swing.JPanel pBRegUsuario;
    private javax.swing.JPanel pBSalirProf;
    private javax.swing.JPanel pBVolverInicioProf;
    private javax.swing.JPanel pBVolverInicioUsuario;
    private javax.swing.JPanel pBYaEstoyReg;
    private javax.swing.JPanel pBYaSalirUsuario;
    private javax.swing.JPanel panelAmpliarDatosPlanAlimentacion;
    private javax.swing.JPanel panelBarraSuperiorVentana;
    private javax.swing.JPanel panelBotonesInicial;
    private javax.swing.JPanel panelBotonesProfesional;
    private javax.swing.JPanel panelBotonesUsuario;
    private javax.swing.JPanel panelContenido;
    private javax.swing.JPanel panelLateral;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JRadioButton rBtnCereal;
    private javax.swing.JRadioButton rBtnFruta;
    private javax.swing.JRadioButton rBtnLegumbre;
    private javax.swing.JRadioButton rBtnOtros;
    private javax.swing.JRadioButton rbtnAliIgerir;
    private javax.swing.JRadioButton rbtnAliIng;
    private javax.swing.JRadioButton rbtnAliOtros;
    private javax.swing.ButtonGroup rbtnGrupConsultaProfMotivo;
    private javax.swing.ButtonGroup rbtnGrupRegAlimentos;
    private javax.swing.JTable tablaAlimentosIngeridosUsuario;
    private javax.swing.JTable tablaAlimentosIngeridosUsuarioInicio;
    private javax.swing.JTable tablaConsultasAProfesionalesUsuarioInicio;
    private javax.swing.JTable tablaConsultasRecibidas;
    private javax.swing.JTable tablaConsultasUsuario;
    private javax.swing.JTable tablaIdearPlanDePlanesProfesional;
    private javax.swing.JTable tablaInicioUltimasConsutasProf;
    private javax.swing.JTable tablaInicioUltimosPlanesProf;
    private javax.swing.JTable tablaPlanAlimentacionUsuario;
    private javax.swing.JTable tablaSolicitudDePlanesProfesional;
    private javax.swing.JTextField txtAlturaSolicitud;
    private javax.swing.JTextArea txtAmpilarInfoAliIngPlanAlimProf;
    private javax.swing.JTextArea txtAmpilarInfoDescripcionPlanAlimProf;
    private javax.swing.JTextArea txtAmpilarInfoDetallesPlanAlimProf;
    private javax.swing.JTextField txtAmpilarInfoNombrePlanAlimProf;
    private javax.swing.JTextArea txtAmpilarInfoPreferenciasPlanAlimProf;
    private javax.swing.JTextArea txtAmpilarInfoRestriccionesPlanAlimProf;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtApellidoProf;
    private javax.swing.JTextArea txtDescripcionConsultaProf;
    private javax.swing.JTextArea txtDescripcionUsuario;
    private javax.swing.JTextArea txtDescripcionUsuarioConsultaRecibidaProf;
    private javax.swing.JTextArea txtDetallesSolicitud;
    private javax.swing.JTextField txtHorasDeActividadSolicitud;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreAlimentoPlanAliProf;
    private javax.swing.JTextField txtNombreAlimentos;
    private javax.swing.JTextField txtNombreProf;
    private javax.swing.JTextField txtPesoSolicitud;
    private javax.swing.JTextArea txtPreguntaUsuarioConsultaUsuario;
    private javax.swing.JTextField txtProporcionAntioxidante;
    private javax.swing.JTextField txtProporcionCarbohidratos;
    private javax.swing.JTextField txtProporcionMinerales;
    private javax.swing.JTextField txtProporcionProteinas;
    private javax.swing.JTextField txtProporcionVitaminas;
    private javax.swing.JTextArea txtResponderUsuarioConsultaRecibidaProf;
    private javax.swing.JTextArea txtRespuestaUsuarioConsultaUsuario;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables

    private String[] diasSemana = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo",};
    private String[] comidas = {"Desayuno", "Almuerzo", "Merienda", "Cena"};
    private String[] nacionalidad = {"Afghanistan", "Albania", "Algeria", "Andorra", "Angola",
        "Antigua and Barbuda", "Argentina", "Armenia", "Australia",
        "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh",
        "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan",
        "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil",
        "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia",
        "Cameroon", "Canada", "Cape Verde", "Central African Republic",
        "Chad", "Chile", "China", "Colombia", "Comoros", "Congo (Brazzaville)",
        "Congo", "Costa Rica", "Cote d'Ivoire", "Croatia", "Cuba", "Cyprus",
        "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic",
        "East Timor (Timor Timur)", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea",
        "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France", "Gabon",
        "Gambia, The", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea",
        "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia",
        "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan",
        "Kenya", "Kiribati", "Korea, North", "Korea, South", "Kuwait", "Kyrgyzstan",
        "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania",
        "Luxembourg", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali",
        "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova",
        "Monaco", "Mongolia", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepa",
        "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Norway", "Oman", "Pakistan", "Palau",
        "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar",
        "Romania", "Russia", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent", "Samoa",
        "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia and Montenegro", "Seychelles",
        "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa",
        "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan",
        "Tajikistan", "Tanzania", "Thailand", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey",
        "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States",
        "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"};

    CustomFont cf = new CustomFont();

}
