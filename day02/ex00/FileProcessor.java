import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class FileProcessor {
    public String getFileType(String fileName, List<Signature> signatures) throws IOException {
        int maxSignatureBytesLength = 0;
        for (Signature signature : signatures) {
            maxSignatureBytesLength = max(maxSignatureBytesLength, signature.getBytes().size());
        }

        maxSignatureBytesLength = min(maxSignatureBytesLength, (int) new File(fileName).length());
        InputStream inputStream = new FileInputStream(fileName);
        byte[] bytes = inputStream.readNBytes(maxSignatureBytesLength);
        inputStream.close();
        for (Signature signature : signatures) {
            if (signature.match(bytes)) {
                return signature.getFileType();
            }
        }
        return null;
    }
}