package parser;

import lexer.Token;
import lexer.TokenType;
import lib.arguments.Arguments;
import lib.functions.DefineFunction;
import lib.values.NoneValue;
import lib.values.NullValue;
import parser.ast.Expression;
import parser.ast.Statement;
import parser.ast.expressions.*;
import parser.ast.statements.*;

import java.util.*;

public class Parser {
    private static final Token EOF = new Token(TokenType.EOF, "");

    private final List<Token> TOKENS;
    private final int SIZE;

    private int currentPosition = 0;

    public Parser(List<Token> tokens) {
        this.TOKENS = tokens;
        this.SIZE = tokens.size();
    }

    public List<Statement> parse() {
        final List<Statement> result = new ArrayList<>();

        while (!match(TokenType.EOF)) {
            result.add(blockOrStatement());
        }

        return result;
    }

    private Statement blockOrStatement() {
        ArrayList<Statement> statements = anyBlock();

        if (statements != null)
            return new BlockStatement(statements);

        return statement();
    }

    private Statement statement() {
        if (compareType(TokenType.WORD) && compareType(1, TokenType.COLON)) {

            String word = getCurrentToken().getValue();
            consume(TokenType.WORD, TokenType.COLON);

            return new LabelStatement(word, blockOrStatement());
        }
        else if (match(TokenType.IF))
            return ifElse();
        else if (match(TokenType.DEF))
            return def();
        else if (match(TokenType.IMPORT))
            return new ImportStatement(expression());
        else if (match(TokenType.WHILE))
            return whileLoop();
        else if (match(TokenType.SWITCH))
            return _switch();
        else if (match(TokenType.FOR))
            return anyFor();
        else if (match(TokenType.BREAK))
            return _break();
        else if (match(TokenType.RETURN))
            return _return();
        else if (match(TokenType.DO))
            return doWhile();
        else if (match(TokenType.CONTINUE))
            return new ContinueStatement();
        else if (match(TokenType.PASS))
            return new PassStatement();

        return assignmentStatement();
    }

    private Statement assignmentStatement() {
        if (compareType(TokenType.WORD) && (compareType(1, TokenType.COMMA)))
            return multiplyAssignment();

        return elementArrayAssignmentStatement();
    }

    private Statement elementArrayAssignmentStatement() {
        if (compareType(TokenType.WORD) && compareType(1, TokenType.LEFT_SQUARE))
            return elementArrayAssignment();


        return new ExpressionStatement(expression());
    }

    public Expression expression() {
        return ternary();
    }

    private Expression ternary() {
        Expression expression = logical_equivalence();

        if (match(TokenType.QUERY)) {
            final Expression trueExpr = logical_equivalence();
            match(TokenType.COLON);
            final Expression falseExpr = logical_equivalence();
            return new TernaryExpression(expression, trueExpr, falseExpr);
        }

        return expression;
    }

    private Expression logical_equivalence() {
        Expression expression = logical_implication();

        for (;;) {
            if (match(TokenType.EQUIVALENCE)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.EQUIVALENCE, expression, logical_implication());
                continue;
            }

            break;
        }

