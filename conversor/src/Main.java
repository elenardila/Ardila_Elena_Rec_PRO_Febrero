import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Esta clase permite gestionar la lectura de archivos en formato CSV, JSON y XML,
 * la conversión de los datos a diferentes formatos y la interacción con el usuario
 * a través de un menú por consola.
 * @author elena
 */
public class Main {

    private static String carpetaSeleccionada = null;
    private static String ficheroSeleccionado = null;
    private static List<Map<String, String>> datos = new ArrayList<>();

    /**
     * Método principal que ejecuta la aplicación, mostrando un menú por consola
     * con las opciones de selección de carpeta, lectura de fichero, conversión a otros formatos y salida.
     * @param args los argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- Conversor XML, JSON, CSV ---\n\n");
            System.out.println("1. Seleccionar carpeta");
            System.out.println("2. Lectura de fichero");
            System.out.println("3. Conversión a otro formato");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    seleccionarCarpeta(teclado);
                    break;
                case 2:
                    leerFichero(teclado);
                    break;
                case 3:
                    convertirA(teclado);
                    break;
                case 4:
                    salir = true;
                    System.out.println("\nSaliendo del programa...\n");
                    break;
                default:
                    System.out.println("\nOpción no válida. Intente de nuevo.\n");
            }
        }

        teclado.close();
    }

    /**
     * Permite al usuario seleccionar una carpeta en el sistema de archivos.
     * Se valida que la ruta sea una carpeta existente.
     * @param teclado el objeto Scanner para leer la entrada del usuario
     */
    private static void seleccionarCarpeta(Scanner teclado) {
        System.out.print("\nIngrese la ruta de la carpeta: ");
        String ruta = teclado.nextLine();
        if (new File(ruta).isDirectory()) {
            carpetaSeleccionada = ruta;
            System.out.println("\nCarpeta seleccionada: " + carpetaSeleccionada);
        } else {
            System.out.println("\nLa ruta ingresada no es válida o no es una carpeta.");
        }
    }

    /**
     * Permite al usuario leer un archivo dentro de la carpeta seleccionada.
     * Dependiendo de la extensión del archivo, se carga en la estructura de datos
     * correspondiente (CSV, JSON o XML).
     * @param teclado el objeto Scanner para leer la entrada del usuario
     */
    private static void leerFichero(Scanner teclado) {
        if (carpetaSeleccionada == null) {
            System.out.println("\nPrimero debe seleccionar una carpeta.");
            return;
        }

        System.out.print("\nIngrese el nombre del fichero (con extensión): ");
        String nombreFichero = teclado.nextLine();
        String rutaFichero = carpetaSeleccionada + File.separator + nombreFichero;

        if (new File(rutaFichero).exists()) {
            ficheroSeleccionado = rutaFichero;
            datos.clear();

            if (nombreFichero.endsWith(".csv")) {
                leerCSV(rutaFichero);
            } else if (nombreFichero.endsWith(".json")) {
                leerJSON(rutaFichero);
            } else if (nombreFichero.endsWith(".xml")) {
                leerXML(rutaFichero);
            } else {
                System.out.println("\nFormato de fichero no soportado.");
                ficheroSeleccionado = null;
            }
        } else {
            System.out.println("\nEl fichero no existe en la carpeta seleccionada.");
        }
    }

    /**
     * Lee un archivo CSV y carga sus datos en la estructura de datos correspondiente.
     * Se supone que el primer registro del archivo es el encabezado con las claves,
     * y los siguientes registros contienen los valores.
     * @param rutaFichero la ruta del archivo CSV
     */
    private static void leerCSV(String rutaFichero) {
        try {
            List<String> lineas = Files.readAllLines(Paths.get(rutaFichero));
            if (lineas.isEmpty()) {
                System.out.println("\nEl archivo CSV está vacío.");
                return;
            }

            String[] encabezados = lineas.get(0).split(",");
            for (int i = 1; i < lineas.size(); i++) {
                String[] valores = lineas.get(i).split(",");
                Map<String, String> registro = new HashMap<>();
                for (int j = 0; j < encabezados.length; j++) {
                    registro.put(encabezados[j].trim(), valores[j].trim());
                }
                datos.add(registro);
            }

            System.out.println("\nArchivo CSV leído correctamente.\n");
            System.out.println("\nDatos cargados: " + datos);
        } catch (IOException e) {
            System.out.println("Error al leer el archivo CSV: " + e.getMessage());
        }
    }

    /**
     * Lee un archivo JSON y carga sus datos en la estructura de datos correspondiente.
     * Este método maneja el formato JSON como un array de objetos, donde cada objeto
     * se convierte en un mapa clave-valor.
     * @param rutaFichero la ruta del archivo JSON
     */
    private static void leerJSON(String rutaFichero) {
        try {
            String contenido = Files.readString(Paths.get(rutaFichero));
            contenido = contenido.trim();

            if (contenido.startsWith("[") && contenido.endsWith("]")) {
                String[] objetos = contenido.substring(1, contenido.length() - 1).split("\\},\\{");

                for (String obj : objetos) {
                    obj = obj.replace("{", "").replace("}", "");
                    String[] clavesValores = obj.split(",");
                    Map<String, String> mapa = new HashMap<>();

                    for (String par : clavesValores) {
                        String[] parClaveValor = par.split(":");
                        String clave = parClaveValor[0].trim().replace("\"", "");
                        String valor = parClaveValor[1].trim().replace("\"", "");
                        mapa.put(clave, valor);
                    }
                    datos.add(mapa);
                }
            }

            System.out.println("\nArchivo JSON leído correctamente.");
            System.out.println("\nDatos cargados: " + datos);
        } catch (IOException e) {
            System.out.println("\nError al leer el archivo JSON: " + e.getMessage());
        }
    }

