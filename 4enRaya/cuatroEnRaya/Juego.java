package cuatroEnRaya;
import java.util.InputMismatchException;
import java.util.Scanner;

/** 
 * Clase que maneja la lógica principal del juego. 
 */
public class Juego {
    private Tablero tablero;
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugadorActual;
    private Jugador ultimoGanador;
    private Jugador ultimoPerdedor;
    private Scanner teclado;
    private String modalidad;
    private char colorJugador1 = 'R';
    private char colorJugador2 = 'A';
    private int numeroPartidas = 1;
    private String ordenSalida = "Siempre Jugador 1";
    private int partidasGanadasJugador1 = 0;
    private int partidasGanadasJugador2 = 0;

    /** 
     * Constructor de la clase Juego. 
     */
    public Juego() {
        this.tablero = new Tablero();
        this.teclado = new Scanner(System.in);
    }

    /** 
     * Permite al usuario elegir la modalidad de juego.
     */
    public void elegirModalidad() {
        System.out.println("Elige la modalidad de juego:");
        System.out.println("[1] - Contra la IA.");
        System.out.println("[2] - Contra otro jugador.\n");

        int opcion = teclado.nextInt();
        teclado.nextLine();
        switch (opcion) {
            case 1:
                modalidad = "IA";
                System.out.println("Has elegido jugar contra la IA.\n");
                break;
            case 2:
                modalidad = "Humano";
                System.out.println("Has elegido jugar contra otro jugador.\n");
                break;
            default:
                System.out.println("Opción no válida. Se seleccionará jugar contra la IA por defecto.");
                modalidad = "IA";
        }
    }

