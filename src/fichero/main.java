
package fichero;

import java.io.IOException;
import funciones.gestionFicheros;

/**
 *
 * @author Carles Cartes Fernandez
 */
public class main {

    public static void main(String[] args) throws IOException {
        
        int opt;
        do{
            System.out.println("\033[34m**************************************************");
            System.out.println("\033[34m1.Nuevo archivo");
            System.out.println("\033[34m2.Listar archivos");
            System.out.println("\033[34m3.Muestra un archivo");
            System.out.println("\033[34m4.Borrar un archivo");
            System.out.println("\033[34m5.Modificar un archivo");
            System.out.println("\033[34m6.Renombrar un archivo");
            System.out.println("\033[34m7.Salir");
            System.out.println("**************************************************\033[0m");
            opt = peticion.pideEntero ("\033[32mEscoge una opción del programa, por favor\033[0m");
            switch (opt){
                case 1:
                    gestionFicheros.creaFicheros();
                    break;
                case 2:
                    gestionFicheros.listarArchivos();
                    break;
                case 3:
                    gestionFicheros.muestraUnArchivo();
                    break;
                case 4:
                    gestionFicheros.borrarUnFichero();
                    break;
                case 5:
                    gestionFicheros.modificarUnArchivo();
                    break;
                case 6:
                    gestionFicheros.renombrarUnArchivo();
                    break;
                case 7:
                    System.out.println("\033[45;30mHas decidido salir del programa, hasta la próxima!\033[0m");
                    break;
                default:
                    System.out.println("\033[31mNo has escogido una opción del menú. Escoge del 1 al 6, o el 7 si quieres salir.\033[0m");
            }        
        }while (opt != 7);
        
        
    }
    
}
