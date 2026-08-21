package application.session;

import application.connection.Connection;
import application.userInterface.UserInterface;
import org.springframework.stereotype.Component;

@Component
public class SessionFactory {
    UserInterface UI;

    public SessionFactory(UserInterface UI){
        this.UI = UI; // упрощенная логика, потому что пока используется только одна реализация
    }

    public Session createNewSession(Connection connection, String newSessionID){
        return new Session(connection, newSessionID, UI);
    }
}
