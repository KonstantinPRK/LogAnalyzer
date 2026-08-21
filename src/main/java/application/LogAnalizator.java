package application;

import application.analysis.Analyzer;
import application.connection.Connection;
import application.connection.ConnectionListener;
import application.session.Session;
import application.session.SessionManager;
import jakarta.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;


public class LogAnalizator {
    ConnectionListener connectionListener;
    SessionManager sessionManager;
    ExecutorService executorService;


    public LogAnalizator(ConnectionListener connectionListener, SessionManager sessionManager, ExecutorService executorService){
        this.connectionListener = connectionListener;
        this.sessionManager = sessionManager;
        this.executorService = executorService;
    }


    @PostConstruct
    public void work() {
        while (true) {
            Connection connection = connectionListener.accept();
            if (connection != null) {
                Session session = sessionManager.createSession(connection);
                Analyzer analyzer = new Analyzer(session);
                executorService.execute(analyzer);
            }
        }
    }
}