    /**
     * Lee un archivo XML y carga sus datos en la estructura de datos correspondiente.
     * Este método maneja el formato XML como una lista de registros, donde cada registro
     * se convierte en un mapa clave-valor.
     * @param rutaFichero la ruta del archivo XML
     */
    private static void leerXML(String rutaFichero) {
        try {
            String contenido = Files.readString(Paths.get(rutaFichero));
            String[] registros = contenido.split("</registro>");
            for (String registro : registros) {
                if (registro.contains("<registro>")) {
                    Map<String, String> mapa = new HashMap<>();
                    String[] elementos = registro.split("<");
                    for (String elemento : elementos) {
                        if (elemento.contains(">")) {
                            String clave = elemento.substring(0, elemento.indexOf(">")).trim();
                            String valor = elemento.substring(elemento.indexOf(">") + 1).trim();
                            if (!clave.isEmpty() && !valor.isEmpty() && !clave.equals("registro")) {
                                mapa.put(clave, valor);
                            }
                        }
                    }
                    datos.add(mapa);
                }
            }

            System.out.println("\nArchivo XML leído correctamente.");
            System.out.println("\nDatos cargados: " + datos);
        } catch (IOException e) {
            System.out.println("\nError al leer el archivo XML: " + e.getMessage());
        }
    }

    /**
     * Permite al usuario convertir los datos cargados a otro formato (CSV, JSON o XML).
     * Se valida que haya datos cargados previamente.
     * @param teclado el objeto Scanner para leer la entrada del usuario
     */
    private static void convertirA(Scanner teclado) {
        if (ficheroSeleccionado == null) {
            System.out.println("\nPrimero debe leer un fichero.\n");
            return;
        }

        System.out.println("\nSeleccione el formato de salida:\n");
        System.out.println("1. CSV");
        System.out.println("2. JSON");
        System.out.println("3. XML");
        System.out.print("Seleccione una opción: \n");
        int opcion = teclado.nextInt();
        teclado.nextLine(); 

        System.out.print("\nIngrese el nombre para el fichero de salida: \n");
        String nombreSalida = teclado.nextLine();

        switch (opcion) {
            case 1:
                convertirACSV(nombreSalida);
                break;
            case 2:
                convertirAJSON(nombreSalida);
                break;
            case 3:
                convertirAXML(nombreSalida);
                break;
            default:
                System.out.println("Opción no válida.\n");
        }
    }

    /**
     * Convierte los datos cargados a un archivo CSV y los guarda en un archivo.
     * @param nombreSalida el nombre del archivo de salida (sin extensión)
     */
    private static void convertirACSV(String nombreSalida) {
        try (FileWriter writer = new FileWriter(nombreSalida + ".csv")) {
            if (datos.isEmpty()) {
                System.out.println("\nNo hay datos para exportar.\n");
                return;
            }

            // Escribir encabezados
            Set<String> claves = datos.get(0).keySet();
            writer.write(String.join(",", claves));
            writer.write("\n");

            // Escribir los datos
            for (Map<String, String> registro : datos) {
                List<String> valores = new ArrayList<>();
                for (String clave : claves) {
                    valores.add(registro.get(clave));
                }
                writer.write(String.join(",", valores));
                writer.write("\n");
            }

            System.out.println("\nDatos exportados a CSV correctamente.\n");
        } catch (IOException e) {
            System.out.println("\nError al exportar a CSV: " + e.getMessage());
        }
    }

    /**
     * Convierte los datos cargados a un archivo JSON y los guarda en un archivo.
     * @param nombreSalida el nombre del archivo de salida (sin extensión)
     */
    private static void convertirAJSON(String nombreSalida) {
        try (FileWriter writer = new FileWriter(nombreSalida + ".json")) {
            if (datos.isEmpty()) {
                System.out.println("\nNo hay datos para exportar.\n");
                return;
            }

            writer.write("[\n");

            for (int i = 0; i < datos.size(); i++) {
                Map<String, String> registro = datos.get(i);
                writer.write("  {\n");

                int j = 0;
                for (Map.Entry<String, String> entry : registro.entrySet()) {
                    writer.write("    \"" + entry.getKey() + "\": \"" + entry.getValue() + "\"");
                    if (j < registro.size() - 1) {
                        writer.write(",");
                    }
                    writer.write("\n");
                    j++;
                }

                writer.write("  }");
                if (i < datos.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }

            writer.write("]\n");
            System.out.println("\nDatos exportados a JSON correctamente.");
        } catch (IOException e) {
            System.out.println("\nError al exportar a JSON: " + e.getMessage());
        }
    }

    /**
     * Convierte los datos cargados a un archivo XML y los guarda en un archivo.
     * @param nombreSalida el nombre del archivo de salida (sin extensión)
     */
    private static void convertirAXML(String nombreSalida) {
        try (FileWriter writer = new FileWriter(nombreSalida + ".xml")) {
            if (datos.isEmpty()) {
                System.out.println("\nNo hay datos para exportar.");
                return;
            }

            writer.write("<registros>\n");
            for (Map<String, String> registro : datos) {
                writer.write("  <registro>\n");
                for (Map.Entry<String, String> entry : registro.entrySet()) {
                    writer.write("    <" + entry.getKey() + ">" + entry.getValue() + "</" + entry.getKey() + ">\n");
                }
                writer.write("  </registro>\n");
            }
            writer.write("</registros>\n");

            System.out.println("\nDatos exportados a XML correctamente.");
        } catch (IOException e) {
            System.out.println("\nError al exportar a XML: " + e.getMessage());
        }
    }
}

