package ch.heigvd.shared.abstractions;

public interface Command {

    CommandType getCommandType();

    Object getValue();
}
