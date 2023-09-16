/**<h1>Course</h1>
 * Implement the notion of a Course
 * @author Nicolas Cuveillier
 */
public final class Course {
    private final CourseType type;
    private final String name;
    private final int price;

    /**
     * constructor for a new Course
     * @param type (CourseType) the field of the course
     * @param name name of the course
     * @param price price of the course
     */
    public Course(CourseType type, String name, int price) {
        this.type = type;
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object course){
        Course c = (Course) course;
        return type == c.type && name.equals(c.name) && price == c.price;
    }

    @Override
    public String toString(){
        return name + " (" + type + ") " + price + " €";
    }

    // Getter
    public String name() {
        return name;
    }

    public int price() {
        return price;
    }
}
