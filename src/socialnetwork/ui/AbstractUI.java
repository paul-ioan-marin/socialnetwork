package socialnetwork.ui;

import socialnetwork.domain.exceptions.FileException;
import socialnetwork.service.Service;

import java.util.Scanner;

import static socialnetwork.domain.constants.PersonalConstants.*;

public class AbstractUI {
    protected Service service;

    public AbstractUI() {
        try {
            service = new Service(URL, USERNAME, PASSWORD);
        } catch (FileException e) {
            System.out.println(e.getMessage());
        }
    }

    public void login() {
        System.out.println("*****************************************");
        System.out.println("Introduce the email for login: ");
        String email = (new Scanner(System.in)).nextLine();
        try {
            AbstractUI ui = null;
            if (email.equals("paul")) ui = new AdministratorUI();
            if (service.findUserByEmail(email) != null) ui = new UserUI();
            if (ui != null) {
                System.out.println("Welcome " + email + "!");
                ui.run();
                return;
            }
            System.out.println("invalid email");
        } catch (FileException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {};
}
