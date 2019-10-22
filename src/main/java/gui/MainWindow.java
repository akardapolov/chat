package gui;

import gui.module.Login;
import gui.module.MessageList;
import gui.module.Send;
import gui.module.UserList;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;

@Slf4j
public class MainWindow {
    private final BasicFrame jFrame;
    private final Login loginToolBar;
    private final UserList userList;

    private JSplitPane splitPaneMessageAndSend;
    private final MessageList messageList;
    private final Send send;

    @Inject
    public MainWindow(BasicFrame jFrame,
                      Login loginToolBar,
                      UserList userList,
                      MessageList messageList,
                      Send send) {
        this.jFrame = jFrame;
        this.loginToolBar = loginToolBar;
        this.userList = userList;
        this.messageList = messageList;
        this.send = send;

        initializeMessageSend();
        composeAll();

        setVisibleAndSetSize();
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
