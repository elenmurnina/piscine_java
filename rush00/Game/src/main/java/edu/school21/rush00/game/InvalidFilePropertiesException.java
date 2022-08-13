package edu.school21.rush00.game;

public class InvalidFilePropertiesException extends RuntimeException {
    public InvalidFilePropertiesException(Throwable cause) {
        super("File properties not found or could not be opened,", cause);
    }
}
