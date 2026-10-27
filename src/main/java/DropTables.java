import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DropTables {
    public static void main(String[] args) {
        String url = "jdbc:h2:./biblioteca";
        try (Connection con = DriverManager.getConnection(url, "sa", "");
             Statement st = con.createStatement()) {
            String dropLoans = "DROP TABLE IF EXISTS prestamos;";
            String dropMembers = "DROP TABLE IF EXISTS socios;";
            String dropBooks = "DROP TABLE IF EXISTS libros;";
            String dropGenres = "DROP TABLE IF EXISTS generos;";
            String dropAuthors = "DROP TABLE IF EXISTS autores;";

            st.executeUpdate(dropLoans);
            System.out.println("Tabla préstamos borrada");
            st.executeUpdate(dropMembers);
            System.out.println("Tabla socios borrada");
            st.executeUpdate(dropBooks);
            System.out.println("Tabla libros borrada");
            st.executeUpdate(dropGenres);
            System.out.println("Tabla géneros borrada");
            st.executeUpdate(dropAuthors);
            System.out.println("Tabla autores borrada");


        } catch (SQLException e) {
            System.out.println("Error al borrar: " + e.getMessage());
        }
    }
}
