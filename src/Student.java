import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** <h1>Student</h1>
 *
 * Implements the concept of student.
 * @author Nicolas Cuveillier
 *
 */
public final class Student {
    private final int uniqueId;
    private final int age;
    private final String name;
    private final String mail;
    private final List<Course> courses;
    private int tuitionBalance;
    private final String password;

    /**
     * private constructor for a Student object
     * @param unique_id unique id gave to every student
     * @param age age of the student
     * @param name name of the student
     * @param mail mail of the student
     * @param courses a list of the student's courses
     * @param password password of the student account
     */
    private Student(int unique_id, int age, String name, String mail, List<Course> courses, String password) {
        this.uniqueId = unique_id;
        this.age = age;
        this.name = name;
        this.mail = mail;
        this.courses = courses;
        this.tuitionBalance = courses.stream().mapToInt(Course::price).sum();
        this.password = password;
    }

    /**
     * static method to create a new student from registration
     * @param age age of the student
     * @param name name of the student
     * @param mail mail of the student
     * @param password password of the student account
     * @return a new student
     */
    public static Student fromRegistration(int age, String name, String mail, String password){
        Random r = new Random();
        return new Student(r.nextInt(1000000), age, name, mail, new ArrayList<>(), password);
    }

    /**
     * static method to create a new student from the fetched data from the db
     * @param uniqueId unique id gave to every student
     * @param age age of the student
     * @param name name of the student
     * @param mail mail of the student
     * @param courses a list of the student's courses
     * @param password password of the student account
     * @return a new student
     */
    public static Student fromLogin(int uniqueId, int age, String name, String mail, List<Course> courses, String password){
        return new Student(uniqueId, age, name, mail, courses, password);
    }

    /**
     * register a course in the student's courses list
     * @param course a course
     */
    public void registerACourse(Course course) {
        if (courses.stream().map(Course::name).noneMatch(n -> n.equals(course.name()))) {
            tuitionBalance += course.price();
            courses.add(course);
        }
    }

    /**
     * remove a course in the student's courses list
     * @param course a course
     */
    public void dropACourse(Course course) {
        if (courses.stream().map(Course::name).anyMatch(n -> n.equals(course.name()))) {
            tuitionBalance -= course.price();
            courses.remove(course);
        }
    }

    // Getters
    public String password() {
        return password;
    }

    public int tuitionBalance() {
        return tuitionBalance;
    }

    public int age() {
        return age;
    }

    public List<Course> courses() {
        return List.copyOf(courses);
    }

    public String mail() {
        return mail;
    }

    public String name() {
        return name;
    }

    public int uniqueId() {
        return uniqueId;
    }
}
