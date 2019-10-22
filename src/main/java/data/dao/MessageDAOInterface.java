package data.dao;

import java.io.Serializable;
import java.util.List;

public interface MessageDAOInterface<T, Id extends Serializable> {
    public T findById(Id id);
    public List<T> findAllBySendAndRec(String sender, String receiver);
    public void persist(T entity);
}
