package domain;

import java.util.Objects;
/**
 * represents a pair of two elements
 * the tuple is unordered, meaning Tuple(a, b) is equal to Tuple(b, a)
 *
 * @param <E1> the type of the first element
 * @param <E2> the type of the second element
 */
public class Tuple<E1, E2>{
    private E1 e1;
    private E2 e2;
    /**
     * constructs a Tuple with the specified elements
     *
     * @param e1 the first element of the tuple
     * @param e2 the second element of the tuple
     */
    public Tuple(E1 e1, E2 e2){
        this.e1 = e1;
        this.e2 = e2;
    }

    /**
     * gets the first element of the tuple
     *
     * @return the first element
     */
    public E1 getE1(){
        return this.e1;
    }


    /**
     * gets the second element of the tuple
     *
     * @return the second element
     */
    public E2 getE2(){
        return this.e2;
    }

    /**
     * compares this tuple to the specified object
     * two tuples are considered equal if they contain the same elements,
     * the order doesn't matter
     *
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;
        //dupa verificarea de mai sus stim ca obiectul trimis este un Tuple
        Tuple<?,?> other = (Tuple<?,?>) obj;
        return (Objects.equals(this.getE1(), other.getE1()) && Objects.equals(this.getE2(), other.getE2())) ||
                (Objects.equals(this.getE1(), other.getE2()) && Objects.equals(this.getE2(), other.getE1()));
    }
    /**
     * generates a hash code for the tuple
     * to ensure that Tuple(a, b) and Tuple(b, a)
     * have the same hash code.
     *
     * @return the hash code value for this tuple
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(this.getE1()) + Objects.hashCode(this.getE2());
    }

    @Override
    public String toString() {
        return "(" + e1 + ", " + e2 + ")";
    }
}






