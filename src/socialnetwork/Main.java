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

import static socialnetwork.domain.constants.Constants.NULLMSG;
import static socialnetwork.domain.constants.PersonalConstants.*;

public class Main {

    public static void main(String[] args) throws Exception {
        // UserInterface ui = new UserInterface();
        // ui.run();
        try {

            ReplyMessageDB gr = new ReplyMessageDB(URL, USERNAME, PASSWORD);
            User u1 = gr.userRepo.findOne(UUID.fromString("796a6c06-fb5d-4325-b4d0-dcd56df41d25"));
            User u2 = gr.userRepo.findOne(UUID.fromString("648f7b50-f49a-45f6-b8fc-162a5ad121ec"));
            User u3 = gr.userRepo.findOne(UUID.fromString("4d9a8993-9a32-4c0d-9a91-60f3b86adf4d"));
            GroupMessage g =gr.groupRepo.findOne(UUID.fromString("d66386eb-16e2-4e61-b70e-e0b40133e09c"));
            ReplyMessage message = new ReplyMessage(u1, g, "mananca", LocalDateTime.now());
            gr.save(message);

        } catch (FileException e) {
            throw e;
        }

    }
}

