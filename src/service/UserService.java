package service;

import domain.Friendship;
import domain.Tuple;
import domain.User;
import repository.Repository;

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
        User result = repository.save(user);
        if (result != null){
            throw new IllegalArgumentException("Exista deja un utilizator cu acest id!");
        }
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
        List<Tuple<Long, Long>> friendshipsToDelete = new ArrayList<>();
        for (Friendship f : friendshipRepository.findAll()) {
            if (f.getIdUser1().equals(id) || f.getIdUser2().equals(id)) {
                friendshipsToDelete.add(f.getID());
            }
        }

        for (Tuple<Long, Long> fId : friendshipsToDelete) {
            friendshipRepository.delete(fId);
        }

        User user = repository.delete(id);
        if(user == null){
            throw new IllegalArgumentException("Nu exista utilizator de sters!");
        }
    }
    /**
     * retrieves all users currently registered in the network
     *
     * @return an Iterable collection containing all users
     */
    public Iterable<User> getAllUsers(){
        return repository.findAll();
    }
}