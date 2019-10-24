package gui;

import data.model.Users;
import data.service.UserService;
import gui.module.Login;
import gui.module.MessageList;
import gui.module.Send;
import gui.module.UserList;
import lombok.extern.slf4j.Slf4j;
import state.SessionState;
import util.Labels;
import util.StackTraceUtil;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@Slf4j
public class MainWindow {
    private final BasicFrame jFrame;
    private final Login loginToolBar;
    private final UserList userList;
    private final MessageList messageList;
    private final Send send;

    private UserService userService;
    private SessionState sessionState;

    private JSplitPane splitPaneMessageAndSend;

    @Inject
    public MainWindow(BasicFrame jFrame,
                      Login loginToolBar,
                      UserList userList,
                      MessageList messageList,
                      Send send,
                      SessionState sessionState,
                      UserService userService) {
        this.jFrame = jFrame;
        this.loginToolBar = loginToolBar;
        this.userList = userList;
        this.messageList = messageList;
        this.send = send;

        this.userService = userService;
        this.sessionState = sessionState;

        initializeMessageSend();
        composeAll();

        setVisibleAndSetSize();

        actionOnShutdownApp();
    }

    private void actionOnShutdownApp() {
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                if (!sessionState.getCurrentUsername().equalsIgnoreCase(Labels.getLabel("username.guest"))){
                    Users users = new Users();
                    users.setUsername(sessionState.getCurrentUsername());
                    users.setOnline("No");

                    try {
                        userService.persist(users);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(jFrame, StackTraceUtil.getCustomStackTrace(ex));
                    }
                }

                System.exit(0);
            }
        });
    }

    private void initializeMessageSend(){
        splitPaneMessageAndSend = new JSplitPane();
        splitPaneMessageAndSend.setDividerLocation(250);
        splitPaneMessageAndSend.setOneTouchExpandable(true);
        splitPaneMessageAndSend.setOrientation(JSplitPane.VERTICAL_SPLIT);
    }

    private void composeAll(){
        this.jFrame.addLoginToolbar(this.loginToolBar);
        this.jFrame.addChatComponentToSplit(this.userList, JSplitPane.LEFT);

        this.splitPaneMessageAndSend.add(this.messageList, JSplitPane.TOP);
        this.splitPaneMessageAndSend.add(this.send, JSplitPane.BOTTOM);

        this.jFrame.addChatComponentToSplit(this.splitPaneMessageAndSend, JSplitPane.RIGHT);

        this.userList.requestFocus();
    }

    // Exception in thread "AWT-EventQueue-0" java.lang.ClassCastException
    private void setVisibleAndSetSize(){
        this.jFrame.setVisible(true);
        this.jFrame.setSize(new Dimension(600, 400));
    }

}
