package edu.school21.printer.app;

public class InvalidArgumentsException extends RuntimeException {
    public InvalidArgumentsException() {
        super("Expected format arguments: <full path to the image.bmp> <character for white pixels> <character for black pixels>.");
    }
}