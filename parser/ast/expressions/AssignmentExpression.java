package parser.ast.expressions;

import lib.Value;
import lib.variables.Variables;
import parser.ast.Expression;
import parser.ast.Visitor;

public class AssignmentExpression implements Expression {
    public final String variable;
    public final Expression expression;
    public final boolean isConst;

    public AssignmentExpression(String variable, Expression expression, boolean isConst) {
        this.variable = variable;
        this.expression = expression;
        this.isConst = isConst;
    }

    @Override
    public Value eval() {
        final Value value = expression.eval();
        Variables.setVariable(variable, value, isConst);
        return value;
    }


    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return isConst ? String.format("(const %s = %s)", variable, expression.toString()) : String.format("(%s = %s)", variable, expression.toString());
    }
}
