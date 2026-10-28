import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;


public class Menu {

    private static final String URL = "jdbc:h2:./biblioteca";
    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        int option = 0;
        try (Connection con = DriverManager.getConnection(URL, "sa", "")){

            do {
                // 1. Mostrar menú
                showMenu();
                // 2. Pedir opción al usuario
                option = readInteger("Elegir opción: ");

                // 3. Ejecutar acción elegida
                switch (option) {
                    case 1 -> registerAuthor(con);
                    case 2 -> listAuthors(con);
                    case 3 -> registerGenre(con);
                    case 4 -> listGenres(con);
                    case 5 -> registerBook(con);
                    case 6 -> listBooks(con);
                    case 7 -> registerMember(con);
                    case 8 -> listMembers(con);
                    case 9 -> registerLoans(con);
                    case 10 -> listLoans(con);
                    case 11 -> showStatistics(con);
                    case 99 -> System.out.println("Has salido de la biblioteca. Gracias por su visita.");
                    default -> System.out.println("Error: Opción no válida");
                }
            }while (option != 99);


        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }

        SC.close();

    }

    private static void showStatistics(Connection con) {
        System.out.println("POR HACER: VER ESTADÍSTICAS");
    }

    private static void listLoans(Connection con) {
        System.out.println("POR HACER: LISTAR PRÉSTAMOS");

    }

    private static void registerLoans(Connection con) {
        System.out.println("POR HACER: REGISTRAR PRÉSTAMO");
    }

    private static void listMembers(Connection con) {

        System.out.println("POR HACER: LISTAR SOCIOS");
    }

    private static void registerMember(Connection con) {
        System.out.println("POR HACER: REGISTRAR SOCIO");

    }

    private static void listBooks(Connection con) {

        System.out.println("POR HACER: LISTAR LIBROS");
    }

    private static void registerBook(Connection con) {

        System.out.println("POR HACER: REGISTRAR LIBRO");
    }

    private static void listGenres(Connection con) {

        System.out.println("POR HACER: LISTAR GÉNEROS");
    }

    private static void registerGenre(Connection con) {

        System.out.println("POR HACER: REGISTRAR GÉNERO");
    }

    private static void listAuthors(Connection con) {

        System.out.println("POR HACER: LISTAR AUTORES");
    }

    private static void registerAuthor(Connection con) {
        System.out.println("POR HACER: REGISTRAR AUTOR");
    }

    private static int readInteger(String message) {
        System.out.println(message);
        // Asegurar que escribe un número
        while (!SC.hasNextInt()) {
            String in = SC.next();
            System.out.println("Error: Opción no válida");
            showMenu();
            System.out.println(message);
        }
        int opt = SC.nextInt();
        SC.nextLine(); // limpiar buffer
        return opt;
    }

    private static void showMenu() {
        System.out.println("\n------------- BIBLIOTECA --------------");
        System.out.println("1. Registrar autor ");
        System.out.println("2. Listar autores");
        System.out.println("3. Registrar género");
        System.out.println("4. Listar géneros");
        System.out.println("5. Registrar libro");
        System.out.println("6. Listar libros");
        System.out.println("7. Registrar Socio");
        System.out.println("8. Listar socios");
        System.out.println("9. Prestar libro");
        System.out.println("10.Listar préstamos");
        System.out.println("11. Estadísticas");
        System.out.println("99. Salir");
    }

}
