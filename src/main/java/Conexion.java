import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    public static void main(String[] args) {
        // 1. Las tres piezas de la conexión. Guardadas en variables
        String url = "jdbc:h2:./biblioteca"; // donde está la base de datos
        String user = "sa";              // usuario por defecto de H2
        String password = "";            // contraseña vacía por defecto

        // 2. try-with-resource: Abre la conexión y la CIERRA SOLA al terminar
        try(Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Conexión abierta con H2");
        } catch (SQLException e) {
            System.out.println("No se pudo conectar" + e.getMessage());
        }
        System.out.println("Programa terminado");

    }
}
