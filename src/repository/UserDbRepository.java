package repository;
import domain.User;
import domain.validators.Validator;
/**
 * database repository implementation for User entities
 */
public class UserDbRepository implements Repository<Long, User> {
    private String url;
    private String username;
    private String password;
    private Validator<User> validator;
    /**
     * constructs a skeleton for the User database repository
     *
     * @param url       the database connection URL
     * @param username  the database username
     * @param password  the database password
     * @param validator the validator used to check User entities
     */
    public UserDbRepository(String url, String username, String password, Validator<User> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }
    /**
     * @throws UnsupportedOperationException because database persistence is not yet implemented
     */
    @Override
    public User findOne(Long id) { throw new UnsupportedOperationException("Metoda neimplementata inca"); }
    /**
     * @throws UnsupportedOperationException because database persistence is not yet implemented
     */
    @Override
    public Iterable<User> findAll() { throw new UnsupportedOperationException("Metoda neimplementata inca"); }
    /**
     * @throws UnsupportedOperationException because database persistence is not yet implemented
     */
    @Override
    public User save(User entity) { throw new UnsupportedOperationException("Metoda neimplementata inca"); }
    /**
     * @throws UnsupportedOperationException because database persistence is not yet implemented
     */
    @Override
    public User delete(Long id) { throw new UnsupportedOperationException("Metoda neimplementata inca"); }
    /**
     * @throws UnsupportedOperationException because database persistence is not yet implemented
     */
    @Override
    public User update(User entity) { throw new UnsupportedOperationException("Metoda neimplementata inca"); }
}
