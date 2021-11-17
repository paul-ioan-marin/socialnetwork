package socialnetwork.service;

import socialnetwork.domain.FriendshipWithStatus;
import socialnetwork.domain.User;
import socialnetwork.domain.exceptions.FileException;
import socialnetwork.domain.exceptions.RepositoryException;


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
}
