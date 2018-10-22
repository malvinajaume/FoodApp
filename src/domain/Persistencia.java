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

/**
 *
 * @author Malvina
 */
public class Persistencia {

    private final String ruta = System.getProperty("user.dir") + "/archivo.txt";

    public void leeDatos(String[] arg) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File("C:\\archivo.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void escribirDatos(Usuario usuario) {
        File archivo = new File(this.ruta);
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(archivo, true));
            bw.write(usuario.datosPersistir());
            bw.newLine();
            bw.close();
        } catch (IOException ex) {
            System.out.println("No se guardo el usuarrio "
                    + usuario.getApellidos() + " en el archivo");
        }
    }
}
