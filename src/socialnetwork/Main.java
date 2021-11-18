package socialnetwork;

import socialnetwork.ui.AbstractUI;
import socialnetwork.ui.AdministratorUI;

public class Main {

    public static void main(String[] args) throws Exception {
        AbstractUI ui = new AbstractUI();
        AbstractUI admin = new AdministratorUI(ui);
        admin.run();
    }
}
