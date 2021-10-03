package parser.ast.statements;

import lib.variables.Variables;
import parser.ast.Expression;
import parser.ast.Statement;
import parser.ast.Visitor;

public class ConstAssignmentStatement extends AssignmentStatement implements Statement {
    public ConstAssignmentStatement(String variable, Expression expression) {
        super(variable, expression);
    }

    @Override
    public void execute() {
        Variables.setConst(variable, expression.eval());
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "const " + super.toString();
    }
}
