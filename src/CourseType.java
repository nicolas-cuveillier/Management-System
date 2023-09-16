/**<h1>CourseType</h1>
 * enum for all the courses' types
 * @author Nicolas Cuveillier
 */
public enum CourseType {
    MATHEMATICS,
    IT,
    PHYSICS,
    SHS;

    /**
     * static method to retrieve the course type from a string name
     * @param type name of the course
     * @return the correspondent CourseType
     */
    public static CourseType fromString(String type){
        switch (type){
            case "MATHEMATICS":
                return MATHEMATICS;
            case "IT":
                return IT;
            case "PHYSICS":
                return PHYSICS;
            case "SHS":
                return SHS;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        switch (this.ordinal()){
            case 0:
                return "MATHEMATICS";
            case 1:
                return "IT";
            case 2:
                return "PHYSICS";
            case 3:
                return "SHS";
            default:
                return "ERROR";
        }

    }
}
