package socialnetwork;

import socialnetwork.ui.AbstractUI;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static socialnetwork.domain.constants.Constants.DATEFORMATTER;

public class Main {

    public static void main(String[] args) throws Exception {
        AbstractUI ui = new AbstractUI();
        ui.login();
    }
}
