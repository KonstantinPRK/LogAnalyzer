package application.session;

import application.connection.Connection;
import application.userInterface.UserInterface;


public class Session {
    private final Connection connection; // пока не используется
    private final String sessionID;
    private final UserInterface UI;

    private boolean sessionIsOpen = false;

    public Session(Connection connection, String newSessionID, UserInterface UI){
        this.connection = connection;
        this.sessionID = newSessionID;
        this.UI = UI;
    }

    public String getID(){return sessionID;}

    public UserInterface openUI(){return UI;}


    public void openSession(){
        sessionIsOpen = true;
    }

    public boolean isOpen(){
        return sessionIsOpen;
    }

    public void closeSession(Session session){
        sessionIsOpen = false;
    }

}