        return expression;
    }

    private Expression logical_implication() {
        Expression expression = logical_disjunction();

        for (;;) {
            if (match(TokenType.IMPLICATION)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.IMPLICATION, expression, logical_disjunction());
                continue;
            }

            if (match(TokenType.REVERSE_IMPLICATION)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.REVERSE_IMPLICATION, expression, logical_disjunction());
                continue;
            }

            break;
        }

        return expression;
    }


    private Expression logical_disjunction() {
        Expression expression = logical_conjunction();

        for (;;) {
            if (match(TokenType.DISJUNCTION)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.DISJUNCTION, expression, logical_conjunction());
                continue;
            }

            if (match(TokenType.NAND)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.NAND, expression, logical_conjunction());
                continue;
            }

            if (match(TokenType.XOR)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.XOR, expression, logical_conjunction());
                continue;
            }

            if (match(TokenType.OR)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.AND, expression, logical_conjunction());
                continue;
            }

            break;
        }

        return expression;
    }

    private Expression logical_conjunction() {
        Expression expression = conditional();

        for (;;) {
            if (match(TokenType.CONJUNCTION)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.CONJUNCTION, expression, conditional());
                continue;
            }

            if (match(TokenType.AND)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.AND, expression, conditional());
                continue;
            }

            if (match(TokenType.NOR)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.NOR, expression, conditional());
                continue;
            }

            break;
        }

        return expression;
    }

    private Expression conditional() {
        Expression expression = spaceship();

        for (;;) {
            if (match(TokenType.CORRESPONDENCE)) {
                expression = conditional_expr(BinaryExpression.OPERATORS.CORRESPONDENCE, expression, spaceship());
                continue;
            }
            if (match(TokenType.NOT_CORRESPONDENCE)) {
                expression = conditional_expr(BinaryExpression.OPERATORS.NOT_CORRESPONDENCE, expression, spaceship());
                continue;
            }
            if (match(TokenType.MORE)) {
                expression = conditional_expr(BinaryExpression.OPERATORS.MORE, expression, spaceship());
                continue;
            }
            if (match(TokenType.SMALLER)) {
                expression = conditional_expr(BinaryExpression.OPERATORS.SMALLER, expression, spaceship());
                continue;
            }
            if (match(TokenType.STRICTLY_MORE)) {
                expression = conditional_expr(BinaryExpression.OPERATORS.STRICTLY_MORE, expression, spaceship());
                continue;
            }
            if (match(TokenType.STRICTLY_SMALLER)) {
                expression = conditional_expr(BinaryExpression.OPERATORS.STRICTLY_SMALLER, expression, spaceship());
                continue;
            }

            break;
        }

        return expression;
    }

    private Expression spaceship() {
        Expression expression = shift();

        for (;;) {
            if (match(TokenType.SPACESHIP)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.SPACESHIP, expression, shift());
                continue;
            }

            break;
        }

        return expression;
    }

    private Expression shift() {
        Expression expression = additive();

        for(;;) {
            if (match(TokenType.LEFT_SHIFT)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.LEFT_SHIFT, expression, additive());
                continue;
            }

            if (match(TokenType.RIGHT_SHIFT)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.RIGHT_SHIFT, expression, additive());
                continue;
            }

            if (match(TokenType.RIGHT_UNSIGNED_SHIFT)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.RIGHT_UNSIGNED_SHIFT, expression, additive());
                continue;
            }
            break;
        }

        return expression;
    }

    private Expression additive() {
        Expression expression = multiplicative();

        for (;;){
            if (match(TokenType.PLUS)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.ADD, expression, multiplicative());
                continue;
            }
            if (match(TokenType.MINUS)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.SUBTRACT, expression, multiplicative());
                continue;
            }
            break;
        }

        return expression;
    }

    private Expression multiplicative() {
        Expression expression = power();

        for (;;) {
            if (match(TokenType.MULTIPLY)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.MULTIPLY, expression, power());
                continue;
            }
            if (match(TokenType.DIVIDE)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.DIVIDE, expression, power());
                continue;
            }
            if (match(TokenType.MOD)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.MOD, expression, power());
                continue;
            }

            break;
        }

        return expression;
    }

    private Expression power() {
        Expression expression = arrOrFunc();

        for (;;) {
            if (match(TokenType.POWER)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.POWER, expression, arrOrFunc());
                continue;
            }

            return expression;
        }
    }

    private Expression arrOrFunc() {
        Expression expression = unary();

        for (;;) {
            if (match(TokenType.LEFT_SQUARE)) {
                expression = new ElementValueArrayExpression(expression, expression());
                consume(TokenType.RIGHT_SQUARE);
                continue;
            }
            if (match(TokenType.LEFT_PAREN)) {
                ArrayList<Expression> expressions = new ArrayList<>();
                if (!match(TokenType.RIGHT_PAREN)) {
                    do {
                        expressions.add(expression());
                    } while (match(TokenType.COMMA));
                    consume(TokenType.RIGHT_PAREN);
                }
                expression = new FunctionCallValueExpression(expression, expressions);
                continue;
            }

            return expression;
        }
    }

    private Expression unary() {
        if (match(TokenType.NEGATION))
            return new UnaryExpression(UnaryExpression.OPERATORS.NEGATION, primary());
        if (match(TokenType.NO))
            return new UnaryExpression(UnaryExpression.OPERATORS.NO, primary());
        if (match(TokenType.PLUS))
            return new UnaryExpression(UnaryExpression.OPERATORS.PLUS, primary());
        if (match(TokenType.INCREMENT))
            return new UnaryExpression(UnaryExpression.OPERATORS.LEFT_INCREMENT, primary());
        if (match(TokenType.DECREMENT))
            return new UnaryExpression(UnaryExpression.OPERATORS.LEFT_DECREMENT, primary());
        if (compareType(TokenType.WORD) && compareType(1, TokenType.INCREMENT)) {
            final Token token = getCurrentToken();
            consume(TokenType.WORD, TokenType.INCREMENT);
            return new UnaryExpression(UnaryExpression.OPERATORS.RIGHT_INCREMENT, new VariableExpression(token.getValue()));
        }
        if (compareType(TokenType.WORD) && compareType(1, TokenType.DECREMENT)) {
            final Token token = getCurrentToken();
            consume(TokenType.WORD, TokenType.DECREMENT);
            return new UnaryExpression(UnaryExpression.OPERATORS.RIGHT_DECREMENT, new VariableExpression(token.getValue()));
        }
        if (match(TokenType.MINUS))
            return new UnaryExpression(UnaryExpression.OPERATORS.MINUS, primary());

        return assignmentExpression();
    }

    private Expression assignmentExpression() {
        if (compareType(TokenType.WORD) && compareType(1, TokenType.EQUALS))
            return assignment();
        if (compareType(TokenType.WORD) && compareType(1, TokenType.OPERATOR_EQUALS))
            return assignmentOperator();

        return primary();
    }

    private Expression primary() {
        final Token currentToken = getCurrentToken();

        if (compareType(TokenType.LEFT_BRACKET))
            return array();
        if (match(TokenType.INTEGER_VALUE))
            return new ValueExpression(Integer.parseInt(currentToken.getValue()));
        if (match(TokenType.BOOLEAN_VALUE))
            return new ValueExpression(Boolean.parseBoolean(currentToken.getValue()));
        if (match(TokenType.DOUBLE_VALUE))
            return new ValueExpression(Double.parseDouble(currentToken.getValue()));
        if (match(TokenType.HEX_NUM))
            return new ValueExpression(Integer.parseInt(currentToken.getValue(), 16));
        if (match(TokenType.OCTAL_NUM))
            return new ValueExpression(Integer.parseInt(currentToken.getValue(), 8));
        if (match(TokenType.BINARY_NUM))
            return new ValueExpression(Integer.parseInt(currentToken.getValue(), 2));
        if (match(TokenType.NULL))
            return new ValueExpression(new NullValue());
        if (match(TokenType.NONE))
            return new ValueExpression(new NoneValue());
        if (match(TokenType.STRING_VALUE))
            return new ValueExpression(currentToken.getValue());
        if (match(TokenType.WORD)) {
            return new VariableExpression(currentToken.getValue());
        }
        if (match(TokenType.LEFT_PAREN)) {
            Expression result = expression();
            match(TokenType.RIGHT_PAREN);
            return result;
        }

        throw new RuntimeException("unknown expression");
    }

    private Expression expressionForArray() {
        if (compareType(TokenType.LEFT_BRACKET)) {
            return array();
        }

        return expression();
    }

    private Statement elementArrayAssignment() {
        ElementArrayExpression elementArrayExpression = (ElementArrayExpression) element();
        consume(TokenType.EQUALS);
        return new ElementAssignmentArrayStatement(elementArrayExpression, expression());
    }

    private Expression assignment() {
        String word = getCurrentToken().getValue();
        consume(TokenType.WORD, TokenType.EQUALS);
        return new AssignmentExpression(word, expression());
    }

    private Statement multiplyAssignment() {
        ArrayList<String> words = new ArrayList<>();
        ArrayList<Expression> expressions = new ArrayList<>();
        AssignmentOperatorExpression.ASSIGNMENT_OPERATORS assignment_operator = null;

        do {
            words.add(getCurrentToken().getValue());
            consume(TokenType.WORD);
        } while(match(TokenType.COMMA));

        if (compareType(TokenType.OPERATOR_EQUALS)) {
            assignment_operator = AssignmentOperatorExpression.ASSIGNMENT_OPERATORS.getType(getCurrentToken().getValue());
            consume(TokenType.OPERATOR_EQUALS);
        }
        else
            consume(TokenType.EQUALS);

        do {
            expressions.add(expression());
        } while(match(TokenType.COMMA));

        return new MultiplyAssignmentStatement(words, expressions, assignment_operator);
    }

    private Expression assignmentOperator() {
        final String word = getCurrentToken().getValue();
        consume(TokenType.WORD);
        final String operator = getCurrentToken().getValue();
        consume(TokenType.OPERATOR_EQUALS);
        return new AssignmentOperatorExpression(word, expression(), AssignmentOperatorExpression.ASSIGNMENT_OPERATORS.getType(operator));
    }

    private Statement whileLoop() {
        Expression expression = expression();
        Statement statement = blockOrStatement();

        if(match(TokenType.ELSE))
            return new WhileStatement(expression, statement, blockOrStatement());
        else
            return new WhileStatement(expression, statement, new PassStatement());
    }

    private Statement doWhile() {
        Statement statement = blockOrStatement();
        consume(TokenType.WHILE);
        Expression expression = expression();

        if(match(TokenType.ELSE))
            return new DoWhileStatement(expression, statement, blockOrStatement());
        else
            return new DoWhileStatement(expression, statement, new PassStatement());
    }

    private Statement anyFor() {
        if (compareType(1, TokenType.COLON) && compareType(2, TokenType.RANGE))
            return rangeFor();
        else if (compareType(1, TokenType.COLON))
            return foreach();

        throw new RuntimeException("");
    }

    private Statement foreach() {
        VariableExpression variableExpression = (VariableExpression) expression();
        consume(TokenType.COLON);
        Expression expression = expression();
        Statement statement = blockOrStatement();

        if (match(TokenType.ELSE))
            return new ForEachStatement(variableExpression, expression, statement, blockOrStatement());
        else
            return new ForEachStatement(variableExpression, expression, statement, new PassStatement());
    }

    private Statement rangeFor() {
        Expression expression = expression();
        consume(TokenType.COLON);
        consume(TokenType.RANGE);
        consume(TokenType.LEFT_PAREN);

        ArrayList<Expression> expressions = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            expressions.add(expression());
            if (!match(TokenType.COMMA))
                break;
        }

        consume(TokenType.RIGHT_PAREN);

        Statement statement = blockOrStatement();

        if (match(TokenType.ELSE))
            return new ForRangeStatement(expression, expressions, statement, blockOrStatement());
        else
            return new ForRangeStatement(expression, expressions, statement, new PassStatement());
    }

    private Statement ifElse() {
        LinkedHashMap<Expression, Statement> conditionals = new LinkedHashMap<>();

        conditionals.put(expression(), blockOrStatement());

        while (match(TokenType.ELIF)) {
            conditionals.put(expression(), blockOrStatement());
        }

        if (match(TokenType.ELSE)) {
            return new ConditionalStatement(conditionals, blockOrStatement());
        } else {
            return new ConditionalStatement(conditionals, new PassStatement());
        }
    }

    private Statement _return() {
        Expression expression;

        if (match(TokenType.PASS))
            expression = new ValueExpression(DefineFunction.DEFAULT_VALUE);
        else
            expression = expression();

        return new ReturnStatement(expression);
    }

    private Statement def() {
        String word = getCurrentToken().getValue();
        consume(TokenType.WORD);
        Arguments arguments = new Arguments();

        consume(TokenType.LEFT_PAREN);

        if (!match(TokenType.RIGHT_PAREN)) {
            do {
                arguments.add(getCurrentToken().getValue());
                consume(TokenType.WORD);
            } while (match(TokenType.COMMA));
            consume(TokenType.RIGHT_PAREN);
        }

        return new DefineFunctionStatement(word, arguments, blockOrStatement());
    }

    private Statement _break() {
        String word = word();

        if (word == null)
            return new BreakStatement();
        else {
            return new BreakLabelStatement(word);
        }
    }

    private Statement _switch() {
        Statement statement;
        Expression expression;
        LinkedHashMap<Expression, Statement> conditionals = new LinkedHashMap<>();
        Expression valueExpression = expression();

        while (match(TokenType.CASE)) {
            expression = expression();

            if (compareType(TokenType.CASE))
                statement = new PassStatement();
            else
                statement = blockOrStatement();

            conditionals.put(expression, statement);
        }

        if (match(TokenType.DEFAULT)) {
            return new SwitchStatement(valueExpression, conditionals, blockOrStatement());
        } else {
            return new SwitchStatement(valueExpression, conditionals, new PassStatement());
        }
    }

    private ArrayList<Statement> anyBlock() {
        ArrayList<Statement> statements = new ArrayList<>();

        if (match(TokenType.LEFT_BRACKET)) {
            while (!match(TokenType.RIGHT_BRACKET))
                statements.add(blockOrStatement());

            return statements;
        }

        return null;
    }

    private Expression element() {
        final String variable = consume(TokenType.WORD).getValue();
        final List<Expression> indexes = new ArrayList<>();

        do {
            consume(TokenType.LEFT_SQUARE);
            indexes.add(expression());
            consume(TokenType.RIGHT_SQUARE);
        } while(compareType(TokenType.LEFT_SQUARE));

        return new ElementArrayExpression(variable, indexes);
    }

    private Expression array() {
        consume(TokenType.LEFT_BRACKET);
        final List<Expression> elements = new ArrayList<>();
        while (!match(TokenType.RIGHT_BRACKET)) {
            elements.add(expressionForArray());
            match(TokenType.COMMA);
        }

        return new ArrayExpression(elements);
    }

    private Expression conditional_expr(BinaryExpression.OPERATORS operator, Expression expression1, Expression expression2) {

        if (match(TokenType.CORRESPONDENCE)) {
            return new BinaryConditionalExpression(operator, BinaryExpression.OPERATORS.CORRESPONDENCE, expression1, expression2, logical_implication());
        }
        else if (match(TokenType.NOT_CORRESPONDENCE)) {
            return new BinaryConditionalExpression(operator, BinaryExpression.OPERATORS.NOT_CORRESPONDENCE, expression1, expression2, logical_implication());
        }
        else if (match(TokenType.SMALLER)) {
            return new BinaryConditionalExpression(operator, BinaryExpression.OPERATORS.SMALLER, expression1, expression2, spaceship());
        }
        else if (match(TokenType.STRICTLY_MORE)) {
            return new BinaryConditionalExpression(operator, BinaryExpression.OPERATORS.STRICTLY_MORE, expression1, expression2, spaceship());
        }
        else if (match(TokenType.STRICTLY_SMALLER)) {
            return new BinaryConditionalExpression(operator, BinaryExpression.OPERATORS.STRICTLY_SMALLER, expression1, expression2, spaceship());
        }
        else if (match(TokenType.MORE)) {
            return new BinaryConditionalExpression(operator, BinaryExpression.OPERATORS.MORE, expression1, expression2, spaceship());
        }

        return new BinaryExpression(operator, expression1, expression2);
    }

    private String word() {
        String word = null;

        if (compareType(TokenType.WORD)) {
            word = getCurrentToken().getValue();
            consume(TokenType.WORD);
        }

        return word;
    }

    private boolean compareType(int fromPosition, TokenType tokenType) {
        return peek(fromPosition).getType() == tokenType;
    }

    private boolean compareType (TokenType tokenType) {
        return getCurrentToken().getType() == tokenType;
    }

    private boolean match(TokenType type) {
        if (type != getCurrentToken().getType())
            return false;

        currentPosition++;
        return true;
    }

    private Token consume(TokenType ... types) {
        Token current = null;

        for (TokenType type : types) {
            current = peek(0);

            if (type != current.getType())
                throw new RuntimeException("Token " + current + " doesn't match " + type);

            currentPosition++;
        }

        if (current == null) throw new RuntimeException("");
        return current;
    }

    private Token getCurrentToken() {
        return peek(0);
    }

    private Token peek(int fromPosition) {
        final int pos = currentPosition + fromPosition;
        return pos >= SIZE ? EOF : TOKENS.get(pos);
    }

}