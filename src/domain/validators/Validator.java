package domain.validators;

/**
 * generic interface for validating entities
 *
 * @param <T> the type of the entity to be validated
 */
//1a refactorizare validator
@FunctionalInterface
public interface Validator<T> {
    /**
     * validates the given entity
     *
     * @param entity the entity to be validated
     * @throws ValidationException if the entity does not meet the validation rules
     */
    void validate(T entity) throws ValidationException;

    /**
     * composes this validator with another validator
     *
     * @param other the validator to be executed after the current one
     * @return a composed validator that applies both validators in sequence
     *
     */
    default Validator<T> andThen(Validator<T> other) {
        return (T entity) -> {
            this.validate(entity);
            other.validate(entity);
        };
    }
}





