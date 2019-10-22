package data.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Messages {
    @Id
    private Long created_time;
    private String message;
    private String from_user;
    private String to_user;

    public Long getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Long created_time) {
        this.created_time = created_time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom_user() {
        return from_user;
    }

    public void setFrom_user(String from_user) {
        this.from_user = from_user;
    }

    public String getTo_user() {
        return to_user;
    }

    public void setTo_user(String to_user) {
        this.to_user = to_user;
    }

    @Override
    public String toString() {
        return "Messages{" +
                "created_time=" + created_time +
                ", message='" + message + '\'' +
                ", from_user='" + from_user + '\'' +
                ", to_user='" + to_user + '\'' +
                '}';
    }
}
