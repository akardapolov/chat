package gui.module;

import data.model.Messages;
import data.service.MessageService;
import gui.BasicFrame;
import pubsub.AppCallback;
import pubsub.Subscriber;
import state.SessionState;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import java.awt.*;

@Singleton
public class MessageList extends JPanel implements AppCallback {
    private BasicFrame jFrame;
    private SessionState sessionState;
    private MessageService messageService;
    private Subscriber subscriber;

    private JTextArea output;
    private JScrollPane jScrollPane;

    @Inject
    public MessageList(BasicFrame jFrame,
                       SessionState sessionState,
                       MessageService messageService,
                       Subscriber subscriber) {
        this.jFrame = jFrame;
        this.sessionState = sessionState;
        this.messageService = messageService;
        this.subscriber = subscriber;
        this.subscriber.addObjectForCallback(this);

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

    void loadAllMessages(String receiver){
        // Load all messages from the server side
        output.setText("");
        messageService
                .findAllBySendAndRec(sessionState.getCurrentUsername(), receiver)
                .stream()
                .forEach(e -> {
                    appendMessage(receiver, e);
                });

        output.setCaretPosition(output.getDocument().getLength());
    }

    void loadMessagesMoreThanCreatedTime(Long created_time, String receiver){
        // Load messages more than created_time from the server side
        messageService
                .findAllMoreCreatedTimeSendRec(created_time, sessionState.getCurrentUsername(), receiver)
                .stream()
                .forEach(e -> {
                    appendMessage(receiver, e);
                });

        output.setCaretPosition(output.getDocument().getLength());
    }

    private void appendMessage(String receiver, Messages message){
        output.append(message.getFrom_user() + " >>: " + message.getMessage() + "\n");
        sessionState.getLastMessageByUserOnTheClientSide()
                .put(receiver, message.getCreated_time());
    }

    public void clearMessageList(){
        output.setText("");
    }

    @Override
    public void fireAction() {
        loadAllMessages(sessionState.getSendToUsername());

        /*loadMessagesMoreThanCreatedTime(
                sessionState.getLastMessageByUserOnTheClientSide().get(sessionState.getSendToUsername()),
                sessionState.getSendToUsername());*/
    }
}
