package application.userInterface.Console;

import org.springframework.stereotype.Component;

@Component
public final class Terminal {
    private static final String COMMAND_REQUEST = "Введите команду, например:", COMMAND_EXAMPLE = "--path logs/*.log --from 2024-08-31 --format markdown", COMMAND_PROMPT = "> ", EMPTY_COMMAND_ERROR = "Ошибка: команда не может быть пустой";
    private final Input input;
    private final Output output;


    public Terminal(Input input, Output output) {
        this.input = input;
        this.output = output;
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
        output.println(formattedReport);
    }


    public void printError(String message) {
        output.printlnError(message);
    }
}
