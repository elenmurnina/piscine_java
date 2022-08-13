package edu.school21.printer.app;

import edu.school21.printer.logic.Printer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Program {
    private static final String BMP_FILE_PATH = "src/resources/image.bmp";

    public static void main(String[] args) {
        BufferedImage image;
        if (args.length != 2 || args[0].length() != 1 || args[1].length() != 1) {
            throw new InvalidArgumentsException();
        }

        try {
            File file = new File(BMP_FILE_PATH);
            image = ImageIO.read(file);
            new Printer(image, args[0].toCharArray()[0], args[1].toCharArray()[0]).print();
        } catch (IOException e) {
            throw new InvalidFileException(e);
        }
    }
}
