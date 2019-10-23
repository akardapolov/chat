package state;

import lombok.Getter;
import lombok.Setter;
import util.Labels;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;

@Singleton
public class SessionState {
    @Getter @Setter
    private String currentUsername = Labels.getLabel("username.guest");
    @Getter @Setter
    private String sendToUsername = ""; // Current companion

    @Getter
    private HashMap<String, Long> lastMessageByUserOnTheClientSide = new HashMap<>();

    @Inject
    public SessionState() { }
}
