package repository;
import domain.Entity;
import domain.validators.ValidationException;
import domain.validators.Validator;
import java.util.HashMap;
import java.util.Map;
/**
 * in memory implementation of the Repository interface
 * data is stored in a HashMap and is lost when the app stops
 *
 * @param <ID> the type of the entity's unique identifier
 * @param <E>  the type of the entity
 */
public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {
    private Validator<E> validator;
    private Map<ID, E> entities;

    /**
     * constructs an InMemoryRepository with the specified validator
     * initializes an empty HashMap for storing entities
     *
     * @param validator the validator used to check entities before saving or updating
     */
    public InMemoryRepository(Validator<E> validator){
        this.validator = validator;
        this.entities = new HashMap<>();

    }
    /**
     * finds and returns an entity based on its ID
     *
     * @param id the identifier of the entity to be searched; must not be null
     * @return the entity with the specified ID, or null if it does not exist
     * @throws IllegalArgumentException if the given ID is null
     */
    @Override
    public E findOne(ID id){
        if ( id == null) {
            throw new IllegalArgumentException("ID-ul nu poate fi null!");
        }
        return entities.get(id);

    }
    /**
     * retrieves all entities currently stored in the memory dictionary
     *
     * @return an Iterable collection of all entities
     */
    @Override
    public Iterable<E> findAll(){
        //.values() returneaza contitnutul, fara id
        return entities.values();
    }
    /**
     * validates and saves the entity in memory
     *
     * @param entity the entity to be saved; must not be null
     * @return null if the entity was successfully saved,
     *         or the entity itself if an object with the same ID already exists
     * @throws IllegalArgumentException if the entity is null
     * @throws ValidationException      if the entity does not pass the validation rules
     */
    @Override
    public E save(E entity) throws ValidationException{
        if(entity == null){
            throw new IllegalArgumentException("Entitatea nu poate fi null!");
        }
        validator.validate(entity);

        //daca id-ul este luat
        if(entities.containsKey(entity.getID())){
            return entity;
        }

        //daca nu, punem in memorie obicetul
        entities.put(entity.getID(), entity);
        return null; //am salvat cu succes

    }
    /**
     * removes and returns the entity associated with the given ID
     *
     * @param id the identifier of the entity to be removed; must not be null
     * @return the removed entity, or null if there is no entity with the given ID
     * @throws IllegalArgumentException if the given ID is null
     */
    @Override
    public E delete(ID id){
        if (id == null){
            throw new IllegalArgumentException("ID-ul nu poate fi null");
        }
        //remove() sterge si returneaza si ce a sters
        return entities.remove(id);
    }
    /**
     * validates and updates an existing entity in memory
     *
     * @param entity the entity with updated fields; must not be null
     * @return null if the entity was successfully updated,
     *         or the initial entity if the ID was not found
     * @throws IllegalArgumentException if the entity is null
     * @throws ValidationException      if the entity does not pass the validation rules
     */
    @Override
    public E update(E entity) throws ValidationException{
        if ( entity == null){
            throw new IllegalArgumentException("Entitatea nu poate fi null");
        }
        validator.validate(entity);
        if(entities.containsKey(entity.getID())){
            entities.put(entity.getID(),entity);
            return null;//a fost updatat cu succes
        }
        return entity; //in caz ca nu a gasit id ul cerut
    }





}
