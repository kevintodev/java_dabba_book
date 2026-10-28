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
        System.out.println("1. Registrar autor\t\t2. Listar autores");
        System.out.println("3. Registrar género\t\t4. Listar géneros");
        System.out.println("5. Registrar libro\t\t6. Listar libros");
        System.out.println("7. Registrar Socio\t\t8. Listar socios");
        System.out.println("9. Prestar libro\t\t10. Listar préstamos");
        System.out.println("11. Estadísticas\t\t99. Salir");
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
        /* Solicitar por teclado el ID del autor, id de género, isbn y sinopsis
         y lo guarda en la tabla libros */
        System.out.println("\n------------- REGISTRAR LIBRO --------------");

        listAuthors(con); // Lista autores para que el usuario elija la id
        int idAuthor = readInteger("[+] ID del autor: ");
        listGenres(con); // Lista géneros para que el usuario elija la id
        int idGenre = readInteger("[+] ID del género: ");
        String title = readStringInput("[+] Título: ");
        String isbn = readStringInput("[+] ISBN: ");
        int year = readInteger("Año de publicación: ");
        String sinopsis = readStringInput("[+] Sinopsis: ");

        String sql = "INSERT INTO libros (id_autor, id_genero, titulo, isbn, anio, sinopsis) VALUES(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idAuthor);
            ps.setInt(2, idGenre);
            ps.setString(3, title);
            ps.setString(4, isbn);
            ps.setInt(5, year);
            ps.setString(6, sinopsis);
            ps.executeUpdate();

            try ( ResultSet rs = ps.getGeneratedKeys() ) {
                if (rs.next()) {
                    System.out.println("Libro registrado con id: " + rs.getInt(1));
                }
            } catch (SQLException e) {
                System.out.println("Error durante el registro: " + e.getMessage());
            }

        } catch ( SQLException e ) {
            System.out.println("Error al registrar libro: " + e.getMessage());
        }
    }

    private static void listBooks(Connection con) {
        /* Lista los libros registrados en la biblioteca */
        String sql = """
                SELECT libros.id, libros.titulo, libros.isbn, libros.anio, libros.sinopsis,
                autores.nombre AS autor, generos.nombre AS genero
                FROM libros
                INNER JOIN autores
                    ON libros.id_autor = autores.id
                INNER JOIN generos
                    ON libros.id_genero = generos.id
        """;
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ResultSet rs = ps.executeQuery();
            System.out.println("\n====================== LISTA DE LIBROS ============================");

            while ( rs.next() ) {
                int id = rs.getInt("id");
                String title = rs.getString("titulo");
                String author = rs.getString("autor");
                String genre = rs.getString("genero");
                String isbn = rs.getString("isbn");
                int year = rs.getInt("anio");
                String synopsis = rs.getString("sinopsis");


                System.out.println("---------------------------------------------------------------------------");
                System.out.printf("ID: %d | Título: %s | Autor: %s | Género: %s | ISBN: %s | Año: %d | Sinopsis: %s%n",
                        id, title, author, genre, isbn, year, synopsis);
            }
            System.out.println("---------------------------------------------------------------------------");
            System.out.println("======================================================================");

        } catch ( SQLException e ) {
            System.out.println("Error al leer la tabla libros: " + e.getMessage());
        }
    }
    private static void registerMember(Connection con) {
        /* Solicita el nombre, apellidos, email y teléfono y lo guarda en la tabla socios */
        System.out.println("\n------------- REGISTRAR SOCIO --------------");
        String name = readStringInput("[+] Nombre: ");
        String firstLastname = readStringInput("[+] Primer apellido: ");
        String secondLastname = readStringInput("[+] Segundo apellido: ");
        String email = readStringInput("[+] Email: ");
        String phone = readStringInput("[+] Teléfono: ");

        String sql = "INSERT INTO socios (nombre, apellido1, apellido2, email, telefono ) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, firstLastname);
            ps.setString(3, secondLastname);
            ps.setString(4, email);
            ps.setString(5, phone);
            ps.executeUpdate();

            try ( ResultSet rs = ps.getGeneratedKeys() ) {
                if (rs.next()) {
                    System.out.println("Socio registrado con id: " + rs.getInt(1));
                }
            } catch (SQLException e) {
                System.out.println("Error durante el registro: " + e.getMessage());
            }

        } catch ( SQLException e ) {
            System.out.println("Error al registrar al socio: " + e.getMessage());
        }

    }
    private static void listMembers(Connection con) {
        /* Lista los socios registrados en la biblioteca */
        String sql = "SELECT * FROM socios";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ResultSet rs = ps.executeQuery();
            System.out.println("\n====================== LISTA DE SOCIOS ============================");

            while ( rs.next() ) {
                int id = rs.getInt("id");
                String name = rs.getString("nombre");
                String firstLastname = rs.getString("apellido1");
                String secondLastname = rs.getString("apellido2");
                String email = rs.getString("email");
                String phone = rs.getString("telefono");
                System.out.println("---------------------------------------------------------------------------");
                System.out.printf("ID: %d | Nombre: %s | Apellido 1: %s | Apellido 2: %s | Email: %s | Teléfono: %s %n",
                        id, name, firstLastname, secondLastname, email, phone);
            }
            System.out.println("---------------------------------------------------------------------------");
            System.out.println("======================================================================");

        } catch ( SQLException e ) {
            System.out.println("Error al leer la tabla socios: " + e.getMessage());
        }

    }

    private static void registerLoan(Connection con) {
        /* Solicitar por teclado el ID del libro y  ID de socio  y lo guarda en la tabla préstamos
        * La fecha de prestamo se pone la fecha actual, y la fecha de devolución a NULL por defecto
        * */
        System.out.println("\n------------- PRESTAR LIBRO --------------");

        listBooks(con); // Lista libros para que el usuario elija la id
        int idBook = readInteger("[+] ID del libro: ");
        listMembers(con); // Lista socios para que el usuario elija la id
        int idMember = readInteger("[+] ID del socio: ");

        String sql = "INSERT INTO prestamos (id_libro, id_socio, fecha_prestamo) VALUES(?, ?, CURRENT_DATE)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idBook);
            ps.setInt(2, idMember);
            ps.executeUpdate();

            try ( ResultSet rs = ps.getGeneratedKeys() ) {
                if (rs.next()) {
                    System.out.println("Préstamo registrado con id: " + rs.getInt(1));
                }
            } catch (SQLException e) {
                System.out.println("Error durante el registro: " + e.getMessage());
            }

        } catch ( SQLException e ) {
            System.out.println("Error al registrar préstamo: " + e.getMessage());
        }
    }

    private static void listLoans(Connection con) {
        /* Lista los préstamos registrados en la biblioteca*/
        // Con coalesce, como fecha_devolución puede ser null, el valor dará 'Libro no devuelto'
        String sql = """
                SELECT
                prestamos.id,
                CAST(prestamos.fecha_prestamo AS VARCHAR) AS fecha_prestamo,
                COALESCE(CAST(prestamos.fecha_devolucion AS VARCHAR), 'Libro no devuelto') AS fecha_devolucion,
                libros.titulo AS libro,
                socios.nombre AS nombre_socio,
                socios.apellido1 AS apellido_socio
                FROM prestamos
                INNER JOIN libros
                    ON prestamos.id_libro = libros.id
                INNER JOIN socios
                    ON prestamos.id_socio = socios.id
                """;
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ResultSet rs = ps.executeQuery();
            System.out.println("\n====================== LISTA DE LIBROS ============================");

            while ( rs.next() ) {
                int id = rs.getInt("id");
                String loanDate = rs.getString("fecha_prestamo");
                String returnDate = rs.getString("fecha_devolucion");
                String book = rs.getString("libro");
                String memberName = rs.getString("nombre_socio");
                String memberLastname = rs.getString("apellido_socio");


                System.out.println("---------------------------------------------------------------------------");
                System.out.printf("ID: %d | Socio: %s %s | Libro: %s | Fecha préstamo: %s | Fecha devolución: %s%n",
                        id, memberName, memberLastname, book, loanDate, returnDate);
            }
            System.out.println("---------------------------------------------------------------------------");
            System.out.println("======================================================================");

        } catch ( SQLException e ) {
            System.out.println("Error al leer la tabla libros: " + e.getMessage());
        }
    }

    private static void showStatistics(Connection con) {
        /* Muestra la cantidad de libros de la base de datos, de libros prestados
        * y de socios*/
        int nBooks = countBooks(con);
        int nMembers = countMembers(con);
        int nLoans = countLoans(con);
        System.out.println("================== ESTADÍSTICAS =====================");
        System.out.println("[+] Libros registrados: " + nBooks);
        System.out.println("[+] Socios registrados: " + nMembers);
        System.out.println("[+] Préstamos de libros registrados: " + nLoans);
        System.out.println("=====================================================");
    }

    private static int countLoans(Connection con) {
        String sql = "SELECT COUNT(*) AS total FROM prestamos";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Error al contar préstamos: " + e.getMessage());
        }
        return 0;
    }

    private static int countMembers(Connection con) {
        String sql = "SELECT COUNT(*) AS total FROM socios";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Error al contar préstamos: " + e.getMessage());
        }
        return 0;
    }

    private static int countBooks(Connection con) {
        String sql = "SELECT COUNT(*) AS total FROM libros";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Error al contar préstamos: " + e.getMessage());
        }
        return 0;
    }

    private static int readInteger(String message) {
        /* Función para leer un entero vía Scanner. Devuelve un entero*/
        System.out.print(message);
        // Asegurar que escribe un número
        while (!SC.hasNextInt()) {
            String in = SC.next();
            System.out.println("Error: Opción no válida");
            showMenu();
            System.out.print(message);
        }
        int opt = SC.nextInt();
        SC.nextLine(); // limpiar buffer
        return opt;
    }

    private static String readStringInput(String message) {
        System.out.print(message);
        String output = SC.nextLine();
        return output;
    }



}
