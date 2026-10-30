package domain.validators;
import domain.Friendship;
/**
 * validator implementation for the Friendship entity
 */
public class FriendshipValidator implements Validator<Friendship> {
    /**
     * validates a Friendship entity
     * checks if the two users involved in the friendship are different
     * and that their IDs are not null
     *
     * @param entity the Friendship entity to validate
     * @throws ValidationException if the validation rules are violated
     */
    @Override
    public void validate(Friendship entity) throws ValidationException{
        //un utilizator nu se poate imprieteni cu el insusi
        if(entity.getIdUser1().equals(entity.getIdUser2())){
            throw new ValidationException("Un utilizator nu poate fi prieten cu el insusi!");
        }
        if(entity.getIdUser1() == null || entity.getIdUser2() == null){
            throw new ValidationException("Id-urile nu pot fi nule!");
        }
    }
}
