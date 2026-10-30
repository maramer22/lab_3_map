package domain.validators;
/**
 * custom exception thrown when an entity fails validation
 */
public class ValidationException extends RuntimeException {
    /**
     * constructs a ValidationException with the specified error message
     *
     * @param message the detail message explaining the validation error
     */
    public ValidationException(String message){
        super(message); //trimite mesajul mai departe catre clasa de baza RuntimeException
    }
    /**
     * constructs a ValidationException with the specified error message and cause
     *
     * @param message the detail message explaining the validation error
     * @param cause   the original exception that caused this validation error
     */
    public ValidationException(String message, Throwable cause){
        super(message, cause);
    }
}
