package socialnetwork.service;

import socialnetwork.domain.FriendshipWithStatus;
import socialnetwork.domain.User;
import socialnetwork.domain.containers.FriendshipList;
import socialnetwork.domain.exceptions.AccessException;
import socialnetwork.domain.exceptions.FileException;
import socialnetwork.domain.exceptions.RepositoryException;
import socialnetwork.repository.database.FriendshipStatusRepositoryDB;
import socialnetwork.repository.database.UserRepositoryDB;

public class AbstractService {
    protected FriendshipStatusRepositoryDB friendships;
    protected UserRepositoryDB users;

    public AbstractService(String url, String username, String password) throws Exception {
        this.friendships = new FriendshipStatusRepositoryDB(url, username, password);
        users = this.friendships.getUsers();
    }

    public AbstractService(AbstractService service) {
        this.friendships = service.friendships;
        this.users = service.users;
    }

    /**
     * @return the friendships in the network.
     */
    public Iterable<FriendshipWithStatus> getFriendships() throws Exception {
        return this.friendships.findAll();
    }

    /**
     * @return the users in the network.
     */
    public Iterable<User> getUsers() throws Exception {
        return users.findAll();
    }

    public User findUserByEmail(String email) throws Exception {
        return users.findByEmail(email);
    }

    /**
     * Adds user by the given attributes;
     * @param email given email;
     * @param firstName given first name;
     * @param lastName given last name;
     * @throws FileException if the file is invalid;
     * @throws RepositoryException if the email exists.
     */
    public void addUser(String email, String firstName, String lastName) throws Exception {
        throw new AccessException("inaccessible method");
    }

    /**
     * Deletes user by the given email;
     * @param email the given email;
     * @throws FileException if the file is invalid;
     * @throws RepositoryException if the email does not exist.
     */
    public void deleteUser(String email) throws Exception {
        throw new AccessException("inaccessible method");
    }

    /**
     * Updates user by the given attributes;
     * @param email given email;
     * @param firstName given first name;
     * @param lastName given last name;
     * @throws FileException if the file is invalid;
     * @throws RepositoryException if the email does not exist.
     */
    public void updateUser(String email, String firstName, String lastName) throws Exception {
        throw new AccessException("inaccessible method");
    }

    /**
     * Adds friendship by 2 given emails;
     * @param email1 the #1 given email;
     * @param email2 the #2 given email;
     * @throws FileException if the file is invalid;
     * @throws RepositoryException if the friendship exists.
     */
    public void addFriendship(String email1, String email2) throws Exception {
        throw new AccessException("inaccessible method");
    }

    /**
     * Deletes friendship by 2 given emails;
     * @param email1 the #1 given email;
     * @param email2 the #2 given email;
     * @throws FileException if the file is invalid;
     * @throws RepositoryException if the friendship does not exist.
     */
    public void deleteFriendship(String email1, String email2) throws Exception {
        throw new AccessException("inaccessible method");
    }

    /**
     * Updates friendship by 2 given emails;
     * @param email1 the #1 given email;
     * @param email2 the #2 given email;
     * @param email3 the #1 email to update;
     * @param email4 the #2 email to update;
     * @throws FileException if the file is invalid;
     * @throws RepositoryException if the friendship does not exist.
     */
    public void updateFriendship(String email1, String email2, String email3, String email4) throws Exception {
        throw new AccessException("inaccessible method");
    }

    public void sendRequest(String email) throws Exception {
        throw new AccessException("inaccessible method");
    }
    public void acceptRequest(String email) throws Exception {
        throw new AccessException("inaccessible method");
    }
    public void declineRequest(String email) throws Exception {
        throw new AccessException("inaccessible method");
    }
    public void deleteFriend(String email) throws Exception {
        throw new AccessException("inaccessible method");
    }
    public FriendshipList acceptedFriendships() throws Exception {
        throw new AccessException("inaccessible method");
    }
    public FriendshipList pendingFriendships() throws Exception {
        throw new AccessException("inaccessible method");
    }
}
