package domain;

import java.util.Objects;

public class Request {
    private Long id;
    private final String from, to;
    private Status status;

    public Request(String username1, String username2) {
        this.from = username1;
        this.to = username2;
        this.status = Status.pending;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return to;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FriendshipRequest{" +
                "username1=" + from +
                ", username2=" + to +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request)) return false;
        Request that = (Request) o;
        return Objects.equals(getFrom(), that.getFrom()) && Objects.equals(getTo(), that.getTo())
                && getStatus() == that.getStatus() && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),getFrom(), getTo(), getStatus());
    }
}
