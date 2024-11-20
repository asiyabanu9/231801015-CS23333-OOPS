import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/note_pad";
    private static final String USER = "root"; // XAMPP default username
    private static final String PASSWORD = ""; // Leave blank if no password is set

    // Method to get a connection to the database
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
            return null;
        }
    }

    // Method to create the notes table with specified fields in the desired order
    public static void createNotesTable() {
        String createNotesTable = "CREATE TABLE IF NOT EXISTS notes (" +
                                  "note_id INT PRIMARY KEY AUTO_INCREMENT, " +
                                  "subcode VARCHAR(20) NOT NULL, " +         // Subject Code
                                  "subject VARCHAR(50) NOT NULL, " +        // Subject Name
                                  "lesson_name VARCHAR(100) NOT NULL, " +   // Lesson Name
                                  "notes TEXT, " +                          // Notes
                                  "remainder VARCHAR(100)" +                // Reminder
                                  ");";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(createNotesTable)) {
            stmt.executeUpdate();
            System.out.println("Notes table created or already exists.");
        } catch (SQLException e) {
            System.out.println("Error creating notes table.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Create notes table if not exists
        createNotesTable();
    }
}

