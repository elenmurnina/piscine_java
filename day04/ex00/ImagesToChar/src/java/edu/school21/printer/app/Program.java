package edu.school21.printer.app;

import edu.school21.printer.logic.Printer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Program {
    private static final String BMP_FILE_EXTENSION = ".bmp";

    public static void main(String[] args) {
        BufferedImage image = null;
        if (args.length != 3 || args[1].length() != 1 || args[2].length() != 1
                || !args[0].substring(args[0].length() - 4).equals(BMP_FILE_EXTENSION)) {
            throw new InvalidArgumentsException();
        }

        try {
            File file = new File(args[0]);
            image = ImageIO.read(file);
            new Printer(image, args[1].toCharArray()[0], args[2].toCharArray()[0]).print();
        } catch (IOException e) {
            throw new InvalidFileException(e);
        }
    }
}
