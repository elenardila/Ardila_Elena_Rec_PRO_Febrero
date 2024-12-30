package cuatroEnRaya;
import java.util.Scanner;
/** 
 * Clase principal que inicia el juego Conecta 4.
*/
public class Main {
    /**
     * Función que muestra el menú principal del juego.
     */
    static void menuPpal(){
        System.out.println("\nBIENVENID@ AL JUEGO DE 4 EN RAYA\n\n");
        System.out.println("MENÚ DE JUEGO\n");
        System.out.println("[1] - Mostrar instrucciones.");
        System.out.println("[2] - Elegir modalidad."); 
        System.out.println("[3] - Jugar partida.");
        System.out.println("[4] - Configuración.");
        System.out.println("[5] - Créditos.");
        System.out.println("[6] - Salir\n");
    }

    /**
     * Método principal que inicia el juego.
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        Juego juego = new Juego();
        Scanner teclado = new Scanner(System.in);
        boolean salir = false;
        try {
            do {
                menuPpal();
                int opcion = teclado.nextInt();
                teclado.nextLine();
                switch (opcion) {
                    case 1 :
                     juego.mostrarInstrucciones();
                     System.out.println("\nPresiona Enter para volver al menú principal.");
                     teclado.nextLine();
                     break;
                    case 2 :
                     juego.elegirModalidad();
                     break;
                    case 3 :
                     juego.iniciar();
                     break;
                    case 4 :
                     juego.configuracion();
                     break;
                    case 5 :
                        juego.mostrarCreditos();
                        break;
                    case 6 :
                     salir = true;
                     break;
                    default : 
                    System.out.println("Dato no válido.");
                }
            } while (!salir);
        } finally {
            teclado.close();
        }
    }
}

