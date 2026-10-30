package domain;
import domain.Tuple;
import java.time.LocalDateTime;
/**
 * represents a friendship relation between two users in the social network
 * the friendship is identified by a Tuple containing the IDs of the two users
 */
public class Friendship extends Entity<Tuple<Long,Long>>{

    private LocalDateTime date;
    /**
     * constructs a new Friendship between two users and sets the creation date
     * the IDs are normalized (the smaller ID always becomes the first element
     *
     * @param idUser1 the ID of the first user
     * @param idUser2 the ID of the second user
     * @param date    the date and time when the friendship was created
     */
    public Friendship(Long idUser1, Long idUser2, LocalDateTime date) {
        Long e1 = Math.min(idUser1, idUser2);
        Long e2 = Math.max(idUser1, idUser2);
        this.setID(new Tuple<>(e1, e2));
        this.date = date;
    }

    /**
     * gets the ID of the first user in the friendship
     * due to normalization, this is always the smaller of the two IDs
     *
     * @return the ID of the first user
     */
    public Long getIdUser1() {
        return this.getID().getE1();
    }
    /**
     * gets the ID of the second user in the friendship
     * due to normalization, this is always the larger of the two IDs
     *
     * @return the ID of the second user
     */
    public Long getIdUser2() {
         return this.getID().getE2();
    }
    /**
     * gets the date and time when the friendship was established
     *
     * @return the date of the friendship
     */
    public LocalDateTime getDate(){
        return this.date;
    }

}
