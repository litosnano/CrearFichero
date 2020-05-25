
package funciones;

import fichero.peticion;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Carles Cartes Fernandez
 */
public class gestionFicheros {

    //defino fuera de las funciones la ruta donde ejecutamos el proyecto para que sirva para todas
    static String carpetaArchivos = System.getProperty("user.dir");
    
    //defino el separador del SO también fuera de las funciones para usarlo en todas y no repetir
    static String separador = File.separator;
 
    public static void creaFicheros() {
        //esta es la función para crear archivos nuevos.
        //no tiene parametros
        //Primero reviso si existe la carpeta "archivos" donde se tiene que grabar los nuevos ficheros creados        
        peticion.creaCarpetaArchivos();
        //ya hemos revisado si la carpeta existe o no y en tal caso la hemos creado con la función externa
        //creo la ruta donde se grabaran los ficheros
        String rutaCarpeta = carpetaArchivos + separador + "archivos";
        boolean sem = true;
        //hasta que no ponga un nombre de archivo que no esté repetido le seguirá preguntando para poner uno correcto
        do{
            String nombre = peticion.pideNombre("\033[32mPonle nombre a tu archivo:\033[0m");
            String extension = "";
            int opt;
            boolean flag = true;
            do{
                 opt = peticion.pideEntero("\033[32m¿Que extensión va a tener tu archivo?\n"
                                        + "\033[32m1. Extensión .doc\n"
                                        + "\033[32m2. Extensión .txt\n"
                                        + "\033[32m3. Extensión .odt\n"
                                        + "\033[32m4. Extensión .html\033[0m");
                switch (opt){
                    case 1:
                        extension = ".doc";
                        flag = false;
                        break;
                    case 2:
                        extension = ".txt";
                        flag = false;
                        break;
                    case 3:
                        extension = ".odt";
                        flag = false;
                        break;
                    case 4:
                        extension = ".html";
                        flag = false;
                        break;
                    default:
                        System.out.print("\033[31mNo has escogido una extensión correcta.\033[0m\n");
                }
            }while (flag);

            //ahora creamos la ruta hacia donde se grabaran los archivos 
            String rutaArchivo = rutaCarpeta + separador + (nombre+extension); 
            //Creo el vinculo al fichero
            File file = new File(rutaArchivo);
            //ahora miramos si el fichero que quiere grabar ya existe o no y en tal caso lo creamos
            try{
                if(file.exists()){
                    System.out.println("\033[31mEste nombre de fichero ya existe.\033[0m");
                }else{
                    System.out.println("\033[35mSe ha creado tu archivo.\033[0m");
                    file.createNewFile();
                    //como hemos creado un archivo nuevo, ahora le pedimos al usuario que lo rellene
                    //creo el vinculo al fichero que se acaba de crear
                    FileWriter fw = new FileWriter (rutaCarpeta + separador + file.getName());
                    //creo el buffer de escritura
                    BufferedWriter bw = new BufferedWriter(fw);
                    //pido al usuario (llamando a una función externa) que quiere poner
                    bw.write(peticion.pideNombre("\033[32mEscribe ahora el texto que quieres añadir al archivo recien creado:\033[0m"));
                    //actualizamos el buffer
                    bw.flush();
                    //cierro el buffer
                    bw.close();
                    System.out.print ("\033[35mGracias por crear tu nuevo fichero y rellenarlo.\033[0m\n");
                    sem = false;
                }
            }catch (IOException e){
                System.out.println ("\033[31mSe ha producido un error: " +e.getMessage()+ "\033[0m");
            }
        }while (sem);
    }
    
