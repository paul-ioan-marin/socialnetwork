package socialnetwork.service;

import socialnetwork.domain.User;
import socialnetwork.domain.constants.Constants;
import socialnetwork.domain.containers.FriendshipList;
import socialnetwork.domain.exceptions.FileException;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserService extends AbstractService {
    private final User user;

    public UserService(AbstractService service, User user) {
        super(service);
        this.user = user;
    }

    public FriendshipList acceptedFriendships() throws FileException {
        return StreamSupport.stream(this.getFriendships().spliterator(), false)
                .filter(friendship -> friendship.status() == Constants.Status.ACCEPTED)
                .filter(friendship -> friendship.isFriend(this.user))
                .collect(Collectors.toCollection(FriendshipList::new));
    }
}
