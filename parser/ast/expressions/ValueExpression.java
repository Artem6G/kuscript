package parser.ast.expressions;

import lib.*;
import lib.values.*;
import parser.ast.Expression;
import parser.ast.Visitor;

public class ValueExpression implements Expression {

    private final Value VALUE;

    public ValueExpression(double value) {
        this.VALUE = new DoubleValue(value);
    }

    public ValueExpression(int value) {
        this.VALUE = new IntegerValue(value);
    }

    public ValueExpression(String value) {
        this.VALUE = new StringValue(value);
    }

    public ValueExpression(char value) {
        this.VALUE = new CharValue(value);
    }

    public ValueExpression(boolean value) {
        this.VALUE = new BooleanValue(value);
    }

    public ValueExpression(Value value) {
        this.VALUE = value;
    }

    @Override
    public Value eval() {
        return VALUE;
    }

    @Override
    public String toString() {
        return String.format("(%s)", VALUE);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
