package socialnetwork.repository.database;

import socialnetwork.domain.User;
import socialnetwork.domain.exceptions.FileException;
import socialnetwork.domain.exceptions.IdException;
import socialnetwork.domain.validator.UserValidator;
import static socialnetwork.domain.constants.Constants.*;

import java.sql.*;
import java.util.Map;
import java.util.UUID;

public class UserRepositoryDB extends RepositoryDB<User> {

    public UserRepositoryDB(String url, String username, String password) {
        super(url, username, password);
        this.validator = new UserValidator();
    }

    @Override
    public User findOne(UUID uuid) throws IdException, FileException, Exception {
        String sql = "select * from users where id = ?";
        return super.findOne(uuid.toString(), sql);
    }

    public User findByEmail(String email) throws IdException, FileException, Exception {
        String sql = "select * from users where email = ?";
        return super.findOne(email, sql);
    }

    @Override
    public Iterable<User> findAll() throws IdException, FileException, Exception {
        String sql = "select * from users";
        return super.findAll(sql);
    }

    @Override
    public User save(User user) throws IdException, FileException, Exception {
        String sql = "insert into users (id, email, first_name, last_name) values (?, ?, ?, ?)";
        String[] attributes = new String[] {user.getId().toString(), user.getEmail(),
                user.getFirstName(), user.getLastName()};
        return super.save(user, sql, attributes);
    }

    @Override
    public User delete(UUID uuid) throws IdException, FileException, Exception {
        String sql = "delete from users where id = ?";
        String[] attributes = new String[] {uuid.toString()};
        return super.delete(uuid, sql, attributes);
    }

    @Override
    public User update(User user) throws IdException, FileException, Exception {
        String sql = "update users set email = ?, first_name = ?, last_name = ? where id = ?";
        String[] attributes = new String[] {user.getEmail(), user.getFirstName(),
                user.getLastName(), user.getId().toString()};
        return super.update(user, sql, attributes);
    }

    protected User getFromDB(ResultSet resultSet) throws FileException, Exception {
        Map<String, String> fromDB = RepositoryDB.getStringDB(resultSet, new String[]{ID, EMAIL, FNAME, LNAME});
        User result = new User(fromDB.get(EMAIL), fromDB.get(FNAME), fromDB.get(LNAME));
        result.setId(UUID.fromString(fromDB.get(ID)));
        return result;
    }
}
