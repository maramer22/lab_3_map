package service;

import domain.Friendship;
import domain.User;
import domain.Tuple;
import repository.Repository;
/**
 * Service class for managing Friendship entities
 * Handles business logic such as establishing and terminating friendships,
 * ensuring referential integrity with the User repository
 */
public class FriendshipService {
    private Repository<Tuple<Long, Long>, Friendship> friendshipRepository;
    private Repository<Long, User> userRepository;
    /**
     * initializes the FriendshipService with the necessary repositories
     *
     * @param friendshipRepository the repository for managing friendships
     * @param userRepository       the repository for managing users (needed to verify user existence)
     */
    public FriendshipService(Repository<Tuple<Long,Long>, Friendship> friendshipRepository, Repository<Long, User> userRepository){
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }
    /**
     * creates and stores a new friendship between two users
     * automatically assigns the current date and time to the friendship
     *
     * @param idUser1 the ID of the first user
     * @param idUser2 the ID of the second user
     * @throws IllegalArgumentException if either user does not exist, or if the friendship already exists
     * @throws domain.validators.ValidationException if the friendship is invalid (e.g., a user befriending themselves)
     */
    public void addFriendship(Long idUser1, Long idUser2){
        User u1 = userRepository.findOne(idUser1);
        User u2 = userRepository.findOne(idUser2);

        if(u1 == null || u2 == null){
            throw new IllegalArgumentException("Utilizatorul/Utilizatorii nu sunt in baza de date!");
        }
        Friendship friendship = new Friendship(idUser1, idUser2, java.time.LocalDateTime.now());

        Friendship result = friendshipRepository.save(friendship);

        if(result != null){
            throw new IllegalArgumentException("O prietenie cu acest id exista deja!!");
        }
    }
    /**
     * removes an existing friendship between two users
     *
     * @param idUser1 the ID of the first user
     * @param idUser2 the ID of the second user
     * @throws IllegalArgumentException if the friendship does not exist
     */
    public void removeFriendship(Long idUser1, Long idUser2) {
        Tuple<Long, Long> id = new Tuple<>(idUser1, idUser2);
        Friendship result = friendshipRepository.delete(id);

        if (result == null) {
            throw new IllegalArgumentException("Nu exista nicio prietenie intre acesti utilizatori!");
        }
    }
    /**
     * retrieves all friendships currently established in the network
     *
     * @return an Iterable collection containing all friendships
     */
    public Iterable<Friendship> getAllFriendships() {
        return friendshipRepository.findAll();
    }
}
