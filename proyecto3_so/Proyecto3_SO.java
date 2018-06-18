
package proyecto3_so;

import Entidades.Directorio;
import Entidades.DiscoVirtual;
import Entidades.Archivo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import java.awt.*;


public class Proyecto3_SO {
    public static void main(String[] args) {
        BufferedReader br = null;
        String input = "";
        DiscoVirtual disco_virtual = null;
        Directorio dir_actual = null;
        
        String dirRaiz = "";


        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            
            while (true) {
                System.out.println("Simulacion de File System");
                System.out.println("Realizado por: Alvaro Ramirez");
                System.out.println("               Diego Guzman");
                System.out.println("               Josue Morales\n\n");
                while(true){
                    System.out.print(dirRaiz + "/:  > ");
                    input = br.readLine();
                    String[] tokens = input.split(" ");
                    String comando = tokens[0];
                    switch (comando){
                        //CRT [# segmentos] [size segmento] [nombre]
                        case "CRT": 
                            if (contarParametros(tokens, 3+1) == false){
                                System.out.println("Parametros incorrectos");
                                break;
                            }
                            System.out.println("creando disco " +tokens[3]);
                            disco_virtual = DiscoVirtual.getInstance(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), tokens[3]);
                            dirRaiz = tokens[3];

                            //disco_virtual.getSector(2);
                            //disco_virtual.escribirSector("contenido\ndel\nSector", 1);
                            //disco_virtual.escribirSector("contenido\ndel\nSector cero (0)", -1);
                            //disco_virtual.escribirSector("contenido\ndel\nSector dos (2)", -1);
                            //int l[] = disco_virtual.escribirSectores("HOLA", new int[0]);
                            
                            dir_actual = disco_virtual.getRaiz();
                            break;
                        //FLE [nombre] [extencion]
                        case "FLE":
                            if (contarParametros(tokens, 2+1) == false){
                                System.out.println("Parametros incorrectos");
                                break;
                            }
                            Archivo archivoNuevo = new Archivo(tokens[1], tokens[2], (new Date()).toString(), 0);
                            if(dir_actual.existeArchivo(archivoNuevo.getNombre(), archivoNuevo.getExtension())){
                            	System.out.println("El nombre de archivo ya existe");
                            	break;
                            }
                            
                            System.out.print("Ingrese el contenido del archivo. Escriba EOF en la ultima linea para terminar:\n");
                            //input = br.readLine();
                            String aux = "";
                            String contenido = "";
                            while( !(aux = br.readLine()).equals("EOF")) {
                                contenido += "\n" + aux;
                             }
                            contenido = contenido.substring(1);
                            
                            archivoNuevo.setSize(contenido.length());
                            
                            if(archivoNuevo.escribirArchivo(contenido)){
                            	dir_actual.addArchivo(archivoNuevo);
                            }
                            
                            break;
                        //MKDIR [nombre]   
                        case "MKDIR":
                            if (contarParametros(tokens, 1) == true){
                                System.out.println("Parametros incorrectos");
                                break;
                            }
                            String nombre_directorio = input.substring("MKDIR ".length());
                            if(!dir_actual.existeDirectorio(nombre_directorio)){
                                System.out.println("Creando directorio " + nombre_directorio);
                                dir_actual.addDirectorio(new Directorio(nombre_directorio));
                            }
                            else{
                                 System.out.print("Directorio ya existe. Digite R para reemplazar, cualquier otro para finalizar la accion: ");
                                 input = br.readLine();
                                 if(input.equals("R")){
                                     System.out.println("Reemplazando directorio " + nombre_directorio);
                                     dir_actual.remplazarDirectorio(new Directorio(nombre_directorio));
                                 }
                            }
                            break;
                            
                        case "CHDIR":
                            if (contarParametros(tokens, 1) == true){
                                System.out.println("Parametros incorrectos");
                                break;
                            }
                            String ir_directorio = input.substring("CHDIR ".length());
                            
                            String[] tokens_dirs;
                            if (ir_directorio.equals("..")){
                                System.out.println("regresando al directorio anterior");
                                dir_actual = disco_virtual.getRaiz();
                                tokens_dirs= dirRaiz.split("/");
                                dirRaiz = disco_virtual.getRaiz().getNombre();
                                 for(int i = 0; true; i++){
                                    try {
                                        String botar = tokens_dirs[i+2];
                                        dir_actual = dir_actual.getDirectorio(tokens_dirs[i+1]);
                                        dirRaiz = dirRaiz + "/"+tokens_dirs[i+1];
                                    }catch (Exception ex) {
                                        break;
                                    }
                                    
                                } 
                            }
                            else{
                                System.out.println("yendo al directorio " + ir_directorio);
                                boolean func = true;
                                tokens_dirs = ir_directorio.split("/");
                                Directorio temp = dir_actual;
                                for(String s : tokens_dirs){
                                    try {
                                        dir_actual = dir_actual.getDirectorio(s);
                                        dirRaiz = dirRaiz + "/" + dir_actual.getNombre();
                                    } catch (Exception ex) {
                                        System.out.println("Directorio no existe");
                                        dir_actual = temp;
                                        func = false;
                                        break;
                                    }
                                }
                            }
                            break;
                            
                        case "LDIR":
                            if (contarParametros(tokens, 1) == false){
                                System.out.println("Parametros incorrectos");
                                break;
                            }
                            System.out.println("Directorios: ");
                            dir_actual.imprimirDirectorios();
                            System.out.println("Archivos: ");
                            dir_actual.imprimirArchivos();
                            break;
                            
                        case "MFLE":
                            if (contarParametros(tokens, 1) == true){
                                System.out.println("Parametros incorrectos");
                                break;
                            }
                            String archivoM = input.substring("MFLE ".length());
                            System.out.println("accediendo al contenido de " + archivoM);
                           
                            String[] temp_extensionM = archivoM.split("\\.");
                            String extensionM = temp_extensionM[(temp_extensionM.length)-1];
                            String nombreM = archivoM.substring(0, archivoM.lastIndexOf("." + extensionM));
                            String contenidoM = "";
                            Archivo arch_buscado = null;
                            try {
                                arch_buscado = dir_actual.getArchivo(nombreM, extensionM);
                                contenidoM = arch_buscado.getContenido();
                            } 
                            catch (Exception ex) {
                                System.out.println("No existe un archivo con ese nombre en la ruta actual");
                                break;
                            }                    
                            
                            final JFrame frame = new JFrame(archivoM);
                            JTextArea ta = new JTextArea(10, 20);
                            JScrollPane sp = new JScrollPane(ta);
                            frame.setLayout(new FlowLayout());
                            frame.setSize(300, 220);
                            frame.getContentPane().add(sp);
                            ta.setText(contenidoM);
                            frame.setVisible(true);
                            
                            System.out.print("Ingrese OK cuando haya terminado de editar... ");
                            String ok = br.readLine();
                            if(ok.equals("OK")){
                                String nuevoContenido = ta.getText();
                                if (nuevoContenido.length() <= disco_virtual.getTamSectores()*disco_virtual.cantSectoresVacios()){
                                    arch_buscado.borrarContenido();
                                    arch_buscado.setPunteros(disco_virtual.escribirSectores(nuevoContenido, arch_buscado.getPunteros()));
                                }
                                else{
                                    System.out.print("No hay suficiente espacio para almacenar el contenido nuevo");
                                }
                                frame.setVisible(false);
                            }
                            
                            break;
                            
                            
                        case "PPT":
                            boolean isFile = false;
                            if (contarParametros(tokens, 1) == false){
                                System.out.println("Parametros incorrectos");
                                break;
                            }
                            
                            String nom_archivo = input.substring("PPT ".length());
                            for (Archivo a: dir_actual.getListaArchivos()){
                                if(nom_archivo.equals(a.getNombre())){
                                    System.out.println("Extension: "+a.getExtension());
                                    System.out.println("Fecha creacion: "+a.getFecha_creacion());
                                    System.out.println("Fecha modificacion: "+a.getFecha_modificacion());
                                    System.out.println("Tamaño: "+a.getSize());
                                    isFile = true;
                                    break;
                                }
                            }
                            if(!isFile)
                                System.out.println("Archivo no existe en el directorio "+dir_actual.getNombre());
                            break;
                            
                        case "VIEW":
                            if (contarParametros(tokens, 1) == true){
                                System.out.println("Parametros incorrectos");
                                break;
                            }
                            String archivoV = input.substring("VIEW ".length());
                            String[] temp_extension = archivoV.split("\\.");
                            String extension = temp_extension[(temp_extension.length)-1];
                            String nombre = archivoV.substring(0, archivoV.lastIndexOf("." + extension));
                            try {
                                Archivo arch_buscadoV = dir_actual.getArchivo(nombre, extension);
                                System.out.println(arch_buscadoV.getContenido());
                            } 
                            catch (Exception ex) {
                                System.out.println("No existe un archivo con ese nombre en la ruta actual");
                            }
                    
                            

                            break;
                            
                        case "CPY":
                        	if(!(tokens.length == 3)){
                                System.out.println("Parametros incorrectos");
                                break;
                            }
                        	
                        	Directorio raizTemp = disco_virtual.getRaiz();
                        	Archivo archivo1, archivo2;
                        	
                        	String[] ruta1 = tokens[1].split("/");
                        	String nomRaiz1 = ruta1[0];
                        	ruta1 = Arrays.copyOfRange(ruta1, 1, ruta1.length);
                        	
                        	if(raizTemp.getNombre().equals(nomRaiz1)){// && raizTemp.existeArchivoRuta(ruta1)){
                        		//Archivo de ruta virtual
                        		//archivo1 = getArchivoRuta(raizTemp, ruta1);
                        		archivo1 = raizTemp.getArchivoRuta(ruta1);
                        	}else{
                        		//Archivo de ruta real
                        	}
                        	
                        	String[] ruta2 = tokens[2].split("/");
                        	String nomRaiz2 = ruta2[0];
                        	ruta2 = Arrays.copyOfRange(ruta2, 1, ruta2.length);
                        	
                        	Directorio destino = null;
                        	if(raizTemp.getNombre().equals(nomRaiz2)){
                        		//Directorio de ruta virtual
                        		destino = raizTemp.getDirectorioRuta(ruta2);
                        		/*
                        		 * 
                        		 * 
                        		 * TENGO QUE PONER QUE CREE EL ARCHIVO AQUI
                        		 * 
                        		 * TAMBIEN FALTA QUE SOBREESCRIBA SI YA EXISTE
                        		 * 
                        		 */
                        		
                        	}else{
                        		//Archivo de ruta real
                        	}
                        	/*
                        	String[] ruta2 = tokens[2].split("/");
                        	String nomRaiz2 = ruta2[0];
                        	ruta2 = Arrays.copyOfRange(ruta2, 1, ruta2.length-1);
                        	
                        	if(raizTemp.getNombre().equals(nomRaiz2) && raizTemp.existeArchivoRuta(ruta2)){
                        		//Archivo de ruta virtual
                        		archivo2 = getArchivoRuta(raizTemp, ruta2);
                        	}else{
                        		//Archivo de ruta real
                        	}
                        	*/
                            /*System.out.print("Indique opcion 10, 11 o 12: ");
                            int opcion = Integer.parseInt(br.readLine());
                            switch(opcion){
                                case 10:
                                    System.out.println("un archivo con ruta real sera copiado a una ruta virtual de MI File System. ");
                                    break;
                                case 11:
                                    System.out.println("un archivo con ruta virtual de MI File System sera copiado a una ruta real");
                                    break;
                                case 12:
                                    System.out.println("un archivo con ruta virtual de MI File System sera copiado a otra ruta virtual de MI File System. ");
                                    break;
                                default:
                                    System.out.println("opcion no valida.");
                            }*/
                            break;
                            
                            
                        case "MOV":
                            break;
                            
                        case "REM":
                            if (contarParametros(tokens, 1) == true){
                                System.out.println("Parametros incorrectos");
                                break;
                            }
                            String remover = input.substring("REM ".length());
                            System.out.println("intentado eliminar " + remover);
                            
                            String[] posible_extension = remover.split("\\.");
                            
                            if(posible_extension.length == 1){
                                if (dir_actual.existeDirectorio(remover)){
                                    dir_actual.borrarDirectorio(remover);
                                }
                                else{
                                    System.out.println("archivo o directorio no existe en el directorio actual");
                                }
                            }
                            else{
                                String ext = posible_extension[(posible_extension.length) -1];
                                String name = remover.substring(0, remover.lastIndexOf("." + ext));
                                if(dir_actual.existeArchivo(name, ext)){
                                    dir_actual.borrarArchivo(name, ext);
                                }
                                else if(dir_actual.existeDirectorio(remover)){
                                    dir_actual.borrarContenidoDirectorio(remover);
                                    dir_actual.borrarDirectorio(remover);
                                }
                                else{
                                    System.out.println("archivo o directorio no existe en el directorio actual");
                                }
                            }
                            
                            
                            
                            break;
                        case "TREE":
                            Directorio raiz = disco_virtual.getRaiz();
                            
                            System.out.println(raiz.getNombre());
                            recorrer(raiz,0); 
                            
                            break;
                            
                        case "FIND":
                            if (contarParametros(tokens, 1) == false){
                                System.out.println("Parametros incorrectos");
                                break;
                            }
                            
                            String nom = input.substring("FIND ".length());
                            
                            break;
                            
                        case "EXIT":
                            System.out.println("Saliendo del File System!");
                            System.exit(0);
                            break;

                        default:
                             System.out.println("Comando no reconocido.");
                    }
                }
            }
            
            
            

        } 
        catch (IOException e) {
            e.printStackTrace();
        } 
        finally {
            if (br != null) {
                try {
                    br.close();
                } 
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
    
    static void recorrer(Directorio dir, int tabs){
        tabs++;
        for(Archivo a: dir.getListaArchivos()){
            imprimirTabs(tabs);
            System.out.println("- Archivo: "+ a.getNombre() + "." + a.getExtension());
        }
        for(Directorio d: dir.getListaDirectorios()){
            imprimirTabs(tabs);
            System.out.println("- Directorio: "+d.getNombre());
            recorrer(d,tabs+1);
        }
        
    }
    
    static void imprimirTabs(int tabs){
        //System.out.println("");
        for(int i=0;i<tabs;i++){
            System.out.print("\t");
        }
    }
    
    static boolean contarParametros(String[] tokens, int cantParametros){
        int contador = 0;
        for (String t : tokens)
            contador++;
        return (contador == cantParametros);
    }
    
    /*private static Archivo getArchivoRuta(Directorio raizTemp, String[] ruta){
    	Archivo archivo = null;
    	Directorio dir_actual_Aux = raizTemp;
		boolean isDir = true;
		for(int indice = 0; indice < ruta.length-1; indice++){
			try{
				dir_actual_Aux = dir_actual_Aux.getDirectorio(ruta[indice]);
			}catch (Exception e){
				System.out.println("Ruta de archivo inexistente");
				isDir = false;
			}
		}
		
		if(isDir){//Si la ruta de salida ingresada existe
			String[] nomArchivo = ruta[ruta.length-1].split("\\.");
			archivo = dir_actual_Aux.getArchivo(nomArchivo[0], nomArchivo[1]);
		}
    	return archivo;
    }*/
}