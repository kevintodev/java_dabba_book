import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TableGenres {
    public static void main(String[] args) {
        String url = "jdbc:h2:./biblioteca";
        try (Connection con = DriverManager.getConnection(url, "sa", "");
             Statement st = con.createStatement()) {
            String sql = """
            CREATE TABLE IF NOT EXISTS generos(
                id INT AUTO_INCREMENT PRIMARY KEY,
                nombre VARCHAR(80) NOT NULL,
                descripcion VARCHAR(500) DEFAULT 'Descripción no disponible'
            )
            """;
            st.executeUpdate(sql);
            System.out.println("Tabla géneros lista");


        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
