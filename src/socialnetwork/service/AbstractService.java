package socialnetwork.service;

import socialnetwork.domain.Friendship;
import socialnetwork.domain.FriendshipWithStatus;
import socialnetwork.domain.User;
import socialnetwork.domain.containers.FriendshipList;
import socialnetwork.domain.exceptions.AccessException;
import socialnetwork.domain.exceptions.FileException;
import socialnetwork.domain.exceptions.RepositoryException;
import socialnetwork.repository.database.FriendshipStatusRepositoryDB;
import socialnetwork.repository.database.UserRepositoryDB;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AbstractService {
    protected FriendshipStatusRepositoryDB friendships;
    protected UserRepositoryDB users;

    public AbstractService(String url, String username, String password) throws FileException {
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
    public Iterable<FriendshipWithStatus> getFriendships() throws FileException {
        return this.friendships.findAll();
    }

    /**
     * @return the users in the network.
     */
    public Iterable<User> getUsers() throws FileException {
        return users.findAll();
    }


    /**
     * Find a user in the db by the email
     * @param email the email that was typed
     * @return the user with that email
     * @throws FileException if email doesn't exist
     */
    public User findUserByEmail(String email) throws FileException {
        return users.findByEmail(email);
    }

    /**
     * Makes a list of people with the same first name
     * @param first_name the first name we are looking for
     * @return a list of people with the same first name
     * @throws FileException if first name doesn't exist
     */
    public List<User> findUsersByFirstName(String first_name) throws FileException{
        Iterable<User> iterableUsers = users.findAll();
        List<User> sameFirstName =  StreamSupport.stream(iterableUsers.spliterator(), false)
                .filter( user -> user.getFirstName().equals(first_name))
                .collect(Collectors.toList());
        return sameFirstName;
    }

    /**
     * Makes a list of people with the same last name
     * @param last_name the last name we are looking for
     * @return a list of people with the same last name
     * @throws FileException if last name doesn't exist
     */
    public List<User> findUsersByLastName(String last_name) throws FileException{
        Iterable<User> iterableUsers = users.findAll();
        List<User> sameLastName =  StreamSupport.stream(iterableUsers.spliterator(), false)
                .filter( user -> user.getLastName().equals(last_name))
                .collect(Collectors.toList());
        return sameLastName;
    }

    /**
     * Makes a string that contains the people with that particular email and mont
     * @param user the user that we are looking for
     * @param month the month in which he made friends
     * @return a string with the name and date
     * @throws FileException
     */
    public String getFriendshipByMonth(User user, int month) throws FileException {
        String sameMonthFriends =  StreamSupport.stream(friendships.findAll().spliterator(), false)
                .filter(friends -> (friends.getLeft().getEmail().equals(user.getEmail())||friends.getRight().getEmail().equals(user.getEmail())))
                .filter(friends -> friends.getDate().getMonthValue() == month)
                .map( friends -> friends.getRight().getFullName()+" | "+friends.getLeft().getFullName()+"" +
                        " | "+friends.getDate().toString() )
                .collect( Collectors.joining( "\n" ));
        return sameMonthFriends;
    }

    /**
     * Adds user by the given attributes;
     * @param email given email;
     * @param firstName given first name;
     * @param lastName given last name;
     * @throws FileException if the file is invalid;
     * @throws RepositoryException if the email exists.
     */
    public void addUser(String email, String firstName, String lastName) throws FileException, RepositoryException, AccessException {
        throw new AccessException("inaccessible method");
    }

    /**
     * Deletes user by the given email;
     * @param email the given email;
     * @throws FileException if the file is invalid;
     * @throws RepositoryException if the email does not exist.
     */
    public void deleteUser(String email) throws FileException, RepositoryException, AccessException {
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
    public void updateUser(String email, String firstName, String lastName) throws FileException, RepositoryException, AccessException {
        throw new AccessException("inaccessible method");
    }

    /**
     * Adds friendship by 2 given emails;
     * @param email1 the #1 given email;
     * @param email2 the #2 given email;
     * @throws FileException if the file is invalid;
     * @throws RepositoryException if the friendship exists.
     */
    public void addFriendship(String email1, String email2) throws FileException, RepositoryException, AccessException {
        throw new AccessException("inaccessible method");
    }

    /**
     * Deletes friendship by 2 given emails;
     * @param email1 the #1 given email;
     * @param email2 the #2 given email;
     * @throws FileException if the file is invalid;
     * @throws RepositoryException if the friendship does not exist.
     */
    public void deleteFriendship(String email1, String email2) throws FileException, RepositoryException, AccessException {
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
    public void updateFriendship(String email1, String email2, String email3, String email4) throws FileException, RepositoryException, AccessException {
        throw new AccessException("inaccessible method");
    }

    public void sendRequest(String email) throws FileException, RepositoryException, AccessException {
        throw new AccessException("inaccessible method");
    }
    public FriendshipList acceptedFriendships() throws FileException, AccessException {
        throw new AccessException("inaccessible method");
    }
}
