import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TableMembers {
    public static void main(String[] args) {
        String url = "jdbc:h2:./biblioteca";
        try (Connection con = DriverManager.getConnection(url, "sa", "");
             Statement st = con.createStatement()) {
            String sql = """
            CREATE TABLE IF NOT EXISTS socios(
                id INT AUTO_INCREMENT PRIMARY KEY,
                nombre VARCHAR(60) NOT NULL,
                apellido1 VARCHAR(60) NOT NULL,
                apellido2 VARCHAR(60),
                email VARCHAR(120),
                telefono VARCHAR(15)
            )
            """;
            st.executeUpdate(sql);
            System.out.println("Tabla socios lista");


        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
