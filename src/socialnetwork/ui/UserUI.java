package socialnetwork.ui;

import socialnetwork.domain.FriendshipWithStatus;
import socialnetwork.domain.User;
import socialnetwork.domain.util.SpecificList;
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
                        case "0": break;
                        case "1": sendRequest(); break;
                        case "2": acceptRequest(); break;
                        case "3": declineRequest(); break;
                        case "4": deleteFriend(); break;
                        case "5": acceptedFriendships(); break;
                        case "6": pendingFriendships(); break;
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
        System.out.println("1 - Send a friend request");
        System.out.println("2 - Accept a friend request");
        System.out.println("3 - Decline a friend request");
        System.out.println("4 - Delete a friend");
        System.out.println("5 - Show friends");
        System.out.println("6 - Show pending list");
        System.out.println("0 - Logout");
        System.out.println("Introduce one of the numbers:");
        return (new Scanner(System.in)).nextLine();
    }

    private void sendRequest() throws Exception {
        System.out.println("Introduce the email:");
        String email = (new Scanner(System.in)).nextLine();
        service.sendRequest(email);
        System.out.println("Request sent");
    }

    private void acceptRequest() throws Exception {
        System.out.println("Introduce the email:");
        String email = (new Scanner(System.in)).nextLine();
        service.acceptRequest(email);
        System.out.println("Request accepted");
    }

    private void declineRequest() throws Exception {
        System.out.println("Introduce the email:");
        String email = (new Scanner(System.in)).nextLine();
        service.declineRequest(email);
        System.out.println("Request declined");
    }

    private void deleteFriend() throws Exception {
        System.out.println("Introduce the email:");
        String email = (new Scanner(System.in)).nextLine();
        service.deleteFriend(email);
        System.out.println("Friend deleted");
    }

    private void showList(SpecificList list) throws Exception {
        for (FriendshipWithStatus friendship : list.process())
            System.out.println("Friendship: " + friendship.theOtherFriend(this.user).getLastName() + " | " +
                    friendship.theOtherFriend(this.user).getFirstName() + " | " +
                    friendship.getDate().toLocalDate().format(DATEFORMATTER));
    }

    private void acceptedFriendships() throws Exception {
        System.out.println("Accepted friendships:");
        showList(() -> service.acceptedFriendships());
    }

    private void pendingFriendships() throws Exception {
        System.out.println("Pending list:");
        showList(() -> service.pendingFriendships());
    }
}
