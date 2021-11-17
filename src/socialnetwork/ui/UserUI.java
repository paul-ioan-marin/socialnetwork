package socialnetwork.ui;

import socialnetwork.domain.User;
import socialnetwork.domain.exceptions.AccessException;
import socialnetwork.domain.exceptions.FileException;
import socialnetwork.domain.exceptions.InputException;
import socialnetwork.domain.exceptions.RepositoryException;
import socialnetwork.service.UserService;

import java.util.Scanner;

public class UserUI extends AbstractUI {
    public UserUI(AbstractUI ui, User user) {
        this.service = new UserService(ui.service, user);
    }

    @Override
    public void run() {
        try {
            String input = "";
            do {
                try {
                    input = input();
                    switch (input) {
                        case "1": sendRequest(); break;
                        default: throw new InputException("Invalid input");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("\n");
            } while (!input.equals("0"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Goodbye");
    }

    /**
     * Shows the menu;
     * @return the input;
     */
    private String input() {
        System.out.println("*****************************************");
        System.out.println("1 - Send a friend request");
        System.out.println("2 - Send a friend request");
        System.out.println("0 - Logout");
        System.out.println("Introduce one of the numbers: ");
        return (new Scanner(System.in)).nextLine();
    }

    private void sendRequest() throws AccessException, FileException, RepositoryException {
        System.out.println("Introduce the email: ");
        String email = (new Scanner(System.in)).nextLine();
        service.sendRequest(email);
        System.out.println("Request sent");
    }
}
