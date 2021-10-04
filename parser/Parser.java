package parser;

import lexer.Token;
import lexer.TokenType;
import lib.arguments.Arguments;
import lib.functions.DefineFunction;
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

    private Statement rawBlockOrStatement() {
        ArrayList<Statement> statements = anyBlock();

        if (statements != null)
            return new RawBlockStatement(statements);

        return statement();
    }

    private Statement blockOrCultivatedStatement() {
        String label = label();

        ArrayList<Statement> statements = anyBlock();

        if (statements != null)
            if (label == null)
                return new BlockStatement(statements);
            else
                return new BlockStatement(statements, label);

        return cultivatedStatement();
    }

    private Statement cultivatedStatement() {
        return new CultivatedStatement(statement());
    }

    private Statement blockOrStatement() {
        String label = label();

        ArrayList<Statement> statements = anyBlock();

        if (statements != null)
            if (label == null)
                return new BlockStatement(statements);
            else
                return new BlockStatement(statements, label);

        return statement();
    }

    private Statement statement() {
        if (match(TokenType.IF))
            return ifElse();
        else if (match(TokenType.DEF))
            return def();
        else if (match(TokenType.IMPORT))
            return new ImportStatement(new ConstantExpression(expression()));
        else if (match(TokenType.WHILE))
            return new WhileStatement(expression(), rawBlockOrStatement());
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
            return _continue();
        else if (match(TokenType.SEMICOLON))
            return new PassStatement();

        return assignmentStatement();
    }

    private Statement assignmentStatement() {
        if (compareType(TokenType.WORD) && (compareType(1, TokenType.COMMA)))
            return multiplyAssignment();
        else if (compareType(TokenType.WORD) && (compareType(1, TokenType.LEFT_PAREN)))
            return new FunctionCallStatement(functionCallExpression());
        else if (compareType(TokenType.WORD) && (compareType(1, TokenType.EQUALS)))
            return assignment();
        else if (compareType(TokenType.CONST) && compareType(1, TokenType.WORD) && (compareType(2, TokenType.EQUALS)))
            return constAssignment();


        return elementArrayAssignmentStatement();
    }

    private Statement elementArrayAssignmentStatement() {
        if (compareType(TokenType.WORD) && compareType(1, TokenType.LEFT_SQUARE))
            return elementArrayAssignment();


        return assignmentOperatorStatement();
    }

    private Statement assignmentOperatorStatement() {
        if (compareType(TokenType.WORD) && compareType(1, TokenType.OPERATOR_EQUALS))
            return assignmentOperator();


        return incrementStatement();
    }

    private Statement incrementStatement() {
        if (compareType(TokenType.WORD) && compareType(1, TokenType.INCREMENT))
            return rightUnary(TokenType.INCREMENT, UnaryExpression.OPERATORS.RIGHT_INCREMENT);
        else if (compareType(TokenType.INCREMENT) && compareType(1, TokenType.WORD))
            return leftUnary(TokenType.INCREMENT, UnaryExpression.OPERATORS.LEFT_INCREMENT);


        return decrementStatement();
    }

    private Statement decrementStatement() {
        if (compareType(TokenType.WORD) && compareType(1, TokenType.DECREMENT))
            return rightUnary(TokenType.DECREMENT, UnaryExpression.OPERATORS.RIGHT_DECREMENT);
        else if (compareType(TokenType.DECREMENT) && compareType(1, TokenType.WORD))
            return leftUnary(TokenType.DECREMENT, UnaryExpression.OPERATORS.LEFT_DECREMENT);

        throw new RuntimeException("");
    }

    private Expression expression() {
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

        while (true) {
            if (match(TokenType.CORRESPONDENCE)) {
                expression = conditional_expr(BinaryExpression.OPERATORS.CORRESPONDENCE, expression, logical_implication());
                continue;
            }
            if (match(TokenType.NOT_CORRESPONDENCE)) {
                expression = conditional_expr(BinaryExpression.OPERATORS.NOT_CORRESPONDENCE, expression, logical_implication());
                continue;
            }
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

        while (true) {
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

        while (true) {
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

        while (true) {
            if (match(TokenType.CONJUNCTION)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.CONJUNCTION, expression, conditional());
                continue;
            }

            if (match(TokenType.NOR)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.NOR, expression, conditional());
                continue;
            }

            if (match(TokenType.AND)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.AND, expression, conditional());
                continue;
            }

            break;
        }

        return expression;
    }

    private Expression conditional() {
        Expression expression = spaceship();

        while (true) {
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

    private Expression spaceship() {
        Expression expression = shift();

        while (true) {
            if (match(TokenType.SPACESHIP)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.SPACESHIP, expression, shift());
                continue;
            }

            break;
        }

        return expression;
    }


    private Expression shift() {
        Expression expression = concatenate();

        while (true) {
            if (match(TokenType.LEFT_SHIFT)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.LEFT_SHIFT, expression, concatenate());
                continue;
            }

            if (match(TokenType.RIGHT_SHIFT)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.RIGHT_SHIFT, expression, concatenate());
                continue;
            }

            if (match(TokenType.RIGHT_UNSIGNED_SHIFT)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.RIGHT_UNSIGNED_SHIFT, expression, concatenate());
                continue;
            }

            break;
        }

        return expression;
    }

    private Expression concatenate() {
        Expression expression = additive();

        while (true) {
            if (match(TokenType.CONCATENATE)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.CONCATENATE, expression, additive());
                continue;
            }

            break;
        }

        return expression;
    }

    private Expression additive() {
        Expression expression = multiplicative();

        while (true) {
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

        while (true) {
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
        Expression expression = unary();

        while (true) {
            if (match(TokenType.POWER)) {
                expression = new BinaryExpression(BinaryExpression.OPERATORS.POWER, expression, unary());
                continue;
            }

            return expression;
        }
    }

    private Expression unary() {
        if (match(TokenType.NEGATION))
            return new UnaryExpression(UnaryExpression.OPERATORS.NEGATION, functionExpression());
        if (match(TokenType.NO))
            return new UnaryExpression(UnaryExpression.OPERATORS.NO, functionExpression());
        if (match(TokenType.PLUS))
            return new UnaryExpression(UnaryExpression.OPERATORS.PLUS, functionExpression());
        if (match(TokenType.INCREMENT))
            return new UnaryExpression(UnaryExpression.OPERATORS.LEFT_INCREMENT, functionExpression());
        if (match(TokenType.DECREMENT))
            return new UnaryExpression(UnaryExpression.OPERATORS.LEFT_DECREMENT, functionExpression());
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
            return new UnaryExpression(UnaryExpression.OPERATORS.MINUS, functionExpression());

        return functionExpression();
    }

    private Expression functionExpression() {
        if (compareType(TokenType.WORD) && compareType(1, TokenType.LEFT_PAREN)) {
            return functionCallExpression();
        }

        return primary();
    }

    private Expression primary() {
        final Token currentToken = getCurrentToken();

        if (compareType(TokenType.LEFT_BRACKET)) {
            return array();
        }
        if (compareType(TokenType.WORD) && compareType(1, TokenType.LEFT_SQUARE)) {
            return element();
        }
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
            return new NullValueExpression();
        if (match(TokenType.STRING_VALUE))
            return new ValueExpression(currentToken.getValue());
        if (match(TokenType.CHAR_VALUE))
            return new ValueExpression(currentToken.getValue().charAt(0));
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

    private Statement constAssignment() {
        consume(TokenType.CONST);
        String word = getCurrentToken().getValue();
        consume(TokenType.WORD, TokenType.EQUALS);
        return new ConstAssignmentStatement(word, expression());
    }

    private Statement assignment() {
        String word = getCurrentToken().getValue();
        consume(TokenType.WORD, TokenType.EQUALS);
        return new AssignmentStatement(word, expression());
    }

    private Statement multiplyAssignment() {
        ArrayList<String> words = new ArrayList<>();
        ArrayList<Expression> expressions = new ArrayList<>();
        AssignmentOperatorStatement.ASSIGNMENT_OPERATORS assignment_operator = null;

        do {
            words.add(getCurrentToken().getValue());
            consume(TokenType.WORD);
        } while(match(TokenType.COMMA));

        if (compareType(TokenType.OPERATOR_EQUALS)) {
            assignment_operator = AssignmentOperatorStatement.ASSIGNMENT_OPERATORS.getType(getCurrentToken().getValue());
            consume(TokenType.OPERATOR_EQUALS);
        }
        else
            consume(TokenType.EQUALS);

        do {
            expressions.add(expression());
        } while(match(TokenType.COMMA));

        return new MultiplyAssignmentStatement(words, expressions, assignment_operator);
    }

    private Statement assignmentOperator() {
        final String word = getCurrentToken().getValue();
        consume(TokenType.WORD);
        final String operator = getCurrentToken().getValue();
        consume(TokenType.OPERATOR_EQUALS);
        return new AssignmentOperatorStatement(word, expression(), AssignmentOperatorStatement.ASSIGNMENT_OPERATORS.getType(operator));
    }

    private Statement leftUnary(TokenType tokenType, UnaryExpression.OPERATORS operator) {
        consume(tokenType);
        final String word = getCurrentToken().getValue();
        consume(TokenType.WORD);
        return new UnaryStatement(new UnaryExpression(operator, new VariableExpression(word)));
    }

    private Statement rightUnary(TokenType tokenType, UnaryExpression.OPERATORS operator) {
        final String word = getCurrentToken().getValue();
        consume(TokenType.WORD, tokenType);
        return new UnaryStatement(new UnaryExpression(operator, new VariableExpression(word)));
    }

    private Statement doWhile() {
        Statement statement = rawBlockOrStatement();
        consume(TokenType.WHILE);
        return new DoWhileStatement(statement, expression());
    }

    private Statement anyFor() {
        Statement[] statements = new Statement[2];
        Expression expression;

        if (compareType(1, TokenType.BOOLEAN))
            return booleanFor();
        else if (compareType(1, TokenType.RANGE))
            return rangeFor();
        else if (compareType(1, TokenType.COLON))
            return foreach();

        if (match(TokenType.COMMA)) {
            statements[0] = new PassStatement();
        } else {
            statements[0] = statement();
            consume(TokenType.COMMA);
        }

        if (match(TokenType.COMMA))
            expression = new ValueExpression(true);
        else {
            expression = expression();
            consume(TokenType.COMMA);
        }

        if (match(TokenType.COMMA))
            statements[1] = new PassStatement();
        else
            statements[1] = statement();

        return new ForStatement(statements[0], expression, statements[1], rawBlockOrStatement());
    }

    private Statement foreach() {
        VariableExpression variableExpression = (VariableExpression) expression();
        consume(TokenType.COLON);
        return new ForEachStatement(variableExpression, expression(), rawBlockOrStatement());
    }

    private Statement rangeFor() {
        Expression expression = expression();
        consume(TokenType.RANGE);
        consume(TokenType.LEFT_PAREN);

        ArrayList<Expression> expressions = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            expressions.add(expression());
            if (!match(TokenType.COMMA))
                break;
        }

        consume(TokenType.RIGHT_PAREN);
        return new ForRangeStatement(expression, expressions, rawBlockOrStatement());
    }

    private Statement booleanFor() {
        Expression expression = expression();
        consume(TokenType.BOOLEAN);
        return new ForBooleanStatement(expression, rawBlockOrStatement());
    }

    private Statement ifElse() {
        LinkedHashMap<Expression, Statement> conditionals = new LinkedHashMap<>();

        conditionals.put(expression(), blockOrCultivatedStatement());

        while (match(TokenType.ELIF)) {
            conditionals.put(expression(), blockOrCultivatedStatement());
        }

        if (match(TokenType.ELSE)) {
            return new IfElseStatement(conditionals, blockOrCultivatedStatement());
        } else {
            return new IfStatement(conditionals);
        }
    }

    private Statement _return() {
        Expression expression;

        if (match(TokenType.SEMICOLON))
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

        return new DefineFunctionStatement(word, arguments, rawBlockOrStatement());
    }

    private BreakStatement _break() {
        String word = word();

        if (word == null)
            return new BreakStatement();
        else {
            return new BreakStatement(word);
        }
    }

    private ContinueStatement _continue() {
        String word = word();

        if (word == null)
            return new ContinueStatement();
        else
            return new ContinueStatement(word);
    }

    private Statement _switch() {
        Statement statement;
        ConstantExpression expression;
        LinkedHashMap<ConstantExpression, Statement> conditionals = new LinkedHashMap<>();
        boolean breakSwitch = match(TokenType.BREAK);
        Expression valueExpression = expression();

        while (match(TokenType.CASE)) {
            expression = new ConstantExpression(expression());

            if (compareType(TokenType.CASE))
                statement = new PassStatement();
            else
                statement = rawBlockOrStatement();

            conditionals.put(expression, statement);
        }

        if (match(TokenType.DEFAULT)) {
            if (breakSwitch)
                return new SwitchBreakStatement(valueExpression, conditionals, rawBlockOrStatement());
            return new SwitchStatement(valueExpression, conditionals, rawBlockOrStatement());
        } else {
            if (breakSwitch)
                return new SwitchBreakStatement(valueExpression, conditionals, new PassStatement());
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

    private FunctionCallExpression functionCallExpression() {
        String word = getCurrentToken().getValue();
        consume(TokenType.WORD, TokenType.LEFT_PAREN);
        ArrayList<Expression> expressions = new ArrayList<>();

        if (!match(TokenType.RIGHT_PAREN)) {
            do {
                expressions.add(expression());
            } while (match(TokenType.COMMA));
            consume(TokenType.RIGHT_PAREN);
        }
        return new FunctionCallExpression(word, expressions);
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
        final List<ConstantExpression> elements = new ArrayList<>();
        while (!match(TokenType.RIGHT_BRACKET)) {
            elements.add(new ConstantExpression(expressionForArray()));
            match(TokenType.COMMA);
        }

        return new ArrayExpression(elements);
    }

    private String label() {
        String label = null;

        if (compareType(TokenType.WORD) && compareType(1, TokenType.COLON)) {
            label = getCurrentToken().getValue();
            consume(TokenType.WORD, TokenType.COLON);
        }

        return label;
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