package socialnetwork.domain;

import socialnetwork.domain.primary.Pair;

public class Friendship extends Pair<User, User> {
    public Friendship(User user1, User user2) {
        super(user1, user2);
    }

    /**
     * Adds the users in the friends lists.
     */
    public void addFriends() {
        getLeft().addFriend(getRight());
        getRight().addFriend(getLeft());
    }

    /**
     * Deletes the users in the friends lists.
     */
    public void deleteFriends() {
        getLeft().deleteFriend(getRight());
        getRight().deleteFriend(getLeft());
    }

    /**
     * Verifies if an user is in a friendship;
     * @param user the user;
     * @return the value of truth.
     */
    public boolean isFriend(User user) {
        return ((getLeft().equals(user)) || (getRight().equals(user)));
    }

    @Override
    public String toString() {
        return "Friendship: " + getLeft().getEmail() + " , " + getRight().getEmail();
    }

    @Override
    public String getFromColumn(int column) {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass())
            return false;
        Friendship aux = new Friendship(((Friendship) obj).getRight(), ((Friendship) obj).getLeft());
        return super.equals(obj) || super.equals(aux);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
