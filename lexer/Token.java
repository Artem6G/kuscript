package lexer;

public class Token {
    private final TokenType tokenType;
    private final String value;

    public Token(TokenType tokenType, String value) {
        this.tokenType = tokenType;
        this.value = value;
    }

    public TokenType getType() {
        return tokenType;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (value != null)
            return String.format("[%s %s] ", tokenType.toString(), value);
        else
            return String.format("[%s] ", tokenType.toString());
    }

}