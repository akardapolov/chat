package state;

import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;

@Singleton
public class SessionState {
    @Getter @Setter
    private String currentUsername = "Guest";
    @Getter @Setter
    private String sendToUsername = "";

    @Getter
    private HashMap<String, Long> lastMessageByUserOnTheClientSide = new HashMap<>();

    @Inject
    public SessionState() { }
}
