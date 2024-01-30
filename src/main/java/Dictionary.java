public class Dictionary {
    private final String[] words;
    private String[] usedWords;

    public Dictionary(String[] words) {
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

}
