import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TableBooks {
    public static void main(String[] args) {
        String url = "jdbc:h2:./biblioteca";
        try (Connection con = DriverManager.getConnection(url, "sa", "");
             Statement st = con.createStatement()) {
            String sql = """
            CREATE TABLE IF NOT EXISTS libros(
                id INT AUTO_INCREMENT PRIMARY KEY,
                id_autor INT NOT NULL,
                id_genero INT,
                titulo VARCHAR(200) NOT NULL,
                isbn VARCHAR(20),
                anio INT,
                sinopsis VARCHAR(500) DEFAULT 'No disponible',
                CONSTRAINT fk_libros_autores
                    FOREIGN KEY (id_autor)
                    REFERENCES autores(id) 
                    ON DELETE CASCADE
                    ON UPDATE CASCADE,
                CONSTRAINT fk_libros_generos
                    FOREIGN KEY (id_genero)
                    REFERENCES generos(id) 
                    ON DELETE CASCADE
                    ON UPDATE CASCADE
            )
            """;
            st.executeUpdate(sql);
            System.out.println("Tabla libros lista");


        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
