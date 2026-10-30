import domain.validators.FriendshipValidator;
import domain.validators.UserValidator;
import repository.RepositoryFactory;
import repository.Repository;
import domain.User;
import domain.Friendship;
import domain.Tuple;
import service.CommunityService;
import service.FriendshipService;
import service.UserService;
import ui.Console;

/**
 * The main entry point of the social network application.
 * Responsible for instantiating the components (validators, repositories, services)
 * and wiring them together using Dependency Injection before starting the user interface.
 */
public class
Main {

    /**
     * the main method
     * it uses a RepositoryFactory to determine the storage mechanism
     * based on the configuration file.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        UserValidator userValidator = new UserValidator();
        FriendshipValidator friendshipValidator = new FriendshipValidator();

        try {
            // Instantiate the factory which reads config.properties
            RepositoryFactory factory = new RepositoryFactory();

            // Create repositories based on factory configuration
            Repository<Long, User> userRepository = factory.createUtilizatorRepository(userValidator);
            Repository<Tuple<Long, Long>, Friendship> friendshipRepository = factory.createPrietenieRepository(friendshipValidator);

            // Wire the repositories to the business logic (Services)
            UserService userService = new UserService(userRepository, friendshipRepository);
            FriendshipService friendshipService = new FriendshipService(friendshipRepository, userRepository);
            CommunityService communityService = new CommunityService(userService, friendshipService);

            // Start the user interface
            Console console = new Console(userService, friendshipService, communityService);
            console.start();

        } catch (Exception e) {
            System.err.println("Eroare critica la pornirea aplicatiei: " + e.getMessage());
        }
    }
}