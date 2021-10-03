package parser.ast.expressions;

import lib.*;
import lib.values.*;
import parser.ast.Expression;
import parser.ast.Visitor;

import java.util.Objects;

public class BinaryExpression implements Expression {

    public final Expression EXPR1, EXPR2;
    private final OPERATORS OPERATION;

    public enum OPERATORS {
        ADD("+"),
        SUBTRACT("-"),
        MULTIPLY("*"),
        DIVIDE("/"),
        MOD("%"),

        POWER("**"),

        CONJUNCTION("&"),
        DISJUNCTION("|"),
        XOR("^"),
        IMPLICATION("->"),
        REVERSE_IMPLICATION("<-"),
        EQUIVALENCE("<>"),
        NOR("~|"),
        NAND("~&"),

        LEFT_SHIFT("<<"),
        RIGHT_SHIFT(">>"),
        RIGHT_UNSIGNED_SHIFT(">>>"),

        AND("&&"),
        OR("||"),

        MORE(">"),
        SMALLER("<"),
        STRICTLY_MORE(">="),
        STRICTLY_SMALLER("<="),
        SPACESHIP("<=>"),

        CORRESPONDENCE("=="),
        NOT_CORRESPONDENCE("!="),

        NULL_MERGER("??"),

        CONCATENATE(".");

        private final String NAME;

        OPERATORS(String name) {
            this.NAME = name;
        }

        @Override
        public String toString() {
            return NAME;
        }
    }

    public BinaryExpression(OPERATORS operation, Expression expr1, Expression expr2) {
        this.EXPR1 = expr1;
        this.EXPR2 = expr2;
        this.OPERATION = operation;
    }

    @Override
    public Value eval() {
        switch (OPERATION) {
            case SUBTRACT:
                return subtract(EXPR1.eval(), EXPR2.eval());
            case MULTIPLY:
                return multiply(EXPR1.eval(), EXPR2.eval());
            case DIVIDE:
                return divide(EXPR1.eval(), EXPR2.eval());
            case POWER:
                return power(EXPR1.eval(), EXPR2.eval());
            case MOD:
                return mod(EXPR1.eval(), EXPR2.eval());
            case CONCATENATE:
                return concatenate(EXPR1.eval(), EXPR2.eval());
            case CONJUNCTION:
                return conjunction(EXPR1.eval(), EXPR2.eval());
            case DISJUNCTION:
                return disjunction(EXPR1.eval(), EXPR2.eval());
            case XOR:
                return xor(EXPR1.eval(), EXPR2.eval());
            case LEFT_SHIFT:
                return left_shift(EXPR1.eval(), EXPR2.eval());
            case RIGHT_SHIFT:
                return right_shift(EXPR1.eval(), EXPR2.eval());
            case RIGHT_UNSIGNED_SHIFT:
                return right_unsigned_shift(EXPR1.eval(), EXPR2.eval());
            case IMPLICATION:
                return implication(EXPR1.eval(), EXPR2.eval());
            case REVERSE_IMPLICATION:
                return reverse_implication(EXPR1.eval(), EXPR2.eval());
            case NOR:
                return nor(EXPR1.eval(), EXPR2.eval());
            case NAND:
                return nand(EXPR1.eval(), EXPR2.eval());
            case CORRESPONDENCE:
                return correspondence(EXPR1.eval(), EXPR2.eval());
            case NOT_CORRESPONDENCE:
                return not_correspondence(EXPR1.eval(), EXPR2.eval());
            case AND:
                return and(EXPR1.eval(), EXPR2.eval());
            case OR:
                return or(EXPR1.eval(), EXPR2.eval());
            case MORE:
                return more(EXPR1.eval(), EXPR2.eval());
            case SMALLER:
                return smaller(EXPR1.eval(), EXPR2.eval());
            case STRICTLY_MORE:
                return strictly_more(EXPR1.eval(), EXPR2.eval());
            case STRICTLY_SMALLER:
                return strictly_smaller(EXPR1.eval(), EXPR2.eval());
            case SPACESHIP:
                return spaceship(EXPR1.eval(), EXPR2.eval());
            case EQUIVALENCE:
                return equivalence(EXPR1.eval(), EXPR2.eval());
            case ADD:
                return add(EXPR1.eval(), EXPR2.eval());
            default:
                throw new RuntimeException("");
        }
    }

    public static Value spaceship(Value value1, Value value2) {
        switch (DataType.type(value1)) {
            case INT:
                return new IntegerValue(Integer.compare(value1.asInteger(), value2.asInteger()));
            case DOUBLE:
                return new DoubleValue(Double.compare(value1.asDouble(), value2.asDouble()));
            default:
                throw new RuntimeException("");
        }
    }

