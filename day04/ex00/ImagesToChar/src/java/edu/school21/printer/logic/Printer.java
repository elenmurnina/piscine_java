package edu.school21.printer.logic;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Printer {
    private final BufferedImage image;
    private final char white;
    private final char black;

    public Printer(BufferedImage image, char white, char black) {
        this.image = image;
        this.white = white;
        this.black = black;
    }

    public void print() {
        for (int x = 0; x < image.getHeight(); x++) {
            for (int y = 0; y < image.getWidth(); y++) {
                if (image.getRGB(y, x) == Color.BLACK.getRGB()) {
                    System.out.print(black);
                } else if (image.getRGB(y, x) == Color.WHITE.getRGB()) {
                    System.out.print(white);
                } else {
                    throw new UnexpectedColorInFileException();
                }
            }
            System.out.println();
        }
    }
}
