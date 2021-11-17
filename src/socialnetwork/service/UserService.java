package socialnetwork.service;

import socialnetwork.domain.User;
import socialnetwork.domain.exceptions.FileException;

public class UserService extends AbstractService {
    private User user;
    public UserService(AbstractService service, User user) {
        super(service);
        this.user = user;
    }
}
