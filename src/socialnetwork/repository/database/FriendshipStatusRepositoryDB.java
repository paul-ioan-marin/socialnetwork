package socialnetwork.repository.database;

import socialnetwork.domain.Friendship;
import socialnetwork.domain.FriendshipWithStatus;
import socialnetwork.domain.User;
import socialnetwork.domain.exceptions.FileException;

import java.sql.ResultSet;
import java.util.Map;
import java.util.UUID;

import static socialnetwork.domain.constants.Constants.*;

public class FriendshipStatusRepositoryDB extends SuperFriendshipRepositoryDB<FriendshipWithStatus> {
    public FriendshipStatusRepositoryDB(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public FriendshipWithStatus save(FriendshipWithStatus friendship) throws FileException {
        String sql = "insert into friendships (id, friend_1, friend_2) values (?, ?, ?, ?)";
        String[] attributes = new String[] {friendship.getId().toString(),
                friendship.getLeft().getId().toString(), friendship.getRight().getId().toString(), friendship.status().getValue()};
        return super.save(friendship, sql, attributes);
    }

    @Override
    public FriendshipWithStatus update(FriendshipWithStatus friendship) throws FileException {
        String sql = "update friendships set friend_1 = ?, friend_2 = ?, status = ? where id = ?";
        String[] attributes = new String[] {friendship.getLeft().getId().toString(), friendship.getRight().getId().toString(),
                friendship.status().getValue(), friendship.getId().toString()};
        return super.update(friendship, sql, attributes);
    }

    @Override
    protected FriendshipWithStatus getFromDB(ResultSet resultSet) throws FileException {
        Map<String, String> fromDB = RepositoryDB.getStringDB(resultSet, new String[]{ID, FRIEND1, FRIEND2, STATUS});
        User user1 = users.findOne(UUID.fromString(fromDB.get(FRIEND1)));
        User user2 = users.findOne(UUID.fromString(fromDB.get(FRIEND2)));
        Status status = Status.fromString(fromDB.get(STATUS));
        FriendshipWithStatus result = new FriendshipWithStatus(user1, user2, status);
        result.setId(UUID.fromString(fromDB.get(ID)));
        return result;
    }

    public FriendshipWithStatus accept(FriendshipWithStatus friendship) throws FileException {
        friendship.accept();
        return update(friendship);
    }

    public FriendshipWithStatus decline(FriendshipWithStatus friendship) throws FileException {
        return delete(friendship.getId());
    }
}
