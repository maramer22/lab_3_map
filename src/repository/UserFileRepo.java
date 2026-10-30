package repository;
import domain.Friendship;
import domain.User;
import domain.validators.Validator;
/**
 * file repository implementation for User entities
 * handles the specific conversion between User objects and text lines
 */
public class UserFileRepo extends AbstractFileRepository<Long, User>{
    /**
     * constructs a file repository for users
     *
     * @param fileName  the path to the file where users are stored
     * @param validator the validator used for User entities
     */
    public UserFileRepo(String fileName, Validator<User> validator){
        super(fileName, validator);
    }

    /**
     * extracts a User entity from a line of text
     * the line should have the format: id;firstName;lastName
     *
     * @param line the text line representing the user
     * @return the reconstructed User entity
     */
    @Override
    public User extractEntity(String line){
        String[] parts = line.split(";",-1);
        Long id = Long.parseLong(parts[0]);
        String prenume = parts[1];
        String nume = parts[2];
        User user = new User(prenume, nume);
        user.setID(id);
        return user;
    }

    /**
     * converts a User entity into a line of text for file storage
     *
     * @param entity the User entity to convert
     * @return a string formatted as id;firstName;lastName
     */
    @Override
    public String createEntityAsString(User entity){
        return entity.getID() + ";" + entity.getFirstName() + ";" + entity.getLastName();
    }



}
