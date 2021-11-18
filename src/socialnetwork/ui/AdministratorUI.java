package socialnetwork.ui;

import socialnetwork.domain.Friendship;
import socialnetwork.domain.User;
import socialnetwork.domain.exceptions.AccessException;
import socialnetwork.domain.exceptions.FileException;
import socialnetwork.domain.exceptions.InputException;
import socialnetwork.domain.exceptions.RepositoryException;
import socialnetwork.service.AdministratorService;

import java.util.List;
import java.util.Scanner;

public class AdministratorUI extends AbstractUI {
    public AdministratorUI(AbstractUI ui) {
        this.service = new AdministratorService(ui.service);
    }

    /**
     * Runs the menu loop;
     */
    @Override
    public void run() throws Exception {
        try {
            String input = "";
            do {
                try {
                    input = input();
                    switch (input) {
                        case "0": break;
                        case "1": addUser(); break;
                        case "2": deleteUser(); break;
                        case "3": updateUser(); break;
                        case "4": addFriendship(); break;
                        case "5": deleteFriendship(); break;
                        case "6": updateFriendship(); break;
                        case "7": showEverything(); break;
                        default: throw new InputException("Invalid input");
                    }
                } catch (Exception e) {
//                    System.out.println(e.getMessage());
throw e;
                }
                System.out.println("\n");
            } while (!input.equals("0"));
        } catch (Exception e) {
//            System.out.println(e.getMessage());
throw e;
        }
        System.out.println("Goodbye");
    }

    /**
     * Shows the menu;
     * @return the input;
     */
    private String input() {
        System.out.println("*****************************************");
        System.out.println("1 - Add user");
        System.out.println("2 - Delete user");
        System.out.println("3 - Update user");
        System.out.println("4 - Add friendship");
        System.out.println("5 - Delete friendship");
        System.out.println("6 - Update friendship");
        System.out.println("7 - Show everything");
        System.out.println("0 - Exit");
        System.out.println("Introduce one of the numbers:");
        return (new Scanner(System.in)).nextLine();
    }

    /**
     * UI adds an user;
     * @throws InputException if input is invalid;
     * @throws FileException if file is invalid;
     * @throws RepositoryException if user exists.
     */
    private void addUser() throws Exception {
        System.out.println("Introduce email, first name and second name, separated by space:");
        List<String> attributes = List.of((new Scanner(System.in)).nextLine().split(" "));
        if (attributes.size() != 3)
            throw new InputException("wrong inputs for user attributes");
        service.addUser(attributes.get(0),attributes.get(1),attributes.get(2));
        System.out.println("User added");
    }

    /**
     * UI deletes an user;
     * @throws FileException if file is invalid;
     * @throws RepositoryException if user does not exist.
     */
    private void deleteUser() throws Exception {
        System.out.println("Introduce email:");
        String attribute = (new Scanner(System.in)).nextLine();
        service.deleteUser(attribute);
        System.out.println("User deleted");
    }

    /**
     * UI updates an user;
     * @throws InputException if input is invalid;
     * @throws FileException if file is invalid;
     * @throws RepositoryException if user does not exist.
     */
    private void updateUser() throws Exception {
        System.out.println("Introduce email, first name and second name, separated by space:");
        List<String> attributes = List.of((new Scanner(System.in)).nextLine().split(" "));
        if (attributes.size() != 3)
            throw new InputException("wrong inputs for user attributes");
        service.updateUser(attributes.get(0),attributes.get(1),attributes.get(2));
        System.out.println("User updated");
    }

    /**
     * UI adds a friendship;
     * @throws InputException if input is invalid;
     * @throws FileException if file is invalid;
     * @throws RepositoryException if friendship exists.
     */
    private void addFriendship() throws Exception {
        System.out.println("Introduce first and second email, separated by space:");
        List<String> attributes = List.of((new Scanner(System.in)).nextLine().split(" "));
        if (attributes.size() != 2)
            throw new InputException("wrong inputs for emails");
        service.addFriendship(attributes.get(0),attributes.get(1));
        System.out.println("Friendship added");
    }

    /**
     * UI deletes a friendship;
     * @throws InputException if input is invalid;
     * @throws FileException if file is invalid;
     * @throws RepositoryException if friendship does not exits.
     */
    private void deleteFriendship() throws Exception {
        System.out.println("Introduce first and second email, separated by space:");
        List<String> attributes = List.of((new Scanner(System.in)).nextLine().split(" "));
        if (attributes.size() != 2)
            throw new InputException("wrong inputs for emails");
        service.deleteFriendship(attributes.get(0),attributes.get(1));
        System.out.println("Friendship deleted");
    }

    /**
     * UI updates a friendship;
     * @throws InputException if input is invalid;
     * @throws FileException if file is invalid;
     * @throws RepositoryException if friendship does not exits.
     */
    private void updateFriendship() throws Exception {
        System.out.println("Introduce first, second, third and fourth email, separated by space:");
        List<String> attributes = List.of((new Scanner(System.in)).nextLine().split(" "));
        if (attributes.size() != 4)
            throw new InputException("wrong inputs for emails");
        service.updateFriendship(attributes.get(0),attributes.get(1),attributes.get(2),attributes.get(3));
        System.out.println("Friendship updated");
    }

    /**
     * Shows the friendships and users.
     */
    private void showEverything() throws Exception {
        System.out.println("Users:");
        for (User user : service.getUsers())
            System.out.println(user.toString());
        System.out.println("\nFriendships:");
        for (Friendship friendship : service.getFriendships())
            System.out.println(friendship.toString());
    }
}
