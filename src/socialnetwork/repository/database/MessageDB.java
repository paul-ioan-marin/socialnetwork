package socialnetwork.repository.database;

import socialnetwork.domain.Message;
import socialnetwork.domain.User;
import socialnetwork.domain.containers.UserList;
import socialnetwork.domain.exceptions.FileException;
import socialnetwork.domain.validator.UserValidator;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static socialnetwork.domain.constants.Constants.*;
import static socialnetwork.domain.constants.Constants.ID;

public class MessageDB extends RepositoryDB<Message> {

    public RepositoryDB<User> userRepo;

    public MessageDB(String url, String username, String password) {
        super(url, username, password);
    }

    /**
     * Finds one message
     * @param uuid the id of the message
     * @return an Message object with that id
     * @throws FileException in case the file doesn't exist
     */
    @Override
    public Message findOne(UUID uuid) throws FileException {
        String sql = "select * from messages where id = ?";
        return super.findOne(uuid.toString(), sql);
    }

    /**
     * Finds all the messages in the db
     * @return
     * @throws FileException
     */
    @Override
    public Iterable<Message> findAll() throws FileException {
        String sql = "select * from messages";
        return super.findAll(sql);
    }

    @Override
    public Message save(Message entity) throws FileException {
        String sql = "insert into messages (id, text_message, timestamp, id_from, id_group_to ) values (?, ?, ?, ?)";
        String[] attributes = new String[] {entity.getId().toString(),entity.getMessage(), entity.getDate().toString(),
                entity.getFrom().getId().toString(), entity.getTo().toString()};
        return super.save(entity, sql, attributes);
    }

    @Override
    public Message delete(UUID uuid) throws FileException {
        String sql = "delete from messages where id = ?";
        String[] attributes = new String[] {uuid.toString()};
        return super.delete(uuid, sql, attributes);
    }

    @Override
    public Message update(Message entity) throws FileException {
        String sql = "update messages set text_message = ?, timestamp = ?, id_from = ?, id_group_to = ? where id = ?";
        String[] attributes = new String[] {entity.getMessage(), entity.getDate().toString(),
                entity.getFrom().getId().toString(), entity.getTo().toString(),entity.getId().toString()};
        return super.update(entity, sql, attributes);
    }

    @Override
    protected Message getFromDB(ResultSet resultSet) throws FileException {
        Map<String, String> fromDB = RepositoryDB.getStringDB(resultSet, new String[]{ID, TEXTMSG, TIMESTAMP, FROM , TO});
        User from = userRepo.findOne(UUID.fromString(fromDB.get(FROM)));
        UserList list_users = new UserList();
        List<String> string_users = List.of(fromDB.get(TO).split(","));
        for (String str: string_users) {
            list_users.add(userRepo.findOne(UUID.fromString(str)));
        }
        String text_message = fromDB.get(TEXTMSG);
        LocalDateTime timestamp = LocalDateTime.parse(fromDB.get(TIMESTAMP),DATEFORMATTER);
        Message result = new Message(from, list_users,text_message,timestamp);
        result.setId(UUID.fromString(fromDB.get(ID)));
        return result;
    }
}