package socialnetwork.domain.containers;

import socialnetwork.domain.User;
import socialnetwork.domain.exceptions.FileException;
import socialnetwork.domain.exceptions.RepositoryException;
import socialnetwork.domain.primary.Entity;
import socialnetwork.repository.database.UserRepositoryDB;

import java.util.*;
import java.util.stream.Collectors;

public class GroupMessage extends Entity<UUID> {
    private List<User> members;

    public GroupMessage() {
        setId(UUID.randomUUID());
    }

    public GroupMessage(List<User> members) {
        setId(UUID.randomUUID());
        this.members = List.copyOf(members);
    }

    public List<User> everyoneBut(User user) {
        List<User> result = new ArrayList<>(List.copyOf(members));
        result.remove(user);
        return result;
    }

    public List<User> getMembers() {
        return members;
    }

    @Override
    public String toString() {
        return members.stream().map(user -> user.getId().toString()).collect(Collectors.joining(","));
    }

    @Override
    public String getFromColumn(int column) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupMessage that = (GroupMessage) o;
        return that.members.containsAll(this.members) && this.members.containsAll(that.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), members);
    }
}