    /** 
     * Configura los parámetros del juego según la entrada del usuario. 
     */
    public void configuracion() {

        System.out.println("\nConfiguración del juego:\n");

        boolean entradaValida = false;

        // Configurar color de los jugadores
        while (!entradaValida) {
            System.out.println("[1] - Elegir color de Jugador 1 (R/A): ");
            try {
                colorJugador1 = teclado.next().charAt(0);
                if (colorJugador1 == 'R' || colorJugador1 == 'A') {
                    entradaValida = true;
                } else {
                    System.out.println("Entrada inválida. Por favor, introduce 'R' o 'A'.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, introduce 'R' o 'A'.");
                teclado.next(); // Limpiar la entrada incorrecta
            }
        }
        entradaValida = false; // Reiniciar para la siguiente entrada

        while (!entradaValida) {
            System.out.println("[2] - Elegir color de Jugador 2 (R/A): ");
            try {
                colorJugador2 = teclado.next().charAt(0);
                if (colorJugador2 == 'R' || colorJugador2 == 'A') {
                    entradaValida = true;
                } else {
                    System.out.println("\nEntrada inválida. Por favor, introduce 'R' o 'A'.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nEntrada inválida. Por favor, introduce 'R' o 'A'.\n");
                teclado.next();
            }
        }
        entradaValida = false;

        while (!entradaValida) {
            System.out.println("[3] - Número de partidas: ");
            try {
                numeroPartidas = teclado.nextInt();
                if (numeroPartidas > 0) {
                    entradaValida = true;
                } else {
                    System.out.println("\nEntrada inválida. Por favor, introduce un número positivo.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, introduce un número entero.");
                teclado.next();
            }
        }
        entradaValida = false;

        while (!entradaValida) {
            System.out.println("[4] - Orden de salida:\n");
            System.out.println(" [1] - Aleatorio");
            System.out.println(" [2] - Sale Ganador");
            System.out.println(" [3] - Sale Perdedor");
            System.out.println(" [4] - Sale Siempre Jugador 1");

            try {
                int opcionOrden = teclado.nextInt();

                switch (opcionOrden) {
                    case 1:
                        ordenSalida = "Aleatorio";
                        entradaValida = true;
                        break;
                    case 2:
                        ordenSalida = "Sale Ganador";
                        entradaValida = true;
                        break;
                    case 3:
                        ordenSalida = "Sale Perdedor";
                        entradaValida = true;
                        break;
                    case 4:
                        ordenSalida = "Siempre Jugador 1";
                        entradaValida = true;
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, introduce un número entre 1 y 4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, introduce un número entre 1 y 4.");
                teclado.next();
            }
        }
        System.out.println("Configuración completada.\n");
    }

    /** 
     * Configura los jugadores según la modalidad y el orden de salida.
     */
    private void configurarJugadores() {
        if ("IA".equals(modalidad)) {
            this.jugador1 = new JugadorHumano(colorJugador1, "Jugador 1");
            this.jugador2 = new JugadorIA(colorJugador2, "IA");
        } else {
            this.jugador1 = new JugadorHumano('R', "Jugador 1");
            this.jugador2 = new JugadorHumano('A', "Jugador 2");
        }
        switch (ordenSalida) {
            case "Aleatorio":
                this.jugadorActual = Math.random() < 0.5 ? jugador1 : jugador2;
                break;
            case "Sale Ganador":
                this.jugadorActual = (ultimoGanador != null) ? ultimoGanador : jugador1;
                break;
            case "Sale Perdedor":
                this.jugadorActual = (ultimoPerdedor != null) ? ultimoPerdedor : jugador1;
                break;
            case "Siempre Jugador 1":
            default:
                this.jugadorActual = jugador1;
                break;
        }
    }

    /**
     * Muestra las instrucciones del juego 
     */ 
    public void mostrarInstrucciones() {
        System.out.println("\nInstrucciones:\n");
        System.out.println(
                "1. El objetivo es alinear cuatro fichas sobre un tablero formado por seis filas y siete columnas.");
        System.out.println(
                "2. Dos jugadores se turnan para dejar caer fichas en las columnas, siempre que ésta no esté completa.");
        System.out.println("3. Cada jugador dispone de 21 fichas de un color (rojas o amarillas).");
        System.out.println(
                "3. El primer jugador en conectar cuatro fichas consecutivas en una fila, columna o diagonal gana la partida.");
        System.out.println(
                "4. Si el tablero se llena y ningún jugador ha conectado cuatro fichas consecutivas, el juego termina en empate.");
        System.out.println();
    }

    /** 
     * Inicia el juego y ejecuta las partidas.
     */
    public void iniciar() {
        for (int i = 0; i < numeroPartidas; i++) {
            configurarJugadores();
            tablero = new Tablero(); // Reiniciar el tablero
            System.out.println("\n¡Comienza la partida " + (i + 1) + " de " + numeroPartidas + "!\n");
            tablero.mostrarTablero();
            boolean victoria = false;
            while (true) {
                System.out.println("Turno de " + jugadorActual.getNombre() + ".");
                int columna = jugadorActual.elegirColumna(tablero);
                if (!tablero.insertarFicha(columna, jugadorActual.getFicha())) {
                    System.out.println();
                    continue;
                }
                tablero.mostrarTablero();

                if (tablero.comprobarVictoria(jugadorActual.getFicha())) {
                    System.out.println("¡" + jugadorActual.getNombre() + " ha ganado!");
                    victoria = true;
                    ultimoGanador = jugadorActual;
                    ultimoPerdedor = (jugadorActual == jugador1) ? jugador2 : jugador1;
                    if (jugadorActual == jugador1) {
                        partidasGanadasJugador1++;
                    } else {
                        partidasGanadasJugador2++;
                    }
                    break;
                }
                cambiarTurno();
            }
            if (!victoria) {
                System.out.println("La partida terminó en empate.");
            }
        }
        mostrarResultados();
    }

    /**
     * Cambia de turno entre los jugadores.
     */
    private void cambiarTurno() {
        jugadorActual = (jugadorActual == jugador1) ? jugador2 : jugador1;
    }

    /**
     * Muestra los resultados finales de las partidas jugadas.
     */
    private void mostrarResultados() {
        System.out.println("Resultados finales:");
        System.out.println("Jugador 1 ha ganado " + partidasGanadasJugador1 + " partidas.");
        System.out.println("Jugador 2 ha ganado " + partidasGanadasJugador2 + " partidas.");
        if (partidasGanadasJugador1 > partidasGanadasJugador2) {
            System.out.println("¡Jugador 1 es el vencedor del encuentro!");
        } else if (partidasGanadasJugador2 > partidasGanadasJugador1) {
            System.out.println("¡Jugador 2 es el vencedor del encuentro!");
        } else {
            System.out.println("El encuentro terminó en empate.");
        }
    }

    /**
     * Muestra los créditos del juego.
     */ 
    public void mostrarCreditos() {
        System.out.println("\nCréditos:\n");
        System.out.println("Esta aplicación ha sido desarrollada por: \n");
        System.out.println("  - Nombre: Elena Ardila Delgado");
        System.out.println("  - Correo: eardilad01@iesalbarregas.es");
        System.out.println("  - Asignatura: Programación");
        System.out.println("  - Curso: 2024/2025");
        System.out.println("  - Centro: IES Albarregas\n");
        System.out.println("¡Gracias por jugar! :)\n");
        System.out.println();
    }
}
