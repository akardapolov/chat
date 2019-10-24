package gui.module;

import data.service.UserService;
import gui.BasicFrame;
import org.jdesktop.swingx.JXTable;
import state.SessionState;
import util.Labels;
import util.ProgressBarUtil;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@Singleton
public class UserList extends JPanel{
    private BasicFrame jFrame;
    private SessionState sessionState;
    private UserService userService;
    private MessageList messageList;
    private Send send;

    private JXTable tableUsersList;
    private JScrollPane jScrollPane;

    private DefaultTableModel model;

    private ListSelectionModel listSelectionModelForUsers;

    @Inject
    public UserList(BasicFrame jFrame,
                    SessionState sessionState,
                    UserService userService,
                    MessageList messageList,
                    Send send) {
        this.jFrame = jFrame;
        this.sessionState = sessionState;
        this.userService = userService;
        this.messageList = messageList;
        this.send = send;

        this.setLayout(new BorderLayout());
        this.initialize();
        this.add(jScrollPane);
    }

    public void setReadyToChat(){
        // load user list and start chatting
        userService.findAll().stream()
                .filter(k -> !k.getUsername().equalsIgnoreCase(sessionState.getCurrentUsername())) // Exclude the current user
                .filter(n -> n.getOnline().equalsIgnoreCase("Yes")) // Online users
                .forEach(e -> model.addRow(new Object[] {e.getUsername()}));

        jScrollPane.setEnabled(true);
    }

    private void initialize(){
        String[] columnNamesForSql = {Labels.getLabel("users.name")};
        model = new DefaultTableModel(columnNamesForSql, 0);

        tableUsersList = new JXTable(model);
        tableUsersList.setEditable(false);
        tableUsersList.setColumnControlVisible(true);
        tableUsersList.setHorizontalScrollEnabled(true);
        tableUsersList.setVisibleRowCount(25);

        listSelectionModelForUsers = tableUsersList.getSelectionModel();
        listSelectionModelForUsers.addListSelectionListener(new TableSelectionHandler());

        jScrollPane =
                new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.getViewport().setScrollMode(JViewport.BLIT_SCROLL_MODE);
        jScrollPane.setViewportView(tableUsersList);

        jScrollPane.setEnabled(false);
    }

    private class TableSelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();

            if (lsm.isSelectionEmpty()) {
                messageList.clearMessageList();
            } else { // Fill message list with data
                ProgressBarUtil.runProgressDialog(()
                        -> {
                    String receiver = tableUsersList.getModel()
                            .getValueAt(tableUsersList.getSelectedRow(), 0).toString();

                    messageList.loadAllMessages(receiver);
                    sessionState.setSendToUsername(receiver);
                    send.setReadyToChat();

                }, jFrame, Labels.getLabel("user.list.error"));
            }
        }
    }

}
