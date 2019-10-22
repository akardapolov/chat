package data.dao;

import data.DbSessionInterface;
import data.model.Users;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class UserDAO implements UserDAOInterface<Users, String> {
    private DbSessionInterface dbSessionInterface;

    @Inject
    public UserDAO(DbSessionInterface dbSession) {
        this.dbSessionInterface = dbSession;
    }

    @Override
    public Users findById(String username) {
        Users user = dbSessionInterface.getCurrentSession().get(Users.class, username);
        return user;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Users> findAll() {
        List<Users> users = (List<Users>) dbSessionInterface.getCurrentSession().createQuery("from Users").list();
        return users;
    }

    @Override
    public void persist(Users user) {
        dbSessionInterface.getCurrentSession().saveOrUpdate(user);
    }

}
