package domain;
import java.util.function.Predicate;

public class CriteriiUtilizator {
    //prenumele incepe cu un prefix dat
    public static Predicate<User> firstNameStartsWith(String prefix){
        return u -> u.getFirstName().startsWith(prefix);
    }

    //numele de familie este exact cel cautat
    public static Predicate<User> lastNameEquals(String name){
        return u -> u.getLastName().equals(name);
    }

    //numele are o lungime minima
    public static Predicate<User> nameLengthGreaterThan(int length){
        return u -> u.getFirstName().length() > length;
    }
}
