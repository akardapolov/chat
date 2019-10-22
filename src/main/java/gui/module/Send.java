package gui.module;

import data.model.Messages;
import data.service.MessageService;
import gui.BasicFrame;
import state.SessionState;
import util.Labels;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.Instant;

@Singleton
public class Send extends JPanel {
    private BasicFrame jFrame;
    private MessageList messageList;
    private SessionState sessionState;
    private MessageService messageService;

    private JTextArea output;
    private JScrollPane jScrollPane;

    @Inject
    public Send(BasicFrame jFrame,
                MessageList messageList,
                SessionState sessionState,
                MessageService messageService) {
        this.jFrame = jFrame;
        this.messageList = messageList;
        this.sessionState = sessionState;
        this.messageService = messageService;

        this.setLayout(new BorderLayout());
        this.initialize();
        this.add(jScrollPane);
    }

    public void setReadyToChat(){
        output.setEditable(true);
    }

    private void initialize(){
        output = new JTextArea(2, 25);
        output.setFont(new Font("Serif", Font.ITALIC, 16));
        output.setLineWrap(true);
        output.setAutoscrolls(true);
        output.setEditable(false);
        output.getDocument().putProperty("filterNewlines", Boolean.TRUE);

        jScrollPane = new JScrollPane(output);

        // Handle press the enter key and send message to recipient
        sendMessageKeyListener();
    }

    private void sendMessageKeyListener(){
        output.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    //send message to server
                    if (output.getText().isEmpty()){
                        JOptionPane.showMessageDialog(jFrame, Labels.getLabel("send.message"));
                    } else {
                        Messages newMessage = new Messages();

                        newMessage.setCreated_time(Instant.now().toEpochMilli());
                        newMessage.setMessage(output.getText());
                        newMessage.setFrom_user(sessionState.getCurrentUsername());
                        newMessage.setTo_user(sessionState.getSendToUsername());

                        messageService.persist(newMessage);
                    }

                    // clear text
                    output.setText("");

                    // reload Message list
                    messageList.loadMessageList(sessionState.getSendToUsername());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) { }
        });
    }
}
