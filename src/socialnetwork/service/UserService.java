package socialnetwork.service;

import socialnetwork.domain.FriendshipWithStatus;
import socialnetwork.domain.User;
import socialnetwork.domain.constants.Constants;
import socialnetwork.domain.containers.FriendshipList;
import socialnetwork.domain.exceptions.FileException;
import socialnetwork.domain.exceptions.RepositoryException;


import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserService extends AbstractService {
    private final User user;

    public UserService(AbstractService service, User user) {
        super(service);
        this.user = user;
    }

    public void sendRequest(String email) throws FileException, RepositoryException {
        User req_user = users.findByEmail(email);
        if (req_user == null) throw new RepositoryException("no user found");
        FriendshipWithStatus friendship = friendships.save(new FriendshipWithStatus(this.user, req_user));
        if (friendship == null) throw new RepositoryException("the friendship already exists");
    }

    public FriendshipList acceptedFriendships() throws FileException {
        return StreamSupport.stream(this.friendships.findByFriend(this.user.getId()).spliterator(), false)
                .filter(friendship -> friendship.status() == Constants.Status.ACCEPTED)
                .collect(Collectors.toCollection(FriendshipList::new));
    }
}
