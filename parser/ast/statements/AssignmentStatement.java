package parser.ast.statements;

import lib.Value;
import lib.variables.Variables;
import parser.ast.Expression;
import parser.ast.Statement;
import parser.ast.Visitor;

public class AssignmentStatement implements Statement {

    public final String variable;
    public final Expression expression;
    public Value value;

    public AssignmentStatement(String variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public void execute() {
        value = expression.eval();
        Variables.setVariable(variable, value);
    }

    @Override
    public String toString() {
        return String.format("%s = %s", variable, expression);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}