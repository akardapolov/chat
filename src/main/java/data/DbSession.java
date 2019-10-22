package data;

import data.model.Messages;
import data.model.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DbSession implements DbSessionInterface {
    private Session currentSession;
    private Transaction currentTransaction;

    @Inject
    public DbSession() {}

    @Override
    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    @Override
    public Session openCurrentSessionwithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    @Override
    public void closeCurrentSession() {
        currentSession.close();
    }

    @Override
    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    @Override
    public Session getCurrentSession() {
        return currentSession;
    }

    @Override
    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    @Override
    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    @Override
    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Users.class);
        configuration.addAnnotatedClass(Messages.class);
        configuration.configure();

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());

        return sessionFactory;
    }
}
