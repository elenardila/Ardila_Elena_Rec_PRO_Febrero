package cuatroEnRaya;
/*
 * Clase que representa a un jugador.
 * De ella heredan JugadorHumano y JugadorIA.
 */

public abstract class Jugador {
    protected char ficha;
    protected String nombre;

    /**
     * Constructor de la clase Jugador.
     * @param ficha Ficha del jugador.
     * @param nombre Nombre del jugador.
     */
    public Jugador(char ficha, String nombre) {
        this.ficha = ficha;
        this.nombre = nombre;
    }

    /**
     * Método que devuelve la ficha del jugador.
     * @return Ficha del jugador.
     */
    public char getFicha() {
        return ficha;
    }

    /**
     * Método que devuelve el nombre del jugador.
     * @return Nombre del jugador.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método que elige una columna para insertar la ficha.
     * @param tablero Tablero de juego.
     * @return Columna elegida.
     */
    public abstract int elegirColumna(Tablero tablero);
}

