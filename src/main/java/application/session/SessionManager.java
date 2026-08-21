package application.session;

import application.connection.Connection;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class SessionManager {
    private final SessionFactory factory;
    private final ConcurrentHashMap<String, Session> sessions;

    private static AtomicLong currentID = new AtomicLong(0); //костыльная реализация айди


    public SessionManager(SessionFactory factory, ConcurrentHashMap<String, Session> concurrentHashMap){
        this.sessions = concurrentHashMap;
        this.factory = factory;
    }



    public Session createSession(Connection connection){
        String newSessionID = String.valueOf(currentID.incrementAndGet());
        Session newSession = factory.createNewSession(connection, newSessionID);
        sessions.put(newSessionID, newSession);
        return newSession;
    }


    public Session getSession(String sessionID){
        return sessions.get(sessionID);
    }
}