    public static Value strictly_smaller(Value value1, Value value2) {
        switch (DataType.type(value1)) {
            case INT:
                return new BooleanValue(value1.asInteger() <= value2.asInteger());
            case DOUBLE:
                return new BooleanValue(value1.asDouble() <= value2.asDouble());
            default:
                throw new RuntimeException("");
        }
    }

    public static Value strictly_more(Value value1, Value value2) {
        switch (DataType.type(value1)) {
            case INT:
                return new BooleanValue(value1.asInteger() >= value2.asInteger());
            case DOUBLE:
                return new BooleanValue(value1.asDouble() >= value2.asDouble());
            default:
                throw new RuntimeException("");
        }
    }

    public static Value smaller(Value value1, Value value2) {
        switch (DataType.type(value1)) {
            case INT:
                return new BooleanValue(value1.asInteger() < value2.asInteger());
            case DOUBLE:
                return new BooleanValue(value1.asDouble() < value2.asDouble());
            default:
                throw new RuntimeException("");
        }
    }

    public static Value more(Value value1, Value value2) {
        switch (DataType.type(value1)) {
            case INT:
                return new BooleanValue(value1.asInteger() > value2.asInteger());
            case DOUBLE:
                return new BooleanValue(value1.asDouble() > value2.asDouble());
            default:
                throw new RuntimeException("");
        }
    }

    public static Value equivalence(Value value1, Value value2) {
        switch (DataType.type(value1)) {
            case INT:
                return new IntegerValue((~value1.asInteger() & ~value2.asInteger()) | (value1.asInteger() & value2.asInteger()));
            case BOOLEAN:
                return new BooleanValue((!value1.asBoolean() & !value2.asBoolean()) | (value1.asBoolean() & value2.asBoolean()));
            default:
                throw new RuntimeException("");
        }
    }

    private Value and(Value value1, Value value2) {
        if (DataType.type(value1) == DataType.BOOLEAN) {
            return new BooleanValue(value1.asBoolean() && value2.asBoolean());
        }
        throw new RuntimeException("");
    }

    private Value or(Value value1, Value value2) {
        if (DataType.type(value1) == DataType.BOOLEAN)
            return new BooleanValue(value1.asBoolean() || value2.asBoolean());
        else
            throw new RuntimeException("");
    }

    public static Value correspondence(Value value1, Value value2) {
        switch (DataType.type(value1)) {
            case INT:
                return new BooleanValue(value1.asInteger() == value2.asInteger());
            case BOOLEAN:
                return new BooleanValue(value1.asBoolean() == value2.asBoolean());
            case DOUBLE:
                return new BooleanValue(value1.asDouble() == value2.asDouble());
            case STRING:
                return new BooleanValue(value1.asString().equals(value2.asString()));
            case CHAR:
                return new BooleanValue(value1.asChar() == value2.asChar());
            case NULL:
                return new BooleanValue(Objects.equals(value1.asString(), value2.asString()));
            default:
                throw new RuntimeException("");
        }
    }

    private Value not_correspondence(Value value1, Value value2) {
        switch (DataType.type(value1)) {
            case INT:
                return new BooleanValue(value1.asInteger() != value2.asInteger());
            case BOOLEAN:
                return new BooleanValue(value1.asBoolean() != value2.asBoolean());
            case DOUBLE:
                return new BooleanValue(value1.asDouble() != value2.asDouble());
            case STRING:
                return new BooleanValue(!value1.asString().equals(value2.asString()));
            case CHAR:
                return new BooleanValue(value1.asChar() != value2.asChar());
            case NULL:
                return new BooleanValue(!Objects.equals(value1.asString(), value2.asString()));
            default:
                throw new RuntimeException("");
        }
    }

    public static Value reverse_implication(Value value1, Value value2) {
        switch (DataType.type(value1)) {
            case INT:
                return new IntegerValue(value1.asInteger() | ~value2.asInteger());
            case BOOLEAN:
                return new BooleanValue(value1.asBoolean() | !value2.asBoolean());
            default:
                throw new RuntimeException("");
        }
    }

    public static Value implication(Value value1, Value value2) {
        switch (DataType.type(value1)) {
            case INT:
                return new IntegerValue(~value1.asInteger() | value2.asInteger());
            case BOOLEAN:
                return new BooleanValue(!value1.asBoolean() | value2.asBoolean());
            default:
                throw new RuntimeException("");
        }
    }

