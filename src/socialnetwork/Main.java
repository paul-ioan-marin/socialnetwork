package socialnetwork;

import socialnetwork.domain.ReplyMessage;
import socialnetwork.domain.User;
import socialnetwork.domain.containers.GroupMessage;
import socialnetwork.domain.containers.UserList;
import socialnetwork.domain.exceptions.FileException;
import socialnetwork.repository.Repository;
import socialnetwork.repository.database.GroupRepositoryDB;
import socialnetwork.repository.database.RepositoryDB;
import socialnetwork.repository.database.UserRepositoryDB;
import socialnetwork.repository.database.message.AbstractMessageDB;
import socialnetwork.repository.database.message.ReplyMessageDB;
import socialnetwork.ui.UserInterface;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import socialnetwork.domain.constants.*;

import static socialnetwork.domain.constants.Constants.DATEFORMATTER;
import static socialnetwork.domain.constants.Constants.NULLMSG;
import static socialnetwork.domain.constants.PersonalConstants.*;

public class Main {

    public static void main(String[] args) throws Exception {
        UserInterface ui = new UserInterface();
        ui.run();
    }
}

