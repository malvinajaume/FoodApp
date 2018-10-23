/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author Malvina
 */
public class Persistencia {

    private final String ruta = System.getProperty("user.dir") + "/usuarios.txt";
    private final String dateFormat = "MM/dd/yyyy HH:mm:ss";
    
    public String datosPersistir(Usuario usuario) {
        //carnes lacteos frutas verduras otros
        String strPreferencias = "";
        for(int i=0; i < usuario.getPreferencias().length; i++) {
            strPreferencias = strPreferencias.concat(String.valueOf(usuario.getPreferencias()[i] ? 1 : 0));
        }
        //salado dulce lacteos carnes rojas otros
        String strRestricciones = "";
        for (int i=0; i < usuario.getPreferencias().length; i++) {
            strRestricciones = strRestricciones.concat(String.valueOf(usuario.getPreferencias()[i] ? 1 : 0));
        }
        DateFormat df = new SimpleDateFormat(dateFormat);
        
        return usuario.getApellidos() + ";" +
                usuario.getNombres() + ";" +
                usuario.getNacionalidad() + ";" +
                df.format(usuario.getNacimiento()) + ";" +
                strPreferencias + ";" +
                strRestricciones + ";" +
                usuario.getDescripcion().concat(" ") + ";";
    }

    public ArrayList<Usuario> leeDatosUsuarios() {
        String cadena;
        ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
        try {
            FileReader f = new FileReader(this.ruta);
            BufferedReader br = new BufferedReader(f);
            while ((cadena = br.readLine()) != null) {
                System.out.println(cadena);
                
                Usuario nuevoUsuario = new Usuario();
                String[] datos = cadena.split(";");
                nuevoUsuario.setApellidos(datos[0]);
                nuevoUsuario.setNombres(datos[1]);
                nuevoUsuario.setNacionalidad(datos[2]);
                nuevoUsuario.setNacimiento(new SimpleDateFormat(dateFormat).parse(datos[3]));
                
                boolean preferencias[] = new boolean[5];
                char[] c_arr = datos[4].toCharArray();
                for(int i=0; i < c_arr.length; i++) {
                    preferencias[i] = (c_arr[i] == '0') ? false : true; 
                }
                nuevoUsuario.setPreferencias(preferencias);
                
                boolean restricciones[] = new boolean[5];
                c_arr = datos[5].toCharArray();
                for(int i=0; i < c_arr.length; i++) {
                    restricciones[i] = (c_arr[i] == '0') ? false : true; 
                }
                nuevoUsuario.setRestricciones(restricciones);
                if(datos[6].equals(" ")){
                    nuevoUsuario.setDescripcion("");
                } else {
                    nuevoUsuario.setDescripcion(datos[6]);
                }
                listaUsuarios.add(nuevoUsuario);
            }
            br.close();
        } catch (IOException ex) {
            System.out.println("No se pudo leer el archivo. Mensaje: "
                    + ex.getMessage());
        } catch (ParseException p) {
            System.out.println(p.getMessage());
        }
        return listaUsuarios;
    }

    public void escribirDatosUsuario(Usuario usuario) {
        File archivo = new File(this.ruta);
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(archivo, true));
            bw.write(datosPersistir(usuario));
            bw.newLine();
            bw.close();
        } catch (IOException ex) {
            System.out.println("No se guardo el usuario "
                    + usuario.getApellidos() + " en el archivo");
        }
    }
}
