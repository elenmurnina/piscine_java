import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Program {
    private static final String DICTIONARY_FILE_NAME = "dictionary.txt";

    public static void main(String[] argc) throws IOException {
        if (argc.length != 2) {
            System.out.println("Wrong arguments.");
            System.exit(-1);
        }

        FileReader firstFile = null;
        FileReader secondFile = null;
        FileWriter dictionaryFile = null;

        try {
            firstFile = new FileReader(argc[0]);
            secondFile = new FileReader(argc[1]);
        } catch (FileNotFoundException e) {
            System.out.println("Can't find file.");
            System.exit(-1);
        }

        try {
            dictionaryFile = new FileWriter(DICTIONARY_FILE_NAME);
        } catch (IOException e) {
            System.out.println("Can't create file " + DICTIONARY_FILE_NAME);
            System.exit(-1);
        }

        BufferedReader bufferFirst = new BufferedReader(firstFile);
        Map<String, Long> wordFirst = fillMapFromStream(bufferFirst);
        bufferFirst.close();

        BufferedReader bufferSecond = new BufferedReader(secondFile);
        Map<String, Long> wordSecond = fillMapFromStream(bufferSecond);
        bufferSecond.close();

        Map<String, Long> dictionaryAll = new TreeMap<>();

        for (String s : wordFirst.keySet()) {
            if (wordSecond.containsKey(s)) {
                dictionaryAll.put(s, wordFirst.get(s) * wordSecond.get(s));
            } else {
                dictionaryAll.put(s, 0L);
            }
        }

        for (String s : wordSecond.keySet()) {
            if (wordFirst.containsKey(s)) {
                dictionaryAll.put(s, wordFirst.get(s) * wordSecond.get(s));
            } else {
                dictionaryAll.put(s, 0L);
            }
        }

        for (String s : dictionaryAll.keySet()) {
            dictionaryFile.write(s + "\n");
        }
        dictionaryFile.close();

        double result = calculateNumerator(dictionaryAll);
        double denominator = calculateDenominator(wordFirst, wordSecond);
        if (denominator == 0) {
            result = 0;
        } else {
            result = result / calculateDenominator(wordFirst, wordSecond);
        }

        System.out.println("Similarity = " + result);
    }

    private static Map<String, Long> fillMapFromStream(BufferedReader bufferedReader) throws IOException {
        Map<String, Long> result = new HashMap<>();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            String[] words = toLower(line.split("\\s+"));
            for (String word : words) {
                if (!word.isEmpty()) {
                    if (!result.containsKey(word)) {
                        result.put(word, 1L);
                    } else {
                        result.put(word, result.get(word) + 1);
                    }
                }
            }
        }

        return result;
    }

    private static double calculateDenominator(Map<String, Long> wordFirst, Map<String, Long> wordSecond) {
        long sumFirst = 0;
        for (long value : wordFirst.values()) {
            sumFirst += value * value;
        }

        long sumSecond = 0;
        for (long value : wordSecond.values()) {
            sumSecond += value * value;
        }
        return Math.sqrt(sumFirst) * Math.sqrt(sumSecond);
    }

    private static double calculateNumerator(Map<String, Long> dictionaryAll) {
        long result = 0;

        for (String s : dictionaryAll.keySet()) {
            result += dictionaryAll.get(s);
        }
        return result;
    }

    private static String[] toLower(String[] words) {
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].toLowerCase();
        }
        return words;
    }
}
