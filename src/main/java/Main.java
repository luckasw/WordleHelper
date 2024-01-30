import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {
    final static String dictionaryFileName = "dictionary.txt";
    public static void main(String[] args) throws FileNotFoundException {
        Dictionary dictionary = initDictionary(dictionaryFileName);
    }

    private static Dictionary initDictionary(String fileName) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        return new Dictionary(reader.lines().toArray(String[]::new));
    }
}
