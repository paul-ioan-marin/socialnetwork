package socialnetwork.repository.database;

import socialnetwork.domain.Friendship;
import socialnetwork.domain.User;
import socialnetwork.domain.exceptions.FileException;
import static socialnetwork.domain.constants.Constants.*;


import java.sql.ResultSet;
import java.util.Map;
import java.util.UUID;

public class FriendshipRepositoryDB extends SuperFriendshipRepositoryDB<Friendship> {
    public FriendshipRepositoryDB(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public Friendship save(Friendship friendship) throws FileException {
        String sql = "insert into friendships (id, friend_1, friend_2) values (?, ?, ?)";
        String[] attributes = new String[] {friendship.getId().toString(),
                friendship.getLeft().getId().toString(), friendship.getRight().getId().toString()};
        return super.save(friendship, sql, attributes);
    }

    @Override
    public Friendship update(Friendship friendship) throws FileException {
        String sql = "update friendships set friend_1 = ?, friend_2 = ? where id = ?";
        String[] attributes = new String[] {friendship.getLeft().getId().toString(),
                friendship.getRight().getId().toString(), friendship.getId().toString()};
        return super.update(friendship, sql, attributes);
    }

    @Override
    protected Friendship getFromDB(ResultSet resultSet) throws FileException {
        Map<String, String> fromDB = RepositoryDB.getStringDB(resultSet, new String[]{ID, FRIEND1, FRIEND2});
        User user1 = users.findOne(UUID.fromString(fromDB.get(FRIEND1)));
        User user2 = users.findOne(UUID.fromString(fromDB.get(FRIEND2)));
        Friendship result = new Friendship(user1, user2);
        result.setId(UUID.fromString(fromDB.get(ID)));
        return result;
    }
}
