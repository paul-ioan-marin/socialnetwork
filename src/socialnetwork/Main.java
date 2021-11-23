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
       // UserInterface ui = new UserInterface();
        // ui.run();
        try {

            ReplyMessageDB gr = new ReplyMessageDB(URL, USERNAME, PASSWORD);
            User u1 = gr.userRepo.findOne(UUID.fromString("de971719-d7d9-4916-8d95-8c4f64c86d53"));
            User u2 = gr.userRepo.findOne(UUID.fromString("55efbbf4-08fa-4020-b9ce-9d9e49cc54b5"));
            GroupMessage g =gr.groupRepo.findOne(UUID.fromString("5feabec9-3aaf-4955-8e77-0c44b6cbcea0"));
            ReplyMessage message = new ReplyMessage(u1, g, "mananca", LocalDateTime.now());
            gr.save(message);

        } catch (FileException e) {
            throw e;
        }

    }
}
