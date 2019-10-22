package data.dao;

import data.model.Users;
import org.hibernate.Session;
import org.junit.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserDAOTest {
    private UserDAO userDAO;
    private Session session;

    private Users users0;
    private Users users1;
    private Users users2;
    private Users users3;

    private List<Users> userListMain = new ArrayList<>();

    @Rule
    public final SessionFactoryRule sf = new SessionFactoryRule();

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws SQLException {
        userDAO = new UserDAO(sf);
        session = sf.getSession();

        users0 = createUsers("Test0", "Yes");
        users1 = createUsers("Test1", "Yes");
        users2 = createUsers("Test2", "Yes");
        users3 = createUsers("Test3", "Yes");

        userListMain.add(users0);
        userListMain.add(users1);
        userListMain.add(users2);

        session.save(users0);
        session.save(users1);
        session.save(users2);

        sf.commit();
    }

    @Test
    public void findById() {
        assertNotNull(users0);
        assertNotNull(users1);
        assertNotNull(users2);

        assertEquals(users0.getUsername(), userDAO.findById(users0.getUsername()).getUsername());
        assertEquals(users1.getUsername(), userDAO.findById(users1.getUsername()).getUsername());
        assertEquals(users2.getUsername(), userDAO.findById(users2.getUsername()).getUsername());
    }

    @Test
    public void findAll() {
        int i = 0;

        // how much input and output
        for (Users original : userDAO.findAll()) {
            if (!userListMain.contains(original)) {
                i++;
            }
        }

        assertEquals(0, i);
    }

    @Test
    public void persist() {
        userDAO.persist(users3);

        assertEquals(users3.getUsername(), userDAO.findById(users3.getUsername()).getUsername());
    }

    private Users createUsers(String username, String isOnline){
        Users users = new Users();
        users.setUsername(username);
        users.setOnline(isOnline);
        return users;
    }
}