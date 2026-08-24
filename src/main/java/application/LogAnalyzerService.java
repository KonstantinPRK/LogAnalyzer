package application;


import application.core.analysis.Analizator;
import application.core.analysis.AnalizatorBuilder;
import application.core.session.UserSession;

import application.core.setting.SystemParameters;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class LogAnalyzerService {
    UserSession session;
    AnalizatorBuilder analizatorBuilder;


    public LogAnalyzerService(AnalizatorBuilder analizatorBuilder){
        this.analizatorBuilder = analizatorBuilder;
    }


    @PostConstruct
    public void start(){
       analyzeInteraction();
    }

    private void analyzeInteraction() {
        while(session.isOpen()){
            requestCommand(session);
            analyze(session);
            displayReport(session);
        }
    }




    private void requestCommand(UserSession session){
        session.requestCommand();
    }

    private void analyze(UserSession session) {
        Analizator analizator = analizatorBuilder.createAnalizator(session.getCommand());
        analizator.run();

    }

    private void displayReport(UserSession session) {
    }



}
