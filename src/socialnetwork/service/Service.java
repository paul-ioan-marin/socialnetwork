package socialnetwork.service;

import socialnetwork.domain.Friendship;
import socialnetwork.domain.ReplyMessage;
import socialnetwork.domain.User;
import socialnetwork.domain.constants.Constants;
import socialnetwork.domain.containers.GroupMessage;
import socialnetwork.domain.containers.UserList;
import socialnetwork.domain.exceptions.FileException;
import socialnetwork.domain.exceptions.RepositoryException;
import socialnetwork.repository.database.FriendshipRepositoryDB;
import socialnetwork.repository.database.GroupRepositoryDB;
import socialnetwork.repository.database.UserRepositoryDB;
import socialnetwork.repository.database.message.ReplyMessageDB;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Service {
    private final FriendshipRepositoryDB friendships;
    private final UserRepositoryDB users;
    private final ReplyMessageDB messages;
    private final GroupRepositoryDB groups;

    public Service(String url, String username, String password) throws Exception {
        this.friendships = new FriendshipRepositoryDB(url, username, password);
        this.users = new UserRepositoryDB(url, username, password);
        this.messages = new ReplyMessageDB(url, username, password);
        this.groups = new GroupRepositoryDB(url, username, password);
    }

    /**
     * @return the friendships in the network.
     */
    public Iterable<Friendship> getFriendships() throws Exception {
        return this.friendships.findAll();
    }

    /**
     * @return the users in the network.
     */
    public Iterable<User> getUsers() throws Exception {
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
     * Adds user by the given attributes;
     * @param email given email;
     * @param firstName given first name;
     * @param lastName given last name;
     * @throws FileException if the file is invalid;
     * @throws RepositoryException if the email exists.
     */
    public void addUser(String email, String firstName, String lastName) throws Exception {
        User user = users.save(new User(email, firstName, lastName));
        if (user == null) throw new RepositoryException("the email already exists");
    }

    /**
     * Deletes user by the given email;
     * @param email the given email;
     * @throws FileException if the file is invalid;
     * @throws RepositoryException if the email does not exist.
     */
    public void deleteUser(String email) throws Exception {
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
    public void updateUser(String email, String firstName, String lastName) throws Exception {
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
    public void addFriendship(String email1, String email2) throws Exception {
        if (users.findByEmail(email1) == null)
            throw new RepositoryException("none of the users has one of the emails");
        if (users.findByEmail(email2) == null)
            throw new RepositoryException("none of the users has one of the emails");
        Friendship friendship = friendships.save(new Friendship(users.findByEmail(email1), users.findByEmail(email2)));
        if (friendship == null) throw new RepositoryException("the friendship already exists");
    }

    /**
     * Deletes friendship by 2 given emails;
     * @param email1 the #1 given email;
     * @param email2 the #2 given email;
     * @throws FileException if the file is invalid;
     * @throws RepositoryException if the friendship does not exist.
     */
    public void deleteFriendship(String email1, String email2) throws Exception {
        if (users.findByEmail(email1) == null || users.findByEmail(email2) == null)
            throw new RepositoryException("none of the users has one of the emails");
        Friendship friendship = new Friendship(users.findByEmail(email1), users.findByEmail(email2));
        for (Friendship i : friendships.findAll())
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
    public void updateFriendship(String email1, String email2, String email3, String email4) throws Exception, RepositoryException {
        if (users.findByEmail(email1) == null)
            throw new RepositoryException("none of the users has one of the emails");
        if (users.findByEmail(email2) == null)
            throw new RepositoryException("none of the users has one of the emails");
        Friendship friendship = new Friendship(users.findByEmail(email1), users.findByEmail(email2));
        for (Friendship i : friendships.findAll())
            if (i.equals(friendship)) {
                friendship.setId(i.getId());
            }
        friendship.setLeft(friendships.getUsers().findByEmail(email3));
        friendship.setRight(friendships.getUsers().findByEmail(email4));
        friendships.update(friendship);
    }

    public void sendMessage(String email_from, String emails_to, String text, String reply_uuid) throws Exception {
        User from = users.findByEmail(email_from);
        if (from == null) throw new Exception("error");
        GroupMessage to_group = new GroupMessage(Stream.concat(groups.parseByEmail(emails_to).stream(),
                Stream.of(from)).collect(Collectors.toList()));
        for (GroupMessage i : groups.findAll())
            if (to_group.equals(i)) {
                to_group.setId(i.getId());
            }
        groups.save(to_group);
        ReplyMessage message;
        if (reply_uuid.equals("-")) message = new ReplyMessage(from, to_group, text, LocalDateTime.now());
        else message = new ReplyMessage(messages.findOne(UUID.fromString(reply_uuid)), from, to_group, text, LocalDateTime.now());
        messages.save(message);
    }

    public List<ReplyMessage> messagesBetween(String email1, String email2) throws Exception {
        return StreamSupport.stream(this.messages.findAll().spliterator(), false)
                .filter(message -> {
                    try {
                        return message.getGroup().getMembers().contains(this.users.findByEmail(email1)) &&
                                message.getGroup().getMembers().contains(this.users.findByEmail(email2));
                    } catch (Exception ignored) {} return false;})
                .filter(message -> {
                    try {
                        return message.getFrom().equals(this.users.findByEmail(email1)) ||
                                message.getFrom().equals(this.users.findByEmail(email2));
                    } catch (Exception ignored) {} return false;})
                .collect(Collectors.toList());
    }
}
