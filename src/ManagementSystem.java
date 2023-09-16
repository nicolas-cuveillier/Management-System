import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**<h1>Management System</h1>
 * GUI for the login/registration process
 * @author Nicolas Cuveillier
 */
public class ManagementSystem implements ActionListener {

    private JTabbedPane tabbedPane1;
    private JButton registerButton;
    private JButton loginButton;
    private JPasswordField passwordRegister;
    private JPanel panel;
    private JTextField uniqueId;
    private JPasswordField passwordLogin;
    private JTextField name;
    private JTextField age;
    private JTextField mail;
    private final DBConnector dbConnector;
    private final JFrame frame;

    /**
     * constructor for the gui
     */
    public ManagementSystem(){
        frame = new JFrame("Management System");
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);
        dbConnector = new DBConnector("mongodb+srv://nicolascuv:%s@smackscluster.upofd31.mongodb.net/?retryWrites=true&w=majority","XZ2wMiWFN2bB0rG0", "_UniManaSys"); //TODO handle password
    }

    /**
     * handle both login or registration of a student
     * @param e button pressed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();

        Student student = null;
        if (obj == loginButton) {
            student = dbConnector.fetchStudentInfo(Integer.parseInt(uniqueId.getText()), String.valueOf(passwordLogin.getPassword()));
            if (Objects.isNull(student)) {
                passwordLogin.setBackground(Color.red); //TODO handle false credentials better
                return;
            }
        } else if (obj == registerButton) {
            student = Student.fromRegistration(Integer.parseInt(age.getText()), name.getText(), mail.getText(), String.valueOf(passwordRegister.getPassword()));
            dbConnector.storeStudentInfo(student, String.valueOf(passwordRegister.getPassword()));
        }
        new StudentDashboard(student, dbConnector);
        frame.dispose();
    }

    public DBConnector getDbConnector() {
        return dbConnector;
    }
}
