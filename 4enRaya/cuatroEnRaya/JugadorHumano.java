package cuatroEnRaya;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que representa a un jugador humano.
 */
public class JugadorHumano extends Jugador {
    private Scanner scanner;

    /**
     * Constructor de la clase JugadorHumano.
     * @param ficha Ficha del jugador.
     * @param nombre Nombre del jugador.
     */
    public JugadorHumano(char ficha, String nombre) {
        super(ficha, nombre);
        this.scanner = new Scanner(System.in);
    }

    /**
     * Método que elige una columna para insertar la ficha.
     * @param tablero Tablero de juego.
     * @return Columna elegida.
     */
    @Override
    public int elegirColumna(Tablero tablero) {
        int columna = -1;
        boolean entradaValida = false;
        while (!entradaValida) {
            System.out.print("\nElige una columna (0-6): ");
            try {
                columna = scanner.nextInt();
                entradaValida = true;
            } catch (InputMismatchException e) {
                System.out.println("\nEntrada inválida. Por favor, introduce un número entre 0 y 6.");
                scanner.next(); // Limpiar la entrada incorrecta
            }
        }
        return columna;
    }
}


