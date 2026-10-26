/**
 * Represents one university course.
 */
public class Course {
    private final String courseCode;
    private final String courseTitle;
    private final int units;

    /**
     * Creates a course with the supplied details.
     *
     * @param courseCode the course code
     * @param courseTitle the course title
     * @param units the number of course units
     */
    public Course(String courseCode, String courseTitle, int units) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.units = units;
    }

    /**
     * Returns the course code.
     *
     * @return the course code
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Returns the course title.
     *
     * @return the course title
     */
    public String getCourseTitle() {
        return courseTitle;
    }

    /**
     * Returns the number of course units.
     *
     * @return the course units
     */
    public int getUnits() {
        return units;
    }

    /**
     * Returns a readable description of this course.
     *
     * @return the course details
     */
    @Override
    public String toString() {
        return courseCode + " - " + courseTitle + " (" + units + " units)";
    }
}