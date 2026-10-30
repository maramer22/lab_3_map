package repository;
/**
 * custom exception thrown when a data persistence error occurs
 * it extends RuntimeException so it doesn't force method signatures to declare it.
 */
public class RepositoryException extends RuntimeException {
    /**
     * constructs a RepositoryException with the specified detail message
     *
     * @param message the detail message explaining what went wrong
     */
    public RepositoryException(String message) {
        super(message);
    }
    /**
     * constructs a RepositoryException with the specified detail message and cause
     * this is used for exception chaining, hiding technical errors behind user-friendly messages
     *
     * @param message the detail message for the user
     * @param cause   the cause of the exception
     */
    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
