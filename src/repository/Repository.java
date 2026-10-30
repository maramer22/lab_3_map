package repository;

import domain.Entity;
import domain.validators.ValidationException;
/**
 * CRUD operations repository interface
 * defines the standard operations for data storage
 *
 * @param <ID> the type of the entity's unique identifier
 * @param <E>  the type of the entity, which must extend Entity<ID>
 */
public interface Repository<ID, E extends Entity<ID>> {
    /**
     * finds and returns an entity based on its ID
     *
     * @param id the identifier of the entity to be searched; must not be null
     * @return the entity with the specified ID, or null if there is no entity with the given ID
     * @throws IllegalArgumentException if the given id is null
     */
    E findOne(ID id); //returneaza entitatea daca o gaseste in memorie, daca nu exista utilizator cu acel id returneaza null
    /**
     * retrieves all entities stored in the repository
     *
     * @return an Iterable collection containing all entities
     */
    Iterable<E> findAll(); //returneaza o structura Iterable, pt a extrage toti utilizatorii din sistem
    /**
     * saves a given entity in the repository
     *
     * @param entity the entity to be saved; must not be null
     * @return null if the entity was successfully saved,
     *         or the entity itself if an entity with the same ID already exists
     * @throws ValidationException      if the entity is not valid according to the business rules
     * @throws IllegalArgumentException if the given entity is null
     */
    E save(E entity) throws ValidationException; // primeste un obiect, daca respecta toate regulile se salveaza si returneaza null, daaca id ul este deja luat, returneaza inapoi entitatea pe care am incercat sa o introducem
    /**
     * deletes and returns an entity based on its ID
     *
     * @param id the identifier of the entity to be removed; must not be null
     * @return the removed entity, or null if there is no entity with the given ID
     * @throws IllegalArgumentException if the given id is null
     */
    E delete(ID id);//returneaza utilizatorul pe care tocmai l am sters, dar daca id ul nu exista returneaza null

    /**
     * updates an existing entity in the repository
     *
     * @param entity the entity with updated fields; must not be null
     * @return null if the entity was successfully updated,
     *         or the entity itself if it does not exist in the repository
     * @throws ValidationException      if the updated entity is not valid
     * @throws IllegalArgumentException if the given entity is null
     */
    E update(E entity) throws ValidationException; //returneaza null daca s a actualizat si entitatea daca id ul nu exista
}
