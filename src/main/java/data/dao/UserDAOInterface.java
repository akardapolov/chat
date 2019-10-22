package data.dao;

import java.io.Serializable;
import java.util.List;

public interface UserDAOInterface<T, Id extends Serializable> {
    public T findById(Id id);
    public List<T> findAll();
    public void persist(T entity);
}
