package service;

import domain.Friendship;
import domain.Tuple;
import domain.User;
import repository.Repository;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
/**
 * service class for managing User entities
 * handles business logic such as adding, removing, and retrieving users,
 * including cascade deletion of friendships when a user is removed
 */
public class UserService {
    private Repository<Long, User> repository;
    private Repository<Tuple<Long, Long>, Friendship> friendshipRepository;

    /**
     * initializes the UserService with the necessary repositories
     *
     * @param repository           the repository for managing users
     * @param friendshipRepository the repository for managing friendships
     */
    public UserService(Repository<Long, User> repository, Repository<Tuple<Long, Long>, Friendship> friendshipRepository){
        this.repository = repository;
        this.friendshipRepository = friendshipRepository;
    }
    /**
     * creates and adds a new user to the network
     *
     * @param id        the unique identifier of the user
     * @param firstName the first name of the user
     * @param lastName  the last name of the user
     * @throws IllegalArgumentException if a user with the given ID already exists
     * @throws domain.validators.ValidationException if the user data is invalid
     */
    public void addUser(Long id, String firstName, String lastName){
        User user = new User(firstName, lastName);
        user.setID(id);

        repository.save(user).ifPresent(u -> {
            throw new IllegalArgumentException("Exista deja un user cu acest id!");
        });
    }
    /**
     * removes a user from the network based on their ID
     * removing all friendships involving this user
     * before deleting the user itself to maintain data consistency.
     *
     * @param id the ID of the user to be removed
     * @throws IllegalArgumentException if no user exists with the given ID
     */
    public void removeUser(Long id){
        //mai inati stergem prieteniile aperente, abia supa usreul
        List<Tuple<Long, Long>> friendshipsToDelete = getFriendshipStream()
                .filter(f -> f.getIdUser1().equals(id) || f.getIdUser2().equals(id))
                .map(Friendship::getID)
                .collect(java.util.stream.Collectors.toList());
        //metoda de instanta a unui obiect anume
        friendshipsToDelete.forEach(friendshipRepository::delete);

        repository.delete(id)
                .orElseThrow(() -> new IllegalArgumentException("Nu exista utilizator de sters!"));
    }
    /**
     * retrieves all users currently registered in the network
     *
     * @return an Iterable collection containing all users
     */
    public Iterable<User> getAllUsers(){
        return repository.findAll();
    }

    /**
     *
     *
     * @param filter criteriul de filtrare
     * @return o lista cu utilizatorii care trec de filtru
     */
    public Iterable<User> filterUsers(java.util.function.Predicate<User> filter) {
        return repository.findAll(filter);
    }

    /**
     * Returneaza lista de prieteni a unui utilizator, ordonata alfabetic.
     * @param userId ID-ul utilizatorului
     * @return o lista de utilizatori (prietenii)
     */
    public List<User> getPrieteniiUnuiUtilizator(Long userId) {
        return getFriendshipStream()
                .filter(f -> f.getIdUser1().equals(userId) || f.getIdUser2().equals(userId))
                .map(f -> f.getIdUser1().equals(userId) ? f.getIdUser2() : f.getIdUser1())
                .map(idPrieten -> repository.findOne(idPrieten)
                        .orElseThrow(() -> new IllegalArgumentException("Prietenul cu ID-ul " + idPrieten + " nu exista!")))
                .sorted(Comparator.comparing(User::getLastName).thenComparing(User::getFirstName))
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));
    }

    /**
     * takes the friendships from the memory and applies .spliterator() in order for us to fork with streams
     * @return
     */
    private java.util.stream.Stream<Friendship> getFriendshipStream() {
        //false - pt ca vrem el unul dupa altul
        return java.util.stream.StreamSupport.stream(friendshipRepository.findAll().spliterator(), false);
    }

}