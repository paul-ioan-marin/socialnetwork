package socialnetwork;

import socialnetwork.domain.ReplyMessage;
import socialnetwork.domain.User;
import socialnetwork.domain.containers.UserList;
import socialnetwork.domain.exceptions.FileException;
import socialnetwork.repository.Repository;
import socialnetwork.repository.database.RepositoryDB;
import socialnetwork.repository.database.UserRepositoryDB;
import socialnetwork.repository.database.message.AbstractMessageDB;
import socialnetwork.repository.database.message.ReplyMessageDB;
import socialnetwork.ui.UserInterface;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import socialnetwork.domain.constants.*;

import static socialnetwork.domain.constants.Constants.NULLMSG;
import static socialnetwork.domain.constants.PersonalConstants.*;

public class Main {

    public static void main(String[] args) {
       // UserInterface ui = new UserInterface();
        // ui.run();
        RepositoryDB<User> userDb = new UserRepositoryDB(URL,USERNAME,PASSWORD);
        ReplyMessageDB replyDb = new ReplyMessageDB(URL,USERNAME,PASSWORD);
        try {
            User u1 = userDb.findOne(UUID.fromString("4d9a8993-9a32-4c0d-9a91-60f3b86adf4d"));
            User u2 = userDb.findOne(UUID.fromString("ecd6d517-d819-4060-b40e-101d21cfb6ba"));
            User u3 = userDb.findOne(UUID.fromString("c6f52155-c663-4771-a05e-732c31086274"));
            User u4 = userDb.findOne(UUID.fromString("648f7b50-f49a-45f6-b8fc-162a5ad121ec"));
            UserList userList = new UserList();
            userList.add(u1);
            userList.add(u2);
            LocalDateTime now = LocalDateTime.now();


        ReplyMessage rep1 = new ReplyMessage(NULLMSG, u3,userList,"pls work",LocalDateTime.now());
        replyDb.save(rep1);
        } catch (FileException e) {
            e.printStackTrace();
        }

    }
}
