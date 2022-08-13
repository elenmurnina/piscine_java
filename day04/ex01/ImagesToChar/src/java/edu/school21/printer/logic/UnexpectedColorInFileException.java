package edu.school21.printer.logic;

public class UnexpectedColorInFileException extends RuntimeException {
    public UnexpectedColorInFileException() {
        super("Wrong color in BMP file. Only black and white expected.");
    }
}