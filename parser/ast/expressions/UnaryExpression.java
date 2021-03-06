package parser.ast.expressions;

import lib.*;
import lib.values.BooleanValue;
import lib.values.DataType;
import lib.values.DoubleValue;
import lib.values.IntegerValue;
import lib.variables.Variables;
import parser.ast.Expression;
import parser.ast.Visitor;

public class UnaryExpression implements Expression {

    public final Expression EXPR1;
    private Value value;
    public final OPERATORS OPERATION;

    public enum OPERATORS {
        PLUS("+"),
        MINUS("-"),
        LEFT_INCREMENT("++"),
        RIGHT_INCREMENT("++"),
        LEFT_DECREMENT("--"),
        RIGHT_DECREMENT("--"),
        NEGATION("~"),
        NO("!");

        private final String NAME;

        OPERATORS(String name) {
            this.NAME = name;
        }

        @Override
        public String toString() {
            return NAME;
        }
    }

    public UnaryExpression(OPERATORS operation, Expression expr1) {
        this.EXPR1 = expr1;
        this.OPERATION = operation;
    }

    @Override
    public Value eval() {
        value = EXPR1.eval();

        switch (OPERATION) {
            case NEGATION: return negation();
            case NO: return no();
            case MINUS: return minus();
            case RIGHT_INCREMENT: return right_increment();
            case RIGHT_DECREMENT: return right_decrement();
            case LEFT_INCREMENT: return left_increment();
            case LEFT_DECREMENT: return left_decrement();
            case PLUS:
                default:
                    return value;
        }
    }

    private Value right_increment() {
        if (!(EXPR1 instanceof VariableExpression))
            throw new RuntimeException("");

        switch (DataType.type(value)) {
            case INT:
                Variables.setVariable(((VariableExpression) EXPR1).WORD, new IntegerValue(value.asNumber().intValue() + 1));
                return value;
            case DOUBLE:
                Variables.setVariable(((VariableExpression) EXPR1).WORD, new DoubleValue(value.asNumber().doubleValue() + 1));
                return value;
            default:
                throw new RuntimeException("");
        }
    }

    private Value right_decrement() {
        if (!(EXPR1 instanceof VariableExpression))
            throw new RuntimeException("");

        switch (DataType.type(value)) {
            case INT:
                Variables.setVariable(((VariableExpression) EXPR1).WORD, new IntegerValue(value.asNumber().intValue() - 1));
                return value;
            case DOUBLE:
                Variables.setVariable(((VariableExpression) EXPR1).WORD, new DoubleValue(value.asNumber().doubleValue() - 1));
                return value;
            default:
                throw new RuntimeException("");
        }
    }

    private Value left_increment() {
        if (!(EXPR1 instanceof VariableExpression))
            throw new RuntimeException("");

        switch (DataType.type(value)) {
            case INT:
                IntegerValue integerValue = new IntegerValue(value.asNumber().intValue() + 1);
                Variables.setVariable(((VariableExpression) EXPR1).WORD, integerValue);
                return integerValue;
            case DOUBLE:
                DoubleValue doubleValue = new DoubleValue(value.asNumber().doubleValue() + 1);
                Variables.setVariable(((VariableExpression) EXPR1).WORD, doubleValue);
                return doubleValue;
            default:
                throw new RuntimeException("");
        }
    }

    private Value left_decrement() {
        if (!(EXPR1 instanceof VariableExpression))
            throw new RuntimeException("");

        switch (DataType.type(value)) {
            case INT:
                IntegerValue integerValue = new IntegerValue(value.asNumber().intValue() - 1);
                Variables.setVariable(((VariableExpression) EXPR1).WORD, integerValue);
                return integerValue;
            case DOUBLE:
                DoubleValue doubleValue = new DoubleValue(value.asNumber().doubleValue() - 1);
                Variables.setVariable(((VariableExpression) EXPR1).WORD, doubleValue);
                return doubleValue;
            default:
                throw new RuntimeException("");
        }
    }

    private Value no() {
        if (DataType.type(value) == DataType.BOOLEAN)
            return new BooleanValue(!value.asBoolean());
        else
            throw new RuntimeException("");
    }

    private Value negation() {
        switch (DataType.type(value)) {
            case INT:
                return new IntegerValue(~value.asNumber().intValue());
            case BOOLEAN:
                return new BooleanValue(!value.asBoolean());
            default:
                throw new RuntimeException("");
        }
    }

    public Value minus() {
        switch (DataType.type(value)) {
            case INT:
                return new IntegerValue(-value.asNumber().intValue());
            case DOUBLE:
                return new DoubleValue(-value.asNumber().doubleValue());
            default:
                throw new RuntimeException("");
        }
    }

    @Override
    public String toString() {
        if (OPERATION == OPERATORS.RIGHT_DECREMENT || OPERATION == OPERATORS.RIGHT_INCREMENT)
            return String.format("(%s%s)", EXPR1, OPERATION);
        return String.format("(%s%s)", OPERATION, EXPR1);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}