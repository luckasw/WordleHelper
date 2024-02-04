import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    final static String words_alpha_five_Path = "Dictionaries/words-alpha-five.txt";
    final static String wordle_La_Path = "Dictionaries/wordle-La.txt";
    final static String wordle_Ta_Path = "Dictionaries/wordle-Ta.txt";
    public static void main(String[] args) throws IOException {
        Wordle_Dictionary words_alpha_five = initDictionary(words_alpha_five_Path);
        Wordle_Dictionary wordle_La = initDictionary(wordle_La_Path); // All possible answers
        Wordle_Dictionary wordle_Ta = initDictionary(wordle_Ta_Path); // All guessable words

        HashMap<Integer, String> map = mapDictionary(words_alpha_five);

        KnownCharacter[] knownCharacters = new KnownCharacter[5];
        ArrayList<Character> notInWord = new ArrayList<>();

/*        knownCharacters[4] = new KnownCharacter(true, 'e');
        notInWord.add('a');
        notInWord.add('s');
        notInWord.add('n');
        notInWord.add('k');
        notInWord.add('z');
        notInWord.add('o');
        notInWord.add('w');
        notInWord.add('i');
        notInWord.add('u');
        knownCharacters[0] = new KnownCharacter(true, 'v');
        knownCharacters[1] = new KnownCharacter(true, 'e');
        knownCharacters[2] = new KnownCharacter(true, 'r');

        wordle_La.addUsedWord("verve");
        wordle_La.addUsedWord("veuve");
        wordle_La.addUsedWord("zowie");


        HashMap<Integer, ArrayList<String>> wordMap = findWord(wordle_La, knownCharacters, notInWord);
        System.out.println(wordMap);*/

        runGame(wordle_La, knownCharacters, notInWord);
    }

    private static void runGame(Wordle_Dictionary wordleLa, KnownCharacter[] knownCharacters, ArrayList<Character> notInWord) throws IOException {
        int tries = 0;
        while (tries < 5) {
            tries++;
            HashMap<Integer, ArrayList<String>> wordMap = findWord(wordleLa, knownCharacters, notInWord);
            int max = 0;
            for (int i : wordMap.keySet()) {
                if (i > max) {
                    max = i;
                }
            }

            System.out.println("Max: " + max);
            System.out.println(wordMap.get(max).toString());

            System.out.print("Enter used word: ");
            String usedWord = readInput();
            char[] usedWordChars = usedWord.toCharArray();
            wordleLa.addUsedWord(usedWord);

            System.out.println("Enter known characters: ");
            for (int i = 0; i < 5; i++) {
                if (knownCharacters[i] != null && knownCharacters[i].isTrueIndex()) {
                    System.out.println("Character " + (i + 1) + " is " + knownCharacters[i].getCharacter());
                    continue;
                }
                char c = usedWordChars[i];
                System.out.print("Is character " + (i + 1) + " in word (y/n): ");
                char answer = readInput().charAt(0);
                boolean b = false;
                if (answer != 'n') {
                    System.out.println("Is true index? (true/false): ");
                    b = Boolean.parseBoolean(readInput());
                }
                if (knownCharacters[i] == null && answer != 'n') { // If the index is free and the character is not 0
                    knownCharacters[i] = new KnownCharacter(b, c);
                } else if (knownCharacters[i] != null && answer != 'n') { // If the index is not free and the character is not 0
                    if (b) {
                        KnownCharacter temp = knownCharacters[i];
                        knownCharacters[i] = new KnownCharacter(b, c);
                        findFreeIndex(knownCharacters, temp.isTrueIndex(), temp.getCharacter());
                    } else findFreeIndex(knownCharacters, b, c);
                }
            }

            for (Character c : usedWordChars) {
                boolean isNotInWord = true;
                for (KnownCharacter knownCharacter : knownCharacters) {
                    if (knownCharacter != null && knownCharacter.getCharacter() == c) {
                        isNotInWord = false;
                        break;
                    }
                }
                if (isNotInWord) {
                    notInWord.add(c);
                }
            }
        }
    }

    private static void findFreeIndex(KnownCharacter[] knownCharacters, boolean b, char c) {
        for (int j = 0; j < 5; j++) {
            if (knownCharacters[j] == null) {
                knownCharacters[j] = new KnownCharacter(b, c);
                break;
            }
        }
    }

    private static String readInput() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    private static Wordle_Dictionary initDictionary(String fileName) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        return new Wordle_Dictionary(reader.lines().toArray(String[]::new));
    }

    private static HashMap<Integer, String> mapDictionary(Wordle_Dictionary wordleDictionary) {
        HashMap<Integer, String> map = new HashMap<>();

        return map;
    }

    private static HashMap<Integer, ArrayList<String>> findWord(Wordle_Dictionary wordleDictionary, KnownCharacter[] knownCharacters, ArrayList<Character> notInWord) {
        String[] words = wordleDictionary.getWords();
        HashMap<Integer, ArrayList<String>> map = new HashMap<>();
        for (String word : words) {
            boolean valid = true;
            int goodChars = 0;
            for (int i = 0; i < 5; i++) {
                if (notInWord.contains(word.charAt(i))) {
                    valid = false;
                    break;
                }
                if (knownCharacters[i] != null) {
                    if (knownCharacters[i].isTrueIndex() && word.charAt(i) != knownCharacters[i].getCharacter()) {
                        valid = false;
                        break;
                    }
                    if (!knownCharacters[i].isTrueIndex() && word.charAt(i) == knownCharacters[i].getCharacter()) {
                        valid = false;
                        break;
                    }
                    if (knownCharacters[i].isTrueIndex() && word.charAt(i) == knownCharacters[i].getCharacter()) {
                        goodChars+=2;
                    }
                    for (KnownCharacter knownCharacter : knownCharacters) {
                        if (knownCharacter != null && knownCharacter.getCharacter() == word.charAt(i)) {
                            goodChars++;
                        }
                    }
                }
            }
            if (valid && wordleDictionary.notUsedWord(word)) {
                if (map.containsKey(goodChars)) {
                    map.get(goodChars).add(word);
                } else {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(word);
                    map.put(goodChars, list);
                }
            }
        }
        return map;
    }
}
