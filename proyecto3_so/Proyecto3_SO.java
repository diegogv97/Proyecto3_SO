
package proyecto3_so;

import Entidades.Directorio;
import Entidades.DiscoVirtual;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;




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
                            
                            break;
                            
                        case "FLE":
                            if (contarParametros(tokens, 2+1) == false){
                                System.out.println("Parametros incorrectos");
                                break;
                            }
                            System.out.print("Ingrese el contenido del archivo: ");
                            input = br.readLine();
                            int size = input.length();
                            //crear archivo. 

                            System.out.println("El contenido es: " + input + "tamanno: " + size);
                            System.out.println("    tamanno: " + size);

                            System.out.println("El conenido es: " + input + "tamanno: " + size);
                            System.out.println("    tamanno: " + size);

                            break;
                            
                        case "MKDIR":
                            if (contarParametros(tokens, 1) == true){
                                System.out.println("Parametros incorrectos");
                                break;
                            }
                            String nombre_directorio = input.substring("MKDIR ".length());
                            System.out.println("creando el directorio " + nombre_directorio);
                            dir_actual.addDirectorio(new Directorio(nombre_directorio));
                            break;
                            
                        case "CHDIR":
                            if (contarParametros(tokens, 1) == true){
                                System.out.println("Parametros incorrectos");
                                break;
                            }
                            String ir_directorio = input.substring("CHDIR ".length());
                            System.out.println("yendo al directorio " + ir_directorio);
                            String[] tokens_dirs = ir_directorio.split("/");
                            Directorio temp = dir_actual;
                            dir_actual = disco_virtual.getRaiz();
                            for(String s : tokens_dirs){
                                try {
                                    dir_actual = dir_actual.getDirectorio(s);
                                } catch (Exception ex) {
                                    System.out.println("Directorio no existe");
                                }
                            }
                            dir_actual = temp;
                            break;
                            
                        case "LDIR":
                            if (contarParametros(tokens, 1) == false){
                                System.out.println("Parametros incorrectos");
                                break;
                            }
                            break;
                            
                        case "MFLE":
                            if (contarParametros(tokens, 1) == true){
                                System.out.println("Parametros incorrectos");
                                break;
                            }
                            String archivo = input.substring("MFLE ".length());
                            System.out.println("accediendo al contenido de " + archivo);
                            break;
                            
                            
                        case "PPT":
                            if (contarParametros(tokens, 1) == false){
                                System.out.println("Parametros incorrectos");
                                break;
                            }
                            break;
                            
                        case "VIEW":
                            if (contarParametros(tokens, 1) == true){
                                System.out.println("Parametros incorrectos");
                                break;
                            }
                            String archivoV = input.substring("MFLE ".length());
                            System.out.println("accediendo al contenido de " + archivoV);
                            break;
                            
                        case "CPY":
                            System.out.print("Indique opcion 10, 11 o 12: ");
                            int opcion = Integer.parseInt(br.readLine());
                            switch(opcion){
                                case 10:
                                    System.out.println("un archivo con ruta virtual sera copiado a una ruta â€œvirtualâ€� de MI File System. ");
                                    break;
                                case 11:
                                    System.out.println("un archivo con ruta virtual de MI File System serÃ¡ copiado a una ruta â€œreal");
                                    break;
                                case 12:
                                    System.out.println("un archivo con ruta virtual de MI File System serÃ¡ copiado a otra ruta â€œvirtualâ€� de MI File System. ");
                                    break;
                                default:
                                    System.out.println("opcion no valida.");
                            }
                            break;
                            
                            
                        case "MOV":
                            break;
                            
                        case "REM":
                            break;
                        case "TREE":
                            break;
                            
                        case "FIND":
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
    
    
    static boolean contarParametros(String[] tokens, int cantParametros){
        int contador = 0;
        for (String t : tokens)
            contador++;
        return (contador == cantParametros);
    }
    
}
