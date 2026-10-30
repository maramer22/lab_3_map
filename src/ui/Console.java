package ui;

import domain.Friendship;
import domain.User;
import domain.validators.ValidationException;
import service.CommunityService;
import service.FriendshipService;
import service.UserService;

import java.util.Scanner;
import java.util.List;
/**
 * the consol Interface for the social network app
 * responsible for interacting with the user, reading input, and delegating
 * operations to the appropriate services while handling exceptions
 */
public class Console {
    private UserService userService;
    private FriendshipService friendshipService;
    private CommunityService communityService;
    private Scanner scanner;

    /**
     * initializes the Console UI with the necessary business logic services
     *
     * @param userService       the service handling user operations
     * @param friendshipService the service handling friendship operations
     * @param communityService  the service handling network algorithms and statistics
     */
    public Console(UserService userService, FriendshipService friendshipService, CommunityService communityService) {
        this.friendshipService = friendshipService;
        this.userService = userService;
        this.communityService = communityService;
        this.scanner = new Scanner(System.in);
    }

    /**
     * prints the available options for the interactive menu
     */
    private void printMenu(){
        System.out.println("Retea de socializare\n");
        System.out.println("1. Adauga utilizator\n");
        System.out.println("2. Sterge utilizator\n");
        System.out.println("3. Afiseaza toti utilizatorii\n");
        System.out.println("4. Adauga un prieten\n");
        System.out.println("5. Sterge un prieten\n");
        System.out.println("6. Afiseaza toate prieteniile\n");
        System.out.println("7. Afiseaza numarul de comunitati\n");
        System.out.println("8. Afiaeaza cea mai sociabila comunitate\n");
        System.out.println("0. Exit\n");
        System.out.print("Alege o optiune: ");

    }
    /**
     * starts the interactive application loop
     * Reads user commands and processes them while handling validation
     */
    public void start(){
        boolean running = true;
        while (running) {
            printMenu();
            String option = scanner.nextLine();
            try{
                switch (option){
                    case "1":
                        addUserUi();
                        break;
                    case "2":
                        removeUserUi();
                        break;
                    case "3":
                        printAllUsers();
                        break;
                    case "0":
                        running = false;
                        System.out.println("Exit");
                        break;
                    case "4":
                        addFriendshipUi();
                        break;
                    case "5":
                        removeFriendshipUi();
                        break;
                    case "6":
                        printAllFriendships();
                        break;
                    case "7":
                        printNumberOfCommunities();
                        break;
                    case "8":
                        printMostSociableCommunity();
                        break;
                    default:
                        System.out.println("Optiune invalida!");
                }
            } catch (ValidationException e){
                System.out.println("Eroare de validare: \n" + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Te rog sa introduci un numar valid pentru ID!");
            } catch (IllegalArgumentException e){
                System.out.println("Eroare de sistem: " + e.getMessage());
            } catch (repository.RepositoryException e) {
                System.out.println("Eroare la baza de date/fisiere: " + e.getMessage());
            }



        }
    }
    /**
     * prompts the user for data and delegates the creation of a new user
     */
    private void addUserUi(){
        System.out.print("Introdu Id-ul: ");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.print("Introdu prenumele: ");
        String firstName = scanner.nextLine();
        System.out.print("Introdu numele de familie:");
        String lastName = scanner.nextLine();
        userService.addUser(id, firstName, lastName);
        System.out.println("Utilizator adaugat cu succes!");

    }
    /**
     * prompts the user for an ID and delegates the deletion of a user
     */
    private void removeUserUi(){
        System.out.print("Introdu Id-ul utilizatorului:");
        Long id = Long.parseLong(scanner.nextLine());
        userService.removeUser(id);
        System.out.println("Utilizator sters cu succes!");
    }
    /**
     * fetches and displays all registered users
     */
    private void printAllUsers(){
        Iterable<User> users = userService.getAllUsers();
        System.out.println("Lista utilizatori: ");
        for (User u : users){
            System.out.println("ID: " + u.getID() + ", Nume: " + u.getLastName() + ", Prenume:" + u.getFirstName());
        }
    }
    /**
     * prompts the user for two IDs and delegates the creation of a friendship
     */
    private void addFriendshipUi(){
        System.out.print("Introduceti ID-ul primului utilizator: ");
        Long idUser1 = Long.parseLong(scanner.nextLine());
        System.out.print("Introduceti ID-ul celui de-al doilea utilizator: ");
        Long idUser2 = Long.parseLong(scanner.nextLine());
        friendshipService.addFriendship(idUser1, idUser2);
        System.out.print("Prietenie adaugata cu succes!");
    }
    /**
     * prompts the user for two IDs and delegates the removal of a friendship
     */
    private void removeFriendshipUi() {
        System.out.print("Introduceti ID-ul primului utilizator: ");
        Long idUser1 = Long.parseLong(scanner.nextLine());
        System.out.print("Introduceti ID-ul celui de-al doilea utilizator: ");
        Long idUser2 = Long.parseLong(scanner.nextLine());

        friendshipService.removeFriendship(idUser1, idUser2);
        System.out.println("Prietenie stearsa cu succes!");
    }
    /**
     * fetches and displays all established friendships
     */
    private void printAllFriendships(){
        Iterable<Friendship> friendships = friendshipService.getAllFriendships();
        System.out.println("Lista prieteniilor:");
        for (Friendship f : friendships) {
            System.out.println("ID prietenie: " + f.getID() + " Useri: " + f.getIdUser1() + " si " + f.getIdUser2());


        }
    }
    /**
     * requests and displays the total number of connected communities in the network
     */
    private void printNumberOfCommunities() {
        int count = communityService.getNumberOfCommunities();
        System.out.println("\nNumarul total de comunitați din retea este: " + count);
    }
    /**
     * requests and displays the members of the most sociable community
     */
    private void printMostSociableCommunity() {
        List<Long> mostSociable = communityService.getMostSociableCommunity();

        if (mostSociable.isEmpty()) {
            System.out.println("\nReteaua nu are utilizatori sau prietenii");
            return;
        }

        System.out.println("\nCea mai sociabila comunitate este formata din utilizatorii cu IDurile:");
        System.out.println(mostSociable);


    }
}

