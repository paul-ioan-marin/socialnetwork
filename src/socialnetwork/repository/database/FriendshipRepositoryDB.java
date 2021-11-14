package socialnetwork.repository.database;

import socialnetwork.domain.Friendship;
import socialnetwork.domain.User;
import socialnetwork.domain.exceptions.FileException;
import socialnetwork.domain.exceptions.IdException;
import socialnetwork.domain.validator.FriendshipValidator;
import static socialnetwork.domain.constants.Constants.*;


import java.sql.ResultSet;
import java.util.Map;
import java.util.UUID;

public class FriendshipRepositoryDB extends RepositoryDB<Friendship> {
    private final UserRepositoryDB users;

    public FriendshipRepositoryDB(String url, String username, String password) {
        super(url, username, password);
        this.validator = new FriendshipValidator();
        users = new UserRepositoryDB(url, username, password);
    }

    @Override
    public Friendship findOne(UUID uuid) throws IdException, FileException {
        String sql = "select * from friendships where id = ?";
        return super.findOne(uuid.toString(), sql);
    }

    @Override
    public Iterable<Friendship> findAll() throws IdException, FileException {
        String sql = "SELECT * from friendships";
        return super.findAll(sql);
    }

    public UserRepositoryDB getUsers() throws IdException, FileException {
        return users;
    }

    @Override
    public Friendship save(Friendship friendship) throws FileException {
        if (!users.contains(friendship.getLeft()) || !users.contains(friendship.getRight())) return null;
        String sql = "insert into friendships (id, friend_1, friend_2) values (?, ?, ?)";
        String[] attributes = new String[] {friendship.getId().toString(),
                friendship.getLeft().getId().toString(), friendship.getRight().getId().toString()};
        return super.save(friendship, sql, attributes);
    }

    @Override
    public Friendship delete(UUID uuid) throws FileException {
        String sql = "delete from friendships where id = ?";
        String[] attributes = new String[] {uuid.toString()};
        return super.delete(uuid, sql, attributes);
    }

    @Override
    public Friendship update(Friendship friendship) throws FileException {
        if (!users.contains(friendship.getLeft()) || !users.contains(friendship.getRight())) return null;
        String sql = "update friendships set friend_1 = ?, friend_2 = ? where id = ?";
        String[] attributes = new String[] {friendship.getLeft().getId().toString(),
                friendship.getId().toString(), friendship.getRight().getId().toString()};
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
