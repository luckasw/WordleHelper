import java.io.*;

public class ParseDictionary {
    public static void main(String[] args) throws IOException {
        String fileName = "words_alpha.txt";
        String[] dictionary = readDictionary(fileName);
        writeDictionary(dictionary);
    }

    private static void writeDictionary(String[] dictionary) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("dictionary.txt"));
        for (String word : dictionary) {
            if (word.length() == 5) {
                writer.write(word);
                writer.newLine();
            }
        }
        writer.close();
    }

    private static String[] readDictionary(String fileName) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        return reader.lines().toArray(String[]::new);
    }
}
