package exceptions;

public final class LexerException extends RuntimeException {

    public LexerException(int currentLine, int currentPositionOnLine, String message) {
        super(String.format("%d:%d %s", currentLine, currentPositionOnLine, message));
    }

}
