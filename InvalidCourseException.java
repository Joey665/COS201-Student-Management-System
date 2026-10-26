/**
 * Signals that course data does not satisfy the application's rules.
 */
public class InvalidCourseException extends Exception {
    /**
     * Creates an exception with a helpful validation message.
     *
     * @param message the validation failure message
     */
    public InvalidCourseException(String message) {
        super(message);
    }
}