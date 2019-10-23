package pubsub;

import com.impossibl.postgres.api.jdbc.PGConnection;
import com.impossibl.postgres.api.jdbc.PGNotificationListener;
import com.impossibl.postgres.jdbc.PGDataSource;
import data.DbSessionInterface;
import lombok.Getter;
import lombok.Setter;
import util.Labels;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Singleton
public class Subscriber {
    private DbSessionInterface dbSession;
    private String channelName = Labels.getLabel("pubsub.channel");

    private List<AppCallback> list = Collections.synchronizedList(new ArrayList());

    @Getter @Setter
    private boolean needUpdate = false;

    @Inject
    public Subscriber(DbSessionInterface dbSession) {
        this.dbSession = dbSession;
    }

    public void initialize(){
        Thread dt = new Thread(new RunningThread(), "RunningThread");
        dt.setDaemon(true);
        dt.start();
    }

    class RunningThread implements Runnable{

        @Override
        public void run() {
            while(true){
                processing();
            }
        }

        private void processing() {
            PGNotificationListener listener = new PGNotificationListener() {
                @Override
                public void notification(int processId, String channelName, String payload) {
                    // fire action
                    for (AppCallback e : list) e.fireAction();
                }
            };

                //connection
                PGDataSource dataSource = new PGDataSource();
                dataSource.setHost("192.168.166.166");
                dataSource.setPort(5432);
                dataSource.setDatabaseName("postgres");
                dataSource.setUser("postgres");
                dataSource.setPassword("postgres");

                try (PGConnection connection = (PGConnection) dataSource.getConnection()){
                    Statement statement = connection.createStatement();
                    statement.execute("LISTEN " + channelName);
                    statement.close();
                    connection.addNotificationListener(listener);
                    Thread.sleep(Long.MAX_VALUE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    public boolean removeObjectForCallback(AppCallback l) {
        return list.remove(l);
    }
    public void addObjectForCallback(AppCallback l) { list.add(l); }
}
