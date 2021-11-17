package socialnetwork.domain;

import socialnetwork.domain.containers.UserList;
import socialnetwork.domain.primary.Entity;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Object user.
 */
public class User extends Entity<UUID> {
    protected String email;
    protected String firstName;
    protected String lastName;
    protected final UserList friends;

    public User(String email, String firstName, String lastName) {
        if (this.getId() == null) this.setId(UUID.randomUUID());
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.friends = new UserList();
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void addFriend(User user) {
        if (this.friends.contains(user))
            return;
        this.friends.add(user);
    }

    public void deleteFriend(User user) {
        this.friends.remove(user);
    }

    @Override
    public String getFromColumn(int column) {
        return switch (column) {
            case 1 -> this.getId().toString();
            case 2 -> this.getEmail();
            case 3 -> this.getFirstName();
            case 4 -> this.getLastName();
            default -> null;
        };
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(this.email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, friends);
    }
}
