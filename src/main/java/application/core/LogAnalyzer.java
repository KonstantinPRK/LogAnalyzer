package application.core;

import application.components.reporting.logReport.Report;
import application.core.processor.CommandProcessor;
import application.core.processor.ProcessorFactory;
import application.core.session.Session;
import application.core.session.SessionManager;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Component
public class LogAnalyzer {
    private final SessionManager sessionManager;
    private final ExecutorService executorService;
    private final ProcessorFactory processorFactory;


    public LogAnalyzer(SessionManager sessionManager,
                       ExecutorService executorService,
                       ProcessorFactory processorFactory) {
        this.sessionManager = sessionManager;
        this.executorService = executorService;
        this.processorFactory = processorFactory;
    }

    @PostConstruct
    public void startAnalyzer() {
        try {
            Session session = sessionManager.createSession();
            CommandProcessor processor = processorFactory.newCommand(session);
            Future<Report> future = executorService.submit(processor);
            session.returnReport(future.get());

        } catch (InterruptedException e) {

            throw new RuntimeException(e);

        } catch (ExecutionException e) {

            throw new RuntimeException(e);
        }
    }
}