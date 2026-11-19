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

import java.io.*;
import java.util.function.Function;


public class FileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID, E> {
    private final String fileName;
    private final Function<String, E> extractEntity;
    private final Function<E,String> createEntityAsString;

    public FileRepository(String fileName, Validator<E> validator, Function<String, E> extractEntity, Function<E, String> createEntityAsString){
        super(validator);
        this.fileName = fileName;
        this.extractEntity = extractEntity;
        this.createEntityAsString = createEntityAsString;
        loadData();

    }

    /**
     * reads all the lines of the file and saves the entities in memory;
     * if the file does not exist, the repository starts empty
     *
     * @throws RuntimeException if the file cannot be read
     */
    private void loadData() {
        java.nio.file.Path path = java.nio.file.Paths.get(fileName);
        try {
            //ia fisierul si pune toate randurile in lines
            java.util.List<String> lines = java.nio.file.Files.readAllLines(path);
            java.util.stream.IntStream.range(0, lines.size()).forEach(i -> {
                String line = lines.get(i);
                if (line.isBlank()) return;

                try {
                    E entity = extractEntity.apply(line);
                    super.save(entity);
                } catch (ValidationException | NumberFormatException | ArrayIndexOutOfBoundsException | java.time.format.DateTimeParseException e) {

                    System.err.println("Linie corupta ignorata in " + fileName + " la linia " + (i + 1) + ". Motiv: " + e.getMessage());
                }
            });
        } catch (java.nio.file.NoSuchFileException e) {
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
        java.nio.file.Path path = java.nio.file.Paths.get(fileName);
        try {
            java.util.List<String> linii = java.util.stream.StreamSupport
                    .stream(findAll().spliterator(), false)
                    .map(createEntityAsString) // REFERINTA LA METODA
                    .collect(java.util.stream.Collectors.toList());

            // Cerinta: scriere cu Files.write
            java.nio.file.Files.write(path, linii);
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
    public java.util.Optional<E> save(E entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entitatea nu poate fi null!");
        }
        java.util.Optional<E> result = super.save(entity);
        if (result.isEmpty())
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
    public java.util.Optional<E> delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID-ul nu poate fi null!");
        }
        java.util.Optional<E> result = super.delete(id);
        if (result.isPresent())
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
    public java.util.Optional<E> update(E entity) {
        java.util.Optional<E> result = super.update(entity);
        if (result.isEmpty())
            writeToFile();
        return result;
    }


}
