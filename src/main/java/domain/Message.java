package domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private Long id;
    private final String from;
    private final String to;
    private final String message;
    private final LocalDateTime date;

    public Message(String from, String to, String message, LocalDateTime date) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message1 = (Message) o;
        return Objects.equals(getId(), message1.getId()) && Objects.equals(getFrom(), message1.getFrom())
                && Objects.equals(getTo(), message1.getTo()) && Objects.equals(getMessage(), message1.getMessage())
                && Objects.equals(getDate(), message1.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFrom(), getTo(), getMessage(), getDate());
    }
}