    public static String [] listarArchivos() {
       
        //vamos a listar todos los archivos que haya dentro de la carpeta archivos que es donde el usuario está guardando todos los archivos.
        //antes de nada, y por si el usuario entra directamente aquí sin haber creado ningun archivo (y por lo tanto la carpeta), creamos la carpeta
        peticion.creaCarpetaArchivos();
        //para sacar el listado lo primero tenemos que crear la ruta a la carpeta que deseamos     
        File carpeta = new File(carpetaArchivos + separador + "archivos");
        String []lista = carpeta.list();
        //hago esta comprobación para ver cuantos archivos hay dentro de la carpeta
        int conf = 0;
        for (int i=0; i<lista.length;i++){
            File con = new File(carpeta + separador + lista[i]);
            if (con.isFile()){
                conf++;
            }
        }
        String []rutaPath = new String [lista.length];
        //verificamos si dentro de la carpeta hay archivos; sy hay carpetas marcara como si no hubiese nada
        if (lista.length == 0 || conf == 0) {
            System.out.println("\033[31m---------------------------------------------\033[0m");
            System.out.println("\033[31mNo hay archivos dentro de la carpeta actual.\033[0m");
            System.out.println("\033[31m---------------------------------------------\033[0m");
        }else{
            //como hay algo, miramos a ver que es
            System.out.println("\033[35m---------------------------------------------------------------------------------------------------------------\033[0m");
            System.out.println("\033[35mEstos son los archivos que hay dentro de la carpeta:\033[0m");
            for (int i=0; i<lista.length; i++){
                File fAux = new File(carpeta + separador + lista[i]);
                //solo mostramos files
                if (fAux.isFile()){   
                    System.out.println(+(i+1)+ "- " +lista[i]);
                    //recojo la ruta absoluta para lanzarla en el return
                    rutaPath[i]=fAux.getAbsolutePath();
                }
            }
            System.out.println("\033[35m---------------------------------------------------------------------------------------------------------------\033[0m");
        }
        return rutaPath;
    }
    
    public static void muestraUnArchivo(){
        //esta función nos va a permitir escoger un fichero y ver su interior
        //no tiene ningun parametro
        //primero traemos el listado de archivos que hay en la carpeta
        String [] lista = gestionFicheros.listarArchivos();
        String [] ab = new String [lista.length];
        //creamos la ruta a la carpeta de la que queremos mostrar los archivos
        File carpeta = new File(carpetaArchivos + separador + "archivos");
        //como contiene rutas absolutas lo transformo a nombre de archivo
        for (int i=0;i<lista.length;i++){
            File abs = new File (carpeta + separador + lista[i]);
            ab [i]= abs.getName();
        }
        //hago esta comprobación para ver cuantos archivos hay dentro de la carpeta
        int conf = 0;
        for (int i=0; i<lista.length;i++){
            File con = new File(carpeta + separador + lista[i]);
            if (con.isFile()){
                conf++;
            }
        }
        //verificamos si dentro de la carpeta hay archivos; si hay carpetas marcara como si no hubiese nada
        if (lista.length == 0 && conf == 0){
        }else{
            boolean bandera = true;
            do{
                //pedimos de que archivo quiere que se muestre su contenido
                int num = peticion.pideEntero("\033[32m¿Que número de archivo quieres visualizar?\n"
                        + "\033[32mSi no quieres visualizar ningun archivo, marca el número 0 y saldrás al menú principal.\033[0m");
                //comprobamos que quiere hacer: salir o mostrar
                //si quiere salir lo sacamos
                if(num == 0){
                    System.out.println("\033[35mHas decidio salir sin modificar ningun archivo.\033[0m");
                    bandera = false;
                //si decide modificar entramos dentro del archivo
                }else{
                    try{
                        //creo el vinculo de lectura
                        FileReader fr = new FileReader(carpeta + separador + ab[(num-1)]);
                        BufferedReader br = new BufferedReader(fr);
                        //abro un string vacío (null) para que entre dentro
                        System.out.println("\033[35mEl archivo contiene lo siguiente:\033[0m");
                        String linea = "";
                        //mostramos por pantalla todas las lineas del archivo hasta que encontremos un null (fin del archivo)
                        while(linea != null){
                            linea = br.readLine();
                            if(linea != null)
                                System.out.println(linea);
                        }
                        bandera = false;
                        //cierro el bufferreader
                        br.close();
                    }catch(FileNotFoundException e){
                        System.out.println("\033[31mEl archivo no existe.\033[0m");
                    }catch(IOException ex){
                        System.out.println ("\033[31mSe ha producido un error: " +ex.getMessage()+ "\033[0m");
                    }catch(ArrayIndexOutOfBoundsException ea){
                        System.out.println("\033[31mNo existe el número de archivo que has escrito.\033[0m");
                    }
                }
            }while(bandera);
        } 
    }
    
