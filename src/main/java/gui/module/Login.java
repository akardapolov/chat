package gui.module;

import data.model.Users;
import data.service.UserService;
import gui.BasicFrame;
import state.SessionState;
import util.Labels;
import util.ProgressBarUtil;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;

@Singleton
public class Login extends JToolBar {
    private BasicFrame jFrame;
    private SessionState sessionState;
    private UserList userList;
    private UserService userService;

    private JLabel loginAsUser;
    private JTextField usernameField;
    private JButton connectButton;

    @Inject
    public Login(BasicFrame jFrame,
                 SessionState sessionState,
                 UserList userList,
                 UserService userService) {
        this.jFrame = jFrame;
        this.sessionState = sessionState;
        this.userList = userList;
        this.userService = userService;

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
    }

    private void connectToSystem(){
        ProgressBarUtil.runProgressDialog(()
                -> {
            try {
                Thread.sleep(100);

                Users users = new Users();
                users.setUsername(usernameField.getText());
                users.setOnline("Yes");

                Users userNameFromDB = this.userService.findById(users.getUsername());

                if (userNameFromDB == null) {
                    this.userService.persist(users);
                } else {
                    if (!userNameFromDB.getUsername().equalsIgnoreCase(users.getUsername())){ // save username if not exist
                        this.userService.persist(users);
                    }
                }

                sessionState.setCurrentUsername(usernameField.getText());

                userList.setReadyToChat();
                usernameField.setEnabled(false);
                connectButton.setEnabled(false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(jFrame, "Error while connecting to server: " + e.getMessage());
            }
        }, jFrame, Labels.getLabel("login.connect.message"));
    }

}
