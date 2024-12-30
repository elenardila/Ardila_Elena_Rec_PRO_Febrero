package cuatroEnRaya;
import java.util.Random;

/**
 * Clase que representa a un jugador controlado por la IA.
 */
public class JugadorIA extends Jugador {
    private Random random;

    /**
     * Constructor de la clase JugadorIA.
     * @param ficha Ficha del jugador.
     * @param nombre Nombre del jugador.
     */
    public JugadorIA(char ficha, String nombre) {
        super(ficha, nombre);
        this.random = new Random();
    }

    /**
     * Método que elige una columna aleatoria para insertar la ficha.
     * @param tablero Tablero de juego.
     * @return Columna elegida.
     */
    @Override
    public int elegirColumna(Tablero tablero) {
        return random.nextInt(7);
    }
}
