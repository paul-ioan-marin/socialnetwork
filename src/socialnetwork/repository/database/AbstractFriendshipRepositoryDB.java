package socialnetwork.repository.database;

import socialnetwork.domain.Friendship;
import socialnetwork.domain.exceptions.FileException;
import socialnetwork.domain.exceptions.IdException;
import socialnetwork.domain.validator.FriendshipValidator;

import java.util.UUID;

public abstract class AbstractFriendshipRepositoryDB<E extends Friendship> extends RepositoryDB<E> {
    protected final UserRepositoryDB users;

    public AbstractFriendshipRepositoryDB(String url, String username, String password) {
        super(url, username, password);
        this.validator = new FriendshipValidator<>();
        users = new UserRepositoryDB(url, username, password);
    }

    @Override
    public E findOne(UUID uuid) throws IdException, FileException {
        String sql = "select * from friendships where id = ?";
        return super.findOne(uuid.toString(), sql);
    }

    @Override
    public Iterable<E> findAll() throws IdException, FileException {
        String sql = "SELECT * from friendships";
        return super.findAll(sql);
    }

    public UserRepositoryDB getUsers() throws IdException, FileException {
        return users;
    }

    @Override
    protected E save(E friendship, String sql, String[] attributes) throws IdException, FileException {
        if (!users.contains(friendship.getLeft()) || !users.contains(friendship.getRight())) return null;
        return super.save(friendship, sql, attributes);
    }

    @Override
    public E delete(UUID uuid) throws FileException {
        String sql = "delete from friendships where id = ?";
        String[] attributes = new String[] {uuid.toString()};
        return super.delete(uuid, sql, attributes);
    }

    @Override
    public E update(E friendship, String sql, String[] attributes) throws IdException, FileException {
        if (!users.contains(friendship.getLeft()) || !users.contains(friendship.getRight())) return null;
        return super.update(friendship, sql, attributes);
    }
}
