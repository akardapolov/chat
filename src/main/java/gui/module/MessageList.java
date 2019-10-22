package gui.module;

import data.service.MessageService;
import gui.BasicFrame;
import state.SessionState;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import java.awt.*;

@Singleton
public class MessageList extends JPanel {
    private BasicFrame jFrame;
    private SessionState sessionState;
    private MessageService messageService;

    private JTextArea output;
    private JScrollPane jScrollPane;

    @Inject
    public MessageList(BasicFrame jFrame,
                       SessionState sessionState,
                       MessageService messageService) {
        this.jFrame = jFrame;
        this.sessionState = sessionState;
        this.messageService = messageService;

        this.setLayout(new BorderLayout());
        this.initialize();
        this.add(jScrollPane);
    }

    private void initialize(){
        output = new JTextArea(7, 25);
        output.setFont(new Font("Serif", Font.ITALIC, 16));
        output.setLineWrap(true);
        output.setAutoscrolls(true);
        output.setEditable(false);
        jScrollPane = new JScrollPane(output);

        jScrollPane.setEnabled(false);
    }

    public void loadMessageList(String receiver){
        // Load all messages from the server side
        output.setText("");
        messageService
                .findAllBySendAndRec(sessionState.getCurrentUsername(), receiver)
                .stream()
                .forEach(e -> {
                    output.append(e.getFrom_user() + " >>: " + e.getMessage() + "\n");
                    sessionState.getLastMessageByUserOnTheClientSide()
                            .put(receiver, e.getCreated_time());
                });
    }

    public void clearMessageList(){
        output.setText("");
    }
}
