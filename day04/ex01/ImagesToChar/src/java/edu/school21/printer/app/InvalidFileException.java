package edu.school21.printer.app;

public class InvalidFileException extends RuntimeException {
    public InvalidFileException(Throwable cause) {
        super("File not found or could not be opened,", cause);
    }
}