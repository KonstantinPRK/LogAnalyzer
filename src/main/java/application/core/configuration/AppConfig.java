package application.core.configuration;

import application.core.session.UserSession;
import application.userInterface.UserInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.PrintStream;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Scanner;

@Configuration
public class AppConfig {
    @Bean
    public HttpClient logHttpClient() {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }


    @Bean(destroyMethod = "")
    public Scanner consoleScanner() {
        return new Scanner(System.in);
    }


    @Bean(name = "consoleOutput", destroyMethod = "")
    public PrintStream consoleOutput() {
        return System.out;
    }


    @Bean
    public UserSession consoleSession(UserInterface userInterface) {
        return new UserSession("console", userInterface);
    }
}
