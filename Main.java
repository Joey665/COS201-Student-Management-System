import java.util.Scanner;

/**
 * Starts the Student Course Management System and handles its menu.
 */
public class Main {
    /**
     * Runs the console application.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        CourseManager courseManager = new CourseManager();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                displayMenu();
                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        addCourseFromInput(scanner, courseManager);
                        break;
                    case "2":
                        courseManager.displayAll();
                        break;
                    case "3":
                        searchCourseFromInput(scanner, courseManager);
                        break;
                    case "4":
                        displayTotalUnits(courseManager);
                        break;
                    case "5":
                        saveCourses(scanner, courseManager);
                        break;
                    case "6":
                        courseManager.loadFromFile();
                        break;
                    case "7":
                        running = false;
                        System.out.println("Program ended.");
                        break;
                    default:
                        System.out.println("Invalid menu choice. Please select 1, 2, 3, 4, 5, 6, or 7.");
                }
            }
        }
    }

    /**
    * Displays the current menu.
     */
    private static void displayMenu() {
        System.out.println("\nStudent Course Management System");
        System.out.println("1. Add Course");
        System.out.println("2. View All Courses");
        System.out.println("3. Search Course by Code");
        System.out.println("4. Compute Total Units");
        System.out.println("5. Save to File");
        System.out.println("6. Load from File");
        System.out.println("7. Exit Program");
        System.out.print("Enter your choice: ");
    }

    /**
     * Reads course details and asks the manager to validate and add the course.
     *
     * @param scanner the input scanner
     * @param courseManager the course manager
     */
    private static void addCourseFromInput(Scanner scanner, CourseManager courseManager) {
        try {
            System.out.print("Enter course code: ");
            String courseCode = scanner.nextLine().trim();

            System.out.print("Enter course title: ");
            String courseTitle = scanner.nextLine().trim();

            System.out.print("Enter units: ");
            int units = Integer.parseInt(scanner.nextLine().trim());

            courseManager.addCourse(courseCode, courseTitle, units);
            System.out.println("Course added successfully.");
        } catch (NumberFormatException exception) {
            System.out.println("Units must be a whole number.");
        } catch (InvalidCourseException exception) {
            System.out.println("Could not add course: " + exception.getMessage());
        }
    }

    /**
     * Reads a course code and displays the matching course when found.
     *
     * @param scanner the input scanner
     * @param courseManager the course manager
     */
    private static void searchCourseFromInput(Scanner scanner, CourseManager courseManager) {
        System.out.print("Enter course code to search: ");
        String courseCode = scanner.nextLine().trim();
        Course course = courseManager.searchByCode(courseCode);

        if (course == null) {
            if (courseManager.isEmpty()) {
                return;
            }
            System.out.println("Course not found.");
        } else {
            System.out.println("Course found: " + course);
        }
    }

    /**
     * Displays the total number of units in all courses.
     *
     * @param courseManager the course manager
     */
    private static void displayTotalUnits(CourseManager courseManager) {
        int totalUnits = courseManager.computeTotalUnits();
        if (totalUnits > 0) {
            System.out.println("Total units: " + totalUnits);
        }
    }

    /**
     * Confirms an empty save and then asks the manager to save courses.
     *
     * @param scanner the input scanner
     * @param courseManager the course manager
     */
    private static void saveCourses(Scanner scanner, CourseManager courseManager) {
        if (courseManager.isEmpty()) {
            System.out.print("The course list is empty. Save an empty file? (Y/N): ");
            String confirmation = scanner.nextLine().trim();
            if (!confirmation.equalsIgnoreCase("Y")) {
                System.out.println("Save cancelled.");
                return;
            }
        }

        courseManager.saveToFile();
    }
}