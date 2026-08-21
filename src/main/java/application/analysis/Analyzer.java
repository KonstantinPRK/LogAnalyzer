package application.analysis;

import application.analysis.Command.Request;
import application.session.Session;


public class Analyzer implements Runnable {
        /*
    получение соединения
    открытие сессии

    в цикле анализатор
    ожидание запроса
    обработка запроса
    выдача результата

    закрытие сессии
     */
    Session session;
    Parser requestParser;
    Validator requestValidator;

    public Analyzer(Session session){
        this.session = session;
    }


    @Override
    public void run() {
        session.openSession();

        while(session.isOpen()){
            requestCommand();
            executeCommand();
            showResult();
        }
    }




    private void requestCommand() {
        String userLine = session.openUI().requestCommand();
        Request rawRequest = requestParser.parse(userLine);
        requestValidator.validate()
        Command command = commandParser(rawRequest);
    }

    private void executeCommand() {

    }


    private void showResult() {

    }
}
