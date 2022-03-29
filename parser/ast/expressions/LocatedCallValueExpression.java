package parser.ast.expressions;

import lib.Value;
import lib.values.DataType;
import parser.ast.Expression;
import parser.ast.Visitor;

public class LocatedCallValueExpression implements Expression {
    public final Expression expression;
    public final String variable;

    public LocatedCallValueExpression(Expression expression, String variable) {
        this.expression = expression;
        this.variable = variable;
    }

    @Override
    public Value eval() {
        Value value = expression.eval();
        if (DataType.type(value) == DataType.CLASS)
            return value.asClass().aClass.variables.get(variable);
        else if (DataType.type(value) == DataType.MODULE)
            return value.asModule().module.variables.get(variable);
        else
            throw new RuntimeException("");
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
