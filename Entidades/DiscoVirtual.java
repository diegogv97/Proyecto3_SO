package Entidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class DiscoVirtual {
    private static DiscoVirtual single_instance = null;
    
    File archivo;
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
    
    public String getNomArchivo() {
		return nomArchivo;
	}

	public void setNomArchivo(String nomArchivo) {
		this.nomArchivo = nomArchivo;
	}

	public int getCantSectores() {
		return cantSectores;
	}

	public void setCantSectores(int cantSectores) {
		this.cantSectores = cantSectores;
	}

	public int getTamSectores() {
		return tamSectores;
	}

	public void setTamSectores(int tamSectores) {
		this.tamSectores = tamSectores;
	}

	// Crea el archivo de texto con los sectores correspondientes para crear el espejo del File System
	private void crearArchivo(int sec){
    	//Crear un objeto File se encarga de crear o abrir acceso a un archivo que se especifica en su constructor
	    archivo = new File(nomArchivo);

	    String contenidoVacio = "";
        //Escribimos en el archivo con el metodo write
        for(int cont = 0; cont < sec; cont++){
        	contenidoVacio += "<----- Sector " + cont + " ----->\n";
        }
        
        escribirArchivo(contenidoVacio.split("\n"));
    }
	
	// Obtiene el contenido del archivo para el sector ingresado por parametro
	public String getSector(int sector){
		String contenidoSector = "";
		try
		{
			//Creamos un archivo FileReader que obtiene lo que tenga el archivo
			FileReader lector = new FileReader(nomArchivo);
	
			//El contenido de lector se guarda en un BufferedReader
			BufferedReader contenido=new BufferedReader(lector);
	
			String texto = "";
			
			boolean isSector = false;
			//Con el siguiente ciclo extraemos todo el contenido del objeto "contenido" y lo mostramos
			while((texto=contenido.readLine())!=null){
				if(!isSector){
					if(texto.equals("<----- Sector " + sector + " ----->")){
						isSector = true;
					}
				}else{
					if(texto.equals("<----- Sector " + (sector + 1) + " ----->")){
						break;
					}
					contenidoSector += texto;
				}
			}
			//System.out.println(contenidoSector);
		}catch(Exception e){
			System.out.println("Error al leer");
		}
		
		return contenidoSector;
	}
	
	// Recibe el contenido de un archivo y va a escribirlo al Disco Virtual (txt)...
	public int[] escribirSectores(String contenidoTotal, int[] sectores){
		int tamVectorSectores = sectores.length;
		int caracteresSector = tamSectores / 2;
		
		int secArchivo = contenidoTotal.length() / caracteresSector;
                if((contenidoTotal.length() % caracteresSector)!= 0){
                    secArchivo++;
                }
		
		int nSectores[] = new int[secArchivo];
		for (int contSec = 0; contSec < secArchivo; contSec++){
			int numSec = -1;
			if(tamVectorSectores >= (contSec + 1)){
				numSec = sectores[contSec];
			}
			
			String subStringSector = "";
			
			if(contSec == (secArchivo - 1)){
				subStringSector = contenidoTotal.substring(contSec * caracteresSector, contenidoTotal.length());             
			}else{
				subStringSector = contenidoTotal.substring(contSec * caracteresSector, ((contSec + 1) * caracteresSector));
			}
			nSectores[contSec] = escribirSector(subStringSector, numSec);
		}
		
		return nSectores;
	}
	
	// Escribe sobre un sector del archivo (False: no pudo escribir, True: se escribio correctamente)
	public int escribirSector(String contenidoSector, int sector){
		boolean hayEspacio = false;
		if(sector >= 0){
			hayEspacio = true;
		}else{
			sector = buscarSectorVacio();
			if(sector != -1){
				hayEspacio = true;
			}
		}
		
		if(hayEspacio){
			int sectorLeido = 0;
			String cont_anterior = "";
			String cont_posterior = "";
			while(sectorLeido < cantSectores){
				if(sectorLeido < sector){
					cont_anterior += "<----- Sector " + sectorLeido + " ----->\n";
					String conteAux = getSector(sectorLeido);
					cont_anterior += conteAux;
					if(!conteAux.isEmpty()){
						cont_anterior += "\n";
					}
				}else if(sectorLeido > sector){
					cont_posterior += "<----- Sector " + sectorLeido + " ----->\n";
					String conteAux = getSector(sectorLeido);
					cont_posterior += conteAux;
					if(!conteAux.isEmpty()){
						cont_posterior += "\n";
					}
				}
				sectorLeido++;
			}
			contenidoSector = "<----- Sector " + sector + " ----->\n" + contenidoSector + "\n";
			String[] contenido = (cont_anterior + contenidoSector + cont_posterior).split("\n");
			archivo = new File(nomArchivo);
			if(escribirArchivo(contenido)){
				return sector;
			}
			
		}
		
		return sector;
	}
	
	private boolean escribirArchivo(String[] contenido){
		try{
			FileOutputStream fos = new FileOutputStream(archivo);
			
	        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	
	        //Escribimos en el archivo con el metodo write
	        for(int cont = 0; cont < contenido.length; cont++){
	        	bw.write(contenido[cont]);
	        	bw.newLine();
	        }
	        //Cerramos la conexion
	        bw.close();
	        return true;
		}catch(Exception e){
			System.out.println("Error al escribir");
		}
		
		return false;
	}

	// Devuelve el numero de un sector vacio, si no hay espacio retorna -1
	public int buscarSectorVacio(){
		for(int sectorActual = 0; sectorActual < cantSectores; sectorActual++){
			String contenidoSector = getSector(sectorActual);
			if(contenidoSector.isEmpty()){
				return sectorActual;
			}
		}
		return -1;
	}

}