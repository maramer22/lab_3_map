package domain.validators;
import domain.User;

/**
 * validator implementation for the User entity
 */
public class UserValidator implements Validator<User> {
    /**
     * validates a User entity
     * checks if the ID is strictly positive, and if the first name and last name
     * are not empty and start with an uppercase letter
     *
     * @param entity the User entity to validate
     * @throws ValidationException if any of the validation rules are violated
     */
    @Override
    public void validate(User entity) throws ValidationException{
        String errorMessage = ""; //strangem aici toate erorile
        if (entity.getID() == null){
            errorMessage += "ID nu poate fi null!\n";
        }else if (entity.getID() <= 0){
            errorMessage += "ID trebuie sa fie mai mare decat 0!\n";
        }

        if(entity.getFirstName() == null || entity.getFirstName().trim().isEmpty()){
            errorMessage += "Prenumele nu poate fi vid! \n";
        } else if (!Character.isUpperCase(entity.getFirstName().charAt(0))){
            errorMessage += "Prenumele trebuie sa inceapa cu litera mare! \n";
        }

        if (entity.getLastName() == null || entity.getLastName().trim().isEmpty()) {
            errorMessage += "Numele nu poate fi vid!\n";
        } else if (!Character.isUpperCase(entity.getLastName().charAt(0))) {
            errorMessage += "Numele trebuie să înceapă cu literă mare!\n";
        }

        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }
    }
}
