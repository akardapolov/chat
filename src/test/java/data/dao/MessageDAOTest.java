package data.dao;

import data.model.Messages;
import data.model.Users;
import org.hibernate.Session;
import org.junit.*;

import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MessageDAOTest {
    private MessageDAO messageDAO;
    private Session session;

    private Users users0;
    private Users users1;
    private Users users2;
    private Users users3;

    private Messages messages0;
    private Messages messages1;
    private Messages messages2;
    private Messages messages3;

    private List<Messages> messageListMain = new ArrayList<>();

    @Rule
    public final SessionFactoryRule sf = new SessionFactoryRule();

    @BeforeClass
    public static void setUpClass() throws Exception {}

    @AfterClass
    public static void tearDownClass() {}

    @Before
    public void setUp() throws SQLException {
        messageDAO = new MessageDAO(sf);
        session = sf.getSession();

        users0 = createUsers("Test0", "Yes");
        users1 = createUsers("Test1", "Yes");
        users2 = createUsers("Test2", "Yes");
        users3 = createUsers("Test3", "Yes");

        messages0 = createMessages(Instant.now().toEpochMilli(), "Hello!", "Test0", "Test1");
        messages1 = createMessages(Instant.now().toEpochMilli()+1, "Hi!", "Test1", "Test0");
        messages2 = createMessages(Instant.now().toEpochMilli()+2, "Nice!", "Test2", "Test3");
        messages3 = createMessages(Instant.now().toEpochMilli()+3, "Ok", "Test3", "Test0");

        session.save(users0);
        session.save(users1);
        session.save(users2);
        session.save(users3);

        messageListMain.add(messages0);
        messageListMain.add(messages1);
        messageListMain.add(messages2);

        messageDAO.persist(messages0);
        messageDAO.persist(messages1);
        messageDAO.persist(messages2);

        sf.commit();
    }

    @Test
    public void findById() {
        assertNotNull(messages0);
        assertNotNull(messages1);
        assertNotNull(messages2);

        assertEquals(messages0.getMessage(), messageDAO.findById(messages0.getCreated_time()).getMessage());
        assertEquals(messages1.getMessage(), messageDAO.findById(messages1.getCreated_time()).getMessage());
        assertEquals(messages2.getMessage(), messageDAO.findById(messages2.getCreated_time()).getMessage());
    }

    @Test
    public void findAll() {
        int i = 0;

        String sender = "Test0";
        String receiver = "Test1";

        // 2 message entity expected
        for (Messages original : messageDAO.findAllBySendAndRec(sender, receiver)) {
            if (messageListMain.contains(original)) {
                i++;
            }
        }

        assertEquals(2, i);
    }

    @Test
    public void persist() {
        messageDAO.persist(messages3);

        assertEquals(messages3.getMessage(), messageDAO.findById(messages3.getCreated_time()).getMessage());
    }

    private Messages createMessages(Long created_time, String message, String from_user, String to_user) {
        Messages messages = new Messages();
        messages.setCreated_time(created_time);
        messages.setMessage(message);
        messages.setFrom_user(from_user);
        messages.setTo_user(to_user);

        return messages;
    }

    private Users createUsers(String username, String isOnline){
        Users users = new Users();
        users.setUsername(username);
        users.setOnline(isOnline);
        return users;
    }
}