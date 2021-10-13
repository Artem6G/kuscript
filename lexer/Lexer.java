package lexer;

import exceptions.LexerException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Lexer {
    private final String INPUT;
    private final StringBuilder stringBuilder = new StringBuilder();
    private final ArrayList<Token> TOKENS;
    private final String OPERATOR_CHARS = ";+-/*(){}[]%<>*|=^~&!?:,";
    private final String WORD_CHARS = "`_$";
    private final String FORMAT_CHARS = "\"tfrnb'\\";
    private final String FORMAT_EQUALS_CHARS = "\"\t\f\r\n\b'\\";
    private final int LENGTH;
    private final ArrayList<String> STATEMENTS = new ArrayList<>(Arrays.asList(
            "if",
            "elif",
            "else",
            "switch",
            "case",
            "default",
            "while",
            "for",
            "break",
            "continue",
            "do",
            "range",
            "import",
            "boolean",
            "null",
            "const",
            "def",
            "return"
    ));
    private final HashMap<String, TokenType> OPERATORS = new HashMap<String, TokenType>() {{
        put("+", TokenType.PLUS);
        put("-", TokenType.MINUS);
        put("/", TokenType.DIVIDE);
        put("%", TokenType.MOD);
        put("(", TokenType.LEFT_PAREN);
        put(")", TokenType.RIGHT_PAREN);
        put("[", TokenType.LEFT_SQUARE);
        put("]", TokenType.RIGHT_SQUARE);
        put("*", TokenType.MULTIPLY);
        put("**", TokenType.POWER);
        put(">", TokenType.MORE);
        put("<", TokenType.SMALLER);
        put(">=", TokenType.STRICTLY_MORE);
        put("<=", TokenType.STRICTLY_SMALLER);
        put("<=>", TokenType.SPACESHIP);
        put("&", TokenType.CONJUNCTION);
        put("~", TokenType.NEGATION);
        put("~|", TokenType.NOR);
        put("~&", TokenType.NAND);
        put("&&", TokenType.AND);
        put("!", TokenType.NO);
        put("^", TokenType.XOR);
        put("?", TokenType.QUERY);
        put(":", TokenType.COLON);
        put("|", TokenType.DISJUNCTION);
        put("||", TokenType.OR);
        put("{", TokenType.LEFT_BRACKET);
        put("}", TokenType.RIGHT_BRACKET);
        put("<<", TokenType.LEFT_SHIFT);
        put(">>", TokenType.RIGHT_SHIFT);
        put(">>>", TokenType.RIGHT_UNSIGNED_SHIFT);
        put("->", TokenType.IMPLICATION);
        put("<-", TokenType.REVERSE_IMPLICATION);
        put("<>", TokenType.EQUIVALENCE);
        put("=", TokenType.EQUALS);
        put("!=", TokenType.NOT_CORRESPONDENCE);
        put("==", TokenType.CORRESPONDENCE);
        put("++", TokenType.INCREMENT);
        put("--", TokenType.DECREMENT);
        put(",", TokenType.COMMA);
        put("$", TokenType.PRINT_EXPRESSION);
        put("*:", TokenType.FULL_BYPASS);
        put(";", TokenType.SEMICOLON);
    }};
    private final ArrayList<String> OPERATOR_EQUALS = new ArrayList<>(Arrays.asList(
            "+=",
            "-=",
            "/=",
            "%=",
            "*=",
            "**=",
            "<=>=",
            "<<=",
            ">>=",
            ">>>=",
            "^=",
            "~|=",
            "~&=",
            "&=",
            "|=",
            "<>=",
            "->=",
            "<-="
    ));

    private int currentPosition = 0;
    private int currentLine, currentPositionOnLine;
    private char currentChar;

    public Lexer(String input) {
        TOKENS = new ArrayList<>();
        LENGTH = input.length();
        this.INPUT = input;
        currentLine = currentPositionOnLine = 1;
    }

    public ArrayList<Token> lex() {
        currentChar = currentChar();

        while (currentPosition < LENGTH) {

            if (compareChar('0') && compareChar(1, 'b')) {
                tokenizeBinaryNumber();
            } else if (compareChar('0') && compareChar(1, 'x')) {
                tokenizeOctalNumber();
            } else if (Character.isDigit(currentChar)) {
                tokenizeNumber();
            } else if (compareChar('/') && compareChar(1, '/')) {
                next(2);
                comment();
            } else if (compareChar('/') && compareChar(1, '*')) {
                next(2);
                multilineComment();
            } else if (Character.isLetter(currentChar) || WORD_CHARS.indexOf(currentChar) != -1) {
                tokenizeWord();
            } else if (compareChar('\'')) {
                tokenizeChar();
            } else if (compareChar('"')) {
                tokenizeText();
            } else if (compareChar('#')) {
                tokenizeHexNumber();
            } else if (OPERATOR_CHARS.indexOf(currentChar) != -1) {
                tokenizeOperator();
            }  else {
                next();
            }
        }

        return TOKENS;
    }

    private void tokenizeOperator() {
        clearBuilder();
        String last = "";

        for (int i = 0; OPERATOR_CHARS.indexOf(currentChar) != -1; i++) {
            stringBuilder.append(currentChar);

            if (OPERATORS.get(stringBuilder.toString()) == null && !OPERATOR_EQUALS.contains(stringBuilder.toString())) {
                addToken(OPERATORS.get(last));
                stringBuilder.delete(0, i);
                i = 0;
            }

            last = stringBuilder.toString();

            next();
        }

        if (!last.equals("")) {
            if (OPERATOR_EQUALS.contains(last)) {
                addToken(TokenType.OPERATOR_EQUALS, last);
                return;
            }

            addToken(OPERATORS.get(last));
        }
    }

    private void tokenizeNumber() {
        clearBuilder();
        TokenType tokenType = TokenType.INTEGER_VALUE;
        char next;

        do {
            if (compareChar('_')) {
                next = next();
                continue;
            }

            if (compareChar('.'))
                if (stringBuilder.toString().indexOf('.') != -1)
                    exception("illegal dot");
                else {
                    tokenType = TokenType.DOUBLE_VALUE;
                }

            stringBuilder.append(currentChar);

            next = next();
        } while (Character.isDigit(next) || next == '.' || next == '_');

        addToken(tokenType, stringBuilder.toString());
    }

    private void tokenizeHexNumber() {
        clearBuilder();

        while (isHexNumber(next())) {
            stringBuilder.append(currentChar);
        }

        addToken(TokenType.HEX_NUM, stringBuilder.toString());
    }

    private void tokenizeBinaryNumber() {
        clearBuilder();
        next();

        while (isBinaryNumber(next())) {
            stringBuilder.append(currentChar);
        }

        addToken(TokenType.BINARY_NUM, stringBuilder.toString());
    }

    private void tokenizeOctalNumber() {
        clearBuilder();
        next();

        while (isOctalNumber(next())) {
            stringBuilder.append(currentChar);
        }

        addToken(TokenType.OCTAL_NUM, stringBuilder.toString());
    }

    private static boolean isOctalNumber(char ch) {
        return ch >= '0' && ch < '8';
    }

    private static boolean isBinaryNumber(char ch) {
        return ch == '0' || ch == '1';
    }

    private static boolean isHexNumber(char ch) {
        return Character.isDigit(ch)
                || ('a' <= ch && ch <= 'f')
                || ('A' <= ch && ch <= 'F');
    }

    private void tokenizeWord() {
        clearBuilder();

        while (Character.isLetterOrDigit(currentChar) || WORD_CHARS.indexOf(currentChar) != -1) {
            stringBuilder.append(currentChar);
            next();
        }

        final String word = stringBuilder.toString();

        if (word.equals("true") || word.equals("false"))
            addToken(TokenType.BOOLEAN_VALUE, word);
        else if (STATEMENTS.contains(word))
            addToken(TokenType.valueOf(word.toUpperCase()));
        else
            addToken(TokenType.WORD, word);
    }

    private void tokenizeText() {
        clearBuilder();
        next(); // skip "

         while (currentChar != '"') {

             if (currentChar == '\\') {
                 char peeked = peek(1);

                 int index = FORMAT_CHARS.indexOf(peeked);

                 if (index != -1) {
                     stringBuilder.append(FORMAT_EQUALS_CHARS.charAt(index));
                     next();
                     continue;
                 }
                 else
                     exception("string not closed");
             }

             stringBuilder.append(currentChar);
             next();
        }
        next();

        addToken(TokenType.STRING_VALUE, stringBuilder.toString());
    }

    private void tokenizeChar() {
        next();
        char ch = currentChar;

        if (ch == '\\') {
            int index = FORMAT_CHARS.indexOf(peek(1));

            if (index != -1) {
                ch = FORMAT_EQUALS_CHARS.charAt(index);
                next();
            }
            else
                exception("illegal escape character");
        }

        if (next() != '\'')
            exception("illegal escape character");

        next();

        addToken(TokenType.CHAR_VALUE, Character.toString(ch));
    }

    private void multilineComment() {
        while (true) {
            if (currentChar == '\0') exception("multiline comment not closed");
            if (currentChar == '*' && compareChar(1, '/')) break;
            currentChar = next();
        }
        next(2);
    }

    private void comment() {
        while ("\r\n\0".indexOf(currentChar) == -1) {
            currentChar = next();
        }
    }

    private char next(int n) {
        for (int i = 0; i < n; i++) {
            currentPosition++;
            check();
        }

        return currentChar;
    }

    private char next() {
        currentPosition++;
        check();
        return currentChar;
    }

    private void check() {
        if (currentChar() == '\n') {
            currentLine++;
            currentPositionOnLine = 0;
        } else {
            currentPositionOnLine++;
        }
    }

    private char currentChar() {
        currentChar = peek(0);
        return currentChar;
    }

    private boolean compareChar(int fromPosition, char ch) {
        return peek(fromPosition) == ch;
    }

    private boolean compareChar (char ch) {
        return currentChar == ch;
    }

    private char peek(int fromPosition) {
        final int pos = currentPosition + fromPosition;
        return pos >= LENGTH ? '\0' : INPUT.charAt(pos);
    }

    private void addToken(TokenType tokenType, String value) {
        TOKENS.add(new Token(tokenType, value));
    }

    private void addToken(TokenType tokenType) {
        TOKENS.add(new Token(tokenType, null));
    }

    private void clearBuilder() {
        stringBuilder.setLength(0);
    }

    private void exception(String message) {
        throw new LexerException(currentLine, currentPositionOnLine, message);
    }

}