package Entidades;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class DiscoVirtual {
    private static DiscoVirtual single_instance = null;
    
    private String nomArchivo = "DiscoVirtual.txt";
    private Directorio raiz;
    private int cantSectores;
    private int tamSectores;

 

    private DiscoVirtual(int cantSectores, int tamSectores, String nombre){
        this.cantSectores = cantSectores;
        this.tamSectores = tamSectores;
    	this.raiz = new Directorio(nombre);
    	
    	crearArchivo(cantSectores);
    }
 
    // static method to create instance of Singleton class
    public static DiscoVirtual getInstance(int cantSec, int tamSec, String nombre)
    {
        if (single_instance == null)
            single_instance = new DiscoVirtual(cantSec, tamSec, nombre);
        return single_instance;
    }
    
    public static boolean isInstanceNull(){
        if (single_instance == null)
            return true;
        return false;
    }


    public Directorio getRaiz() {
        return raiz;
    }

    public void setRaiz(Directorio raiz) {
        this.raiz = raiz;
    }
    
 
 


    private void crearArchivo(int sec){
    	try {
        //Crear un objeto File se encarga de crear o abrir acceso a un archivo que se especifica en su constructor
        File archivo = new File(nomArchivo);

        //Crear objeto FileWriter que sera el que nos ayude a escribir sobre archivo
        //FileWriter escribir = new FileWriter(archivo, false);
        FileOutputStream fos = new FileOutputStream(archivo);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        //Escribimos en el archivo con el metodo write
        for(int cont = 0; cont < sec; cont++){
        	bw.write("<Sector " + cont + ">");
        	bw.newLine();
        }
        
        //Cerramos la conexion
        bw.close();
        
        } catch(Exception e) {
        	System.out.println("Error al escribir");
        }
    }

}
