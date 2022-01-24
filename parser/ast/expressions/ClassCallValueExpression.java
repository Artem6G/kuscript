package parser.ast.expressions;

import lib.Value;
import parser.ast.Expression;
import parser.ast.Visitor;

public class ClassCallValueExpression implements Expression {
    public final Expression expression;
    public final String variable;

    public ClassCallValueExpression(Expression expression, String variable) {
        this.expression = expression;
        this.variable = variable;
    }

    @Override
    public Value eval() {
        return expression.eval().asClass().aClass.variables.get(variable);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("(%s.%s)", expression, variable);
    }
}
