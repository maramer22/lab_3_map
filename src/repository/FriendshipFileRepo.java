package repository;
import domain.Friendship;
import domain.Tuple;
import domain.validators.Validator;
import java.time.LocalDateTime;
/**
 * file repository implementation for Friendship entities
 * handles the specific conversion between Friendship objects and text lines
 */
public class FriendshipFileRepo extends AbstractFileRepository<Tuple<Long, Long>, Friendship> {
    /**
     * constructs a file repository for friendships.
     *
     * @param fileName  the path to the file where friendships are stored
     * @param validator the validator used for Friendship entities
     */
    public FriendshipFileRepo(String fileName, Validator<Friendship> validator){
        super(fileName, validator);
    }
    /**
     * extracts a Friendship entity from a line of text
     * the line should have the format: idUser1;idUser2;date
     *
     * @param line the text line representing the friendship
     * @return the reconstructed Friendship entity
     */
    @Override
    public Friendship extractEntity(String line){
        String[] parts = line.split(";");
        Long idUser1 = Long.parseLong(parts[0]);
        Long idUser2 = Long.parseLong(parts[1]);
        LocalDateTime date = LocalDateTime.parse(parts[2]);
        return new Friendship(idUser1, idUser2, date);
    }
    /**
     * converts a Friendship entity into a line of text for file storage
     *
     * @param entity the Friendship entity to convert
     * @return a string formatted as idUser1;idUser2;date
     */
    @Override
    public String createEntityAsString(Friendship entity){
        return entity.getIdUser1() + ";" + entity.getIdUser2() + ";" + entity.getDate();
    }
}
