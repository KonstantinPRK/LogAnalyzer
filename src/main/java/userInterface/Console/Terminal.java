package userInterface.Console;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public final class Terminal {
    private final Input input;
    private final Output output;

    private static final String COMMAND_REQUEST = "Введите команду, например:";
    private static final String COMMAND_EXAMPLE = "analyzer --path logs/*.log --from 2024-08-31 --format markdown";
    private static final String COMMAND_PROMPT = "> ";
    private static final String EMPTY_COMMAND_ERROR = "Ошибка: команда не может быть пустой";
    private static final String ERROR_PREFIX = "Ошибка: ";
    private static final String UNKNOWN_ERROR = "неизвестная ошибка";
    

    public Terminal(Input input, Output output) {
        this.input = Objects.requireNonNull(input, "input");
        this.output = Objects.requireNonNull(output, "output");
    }

    public String requestCommandLine() {
        output.println(COMMAND_REQUEST);
        output.println(COMMAND_EXAMPLE);

        while (true) {
            output.print(COMMAND_PROMPT);
            String commandLine = input.readLine().trim();

            if (!commandLine.isEmpty()) {
                return commandLine;
            }

            output.printlnError(EMPTY_COMMAND_ERROR);
        }
    }

    public void printReport(String formattedReport) {
        output.print(Objects.requireNonNull(formattedReport, "formattedReport"));
    }

    public void printError(Exception exception) {
        Objects.requireNonNull(exception, "exception");

        String message = exception.getMessage();
        if (message == null || message.isBlank()) {
            message = UNKNOWN_ERROR;
        }

        output.printlnError(ERROR_PREFIX + message);
    }
}
