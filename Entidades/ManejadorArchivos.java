package Entidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;

public class ManejadorArchivos {
	
	public static boolean existeArchivoRuta(String ruta){
		File file = new File(ruta);
		
		if(file.exists()){
			return true;
		}
		
		return false;
	}
	
	public static String leerArchivoRuta(String ruta){
		String contenidoArchivo = "";
		try{
			//Creamos un archivo FileReader que obtiene lo que tenga el archivo
			FileReader lector = new FileReader(ruta);
	
			//El contenido de lector se guarda en un BufferedReader
			BufferedReader contenido=new BufferedReader(lector);
	
			String texto = "";
			
			//Con el siguiente ciclo extraemos todo el contenido del objeto "contenido" y lo mostramos
			while((texto=contenido.readLine())!=null){
				contenidoArchivo += texto;
			}
			//System.out.println(contenidoSector);
		}catch(Exception e){
			System.out.println("Error al leer");
		}
		
		return contenidoArchivo;
	}
	
	public static boolean escribirArchivo(String ruta, String contenido){
		try{
			FileOutputStream fos = new FileOutputStream(ruta);
			
	        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	
	        String[] content = contenido.split("\n");
	        //Escribimos en el archivo con el metodo write
	        for(int cont = 0; cont < content.length; cont++){
	        	bw.write(content[cont]);
	        	bw.newLine();
	        }
	        //Cerramos la conexion
	        bw.close();
	        return true;
		}catch(Exception e){
			System.out.println("Error al crear archivo real");
		}
		
		return false;
	}
	
}
