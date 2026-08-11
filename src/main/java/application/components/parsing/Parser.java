package application.components.parsing;

public interface Parser<InfoType> {
    public InfoType parse(String unparsedLine);
}
