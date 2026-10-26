import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Manages the courses stored by the application.
 */
public class CourseManager {
    private static final String FILE_NAME = "courses.txt";
    private final ArrayList<Course> courses;
    private static final Pattern COURSE_CODE_PATTERN = Pattern.compile("[A-Z]{3}[0-9]{3}");

    /**
     * Creates an empty course manager.
     */
    public CourseManager() {
        courses = new ArrayList<>();
    }

    /**
     * Reports whether the course list is empty.
     *
     * @return true when no courses have been added
     */
    public boolean isEmpty() {
        return courses.isEmpty();
    }

    /**
     * Validates and adds a course to the list.
     *
     * @param courseCode the course code entered by the user
     * @param courseTitle the course title entered by the user
     * @param units the number of course units
     * @throws InvalidCourseException if any course rule is violated
     */
    public void addCourse(String courseCode, String courseTitle, int units)
            throws InvalidCourseException {
        String normalizedCode = courseCode.trim().toUpperCase();
        String normalizedTitle = courseTitle.trim();

        validateCourse(normalizedCode, normalizedTitle, units);

        addValidatedCourse(courses, normalizedCode, normalizedTitle, units);
    }

    /**
     * Validates the fields required to create a course.
     *
     * @param courseCode the normalized course code
     * @param courseTitle the normalized course title
     * @param units the number of course units
     * @throws InvalidCourseException if a field is invalid
     */
    private void validateCourse(String courseCode, String courseTitle, int units)
            throws InvalidCourseException {
        if (!COURSE_CODE_PATTERN.matcher(courseCode).matches()) {
            throw new InvalidCourseException("Course code must contain 3 letters followed by 3 digits.");
        }
        if (courseTitle.isEmpty()) {
            throw new InvalidCourseException("Course title must not be empty.");
        }
        if (units < 1 || units > 6) {
            throw new InvalidCourseException("Units must be an integer between 1 and 6.");
        }
    }

    /**
     * Adds already normalized and validated course data to a supplied list.
     *
     * @param targetCourses the list receiving the course
     * @param courseCode the normalized course code
     * @param courseTitle the normalized course title
     * @param units the number of course units
     * @throws InvalidCourseException if the code is duplicated
     */
    private void addValidatedCourse(ArrayList<Course> targetCourses, String courseCode,
            String courseTitle, int units) throws InvalidCourseException {
        for (Course course : targetCourses) {
            if (course.getCourseCode().equals(courseCode)) {
                throw new InvalidCourseException("A course with that code already exists.");
            }
        }

        targetCourses.add(new Course(courseCode, courseTitle, units));
    }

    /**
     * Displays all courses in a neatly aligned table.
     */
    public void displayAll() {
        if (courses.isEmpty()) {
            System.out.println("No courses have been added yet.");
            return;
        }

        System.out.printf("%-12s %-40s %5s%n", "Code", "Title", "Units");
        System.out.println("------------------------------------------------------------");
        for (Course course : courses) {
            System.out.printf("%-12s %-40s %5d%n",
                    course.getCourseCode(), course.getCourseTitle(), course.getUnits());
        }
    }

    /**
     * Searches for a course by its code using a recursive helper.
     *
     * @param courseCode the code to search for
     * @return the matching course, or null when no course matches
     */
    public Course searchByCode(String courseCode) {
        if (courses.isEmpty()) {
            System.out.println("No courses have been added yet.");
            return null;
        }

        String normalizedCode = courseCode.trim().toUpperCase();
        return searchByCodeRecursive(normalizedCode, 0);
    }

    /**
     * Searches the list from the supplied index until a matching course is found.
     *
     * @param courseCode the normalized code to search for
     * @param index the current list index
     * @return the matching course, or null at the end of the list
     */
    private Course searchByCodeRecursive(String courseCode, int index) {
        // RECURSION USED HERE
        if (index == courses.size()) {
            return null;
        }

        Course currentCourse = courses.get(index);
        if (currentCourse.getCourseCode().equals(courseCode)) {
            return currentCourse;
        }

        return searchByCodeRecursive(courseCode, index + 1);
    }

    /**
     * Computes the total units using a recursive helper.
     *
     * @return the total units, or zero when no courses exist
     */
    public int computeTotalUnits() {
        if (courses.isEmpty()) {
            System.out.println("No courses have been added yet.");
            return 0;
        }

        return computeTotalUnitsRecursive(0);
    }

    /**
     * Adds course units from the supplied index through the end of the list.
     *
     * @param index the current list index
     * @return the total units from this index onward
     */
    private int computeTotalUnitsRecursive(int index) {
        // RECURSION USED HERE
        if (index == courses.size()) {
            return 0;
        }

        return courses.get(index).getUnits() + computeTotalUnitsRecursive(index + 1);
    }

    /**
     * Saves all courses to courses.txt using the format CODE|TITLE|UNITS.
     */
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Course course : courses) {
                writer.write(course.getCourseCode() + "|" + course.getCourseTitle()
                        + "|" + course.getUnits());
                writer.newLine();
            }
            System.out.println("Courses saved successfully to " + FILE_NAME + ".");
        } catch (IOException exception) {
            System.out.println("Could not save courses: " + exception.getMessage());
        }
    }

    /**
     * Loads courses from courses.txt, replacing the current list.
     */
    public void loadFromFile() {
        ArrayList<Course> loadedCourses = new ArrayList<>();
        int loadedCount = 0;
        int malformedCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\|", -1);
                if (fields.length != 3) {
                    malformedCount++;
                    continue;
                }

                try {
                    String courseCode = fields[0].trim().toUpperCase();
                    String courseTitle = fields[1].trim();
                    int units = Integer.parseInt(fields[2].trim());
                    validateCourse(courseCode, courseTitle, units);
                    addValidatedCourse(loadedCourses, courseCode, courseTitle, units);
                    loadedCount++;
                } catch (NumberFormatException | InvalidCourseException exception) {
                    malformedCount++;
                }
            }

            courses.clear();
            courses.addAll(loadedCourses);
            System.out.println("Loaded " + loadedCount + " course(s). Skipped "
                    + malformedCount + " malformed line(s).");
        } catch (FileNotFoundException exception) {
            System.out.println("No courses file was found. Nothing was loaded.");
        } catch (IOException exception) {
            System.out.println("Could not load courses: " + exception.getMessage());
        }
    }
}