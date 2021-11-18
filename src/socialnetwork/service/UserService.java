package socialnetwork.service;

import socialnetwork.domain.FriendshipWithStatus;
import socialnetwork.domain.User;
import socialnetwork.domain.constants.Constants;
import socialnetwork.domain.containers.FriendshipList;
import socialnetwork.domain.exceptions.RepositoryException;
import socialnetwork.domain.util.*;


import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserService extends AbstractService {
    private final User user;

    public UserService(AbstractService service, User user) {
        super(service);
        this.user = user;
    }

    public void sendRequest(String email) throws Exception {
        User req_user = users.findByEmail(email);
        if (req_user == null) throw new RepositoryException("no user found");
        FriendshipWithStatus friendship = friendships.request(new FriendshipWithStatus(this.user, req_user));
        if (friendship == null) throw new RepositoryException("the friend request or friendship already exists");
    }

    private void doOperation(SpecificList list, SpecificOperation operation, String email,
                             Exception exception) throws Exception {
        User req_user = users.findByEmail(email);
        if (req_user == null) throw new RepositoryException("no user found");
        FriendshipWithStatus friendship = new FriendshipWithStatus(req_user, this.user);
        for (FriendshipWithStatus request : list.process())
            if (request.equals(friendship)) {
                operation.process(request);
                return;
            }
        throw exception;
    }

    public void acceptRequest(String email) throws Exception {
        doOperation(this::pendingFriendships,this.friendships::accept,
                email, new RepositoryException("the friend request does not exist"));
    }

    public void declineRequest(String email) throws Exception {
        doOperation(this::pendingFriendships,this.friendships::decline,
                email, new RepositoryException("the friend request does not exist"));
    }

    public void deleteFriend(String email) throws Exception {
        doOperation(this::acceptedFriendships,this.friendships::decline,
                email, new RepositoryException("the friendship does not exist"));
    }

    public FriendshipList acceptedFriendships() throws Exception {
        return StreamSupport.stream(this.friendships.findByFriend(this.user.getId()).spliterator(), false)
                .filter(friendship -> friendship.status() == Constants.Status.ACCEPTED)
                .collect(Collectors.toCollection(FriendshipList::new));
    }

    public FriendshipList pendingFriendships() throws Exception {
        return StreamSupport.stream(this.friendships.findByFriend(this.user.getId()).spliterator(), false)
                .filter(friendship -> friendship.getRight().equals(this.user))
                .filter(friendship -> friendship.status() == Constants.Status.PENDING)
                .collect(Collectors.toCollection(FriendshipList::new));
    }
}
