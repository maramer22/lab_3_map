package domain.validators;

/**
 * generic interface for validating entities
 *
 * @param <T> the type of the entity to be validated
 */
public interface Validator<T> {
    /**
     * validates the given entity
     *
     * @param entity the entity to be validated
     * @throws ValidationException if the entity does not meet the validation rules
     */
    void validate(T entity) throws ValidationException;
}
