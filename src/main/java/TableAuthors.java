import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TableAuthors {
    public static void main(String[] args) {
        String url = "jdbc:h2:./biblioteca";
        try (Connection con = DriverManager.getConnection(url, "sa", "");
             Statement st = con.createStatement()) {
            String sql = """
                CREATE TABLE IF NOT EXISTS autores(
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nombre VARCHAR(120) NOT NULL,
                    anio_nacimiento INT,
                    nacionalidad VARCHAR(60),
                    premios INT DEFAULT 0
                )
            """;
            st.executeUpdate(sql);
            System.out.println("Tabla autores lista");


        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
