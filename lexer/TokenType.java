package lexer;

public enum TokenType {
    WORD,

    NULL,

    HEX_NUM,
    OCTAL_NUM,
    BINARY_NUM,

    BOOLEAN,

    CONST,

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
    SEMICOLON,

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

    OPERATOR_EQUALS,

/*  POWER_EQUALS **=
    MOD_EQUALS %=
    DIVIDE_EQUALS /=
    MULTIPLY_EQUALS *=
    PLUS_EQUALS +=
    MINUS_EQUALS -=
    CONCATENATE_EQUALS .=
    SPACESHIP_EQUALS <=>=
    LEFT_SHIFT_EQUALS <<=
    RIGHT_SHIFT_EQUALS >>=
    RIGHT_UNSIGNED_SHIFT_EQUALS >>>=
    XOR_EQUALS ^=
    NOR_EQUALS ~|=
    NAND_EQUALS ~&=
    CONJUNCTION_EQUALS &=
    DISJUNCTION_EQUALS |=
    EQUIVALENCE_EQUALS <>=
    IMPLICATION_EQUALS ->=
    REVERSE_IMPLICATION_EQUALS <-=
*/

    CONCATENATE, // .

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