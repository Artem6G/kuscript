package parser.ast.statements;

import parser.ast.Statement;
import parser.ast.Visitor;
import parser.ast.expressions.AssignmentExpression;

public class AssignmentStatement implements Statement {

    public final AssignmentExpression assignmentExpression;

    public AssignmentStatement(AssignmentExpression assignmentExpression) {
        this.assignmentExpression = assignmentExpression;
    }

    @Override
    public void execute() {
        assignmentExpression.eval();
    }

    @Override
    public String toString() {
        return assignmentExpression.toString();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}