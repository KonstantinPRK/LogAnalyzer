package application.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import application.userInterface.UserInterface;
import application.userInterface.console.Console;

import java.io.PrintStream;
import java.util.Scanner;

@Configuration
public class AppConfig {
    @Bean
    public UserInterface userInterface(){
        return new Console();
    }

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

    @Bean
    public PrintStream printStream() {
        return new PrintStream(System.out);
    }
}