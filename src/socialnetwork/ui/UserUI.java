package socialnetwork.ui;

import socialnetwork.domain.exceptions.InputException;

import java.util.Scanner;

public class UserUI extends AbstractUI {
    @Override
    public void run() {
        try {
            String input = "";
            do {
                try {
                    input = input();
                    switch (input) {
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
        System.out.println("1 - Login");
        System.out.println("2 - Delete user");
        System.out.println("0 - Logout");
        System.out.println("Introduce one of the numbers: ");
        return (new Scanner(System.in)).nextLine();
    }
}
