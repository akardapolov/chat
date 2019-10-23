package gui.module;

import data.model.Users;
import data.service.UserService;
import gui.BasicFrame;
import pubsub.Subscriber;
import state.SessionState;
import util.Labels;
import util.ProgressBarUtil;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@Singleton
public class Login extends JToolBar {
    private BasicFrame jFrame;
    private SessionState sessionState;
    private UserList userList;
    private UserService userService;
    private Subscriber subscriber;

    private JLabel loginAsUser;
    private JTextField usernameField;
    private JButton connectButton;

    @Inject
    public Login(BasicFrame jFrame,
                 SessionState sessionState,
                 UserList userList,
                 UserService userService,
                 Subscriber subscriber) {
        this.jFrame = jFrame;
        this.sessionState = sessionState;
        this.userList = userList;
        this.userService = userService;
        this.subscriber = subscriber;

        this.initialize();
    }

    private void initialize(){
        this.loginAsUser = new JLabel(Labels.getLabel("login.enter.username"));
        this.usernameField = new JTextField();
        this.connectButton = new JButton(Labels.getLabel("login.button"));

        this.add(loginAsUser);
        this.add(usernameField);
        this.add(connectButton);

        // Handle connect to system and ready to start chatting
        this.connectButton.addActionListener(e -> connectToSystem());
        // Handle enter in input string and doClick on connectButton
        this.sendMessageKeyListener();
    }

    private void connectToSystem(){
        ProgressBarUtil.runProgressDialog(()
                -> {
            try {

                Users users = new Users();
                users.setUsername(usernameField.getText());
                users.setOnline("Yes");

                this.userService.persist(users);

                sessionState.setCurrentUsername(usernameField.getText());

                userList.setReadyToChat();
                usernameField.setEnabled(false);
                connectButton.setEnabled(false);

                subscriber.initialize();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(jFrame, "Error while connecting to server: " + e.getMessage());
            }
        }, jFrame, Labels.getLabel("login.connect.message"));
    }


    private void sendMessageKeyListener(){
        usernameField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    connectButton.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) { }
        });
    }

}
