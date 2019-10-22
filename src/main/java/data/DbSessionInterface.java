package data;

import org.hibernate.Session;
import org.hibernate.Transaction;

public interface DbSessionInterface {
    Session openCurrentSession();

    Session openCurrentSessionwithTransaction();

    void closeCurrentSession();

    void closeCurrentSessionwithTransaction();

    Session getCurrentSession();

    void setCurrentSession(Session currentSession);

    Transaction getCurrentTransaction();

    void setCurrentTransaction(Transaction currentTransaction);
}
