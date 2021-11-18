package socialnetwork.service;

import socialnetwork.domain.Friendship;
import socialnetwork.domain.FriendshipWithStatus;
import socialnetwork.domain.User;
import socialnetwork.domain.containers.UserList;
import socialnetwork.domain.exceptions.FileException;
import socialnetwork.domain.exceptions.RepositoryException;
import socialnetwork.repository.database.FriendshipRepositoryDB;
import socialnetwork.repository.database.FriendshipStatusRepositoryDB;
import socialnetwork.repository.database.SuperFriendshipRepositoryDB;
import socialnetwork.repository.database.UserRepositoryDB;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Service {
    private final FriendshipStatusRepositoryDB friendships;
    private final UserRepositoryDB users;

    public Service(String url, String username, String password) throws FileException {
        this.friendships = new FriendshipStatusRepositoryDB(url, username, password);
        users = this.friendships.getUsers();
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
        //Set<User> users = new HashSet<>();
        //for (User user : this.users.findAll()) {
        //    users.add(user);
        //    for (Friendship friendship : this.friendships.findAll()) {
        //        if (friendship.getLeft().equals(user))
        //            user.addFriend(friendship.getRight());
        //        if (friendship.getRight().equals(user))
        //            user.addFriend(friendship.getLeft());
        //    }
        //}
        //return users;
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
     * Adds user by the given attributes;
     * @param email given email;
     * @param firstName given first name;
     * @param lastName given last name;
     * @throws FileException if the file is invalid;
     * @throws RepositoryException if the email exists.
     */
    public void addUser(String email, String firstName, String lastName) throws FileException, RepositoryException {
        User user = users.save(new User(email, firstName, lastName));
        if (user == null) throw new RepositoryException("the email already exists");
    }

    /**
     * Deletes user by the given email;
     * @param email the given email;
     * @throws FileException if the file is invalid;
     * @throws RepositoryException if the email does not exist.
     */
    public void deleteUser(String email) throws FileException, RepositoryException {
        User user = users.findByEmail(email);
        if (user == null)
            throw new RepositoryException("the email does not exits");
        ArrayList<Friendship> toDel = new ArrayList<>();
        for (Friendship friendship : friendships.findAll())
            if (friendship.isFriend(user))
                toDel.add(friendship);
        for(Friendship friendship : toDel)
            friendships.delete(friendship.getId());
        user = users.delete(user.getId());
        if (user == null) throw new RepositoryException("the email does not exist");
    }

    /**
     * Updates user by the given attributes;
     * @param email given email;
     * @param firstName given first name;
     * @param lastName given last name;
     * @throws FileException if the file is invalid;
     * @throws RepositoryException if the email does not exist.
     */
    public void updateUser(String email, String firstName, String lastName) throws FileException, RepositoryException {
        User user = friendships.getUsers().findByEmail(email);
        if (user == null)
            throw new RepositoryException("the email does not exist");
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user = users.update(user);
        if (user == null) throw new RepositoryException("the email does not exist");
    }

    /**
     * Adds friendship by 2 given emails;
     * @param email1 the #1 given email;
     * @param email2 the #2 given email;
     * @throws FileException if the file is invalid;
     * @throws RepositoryException if the friendship exists.
     */
    public void addFriendship(String email1, String email2) throws FileException, RepositoryException {
        if (users.findByEmail(email1) == null)
            throw new RepositoryException("none of the users has one of the emails");
        if (users.findByEmail(email2) == null)
            throw new RepositoryException("none of the users has one of the emails");
        Friendship friendship = friendships.save(new FriendshipWithStatus(users.findByEmail(email1), users.findByEmail(email2)));
        if (friendship == null) throw new RepositoryException("the friendship already exists");
    }

    /**
     * Deletes friendship by 2 given emails;
     * @param email1 the #1 given email;
     * @param email2 the #2 given email;
     * @throws FileException if the file is invalid;
     * @throws RepositoryException if the friendship does not exist.
     */
    public void deleteFriendship(String email1, String email2) throws FileException, RepositoryException {
        if (users.findByEmail(email1) == null || users.findByEmail(email2) == null)
            throw new RepositoryException("none of the users has one of the emails");
        FriendshipWithStatus friendship = new FriendshipWithStatus(users.findByEmail(email1), users.findByEmail(email2));
        for (FriendshipWithStatus i : friendships.findAll())
            if (i.equals(friendship)) {
                friendships.delete(i.getId());
                return;
            }
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
    public void updateFriendship(String email1, String email2, String email3, String email4) throws FileException, RepositoryException {
        if (users.findByEmail(email1) == null)
            throw new RepositoryException("none of the users has one of the emails");
        if (users.findByEmail(email2) == null)
            throw new RepositoryException("none of the users has one of the emails");
        FriendshipWithStatus friendship = new FriendshipWithStatus(users.findByEmail(email1), users.findByEmail(email2));
        for (FriendshipWithStatus i : friendships.findAll())
            if (i.equals(friendship)) {
                friendship.setId(i.getId());
            }
        friendship.setLeft(friendships.getUsers().findByEmail(email3));
        friendship.setRight(friendships.getUsers().findByEmail(email4));
        friendships.update(friendship);
    }
}
