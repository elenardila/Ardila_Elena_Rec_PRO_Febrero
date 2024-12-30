package cuatroEnRaya;
/**
 * Clase que representa el tablero de juego.
 */
public class Tablero {
    private char[][] tablero;
    private static final String ROJO = "\u001B[31m";
    private static final String AMARILLO = "\u001B[33m";
    private static final String RESET = "\u001B[0m";

    /**
     * Constructor de la clase Tablero.
     */
    public Tablero() {
        this.tablero = new char[6][7];
        inicializarTablero();
    }

    /*
     * Inicializa el tablero con espacios en blanco.
     */
    private void inicializarTablero() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = ' ';
            }
        }
    }

    /**
     * Método que inserta las fichas en las columnas.
     * @param columna
     * @param ficha
     * @return true si se ha insertado la ficha, false en caso contrario.
     */
    public boolean insertarFicha(int columna, char ficha) {
        if (columna < 0 || columna >= tablero[0].length) {
            System.out.println("\nColumna inválida. Intenta de nuevo.");
            return false;
        }

        for (int i = tablero.length - 1; i >= 0; i--) {
            if (tablero[i][columna] == ' ') {
                tablero[i][columna] = ficha;
                return true;
            }
        }

        System.out.println("\nColumna llena. Intenta de nuevo.");
        return false;
    }

    /**
     * Comprueba si hay 4 fichas iguales en alguna fila.
     * @param ficha
     * @return true si hay 4 fichas iguales en alguna fila, false en caso contrario.
     */
    public boolean comprobarFila(char ficha) {
        for (int i = 0; i < tablero.length; i++) {
            int contador = 0;
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j] == ficha) {
                    contador++;
                    if (contador == 4) {
                        return true;
                    }
                } else {
                    contador = 0;
                }
            }
        }
        return false;
    }

    /**
     * Comprueba si hay 4 fichas iguales en alguna columna.
     * @param ficha
     * @return true si hay 4 fichas iguales en alguna columna, false en caso contrario.
     */
    public boolean comprobarColumna(char ficha) {
        for (int j = 0; j < tablero[0].length; j++) {
            int contador = 0;
            for (int i = 0; i < tablero.length; i++) {
                if (tablero[i][j] == ficha) {
                    contador++;
                    if (contador == 4) {
                        return true;
                    }
                } else {
                    contador = 0;
                }
            }
        }
        return false;
    }

    /**
     * Comprueba si hay 4 fichas iguales en alguna diagonal derecha.
     * @param ficha
     * @return true si hay 4 fichas iguales en alguna diagonal derecha, false en caso contrario.
     */
    public boolean comprobarDiagonalDcha(char ficha) {
        for (int i = 0; i < tablero.length - 3; i++) {
            for (int j = 0; j < tablero[i].length - 3; j++) {
                if (tablero[i][j] == ficha &&
                    tablero[i + 1][j + 1] == ficha &&
                    tablero[i + 2][j + 2] == ficha &&
                    tablero[i + 3][j + 3] == ficha) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Comprueba si hay 4 fichas iguales en alguna diagonal izquierda.
     * @param ficha
     * @return true si hay 4 fichas iguales en alguna diagonal izquierda, false en caso contrario.
     */
    public boolean comprobarDiagonalIzq(char ficha) {
        for (int i = 0; i < tablero.length - 3; i++) {
            for (int j = 3; j < tablero[i].length; j++) {
                if (tablero[i][j] == ficha &&
                    tablero[i + 1][j - 1] == ficha &&
                    tablero[i + 2][j - 2] == ficha &&
                    tablero[i + 3][j - 3] == ficha) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Comprueba si hay victoria.
     * @param ficha
     * @return true si hay victoria, false en caso contrario.
     */
    public boolean comprobarVictoria(char ficha) {
        return comprobarFila(ficha) || comprobarColumna(ficha) || comprobarDiagonalDcha(ficha) || comprobarDiagonalIzq(ficha);
    }

    /**
     * Muestra el tablero de juego.
     */
    public void mostrarTablero() {
        System.out.println("  0   1   2   3   4   5   6");
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                char ficha = tablero[i][j];
                if (ficha == 'R') {
                    System.out.print("| " + ROJO + ficha + RESET + " ");
                } else if (ficha == 'A') {
                    System.out.print("| " + AMARILLO + ficha + RESET + " ");
                } else {
                    System.out.print("| " + ficha + " ");
                }
            }
            System.out.println("|");
        }
        System.out.println("-----------------------------");
    }
}

