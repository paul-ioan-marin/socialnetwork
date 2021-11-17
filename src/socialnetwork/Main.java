package socialnetwork;

import socialnetwork.domain.FriendshipWithStatus;
import socialnetwork.domain.User;
import socialnetwork.domain.constants.Constants;
import socialnetwork.ui.AbstractUI;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static socialnetwork.domain.constants.Constants.DATEFORMATTER;
import static socialnetwork.domain.constants.Constants.NULLDATE;

public class Main {

    public static void main(String[] args) throws Exception {
        AbstractUI ui = new AbstractUI();
        ui.login();
    }
}
