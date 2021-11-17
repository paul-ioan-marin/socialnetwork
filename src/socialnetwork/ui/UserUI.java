package socialnetwork.ui;

import socialnetwork.domain.FriendshipWithStatus;
import socialnetwork.domain.User;
import socialnetwork.domain.exceptions.AccessException;
import socialnetwork.domain.exceptions.FileException;
import socialnetwork.domain.exceptions.InputException;
import socialnetwork.service.UserService;

import java.util.Scanner;

import static socialnetwork.domain.constants.Constants.DATEFORMATTER;

public class UserUI extends AbstractUI {
    private final User user;

    public UserUI(AbstractUI ui, User user) {
        this.service = new UserService(ui.service, user);
        this.user = user;
    }

    @Override
    public void run() throws Exception {
        try {
            String input = "";
            do {
                try {
                    input = input();
                    switch (input) {
                        case "1": acceptedFriendships(); break;
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
        System.out.println("1 - Login");
        System.out.println("2 - Delete user");
        System.out.println("0 - Logout");
        System.out.println("Introduce one of the numbers: ");
        return (new Scanner(System.in)).nextLine();
    }

    private void acceptedFriendships() throws AccessException, FileException {
        for (FriendshipWithStatus friendship : this.service.acceptedFriendships())
            System.out.println("Friendship: " + friendship.theOtherFriend(this.user).getLastName() + " | " +
                    friendship.theOtherFriend(this.user).getFirstName() + " | " +
                    friendship.getDate().toLocalDate().format(DATEFORMATTER));
    }
}
