package domain;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * represents a user entity
 */
public class User extends Entity<Long>{
    private String firstName;
    private String lastName;

    private List<User> friends; //o lista pentru a tine evidenta prietenilor userului

    /**
     * constructs a new User with the specified first name and last name
     * the friends list is initialized as empty.
     *
     * @param firstName the first name of the user
     * @param lastName  the last name of the user
     */
    public User(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.friends = new ArrayList<>(); //initializam lista de prieteni ca fiind goala
    }
    /**
     * gets the first name of the user
     *
     * @return the user's first name
     */
    public String getFirstName(){
        return firstName;
    }

    /**
     * updates the first name of the user
     *
     * @param firstName the new first name
     */
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    /**
     * gets the last name of the user
     *
     * @return the user's last name
     */
    public String getLastName(){
        return lastName;
    }
    /**
     * updates the last name of the user
     *
     * @param lastName the new last name
     */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    /**
     * gets the list of friends of the user
     *
     * @return a list containing the user's friends
     */
    public List<User> getFriends(){
        return friends;
    }

    /**
     * returns a string representation of the user
     *
     * @return a string containing the user's ID, first name, and last name
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + getID() +
                ", firstName=" + firstName + '\'' +
                ", lastName=" + lastName + '\'' +
                '}';
    }

}
