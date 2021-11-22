package socialnetwork.repository.database.message;

import socialnetwork.domain.Message;
import socialnetwork.domain.ReplyMessage;
import socialnetwork.domain.User;
import socialnetwork.domain.containers.UserList;
import socialnetwork.domain.exceptions.FileException;
import socialnetwork.repository.database.RepositoryDB;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static socialnetwork.domain.constants.Constants.*;

public class ReplyMessageDB extends AbstractMessageDB<ReplyMessage>{

    public ReplyMessageDB(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public ReplyMessage save(ReplyMessage entity) throws FileException {
        String sql = "insert into messages (id, text_message, timestamp, id_from, id_group_to, base_message) values (?, ?, ?, ?, ?, ?)";
        String[] attributes = new String[] {entity.getId().toString(),entity.getMessage(), entity.getDate().toString(),
                entity.getFrom().getId().toString(), entity.getTo().toString() , entity.getReply().getId().toString()};
        return super.save(entity, sql, attributes);
    }

    @Override
    public ReplyMessage update(ReplyMessage entity) throws FileException {
        String sql = "update messages set text_message = ?, timestamp = ?, id_from = ?, id_group_to = ?, base_message= ? where id = ?";
        String[] attributes = new String[] {entity.getMessage(), entity.getDate().toString(),
                entity.getFrom().getId().toString(), entity.getTo().toString(),entity.getReply().getId().toString(), entity.getId().toString()};
        return super.update(entity, sql, attributes);
    }

    @Override
    protected ReplyMessage getFromDB(ResultSet resultSet) throws FileException {
        Map<String, String> fromDB = RepositoryDB.getStringDB(resultSet, new String[]{ID, TEXTMSG, TIMESTAMP, FROM , TO, BASEMSG});
        User from = userRepo.findOne(UUID.fromString(fromDB.get(FROM)));
        UserList list_users = new UserList();
        List<String> string_users = List.of(fromDB.get(TO).split(","));
        for (String str: string_users) {
            list_users.add(userRepo.findOne(UUID.fromString(str)));
        }
        String text_message = fromDB.get(TEXTMSG);
        LocalDateTime timestamp = LocalDateTime.parse(fromDB.get(TIMESTAMP),DATEFORMATTER);
        Message basetext = findOne(UUID.fromString(fromDB.get(BASEMSG)));
        ReplyMessage result = new ReplyMessage(basetext,from, list_users,text_message,timestamp);
        result.setId(UUID.fromString(fromDB.get(ID)));
        return result;
    }
}
