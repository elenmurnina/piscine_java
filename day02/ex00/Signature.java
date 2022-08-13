import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Signature {
    private static final String SIGNATURES_FILE_NAME = "../signatures.txt";

    private final String fileType;
    private final List<Byte> bytes;

    public Signature(String fileType, List<Byte> bytes) {
        this.fileType = fileType;
        this.bytes = bytes;
    }

    public String getFileType() {
        return fileType;
    }

    public List<Byte> getBytes() {
        return bytes;
    }

    public static List<Signature> readSignatures() throws IOException {
        List<Signature> result = new ArrayList<>();
        InputStream inputStream = new FileInputStream(SIGNATURES_FILE_NAME);
        List<Byte> line = new ArrayList<>();
        for (byte b : inputStream.readAllBytes()) {
            if (b == '\n') {
                result.add(convertLineToSignature(line));
                line.clear();
            } else {
                line.add(b);
            }
        }
        inputStream.close();
        if (!line.isEmpty()) {
            result.add(convertLineToSignature(line));
        }
        return result;
    }

    private static Signature convertLineToSignature(List<Byte> line) {
        int commaPos = line.indexOf((byte) ',');
        if (commaPos < 0) {
            throw new RuntimeException("Signatures file is invalid: comma not found.");
        }
        StringBuilder type = new StringBuilder();
        for (int i = 0; i < commaPos; i++) {
            type.append((char) ((byte) line.get(i)));
        }
        List<Byte> bytes = new ArrayList<>();
        byte cur = 0;
        for (int i = commaPos + 2; i < line.size(); i++) {
            byte b = line.get(i);
            if (b == ' ') {
                bytes.add(cur);
                cur = 0;
            } else {
                cur = (byte) (cur * 16 + toDigit(b));
            }
        }
        bytes.add(cur);
        return new Signature(type.toString(), bytes);
    }

    private static byte toDigit(byte b) {
        if (b >= '0' && b <= '9') {
            return (byte) (b - '0');
        } else {
            return (byte) (b - 'A' + 10);
        }
    }

    public boolean match(byte[] bytes) {
        if (bytes.length < this.bytes.size()) {
            return false;
        }
        for (int i = 0; i < this.bytes.size(); i++) {
            if (bytes[i] != this.bytes.get(i)) {
                return false;
            }
        }
        return true;
    }
}
