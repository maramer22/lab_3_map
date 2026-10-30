package domain;
import java.util.Objects;
public class Entity<ID>{
    private ID id;

    /**
     * gets the unique identifier of the entity
     *
     * @return the unique identifier of type ID
     */
    public ID getID(){
        return id;
    }

    /**
     * sets or updates the unique identifier of the entity
     *
     * @param id the new identifier to be set for the entity
     */
    public void setID(ID id){
        this.id = id;
    }
    /**
     * compares this entity to another object based on their IDs
     *
     * @param o the object to compare with
     * @return true if the entities have the same ID, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(id, entity.id);
    }

    /**
     * generates a hash code for the entity based on its ID
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
