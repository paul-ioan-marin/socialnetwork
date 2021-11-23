package socialnetwork.repository.database.message;

import socialnetwork.domain.Message;
import socialnetwork.domain.ReplyMessage;
import socialnetwork.domain.User;
import socialnetwork.domain.containers.GroupMessage;
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
    public ReplyMessage save(ReplyMessage entity) throws FileException, Exception {
        String sql = "insert into messages (id, text_message, timestamp, id_from, id_group, base_message) values (?, ?, ?, ?, ?, ?)";
        String[] attributes = new String[] {entity.getId().toString(),entity.getMessage(), entity.getDate().toString(),
                entity.getFrom().getId().toString(), entity.getGroup().getId().toString(), entity.getReply().getId().toString()};
        return super.save(entity, sql, attributes);
    }

    @Override
    public ReplyMessage update(ReplyMessage entity) throws FileException, Exception {
        String sql = "update messages set text_message = ?, timestamp = ?, id_from = ?, id_group = ?, base_message= ? where id = ?";
        String[] attributes = new String[] {entity.getMessage(), entity.getDate().toString(),
                entity.getFrom().getId().toString(), entity.getGroup().getId().toString(),entity.getReply().getId().toString(), entity.getId().toString()};
        return super.update(entity, sql, attributes);
    }

    @Override
    protected ReplyMessage getFromDB(ResultSet resultSet) throws FileException, Exception {
        Map<String, String> fromDB = RepositoryDB.getStringDB(resultSet, new String[]{ID, TEXTMSG, TIMESTAMP, FROM , TO, BASEMSG});
        String text_message = fromDB.get(TEXTMSG);
        LocalDateTime timestamp = LocalDateTime.parse(fromDB.get(TIMESTAMP),DATEFORMATTER);
        User from = userRepo.findOne(UUID.fromString(fromDB.get(FROM)));
        GroupMessage to = groupRepo.findOne(UUID.fromString(fromDB.get(TO)));
        Message basetext = findOne(UUID.fromString(fromDB.get(BASEMSG)));
        ReplyMessage result = new ReplyMessage(basetext, from, to, text_message, timestamp);
        result.setId(UUID.fromString(fromDB.get(ID)));
        return result;
    }
}