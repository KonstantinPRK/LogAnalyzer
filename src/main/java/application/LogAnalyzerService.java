package application;

import application.core.analysis.LogAnalysisTask;
import application.factory.LogAnalysisTaskFactory;
import application.parser.commandParser.Command;
import application.reporter.Report;
import application.core.session.UserSession;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public final class LogAnalyzerService {
    private final UserSession session;
    private final LogAnalysisTaskFactory analysisTaskFactory;


    public LogAnalyzerService(UserSession session, LogAnalysisTaskFactory analysisTaskFactory) {
        this.session = Objects.requireNonNull(session, "session");
        this.analysisTaskFactory = Objects.requireNonNull(analysisTaskFactory, "analysisTaskFactory");
    }


    @PostConstruct
    public void start() {
        runSession(session);
    }


    private void runSession(UserSession session) {
        while (session.isOpen()) {
            analyze(session);
        }
    }


    private void analyze(UserSession session) {
        try {
            Command command = session.requestCommand();
            LogAnalysisTask analysisTask = analysisTaskFactory.create(command);
            Report report = analysisTask.call();

            session.displayReport(report);
        } catch (Exception exception) {
            session.displayError(exception);
        }
    }
}
