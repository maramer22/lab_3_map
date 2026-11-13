package repository;
import domain.Friendship;
import domain.Tuple;
import domain.User;
import domain.validators.Validator;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import java.util.Map;
import java.util.HashMap;
import java.util.function.Supplier;

import java.util.function.Function;

/**
 * simple Factory class responsible for creating repository instances
 * based on the configuration provided in the config.properties file
 */
public class RepositoryFactory {
    private Properties properties;
    /**
     * initializes the factory by loading the configuration properties from the file
     *
     * @throws RuntimeException if the configuration file cannot be read
     */
    public RepositoryFactory() {
        properties = new Properties();
        try (FileReader reader = new FileReader("src/main/resources/config.properties")) {
            properties.load(reader);//citim tot textul din fisier si il transforma in chei si valori
        } catch (IOException e) {
            throw new RuntimeException("Eroare la citirea fisierului de configurare!", e);
        }
    }
    /**
     * creates and returns a repository for User entities based on the configured repository type
     *
     * @param validator the validator to be injected into the created repository
     * @return the instantiated User repository
     * @throws RuntimeException         if the repository type is missing from the configuration
     * @throws IllegalArgumentException if the repository type is unknown
     */
    public Repository<Long, User> createUtilizatorRepository(Validator<User> validator) {
        String type = properties.getProperty("repository.type");//cauta valoarea cheii repository type
        if (type == null) {
            throw new RuntimeException("Tipul repository-ului nu este specificat in config.properties!");
        }

        RepositoryType repoType = RepositoryType.valueOf(type); //transformam textul simplu in tipul explicit definit mai jos

        //facem un dictionar de suppliers
        Map<RepositoryType, Supplier<Repository<Long,User>>> creators = new HashMap<>();
        //asociem fiecarei chei expr lambda
        creators.put(RepositoryType.MEMORY, () -> new InMemoryRepository<>(validator));

        //pt FILE
        creators.put(RepositoryType.FILE, () -> {
            String usersFile = properties.getProperty("users.file");
            //return new UserFileRepo(usersFile, validator);
            Function<String, User> userExtractor = (String line) ->{
                String[] parts = line.split(";",-1);
                Long id = Long.parseLong(parts[0]);
                User user = new User(parts[1], parts[2]);
                user.setID(id);
                return user;
            };

            Function<User, String> userSerializer = (User u) -> {
                return u.getID() + ";" + u.getFirstName() + ";" + u.getLastName();
            };

            return new FileRepository<>(usersFile, validator, userExtractor, userSerializer);
        });

        //pt baza de date
        creators.put(RepositoryType.DATABASE, () -> new UserDbRepository(
                properties.getProperty("db.url"),
                properties.getProperty("db.user"),
                properties.getProperty("db.password"),
                validator
        ));

        //luama furnizorul corespunzator din dictionar
        Supplier<Repository<Long, User>> creator = creators.get(repoType);

        if (creator == null) {
            throw new IllegalArgumentException("Tip de repository necunoscut!");
        }

        return creator.get(); //executam lambda su fabricam obiectul curent





//        switch (repoType) {
//            case FILE:
//                String usersFile = properties.getProperty("users.file"); //citeste din configurari calea catre fisierul de users
//                return new UserFileRepo(usersFile, validator); //instantiaza repoul de fisier
//            case DATABASE:
//                return new UserDbRepository(
//                        properties.getProperty("db.url"),
//                        properties.getProperty("db.user"),
//                        properties.getProperty("db.password"),
//                        validator
//                );
//            case MEMORY:
//                return new InMemoryRepository<>(validator);
//            default:
//                throw new IllegalArgumentException("Tip de repository necunoscut!");
//        }
    }
    /**
     * creates and returns a repository for Friendship entities based on the configured repository type
     *
     * @param validator the validator to be injected into the created repository
     * @return the instantiated Friendship repository
     * @throws RuntimeException         if the repository type is missing from the configuration
     * @throws IllegalArgumentException if the repository type is unknown
     */
    public Repository<Tuple<Long, Long>, Friendship> createPrietenieRepository(Validator<Friendship> validator) {
        String type = properties.getProperty("repository.type");
        if (type == null) {
            throw new RuntimeException("Tipul repository-ului nu este specificat in config.properties!");
        }

        RepositoryType repoType = RepositoryType.valueOf(type);
        java.util.Map<RepositoryType, java.util.function.Supplier<Repository<Tuple<Long, Long>, Friendship>>> creators = new java.util.HashMap<>();

        creators.put(RepositoryType.MEMORY, () -> new InMemoryRepository<>(validator));

        creators.put(RepositoryType.DATABASE, () -> new FriendshipDbRepository(
                properties.getProperty("db.url"),
                properties.getProperty("db.user"),
                properties.getProperty("db.password"),
                validator
        ));

        creators.put(RepositoryType.FILE, () -> {
            String friendshipsFile = properties.getProperty("friendships.file");

            java.util.function.Function<String, Friendship> friendshipExtractor = (String line) -> {
                String[] parts = line.split(";");
                Long idUser1 = Long.parseLong(parts[0]);
                Long idUser2 = Long.parseLong(parts[1]);
                java.time.LocalDateTime date = java.time.LocalDateTime.parse(parts[2]);
                return new Friendship(idUser1, idUser2, date);
            };

            java.util.function.Function<Friendship, String> friendshipSerializer = (Friendship f) -> {
                return f.getIdUser1() + ";" + f.getIdUser2() + ";" + f.getDate();
            };
            return new FileRepository<>(friendshipsFile, validator, friendshipExtractor, friendshipSerializer);
        });

        java.util.function.Supplier<Repository<Tuple<Long, Long>, Friendship>> creator = creators.get(repoType);

        if (creator == null) {
            throw new IllegalArgumentException("Tip de repository necunoscut!");
        }

        return creator.get();

//        switch (repoType) {
//            case FILE:
//                String friendshipsFile = properties.getProperty("friendships.file");
//                return new FriendshipFileRepo(friendshipsFile, validator);
//            case DATABASE:
//                return new FriendshipDbRepository(
//                        properties.getProperty("db.url"),
//                        properties.getProperty("db.user"),
//                        properties.getProperty("db.password"),
//                        validator
//                );
//            case MEMORY:
//                return new InMemoryRepository<>(validator);
//            default:
//                throw new IllegalArgumentException("Tip de repository necunoscut!");
//        }
    }


}
