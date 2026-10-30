package repository;
import domain.Entity;
import domain.validators.ValidationException;
import domain.validators.Validator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID, E> {
    private final String fileName;

    public AbstractFileRepository(String fileName, Validator<E> validator){
        super(validator);
        this.fileName = fileName;
        loadData();

    }
    /**
     * creates an entity from a line of the file
     *
     * @param line - a line of the file
     * @return the entity described by the line
     */
    public abstract E extractEntity(String line);

    /**
     * converts an entity into a line of the file
     *
     * @param entity - the entity to be converted
     * @return the line of text that describes the entity
     */
    public abstract String createEntityAsString(E entity);

    /**
     * reads all the lines of the file and saves the entities in memory;
     * if the file does not exist, the repository starts empty
     *
     * @throws RuntimeException if the file cannot be read
     */
    private void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank())
                    continue;

                try {
                    E entity = extractEntity(line);
                    super.save(entity);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException | java.time.format.DateTimeParseException | ValidationException e) {
                    ///prindem liniile corupte
                    System.err.println("Linie corupta ignorata in " + fileName + " la linia " + lineNumber + ". Motiv: " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul " + fileName + " nu exista; repository gol.");
        } catch (IOException e) {
            throw new RepositoryException("Eroare la citirea fisierului " + fileName, e);
        }
    }

    /**
     * rewrites the whole file with the entities currently in memory
     *
     * @throws RuntimeException if the file cannot be written
     */
    private void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (E entity : findAll()) {
                writer.write(createEntityAsString(entity));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RepositoryException("Eroare la scrierea fisierului " + fileName, e);
        }
    }

    /**
     * saves the entity in memory and, if it was saved, rewrites the file
     *
     * @param entity entity must be not null
     * @return null - if the given entity is saved
     * otherwise returns the entity (id already exists)
     * @throws domain.validators.ValidationException if the entity is not valid
     * @throws IllegalArgumentException                   if the given entity is null.
     */
    @Override
    public E save(E entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entitatea nu poate fi null!");
        }
        E result = super.save(entity);
        if (result == null)
            writeToFile();
        return result;
    }
    /**
     * removes the entity from memory and, if it existed, rewrites the file
     *
     * @param id id must be not null
     * @return the removed entity or null if there is no entity with the given id
     */
    @Override
    public E delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID-ul nu poate fi null!");
        }
        E result = super.delete(id);
        if (result != null)
            writeToFile();
        return result;
    }

    /**
     * updates the entity in memory and, if it was updated, rewrites the file
     *
     * @param entity entity must not be null
     * @return null - if the entity is updated,
     * otherwise returns the entity - (e.g id does not exist).
     * @throws IllegalArgumentException                   if the given entity is null.
     * @throws domain.validators.ValidationException if the entity is not valid.
     */
    @Override
    public E update(E entity) {
        E result = super.update(entity);
        if (result == null)
            writeToFile();
        return result;
    }


}
