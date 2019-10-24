package data.dao;

import data.DbSessionInterface;
import data.model.Messages;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class MessageDAO implements MessageDAOInterface<Messages, Long> {
    private DbSessionInterface dbSessionInterface;

    @Inject
    public MessageDAO(DbSessionInterface dbSession) {
        this.dbSessionInterface = dbSession;
    }

    @Override
    public Messages findById(Long created) {
        Messages message = dbSessionInterface.getCurrentSession().get(Messages.class, created);
        return message;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Messages> findAllBySendAndRec(String sender, String receiver) {
        String hql = "from Messages s where s.from_user in (:from_user0, :to_user0) " +
                "and s.to_user in (:from_user0, :to_user0) order by s.created_time ASC";

        List<Messages> messages  = (List<Messages>) dbSessionInterface.getCurrentSession().createQuery(hql)
                    .setParameter("from_user0", sender)
                    .setParameter("to_user0", receiver)
                    .list();

        return messages;
    }

    @Override
    public List<Messages> findAllMoreCreatedTimeSendRec(Long created_time, String sender, String receiver) {
        String hql = "from Messages s where s.created_time > :created_time0 and " +
                "s.from_user in (:from_user0, :to_user0) " +
                "and s.to_user in (:from_user0, :to_user0) order by s.created_time ASC";

        List<Messages> messages =
                (List<Messages>) dbSessionInterface.getCurrentSession().createQuery(hql)
                        .setParameter("created_time0", created_time)
                        .setParameter("from_user0", sender)
                        .setParameter("to_user0", receiver)
                        .list();

        return messages;
    }

    @Override
    public void persist(Messages message) {
        dbSessionInterface.getCurrentSession().save(message);
    }
}
