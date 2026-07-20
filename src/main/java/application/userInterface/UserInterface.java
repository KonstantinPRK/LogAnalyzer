package application.userInterface;

import application.model.RequestConfig;

public interface UserInterface {
    public RequestConfig getConfiguration(String[] args);
    public void displayReport(String report);
    public void displayError(String message);
}
