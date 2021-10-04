package parser.ast.expressions;

import lib.values.NullValue;
import lib.Value;
import parser.ast.Expression;
import parser.ast.Visitor;

public class NullValueExpression implements Expression {
    private final Value VALUE;

    public NullValueExpression() {
        VALUE = new NullValue();
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
