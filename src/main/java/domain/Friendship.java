package domain;
import java.time.LocalDate;
import java.util.Objects;

public class Friendship {
    private Long id;
    private final String username1;
    private final String username2;
    private final LocalDate friendshipDate;

    public Friendship(String id1, String id2, LocalDate friendshipDate) {
        this.username1 = id1;
        this.username2 = id2;
        this.friendshipDate = friendshipDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername1() {
        return username1;
    }

    public String getUsername2() {
        return username2;
    }

    public LocalDate getFriendshipDate() {
        return friendshipDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Friendship)) return false;
        Friendship that = (Friendship) o;
        return getUsername1().equals(that.getUsername1())  && getUsername2().equals(that.getUsername2())
                && Objects.equals(getFriendshipDate(),
                that.getFriendshipDate()) && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername1(), getUsername2(), getFriendshipDate(), getId());
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "username1=" + username1 +
                ", username2=" + username2 +
                ", friendshipDate=" + friendshipDate +
                '}';
    }
}