    public static Value nor(Value value1, Value value2) {
        switch (DataType.type(value1)) {
            case INT:
                return new IntegerValue(~(value1.asInteger() | value2.asInteger()));
            case BOOLEAN:
                return new BooleanValue(!(value1.asBoolean() | value2.asBoolean()));
            default:
                throw new RuntimeException("");
        }
    }

    public static Value nand(Value value1, Value value2) {
        switch (DataType.type(value1)) {
            case INT:
                return new IntegerValue(~(value1.asInteger() & value2.asInteger()));
            case BOOLEAN:
                return new BooleanValue(!(value1.asBoolean() & value2.asBoolean()));
            default:
                throw new RuntimeException("");
        }
    }

    public static Value right_unsigned_shift(Value value1, Value value2) {
        if (DataType.type(value1) == DataType.INT)
            return new IntegerValue(value1.asInteger() >>> value2.asInteger());
        else
            throw new RuntimeException("");
    }

    public static Value right_shift(Value value1, Value value2) {
        if (DataType.type(value1) == DataType.INT)
            return new IntegerValue(value1.asInteger() >> value2.asInteger());
        else
            throw new RuntimeException("");
    }

    public static Value left_shift(Value value1, Value value2) {
        if (DataType.type(value1) == DataType.INT)
            return new IntegerValue(value1.asInteger() << value2.asInteger());
        else
            throw new RuntimeException("");
    }

    public static Value xor(Value value1, Value value2) {
        switch (DataType.type(value1)) {
            case INT:
                return new IntegerValue(value1.asInteger() ^ value2.asInteger());
            case BOOLEAN:
                return new BooleanValue(value1.asBoolean() ^ value2.asBoolean());
            default:
                throw new RuntimeException("");
        }
    }

    public static Value disjunction(Value value1, Value value2) {
        switch (DataType.type(value1)) {
            case INT:
                return new IntegerValue(value1.asInteger() | value2.asInteger());
            case BOOLEAN:
                return new BooleanValue(value1.asBoolean() | value2.asBoolean());
            default:
                throw new RuntimeException("");
        }
    }

    public static Value conjunction(Value value1, Value value2) {
        switch (DataType.type(value1)) {
            case INT:
                return new IntegerValue(value1.asInteger() & value2.asInteger());
            case BOOLEAN:
                return new BooleanValue(value1.asBoolean() & value2.asBoolean());
            default:
                throw new RuntimeException("");
        }
    }

    public static Value add(Value value1, Value value2) {
        switch (DataType.type(value1)) {
            case INT:
                return new IntegerValue(value1.asInteger() + value2.asInteger());
            case DOUBLE:
                return new DoubleValue(value1.asDouble() + value2.asDouble());
            default:
                throw new RuntimeException("");
        }
    }

    public static Value subtract(Value value1, Value value2) {
        switch (DataType.type(value1)) {
            case INT:
                return new IntegerValue(value1.asInteger() - value2.asInteger());
            case DOUBLE:
                return new DoubleValue(value1.asDouble() - value2.asDouble());
            default:
                throw new RuntimeException("");
        }
    }

    public static Value multiply(Value value1, Value value2) {
        switch (DataType.type(value1)) {
            case INT:
                return new IntegerValue(value1.asInteger() * value2.asInteger());
            case DOUBLE:
                return new DoubleValue(value1.asDouble() * value2.asDouble());
            default:
                throw new RuntimeException("");
        }
    }

    public static Value divide(Value value1, Value value2) {
        switch (DataType.type(value1)) {
            case INT:
                return new IntegerValue(value1.asInteger() / value2.asInteger());
            case DOUBLE:
                return new DoubleValue(value1.asDouble() / value2.asDouble());
            default:
                throw new RuntimeException("");
        }
    }

    public static Value mod(Value value1, Value value2) {
        switch (DataType.type(value1)) {
            case INT:
                return new IntegerValue(value1.asInteger() % value2.asInteger());
            case DOUBLE:
                return new DoubleValue(value1.asDouble() % value2.asDouble());
            default:
                throw new RuntimeException("");
        }
    }

    public static Value power(Value value1, Value value2) {
        switch (DataType.type(value1)) {
            case INT:
                return new IntegerValue((int) Math.pow(value1.asDouble(), value2.asDouble()));
            case DOUBLE:
                return new DoubleValue(Math.pow(value1.asDouble(), value2.asDouble()));
            default:
                throw new RuntimeException("");
        }
    }

    public static Value concatenate(Value value1, Value value2) {
        if (!(value1 instanceof NullValue) && !(value2 instanceof NullValue))
            return new StringValue(value1.asString() + value2.asString());
        else
            throw new RuntimeException("");
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", EXPR1, OPERATION, EXPR2);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}