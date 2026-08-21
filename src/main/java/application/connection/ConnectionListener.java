package application.connection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionListener {
    private final BlockingQueue<Connection> connectionQueue; // представим что оно как-то туда попадает

    public ConnectionListener() {
        this.connectionQueue = new LinkedBlockingQueue<>();
        fakeConnection();
    }

    private void fakeConnection(){
        try {
            connectionQueue.put(new Connection());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection accept()  {
        try {
            return connectionQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
