package data.service;

import data.DbSessionInterface;
import data.dao.MessageDAO;
import data.dao.MessageDAOInterface;
import data.model.Messages;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class MessageService {
    private MessageDAOInterface messageDAOInterface;
    private DbSessionInterface dbSession;

    @Inject
    public MessageService(MessageDAO messageDAOInterface, DbSessionInterface dbSession) {
        this.messageDAOInterface = messageDAOInterface;
        this.dbSession = dbSession;
    }

    public void persist(Messages message) {
        dbSession.openCurrentSessionwithTransaction();
        messageDAOInterface.persist(message);
        dbSession.closeCurrentSessionwithTransaction();
    }

    public Messages findById(Long created_time) {
        dbSession.openCurrentSession();
        Messages messages = (Messages) messageDAOInterface.findById(created_time);
        dbSession.closeCurrentSession();
        return messages;
    }

    public List<Messages> findAllBySendAndRec(String sender, String receiver) {
        dbSession.openCurrentSession();
        List<Messages> messages = (List<Messages>) messageDAOInterface.findAllBySendAndRec(sender, receiver);
        dbSession.closeCurrentSession();
        return messages;
    }

    public List<Messages> findAllMoreCreatedTimeSendRec(Long created_time, String sender, String receiver) {
        dbSession.openCurrentSession();
        List<Messages> messages = (List<Messages>)
                messageDAOInterface.findAllMoreCreatedTimeSendRec(created_time, sender, receiver);
        dbSession.closeCurrentSession();
        return messages;
    }

}
