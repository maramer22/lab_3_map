package repository;
import domain.Friendship;
import domain.Tuple;
import domain.validators.Validator;
/**
 * database repository implementation for Friendship entities
 */
public class FriendshipDbRepository implements Repository<Tuple<Long,Long>, Friendship>{
    private String url;
    private String username;
    private String password;
    private Validator<Friendship> validator;
    /**
     * constructs a skeleton for the Friendship database repository
     *
     * @param url       the database connection URL
     * @param username  the database username
     * @param password  the database password
     * @param validator the validator used to check Friendship entities
     */
    public FriendshipDbRepository(String url, String username, String password, Validator<Friendship> validator){
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }
    /**
     * @throws UnsupportedOperationException because database persistence is not yet implemented
     */
    @Override
    public Friendship findOne(Tuple<Long, Long> id) { throw new UnsupportedOperationException("Metoda neimplementata inca"); }
    /**
     * @throws UnsupportedOperationException because database persistence is not yet implemented
     */
    @Override
    public Iterable<Friendship> findAll() { throw new UnsupportedOperationException("Metoda neimplementata inca"); }
    /**
     * @throws UnsupportedOperationException because database persistence is not yet implemented
     */
    @Override
    public Friendship save(Friendship entity) { throw new UnsupportedOperationException("Metoda neimplementata inca"); }
    /**
     * @throws UnsupportedOperationException because database persistence is not yet implemented
     */
    @Override
    public Friendship delete(Tuple<Long, Long> id) { throw new UnsupportedOperationException("Metoda neimplementata inca"); }
    /**
     * @throws UnsupportedOperationException because database persistence is not yet implemented
     */
    @Override
    public Friendship update(Friendship entity) { throw new UnsupportedOperationException("Metoda neimplementata inca"); }
}