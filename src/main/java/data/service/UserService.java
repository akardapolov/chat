package data.service;

import data.DbSessionInterface;
import data.dao.UserDAO;
import data.dao.UserDAOInterface;
import data.model.Users;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class UserService {
    private UserDAOInterface userDAO;
    private DbSessionInterface dbSession;

    @Inject
    public UserService(UserDAO userDAO, DbSessionInterface dbSession) {
        this.userDAO = userDAO;
        this.dbSession = dbSession;
    }

    public void persist(Users usersEntity) {
        dbSession.openCurrentSessionwithTransaction();
        userDAO.persist(usersEntity);
        dbSession.closeCurrentSessionwithTransaction();
    }

    public Users findById(String username) {
        dbSession.openCurrentSession();
        Users users = (Users) userDAO.findById(username);
        dbSession.closeCurrentSession();
        return users;
    }

    public List<Users> findAll() {
        dbSession.openCurrentSession();
        List<Users> users = userDAO.findAll();
        dbSession.closeCurrentSession();
        return users;
    }

}
