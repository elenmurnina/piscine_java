import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Program {

    private static final String EOF = "42";
    private static final String PROCESSED_OUTCOME = "PROCESSED";
    private static final String UNDEFINED_OUTCOME = "UNDEFINED";

    public static void main(String[] args) throws IOException {
        List<Signature> signatures = Signature.readSignatures();
        Scanner scanner = new Scanner(System.in);
        FileWriter result = new FileWriter("result.txt");
        FileProcessor fileProcessor = new FileProcessor();
        try {
            while (true) {
                String filePath = scanner.nextLine();
                if (filePath.equals(EOF)) {
                    break;
                }
                String fileType = fileProcessor.getFileType(filePath, signatures);
                if (fileType != null) {
                    result.write(fileType + '\n');
                    System.out.println(PROCESSED_OUTCOME);
                } else {
                    System.out.println(UNDEFINED_OUTCOME);
                }
            }
        } finally {
            result.close();
        }
    }
}