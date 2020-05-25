
package fichero;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Carles Cartes Fernandez
 */
public class peticion {
    static BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
    
    public static void creaCarpetaArchivos(){
        //voy a crear o verificar si existe la carpeta "archivos dentro del proyecto
        //defino la ruta donde se va a ejecutar el proyecto
        String carpetaArchivos = System.getProperty("user.dir");
        //defino el separador para el SO
        String separador = File.separator;
        //defino la ruta del proyecto, donde se guardará todo
        String rutaCarpeta = carpetaArchivos + separador + "archivos";
        //creo el vinculo a la ruta
        File carpeta = new File(rutaCarpeta);
        //reviso si la carpeta existe ya o no
        if(carpeta.exists()){
            //si existe no hacemos nada porque ya existe
        }else{
            //Creamos la carpeta si no existe
            carpeta.mkdir(); 
        }
        //ya hemos revisado si la carpeta existe o no y en tal caso la hemos creado
    }
    
    public static int pideEntero(String pregunta){
        //función en la que vamos a obtener un número entero por parte del usuario y nos servirá para futuras funciones
        //aquí tenemos un paramtro String que es pregunta.
        boolean flag = true;
        int num = 0;
        do{
            try {
                System.out.println (pregunta);
                num = Integer.parseInt (br.readLine());
                flag = false;
            } catch (NumberFormatException e){
                System.out.println ("\033[31mNo has introducido un número correcto.\033[31m");
            } catch (IOException ex){
                System.out.println ("\033[31mSe ha producido un error: " +ex.getMessage()+ "\033[31m");
            }
        } while(flag);
        return num;
    }
    
    public static String pideNombre(String pregunta){
        //función que va a recoger el nombre de los archivos que quiere crear el usuario
        boolean flag = true;
        String nombre = "";
        do{
            try{
                System.out.println(pregunta);
                nombre = (br.readLine());
                flag = false;
            }catch (IOException e){
                System.out.println("\033[31mSe ha producido un error inesperado: " +e.getMessage()+ "\033[0m" );
            }
        }while (flag);
        return nombre;
    }
    
}
