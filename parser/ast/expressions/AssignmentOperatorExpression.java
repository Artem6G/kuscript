package parser.ast.expressions;

import lib.Value;
import lib.variables.Variables;
import parser.ast.Expression;
import parser.ast.Visitor;

public class AssignmentOperatorExpression implements Expression {

    public enum ASSIGNMENT_OPERATORS {
        PLUS_EQUALS("+="),
        MINUS_EQUALS("-="),
        MULTIPLY_EQUALS("*="),
        DIVIDE_EQUALS("/="),
        POWER_EQUALS("**="),
        MOD_EQUALS("%="),
        SPACESHIP_EQUALS("<=>="),
        CONCATENATE_EQUALS(".="),
        LEFT_SHIFT_EQUALS("<<="),
        RIGHT_SHIFT_EQUALS(">>="),
        RIGHT_UNSIGNED_SHIFT_EQUALS(">>>="),
        XOR_EQUALS("^="),
        NOR_EQUALS("~|="),
        NAND_EQUALS("~&="),
        CONJUNCTION_EQUALS("&="),
        DISJUNCTION_EQUALS("|="),
        IMPLICATION_EQUALS("->="),
        REVERSE_IMPLICATION_EQUALS("<-="),
        EQUIVALENCE_EQUALS("<>="),
        NULL_MERGER_EQUALS("??=");

        private final String NAME;

        ASSIGNMENT_OPERATORS(String name) {
            this.NAME = name;
        }

        public static ASSIGNMENT_OPERATORS getType(String str) {
            switch(str) {
                case "+=": return PLUS_EQUALS;
                case "-=": return MINUS_EQUALS;
                case "*=": return MULTIPLY_EQUALS;
                case "/=": return DIVIDE_EQUALS;
                case "**=": return POWER_EQUALS;
                case "%=": return MOD_EQUALS;
                case "<=>=": return SPACESHIP_EQUALS;
                case ".=": return CONCATENATE_EQUALS;
                case "<<=": return LEFT_SHIFT_EQUALS;
                case ">>=": return RIGHT_SHIFT_EQUALS;
                case ">>>=": return RIGHT_UNSIGNED_SHIFT_EQUALS;
                case "^=": return XOR_EQUALS;
                case "~|=": return NOR_EQUALS;
                case "~&=": return NAND_EQUALS;
                case "&=": return CONJUNCTION_EQUALS;
                case "|=": return DISJUNCTION_EQUALS;
                case "->=": return IMPLICATION_EQUALS;
                case "<-=": return REVERSE_IMPLICATION_EQUALS;
                case "<>=": return EQUIVALENCE_EQUALS;
            }
            throw new RuntimeException("");
        }

        @Override
        public String toString() {
            return NAME;
        }
    }

    public final String VARIABLE;
    public final Expression EXPRESSION;
    private final ASSIGNMENT_OPERATORS ASSIGNMENT_OPERATOR;

    public AssignmentOperatorExpression(String variable, Expression expression, ASSIGNMENT_OPERATORS assignment_operator) {
        this.VARIABLE = variable;
        this.EXPRESSION = expression;
        this.ASSIGNMENT_OPERATOR = assignment_operator;
    }

    @Override
    public Value eval() {
        final Value value = EXPRESSION.eval();
        final Value result = receive(ASSIGNMENT_OPERATOR, VARIABLE, value);
        Variables.setVariable(VARIABLE, result);
        return result;
    }

    public static Value receive(ASSIGNMENT_OPERATORS assignment_operator, String variable, Value value) {
        final Value firstValue = Variables.get(variable);
        switch (assignment_operator) {
            case PLUS_EQUALS:
                return BinaryExpression.add(firstValue, value);
            case MINUS_EQUALS:
                return BinaryExpression.subtract(firstValue, value);
            case MULTIPLY_EQUALS:
                return BinaryExpression.multiply(firstValue, value);
            case DIVIDE_EQUALS:
                return BinaryExpression.divide(firstValue, value);
            case MOD_EQUALS:
                return BinaryExpression.mod(firstValue, value);
            case POWER_EQUALS:
                return BinaryExpression.power(firstValue, value);
            case SPACESHIP_EQUALS:
                return BinaryExpression.spaceship(firstValue, value);
            case LEFT_SHIFT_EQUALS:
                return BinaryExpression.left_shift(firstValue, value);
            case RIGHT_SHIFT_EQUALS:
                return BinaryExpression.right_shift(firstValue, value);
            case RIGHT_UNSIGNED_SHIFT_EQUALS:
                return BinaryExpression.right_unsigned_shift(firstValue, value);
            case XOR_EQUALS:
                return BinaryExpression.xor(firstValue, value);
            case NOR_EQUALS:
                return BinaryExpression.nor(firstValue, value);
            case NAND_EQUALS:
                return BinaryExpression.nand(firstValue, value);
            case CONJUNCTION_EQUALS:
                return BinaryExpression.conjunction(firstValue, value);
            case DISJUNCTION_EQUALS:
                return BinaryExpression.disjunction(firstValue, value);
            case IMPLICATION_EQUALS:
                return BinaryExpression.implication(firstValue, value);
            case REVERSE_IMPLICATION_EQUALS:
                return BinaryExpression.reverse_implication(firstValue, value);
            case EQUIVALENCE_EQUALS:
                return BinaryExpression.equivalence(firstValue, value);
            default:
                throw new RuntimeException("");
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("(%s %s %s)", VARIABLE, ASSIGNMENT_OPERATOR, EXPRESSION);
    }
}