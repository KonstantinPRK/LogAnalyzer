package userInterface.Console;

import application.parser.commandParser.CommandParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.PrintStream;
import java.util.Scanner;


@Configuration
public class ConsoleConfiguration {
    @Bean(destroyMethod = "")
    public Scanner consoleScanner() {
        return new Scanner(System.in);
    }

    @Bean(name = "consoleStandardOutput", destroyMethod = "")
    public PrintStream consoleStandardOutput() {
        return System.out;
    }

    @Bean(name = "consoleErrorOutput", destroyMethod = "")
    public PrintStream consoleErrorOutput() {
        return System.err;
    }

    @Bean
    public CommandParser commandParser() {
        return new CommandParser();
    }
}
