package socialnetwork.repository.database.message;

import socialnetwork.domain.Message;
import socialnetwork.domain.User;
import socialnetwork.domain.exceptions.FileException;
import socialnetwork.domain.exceptions.IdException;
import socialnetwork.domain.validator.MessageValidator;
import socialnetwork.repository.database.RepositoryDB;
import socialnetwork.repository.database.UserRepositoryDB;

import java.util.UUID;

public abstract class AbstractMessageDB<E extends Message> extends RepositoryDB<E> {

    public RepositoryDB<User> userRepo;

    public AbstractMessageDB(String url, String username, String password) {
        super(url, username, password);
        this.validator = new MessageValidator<>();
        this.userRepo = new UserRepositoryDB(url,username,password);
    }


    @Override
    public E findOne(UUID uuid) throws FileException {
        String sql = "select * from messages where id = ?";
        return super.findOne(uuid.toString(), sql);
    }


    @Override
    public Iterable<E> findAll() throws FileException {
        String sql = "select * from messages";
        return super.findAll(sql);
    }

    @Override
    protected E save(E entity, String sql, String[] attributes) throws IdException, FileException {
        if (!userRepo.contains(entity.getFrom())|| !userRepo.contains(entity.getTo())) return null;
        return super.save(entity, sql, attributes);
    }

    @Override
    protected E update(E entity, String sql, String[] attributes) throws IdException, FileException {
        if (!userRepo.contains(entity.getFrom())|| !userRepo.contains(entity.getTo())) return null;
        return super.update(entity, sql, attributes);
    }

    @Override
    public E delete(UUID uuid) throws FileException {
        String sql = "delete from messages where id = ?";
        String[] attributes = new String[] {uuid.toString()};
        return super.delete(uuid, sql, attributes);
    }


}
