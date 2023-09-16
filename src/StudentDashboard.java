import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**<h1>Student </h1>
 * GUI for the student dashboard
 * @author Nicolas Cuveillier
 */
public class StudentDashboard implements ActionListener {
    private JTabbedPane tabbedPane1;
    private JPanel panel;
    private JList<Course> allCourses;
    private JList<Course> studentCourses;
    private JLabel name;
    private JLabel age;
    private JLabel mail;
    private JLabel tuition;
    private JButton deleteButton;
    private JButton selectButton;
    private final DBConnector dbConnector;
    private final Student student;

    /**
     * construct a student dashboard reusing the DBConnector instance from ManagementSystem gui
     * @param student the student
     * @param dbConnector connector the the database
     */
    public StudentDashboard(Student student, DBConnector dbConnector){
        this.dbConnector = dbConnector;
        this.student = student;
        JFrame frame = new JFrame("Student Dashboard");
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        deleteButton.addActionListener(this);
        selectButton.addActionListener(this);

        // set student info //TODO bindings
        name.setText("Firstname Lastname : " + student.name());
        age.setText("Age : " + student.age());
        mail.setText("Mail : " + student.mail());
        studentCourses.setListData(student.courses().toArray(new Course[0]));
        tuition.setText("Tuition : " + student.tuitionBalance() + " €");

        // set courses
        allCourses.setListData(dbConnector.fetchCourses().stream()
                .filter(c -> !student.courses().contains(c)).toArray(Course[]::new));
    }

    /**
     * handle course registration and removal when buttons are pressed
     * @param e pressed button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();

        if(obj == deleteButton){
            for (Course c: studentCourses.getSelectedValuesList()) {
                student.dropACourse(c);
            }
        } else if (obj == selectButton){
            for (Course c: studentCourses.getSelectedValuesList()) {
                student.registerACourse(c);
            }
        }

        // reset courses
        allCourses.setListData(dbConnector.fetchCourses().stream()
                .filter(c -> !student.courses().contains(c)).toArray(Course[]::new));
        studentCourses.setListData(student.courses().toArray(new Course[0]));

        // update tuition
        tuition.setText("Tuition : " + student.tuitionBalance() + " €");

        // store new data to db
        dbConnector.storeStudentInfo(student, student.password());
    }
}
