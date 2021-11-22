package socialnetwork.repository.database;

import socialnetwork.domain.exceptions.FileException;
import socialnetwork.domain.exceptions.IdException;
import socialnetwork.domain.primary.Entity;
import socialnetwork.domain.validator.Validator;
import socialnetwork.repository.Repository;

import java.sql.*;
import java.util.*;

public abstract class RepositoryDB<E extends Entity<UUID>> implements Repository<UUID, E> {
    private final String url;
    private final String username;
    private final String password;
    protected Validator<E> validator;

    public RepositoryDB(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    protected E findOne(String value, String sql) throws IdException, FileException {
        if (value == null) throw new IdException("value must not be null");
        try {
            ResultSet resultSet = this.executeQuery(sql, new String[] {value});
            if (resultSet.next()) {
                E entity = getFromDB(resultSet);
                validator.validate(entity);
                return entity;
            }
        } catch (SQLException e) { throw new FileException("corrupted file"); }
        return null;
    }

    protected Iterable<E> findAll(String sql) throws FileException {
        Set<E> entities = new HashSet<>();
        try {
            ResultSet resultSet = this.executeQuery(sql, new String[] {});
            while (resultSet.next()) {
                E entity = getFromDB(resultSet);
                validator.validate(entity);
                entities.add(entity);
            }
        } catch (SQLException e) { throw new FileException("corrupted file"); }
        return entities;
    }

    protected E save(E entity, String sql, String[] attributes) throws IdException, FileException {
        if (entity == null) throw new IdException("entity must not be null");
        if(this.contains(entity)) return null;
        validator.validate(entity);
        this.modifyDB(sql, attributes);
        return entity;
    }

    protected E delete(UUID uuid, String sql, String[] attributes) throws IdException, FileException {
        if (uuid == null) throw new IdException("id must not be null");
        E entity = findOne(uuid);
        if (entity == null) return null;
        this.modifyDB(sql, attributes);
        return entity;
    }

    protected E update(E entity, String sql, String[] attributes) throws IdException, FileException {
        if (entity == null) throw new IdException("entity must not be null");
        if (findOne(entity.getId()) == null) return null;
        validator.validate(entity);
        this.modifyDB(sql, attributes);
        return entity;
    }

    protected abstract E getFromDB(ResultSet resultSet) throws FileException;

    public boolean contains(E entity) throws FileException {
        for (E e : this.findAll()) if (e.equals(entity)) return true;
        return false;
    }

    public boolean contains(List<E> entities) throws FileException {
        for (E entity: entities) {
            if(!contains(entity)){return false;}
        }
        return true;
    }

    private PreparedStatement execute(String sql, String[] attributes) throws SQLException {
        Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < attributes.length; i++) {
            statement.setString(i + 1, attributes[i]);
        }
        return statement;
    }

    private ResultSet executeQuery(String sql, String[] attributes) throws SQLException {
        return this.execute(sql, attributes).executeQuery();
    }

    private void modifyDB(String sql, String[] attributes) throws FileException {
        try { this.execute(sql, attributes).executeUpdate(); }
        catch (SQLException e) { throw new FileException("corrupted file"); }
    }

    protected static Map<String, String> getStringDB(ResultSet resultSet, String[] columnsArray) throws FileException {
        Map<String, String> result = new HashMap<>();
        try {
            for (String column : columnsArray)
                result.put(column, resultSet.getString(column));
        } catch (SQLException e) { throw new FileException("corrupted file"); }
        return result;
    }
}
