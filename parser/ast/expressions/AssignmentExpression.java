package parser.ast.expressions;

import lib.Value;
import lib.variables.Variables;
import parser.ast.Expression;
import parser.ast.Visitor;

public class AssignmentExpression implements Expression {
    public final String variable;
    public final Expression expression;

    public AssignmentExpression(String variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public Value eval() {
        final Value value = expression.eval();
        Variables.setVariable(variable, value);
        return value;
    }


    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("(%s = %s)", variable, expression.toString());
    }
}
