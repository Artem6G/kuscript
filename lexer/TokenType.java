package lexer;

public enum TokenType {
    WORD,

    NULL,

    HEX_NUM,
    OCTAL_NUM,
    BINARY_NUM,

    BOOLEAN,

    INTEGER_VALUE,
    DOUBLE_VALUE,
    BOOLEAN_VALUE,
    STRING_VALUE,
    CHAR_VALUE,

    IF,
    ELIF,
    ELSE,

    SWITCH,
    CASE,
    DEFAULT,

    PASS,

    DO,
    WHILE,
    FOR,

    RANGE,

    BREAK,
    CONTINUE,

    DEF,
    RETURN,

    IMPORT,

    PRINT_EXPRESSION, // $

    COMMA, // ,

    QUERY, // ?
    COLON, // :

    FULL_BYPASS, // *:

    EQUALS, // =
    NOT_CORRESPONDENCE, // !=
    CORRESPONDENCE, // ==

    INCREMENT, // ++
    DECREMENT, // --

    POWER, // **
    MOD, // %
    DIVIDE, // /
    MULTIPLY, // *
    PLUS, // +
    MINUS, // -

    OPERATOR_EQUALS, // {OPERATOR}=

    LEFT_PAREN, // (
    RIGHT_PAREN, // )

    LEFT_BRACKET, // {
    RIGHT_BRACKET, // }

    LEFT_SQUARE, // [
    RIGHT_SQUARE, // ]

    MORE, // >
    SMALLER, // <
    STRICTLY_MORE, // >=
    STRICTLY_SMALLER, // <=
    SPACESHIP, // <=>

    CONJUNCTION, // &
    DISJUNCTION, // |
    XOR, // ^
    NEGATION, // ~
    NOR, // ~|
    NAND, // ~&

    EQUIVALENCE, // <>
    IMPLICATION, // ->
    REVERSE_IMPLICATION, // <-

    LEFT_SHIFT, // <<
    RIGHT_SHIFT, // >>
    RIGHT_UNSIGNED_SHIFT, // >>>

    NO, // !
    AND, // &&
    OR, // ||

    EOF
}