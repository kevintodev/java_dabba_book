import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TableLoans {
    public static void main(String[] args) {
        String url = "jdbc:h2:./biblioteca";
        try (Connection con = DriverManager.getConnection(url, "sa", "");
             Statement st = con.createStatement()) {
            String sql = """
            CREATE TABLE IF NOT EXISTS prestamos(
                id INT AUTO_INCREMENT PRIMARY KEY,
                id_libro INT NOT NULL,
                id_socio INT NOT NULL,
                fecha_prestamo DATE NOT NULL,
                fecha_devolucion DATE,
                CONSTRAINT fk_prestamos_libros
                    FOREIGN KEY (id_libro)
                    REFERENCES libros(id) 
                    ON DELETE RESTRICT,
                CONSTRAINT fk_prestamos_socios
                    FOREIGN KEY (id_socio)
                    REFERENCES socios(id) 
                    ON DELETE CASCADE         
                    ON UPDATE CASCADE         
            )
            """;
            st.executeUpdate(sql);
            System.out.println("Tabla préstamos lista");


        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
