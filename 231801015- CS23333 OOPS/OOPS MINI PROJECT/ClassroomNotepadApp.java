import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClassroomNotepadApp {
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

    // Method to create the popup window for entering classroom notes
    public static void showNoteEntryPopup() {
        JFrame frame = new JFrame("Enter Classroom Note");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 450);

        // Creating form elements in specified order
        JLabel subcodeLabel = new JLabel("Subject Code:");
        JTextField subcodeField = new JTextField(15);

        JLabel subjectLabel = new JLabel("Subject Name:");
        JTextField subjectField = new JTextField(15);

        JLabel lessonLabel = new JLabel("Lesson Name:");
        JTextField lessonField = new JTextField(15);

        JLabel notesLabel = new JLabel("Notes:");
        JTextArea notesArea = new JTextArea(4, 15);
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);

        JLabel remainderLabel = new JLabel("Reminder:");
        JTextField remainderField = new JTextField(15);

        JButton submitButton = new JButton("Submit");

        // Adding an action listener to the submit button
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String subcode = subcodeField.getText();
                String subject = subjectField.getText();
                String lessonName = lessonField.getText();
                String notes = notesArea.getText();
                String remainder = remainderField.getText();

                // Validate input
                if (subcode.isEmpty() || subject.isEmpty() || lessonName.isEmpty() || notes.isEmpty() || remainder.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Call method to insert into database
                insertClassroomNote(subcode, subject, lessonName, notes, remainder);
                JOptionPane.showMessageDialog(frame, "Classroom note added successfully!");

                // Clear input fields after successful insertion
                subcodeField.setText("");
                subjectField.setText("");
                lessonField.setText("");
                notesArea.setText("");
                remainderField.setText("");
            }
        });

        // Adding components to the frame in the specified order
        frame.setLayout(new GridLayout(6, 2));
        frame.add(subcodeLabel);
        frame.add(subcodeField);
        frame.add(subjectLabel);
        frame.add(subjectField);
        frame.add(lessonLabel);
        frame.add(lessonField);
        frame.add(notesLabel);
        frame.add(new JScrollPane(notesArea));
        frame.add(remainderLabel);
        frame.add(remainderField);
        frame.add(submitButton);

        frame.setVisible(true); // Display the popup
    }

    // Method to insert a classroom note into the database
    public static void insertClassroomNote(String subcode, String subject, String lessonName, String notes, String remainder) {
        String insertQuery = "INSERT INTO notes (subcode, subject, lesson_name, notes, remainder) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            pstmt.setString(1, subcode);
            pstmt.setString(2, subject);
            pstmt.setString(3, lessonName);
            pstmt.setString(4, notes);
            pstmt.setString(5, remainder);

            pstmt.executeUpdate();
            System.out.println("Classroom note inserted successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting classroom note.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Show the popup window for entering classroom notes
        showNoteEntryPopup();
    }
}
