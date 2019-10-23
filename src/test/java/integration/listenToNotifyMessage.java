package integration;
import com.impossibl.postgres.api.jdbc.PGConnection;
import com.impossibl.postgres.api.jdbc.PGNotificationListener;
import com.impossibl.postgres.jdbc.PGDataSource;

import java.sql.Statement;

public class listenToNotifyMessage {

    public static void listenToNotifyMessage() {
        PGDataSource dataSource = new PGDataSource();
        dataSource.setHost("192.168.166.166");
        dataSource.setPort(5432);
        dataSource.setDatabaseName("postgres");
        dataSource.setUser("postgres");
        dataSource.setPassword("password");

        PGNotificationListener listener = new PGNotificationListener() {

        @Override
        public void notification(int processId, String channelName, String payload) {
                // fire code
                System.out.println(
                        "Message " + " on chanel:" + channelName + " payload:" + payload);
            }
        };

        try (PGConnection connection = (PGConnection) dataSource.getConnection()){
            Statement statement = connection.createStatement();
            statement.execute("LISTEN test");
            statement.close();
            connection.addNotificationListener(listener);
            Thread.sleep(10000000);
            //while (true){ }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void main(String... args) {
        listenToNotifyMessage();
    }
}
