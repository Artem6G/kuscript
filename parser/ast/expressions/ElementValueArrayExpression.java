package parser.ast.expressions;

import lib.Value;
import lib.values.ArrayValue;
import parser.ast.Expression;
import parser.ast.Visitor;

public class ElementValueArrayExpression implements Expression {
    public final Expression expression;
    public final Expression expressionIndex;

    public ElementValueArrayExpression(Expression expression, Expression expressionIndex) {
        this.expression = expression;
        this.expressionIndex = expressionIndex;
    }

    @Override
    public Value eval() {
        return ((ArrayValue) expression.eval()).get(expressionIndex.eval().asNumber().intValue());
    }

    @Override
    public String toString() {
        return String.format("(%s[%s])", expression, expressionIndex);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
