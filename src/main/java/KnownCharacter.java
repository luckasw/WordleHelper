public class KnownCharacter {
    private final boolean trueIndex;
    private final char character;

    public KnownCharacter(boolean trueIndex, char character) {
        this.trueIndex = trueIndex;
        this.character = character;
    }

    public boolean isTrueIndex() {
        return this.trueIndex;
    }

    public char getCharacter() {
        return this.character;
    }
}
