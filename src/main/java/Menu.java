import java.sql.*;
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
                    case 9 -> registerLoan(con);
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

    private static void registerAuthor(Connection con) {
        /* Solicita datos del autor por teclado y lo guarda en la base de datos  */
        System.out.println("\n------------- REGISTRAR AUTOR --------------");
        String name = readStringInput("[+] Nombre del autor: ");
        int year = readInteger("[+] Año de nacimiento: ");
        String nationality = readStringInput("[+] Nacionalidad: ");
        int nPrizes = readInteger("[+] Número de premios: ");

        String sql = "INSERT INTO autores (nombre, anio_nacimiento, nacionalidad, premios) VALUES(?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setInt(2, year);
            ps.setString(3, nationality);
            ps.setInt(4, nPrizes);
            ps.executeUpdate();

            try ( ResultSet rs = ps.getGeneratedKeys() ) {
                if (rs.next()) {
                    System.out.println("Autor registrado con id: " + rs.getInt(1));
                }
            } catch (SQLException e) {
                System.out.println("Error durante el registro: " + e.getMessage());
            }

        } catch ( SQLException e ) {
            System.out.println("Error al registrar autor: " + e.getMessage());
        }


    }



    private static void listAuthors(Connection con) {
        /* Lista todos los autores registrados en la biblioteca */

        String sql = "SELECT * FROM autores";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ResultSet rs = ps.executeQuery();
            System.out.println("\n====================== LISTA DE AUTORES ============================");

            while ( rs.next() ) {
                int id = rs.getInt("id");
                String name = rs.getString("nombre");
                int year = rs.getInt("anio_nacimiento");
                String nationality = rs.getString("nacionalidad");
                int nPrizes = rs.getInt("premios");
                System.out.println("---------------------------------------------------------------------------");
                System.out.printf("ID: %d | Nombre: %s | Año de nacimiento: %d |  Nacionalidad: %s | Premios: %d%n",
                        id, name, year, nationality, nPrizes);
            }
            System.out.println("---------------------------------------------------------------------------");
            System.out.println("======================================================================");

        } catch ( SQLException e ) {
            System.out.println("Error al leer autores: " + e.getMessage());
        }
    }


    private static void registerGenre(Connection con) {
        /* Solicita el nombre y la descripción del género por teclado y lo guarda en la tabla géneros */
        System.out.println("\n------------- REGISTRAR GÉNERO --------------");
        String name = readStringInput("[+] Nombre del género: ");
        String description = readStringInput("[+] Descripción: ");

        String sql = "INSERT INTO generos (nombre, descripcion) VALUES(?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, description);
            ps.executeUpdate();

            try ( ResultSet rs = ps.getGeneratedKeys() ) {
                if (rs.next()) {
                    System.out.println("Género registrado con id: " + rs.getInt(1));
                }
            } catch (SQLException e) {
                System.out.println("Error durante el registro: " + e.getMessage());
            }

        } catch ( SQLException e ) {
            System.out.println("Error al registrar género: " + e.getMessage());
        }
    }

    private static void listGenres(Connection con) {
        /* Lista los géneros de libros registrados en la biblioteca */
        String sql = "SELECT * FROM generos";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ResultSet rs = ps.executeQuery();
            System.out.println("\n====================== LISTA DE AUTORES ============================");

            while ( rs.next() ) {
                int id = rs.getInt("id");
                String name = rs.getString("nombre");
                String description = rs.getString("descripcion");
                System.out.println("---------------------------------------------------------------------------");
                System.out.printf("ID: %d | Nombre: %s | Descripción: %s%n", id, name, description);
            }
            System.out.println("---------------------------------------------------------------------------");
            System.out.println("======================================================================");

        } catch ( SQLException e ) {
            System.out.println("Error al leer la tabla géneros: " + e.getMessage());
        }
    }

    private static void registerBook(Connection con) {

        System.out.println("POR HACER: REGISTRAR LIBRO");
    }

    private static void listBooks(Connection con) {

        System.out.println("POR HACER: LISTAR LIBROS");
    }
    private static void registerMember(Connection con) {
        System.out.println("POR HACER: REGISTRAR SOCIO");

    }
    private static void listMembers(Connection con) {

        System.out.println("POR HACER: LISTAR SOCIOS");
    }

    private static void registerLoan(Connection con) {
        System.out.println("POR HACER: REGISTRAR PRÉSTAMO");
    }

    private static void listLoans(Connection con) {
        System.out.println("POR HACER: LISTAR PRÉSTAMOS");

    }

    private static void showStatistics(Connection con) {
        System.out.println("POR HACER: VER ESTADÍSTICAS");
    }

    private static int readInteger(String message) {
        /* Función para leer un entero vía Scanner. Devuelve un entero*/
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

    private static String readStringInput(String message) {
        System.out.println(message);
        String output = SC.nextLine();
        return output;
    }



}
