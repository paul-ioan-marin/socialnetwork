package socialnetwork.domain.validator;

import socialnetwork.domain.Friendship;
import socialnetwork.domain.User;
import socialnetwork.domain.exceptions.ValidationException;

public class FriendshipValidator implements Validator<Friendship> {
    @Override
    public void validate(Friendship entity) throws ValidationException {
        if (entity.getLeft().equals(entity.getRight()))
            throw new ValidationException("Users must be different");
    }
}
