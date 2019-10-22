package data.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Users {
    @Id
    private String username;
    private String online;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", online='" + online + '\'' +
                '}';
    }
}