    public static void borrarUnFichero(){
        //esta es la función para borrar archivos
        //no recibe ningun parametro
        //primero creo la carpeta archivos por si aun no existe, para que no me salga la excepción NullPointerException
        peticion.creaCarpetaArchivos();
        //para borrar un archivo primero indicamos la ruta donde se encuentran los archivos
        File carpeta = new File(carpetaArchivos + separador + "archivos");
        String []lista = carpeta.list();
        //verifico si hay algo dentro de la carpeta archivos
        if(lista.length == 0){
            System.out.println("\033[31m---------------------------------------------\033[0m");
            System.out.println("\033[31mNo hay archivos dentro de la carpeta actual.\033[0m");
            System.out.println("\033[31m---------------------------------------------\033[0m");
        }else{
            System.out.println("\033[35m---------------------------------------------------------------------------------------------------------------\033[0m");
            System.out.println("\033[35mA continuación se muestran todo lo que hay dentro de la carpeta " +carpeta.getName()+ " con su número de posición.\033[0m");
            //enseñamos al usuario todos los archivos que hay dentro de la carpeta (gracias al array que hemos creado)
            //se muestra los archivos que hay dentro de la carpeta
            for (int i=0; i<lista.length; i++){
                File fAux = new File(carpeta + separador + lista[i]);
                //solo mostramos files
                if (fAux.isFile()){
                    System.out.println(+(i+1)+ "- " +lista[i]);
                }
            }
            //preguntamos que archivo quiere que borremos y comprobamos si existe mediante un try/catch.
            //hasta que no elimine un archivo o decida salir de la aplicación seguirá preguntando,
            boolean flag = true;
            do {
                try {
                    System.out.println("\033[35m---------------------------------------------------------------------------------------------------------------\033[0m");
                    int num = peticion.pideEntero("\033[32m¿Que número de archivo quieres borrar?\n"
                            + "\033[32mSi no quieres borrar nada, marca el número 0 y saldrás al menú principal.\033[0m");
                    //comprobamos que quiere hacer: salir o eliminar
                    //si quiere salir lo sacamos
                    if(num == 0){
                        System.out.println("\033[35mHas decidio salir sin borrar ningun archivo.\033[0m");
                        flag = false;
                    //si decide eliminar comprobamos si el archivo a eliminar existe,
                    }else{
                        File borrar = new File (carpeta + separador + lista[(num-1)]);
                        if (borrar.exists()){
                            if (borrar.isDirectory()){
                                System.out.println("\033[31mLo siento, no puedes borrar lo que hay esta posición seleccionada.\033[0m");
                            }else{
                                //si existe lo eliminamos
                                borrar.delete();
                                System.out.println("\033[35mSe ha procedido a borrar el archivo " +borrar.getName()+ "\033[0m");
                                flag = false;
                            }    
                        }
                    }
                //si no existe saltará el catch ya que se está llendo fuera del array
                }catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("\033[31mNo existe el número de archivo que has escrito.\033[0m");
                }
            }while (flag);
        }
    }
    
    public static void modificarUnArchivo(){      
        //esta es la función que va a modificar el interior de un archivo
        //no contiene ningún parametro
        //para modificar un archivo primero indicamos la ruta donde se encuentran los archivos
        File carpeta = new File(carpetaArchivos + separador + "archivos");
        //primero traemos el listado de archivos que hay en la carpeta
        String[] lista = gestionFicheros.listarArchivos();
        String [] ab = new String [lista.length];
        //como traemos una ruta absoluta la transformo en nombre de archivo
        for (int i=0;i<lista.length;i++){
            File abs = new File (carpeta + separador + lista[i]);
            ab [i]= abs.getName();
        }
        //hago esta comprobación para ver cuantos archivos hay dentro de la carpeta
        int conf = 0;
        for (int i=0; i<lista.length;i++){
            File con = new File(carpeta + separador + lista[i]);
            if (con.isFile()){
                conf++;
            }
        }
        //verificamos si dentro de la carpeta hay archivos; si hay carpetas marcara como si no hubiese nada
        if (lista.length == 0 && conf == 0){
        }else{
            int opt;
            boolean bandera = true;
            do{
                int num = peticion.pideEntero("\033[32m¿Que número de archivo quieres modificar?\n"
                        + "\033[32mSi no quieres modificar ningun archivo, marca el número 0 y saldrás al menú principal.\033[0m");
                //comprobamos que quiere hacer: salir o modificar
                //si quiere salir lo sacamos
                if(num == 0){
                    System.out.println("\033[35mHas decidio salir sin modificar ningun archivo.\033[0m");
                    bandera = false;
                //si decide modificar entramos dentro del archivo
                }else{
                    try{
                        boolean sem = true;
                        do{
                            if((num-1>=lista.length) || num<=0){
                                num = peticion.pideEntero("\033[32mLo siento, no existe el número de archivo que has escrito. Pon uno correcto, por favor.\033[0m");
                            }else{
                                boolean flag = true;
                                do{
                                    opt = peticion.pideEntero("\033[32mPuedes realizar tres cosas:\n"
                                        + "\033[32m1. Borrar todo el interior del archivo y escribir algo nuevo.\n"
                                        + "\033[32m2. Añadir algo al final de lo que ya existe en el archivo.\n"
                                        + "\033[32m3. Salir porque me he equivocado de archivo.\033[0m");
                                    switch (opt){
                                        case 1:
                                            FileWriter fw = new FileWriter(carpeta + separador + ab[(num-1)]);
                                            //creo el buffer de escritura
                                            BufferedWriter bw = new BufferedWriter(fw);
                                            //pido al usuario llamando a una función externa que quiere poner
                                            bw.write(peticion.pideNombre("\033[32mEscribe ahora para poner algo en el archivo.\033[0m"));
                                            //actualizamos el buffer
                                            bw.flush();
                                            //cierro el buffer
                                            bw.close();
                                            flag = false;
                                            bandera = false;
                                            break;
                                        case 2:
                                            //creo el vinculo al fichero. Con el true del final lo que hago es que lo que ponga el individuo se añada a lo que hay
                                            FileWriter fw1 = new FileWriter (carpeta + separador + ab[(num-1)], true);
                                            //creo el buffer de escritura
                                            BufferedWriter bw1 = new BufferedWriter(fw1);
                                            //pido al usuario llamando a una función externa que quiere poner
                                            bw1.write("\n" +peticion.pideNombre("\033[32mEscribe ahora lo que quieres añadir al final de este archivo.\033[0m"));
                                            //actualizamos el buffer
                                            bw1.flush();
                                             //cierro el buffer
                                            bw1.close();
                                            flag = false;
                                            bandera = false;
                                            break;
                                        case 3:
                                            System.out.println("\033[35mHas decidido no modificar nada de este archivo.\033[0m");
                                            flag = false;
                                            break;
                                        default:
                                            System.out.println ("\033[31mNo has escogido una de las tres opciones posibles.\033[0m");
                                    }
                                }while(flag);
                                sem = false;
                            }
                        }while(sem);
                    }catch(IOException e){
                        System.out.println ("\033[31mSe ha producido un error: " +e.getMessage()+ "\033[0m");
                    }catch(ArrayIndexOutOfBoundsException ea){
                        System.out.println("\033[31mNo existe el número de archivo que has escrito.\033[0m");
                    }
                }
            }while (bandera);
        }
    }
    
    public static void renombrarUnArchivo() {
        //esta es la función para cambiar el nombre de un archivo
        //no recibe ningun parametro
        //primero creo la carpeta archivos por si aun no existe, para que no me salga la excepción NullPointerException
        peticion.creaCarpetaArchivos();
        //para renombrar un archivo primero indicamos la ruta donde se encuentran los archivos
        File carpeta = new File(carpetaArchivos + separador + "archivos");
        String []lista = carpeta.list();
        //verifico si hay algo dentro de la carpeta archivos
        if(lista.length == 0){
            System.out.println("\033[31m---------------------------------------------\033[0m");
            System.out.println("\033[31mNo hay archivos dentro de la carpeta actual.\033[0m");
            System.out.println("\033[31m---------------------------------------------\033[0m");
        }else{
            System.out.println("\033[35m---------------------------------------------------------------------------------------------------------------");
            System.out.println("\033[35mA continuación se muestran todos los archivos que hay dentro de la carpeta " +carpeta.getName()+ " con su número de posición.");
            //enseñamos al usuario todos los archivos que hay dentro de la carpeta (gracias al array que hemos creado)
            //se muestra todo: archivos y carpetas YA QUE EL ENUNCIADO DEL EJERCICIO NO MARCA LO CONTRARIO 
            for (int i=0; i<lista.length; i++){
                File fAux = new File(carpeta + separador + lista[i]);
                //solo mostramos files
                if (fAux.isFile()){
                    System.out.println(+(i+1)+ "- " +lista[i]);
                }
            }
            System.out.println("\033[35m---------------------------------------------------------------------------------------------------------------\033[0m");
            //preguntamos que archivo quiere que renombrar y comprobamos si existe mediante un try/catch.
            //hasta que no renombre un archivo o decida salir de la aplicación seguirá preguntando,
            boolean flag = true;
            do {
                try {
                    int num = peticion.pideEntero("\033[32m¿Que número de archivo quieres renombrar?\n"
                            + "\033[32mSi no quieres renombrar, marca el número 0 y saldrás al menú principal.\033[0m");
                    //comprobamos que quiere hacer: salir o renombrar
                    //si quiere salir lo sacamos
                    if(num == 0){
                        System.out.println("\033[35mHas decidio salir sin renombrar ningun archivo.\033[0m");
                        flag = false;
                    //si decide renombrar comprobamos si el archivo a renombrar existe,
                    }else{
                        File renombrar = new File (carpeta + separador + lista[(num-1)]);
                        if (renombrar.isDirectory()){
                            System.out.println("\033[31mLo siento, no puedes cambiar el nombre de lo que hay en esta posición seleccionada.\033[0m");
                        }else{
                            if (renombrar.exists()){
                                //si existe lo renombramos
                                String nn = peticion.pideNombre("\033[32m¿Que nombre deseas ponerle?\033[0m");
                                //File fNuevo = new File(carpeta + separador + nn);
                                //estos son los caracteres especiales
                                String caracEspeciales = ".*[!@#$%&*()_+=|<>?{}\\[\\]~-].*";
                                //si contiene caracteres especiales no le dejamos cambiar el nombre y lo sacamos al menu principal
                                //yo lo habría mandado a renombrar otra vez, jeje
                                if(nn.matches(caracEspeciales)){
                                    System.out.println("\033[31mEl nombre no puede contener caracteres especiales: " +caracEspeciales+ "\n"
                                            + "El ejercicio dice que te envie al menu principal, yo te habría mandado a renombrarlo bien, jeje.\033[0m");
                                    flag = false;
                                //si no contiene caracteres especiales cambiamos el nombre del archivo
                                }else{
                                    String extension = "";
                                    int opt;
                                    boolean sem = true;
                                    do{
                                         opt = peticion.pideEntero("\033[32m¿Que extensión va a tener tu archivo?\n"
                                                                + "\033[32m1. Extensión .doc\n"
                                                                + "\033[32m2. Extensión .txt\n"
                                                                + "\033[32m3. Extensión .odt\n"
                                                                + "\033[32m4. Extensión .html\033[0m");
                                        switch (opt){
                                            case 1:
                                                extension = ".doc";
                                                sem = false;
                                                break;
                                            case 2:
                                                extension = ".txt";
                                                sem = false;
                                                break;
                                            case 3:
                                                extension = ".odt";
                                                sem = false;
                                                break;
                                            case 4:
                                                extension = ".html";
                                                sem = false;
                                                break;
                                            default:
                                                System.out.print("\033[31mNo has escogido una extensión correcta.\033[0m\n");
                                        }
                                    }while (sem);
                                    File fNuevo = new File(carpeta + separador + (nn+extension));
                                    //ahora miramos si el fichero que quiere grabar ya existe o no y en tal caso lo creamos
                                    if(fNuevo.exists()){
                                        System.out.println("\033[31mEste nombre de fichero ya existe.\033[0m");
                                    }else{
                                        System.out.println("\033[35mSe ha creado tu archivo.\033[0m");
                                        renombrar.renameTo(fNuevo);
                                        System.out.println("\033[35mSe ha procedido a renombrar el archivo, antes se llamaba " +renombrar.getName()+ " y ahora " +fNuevo.getName()+ "\033[0m");
                                        flag = false;
                                    }
                                }
                            }
                        }
                    }
                //si no existe saltará el catch ya que se está llendo fuera del array
                }catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("\033[31mNo existe el número de archivo que has escrito.\033[0m");
                }
            }while (flag);
        }
    } 
}
