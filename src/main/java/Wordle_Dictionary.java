public class Wordle_Dictionary {
    private final String[] words;
    private String[] usedWords;

    public Wordle_Dictionary(String[] words) {
        this.words = words;
        this.usedWords = new String[5];
    }

    public void addUsedWord(String word) {
        for (int i = 0; i < this.usedWords.length; i++) {
            if (this.usedWords[i] == null) {
                this.usedWords[i] = word;
                break;
            }
        }
    }

    public String[] getWords() {
        return this.words;
    }

    public boolean notUsedWord(String word) {
        for (String usedWord : this.usedWords) {
            if (usedWord != null && usedWord.equals(word)) {
                return false;
            }
        }
        return true;
    }
}
