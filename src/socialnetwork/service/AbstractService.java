package socialnetwork.service;

import socialnetwork.domain.FriendshipWithStatus;
import socialnetwork.domain.User;
import socialnetwork.domain.containers.FriendshipList;
import socialnetwork.domain.exceptions.AccessException;
import socialnetwork.repository.database.friendship.FriendshipStatusRepositoryDB;
import socialnetwork.repository.database.UserRepositoryDB;

/**
 * Abstract Service.
 */
public class AbstractService {
    protected FriendshipStatusRepositoryDB friendships;
    protected UserRepositoryDB users;

    public AbstractService(String url, String username, String password) {
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

    /**
     * Finds the user with a given email;
     * @param email the given email;
     * @return the user with the given email.
     */
    public User findUserByEmail(String email) throws Exception {
        return users.findByEmail(email);
    }

    /**
     * Adds user by the given attributes;
     * @param email given email;
     * @param firstName given first name;
     * @param lastName given last name;
     */
    public void addUser(String email, String firstName, String lastName) throws Exception {
        throw new AccessException("inaccessible method");
    }

    /**
     * Deletes user by the given email;
     * @param email the given email;
     */
    public void deleteUser(String email) throws Exception {
        throw new AccessException("inaccessible method");
    }

    /**
     * Updates user by the given attributes;
     * @param email given email;
     * @param firstName given first name;
     * @param lastName given last name;
     */
    public void updateUser(String email, String firstName, String lastName) throws Exception {
        throw new AccessException("inaccessible method");
    }

    /**
     * Adds friendship by 2 given emails;
     * @param email1 the #1 given email;
     * @param email2 the #2 given email;
     */
    public void addFriendship(String email1, String email2) throws Exception {
        throw new AccessException("inaccessible method");
    }

    /**
     * Deletes friendship by 2 given emails;
     * @param email1 the #1 given email;
     * @param email2 the #2 given email;
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
     */
    public void updateFriendship(String email1, String email2, String email3, String email4) throws Exception {
        throw new AccessException("inaccessible method");
    }

    /**
     * Sends request to a specific user, given by email;
     * @param email the given email.
     */
    public void sendRequest(String email) throws Exception {
        throw new AccessException("inaccessible method");
    }

    /**
     * Accepts a request of a specific user, given by email;
     * @param email the given email.
     */
    public void acceptRequest(String email) throws Exception {
        throw new AccessException("inaccessible method");
    }

    /**
     * Declines a request of a specific user, given by email;
     * @param email the given email.
     */
    public void declineRequest(String email) throws Exception {
        throw new AccessException("inaccessible method");
    }

    /**
     * Deletes a friendship with a specific user, given by email;
     * @param email the given email.
     */
    public void deleteFriend(String email) throws Exception {
        throw new AccessException("inaccessible method");
    }

    /**
     * Returns the list with the accepted friendships;
     * @return the list with the accepted friendships.
     */
    public FriendshipList acceptedFriendships() throws Exception {
        throw new AccessException("inaccessible method");
    }

    /**
     * Returns the pendig list;
     * @return the pending list.
     */
    public FriendshipList pendingFriendships() throws Exception {
        throw new AccessException("inaccessible method");
    }
}
