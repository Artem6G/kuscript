package parser.ast.statements;

import parser.ast.Statement;
import parser.ast.Visitor;
import parser.ast.expressions.AssignmentOperatorExpression;

public class AssignmentOperatorStatement implements Statement {

    public final AssignmentOperatorExpression assignmentOperatorExpression;

    public AssignmentOperatorStatement(AssignmentOperatorExpression assignmentOperatorExpression) {
        this.assignmentOperatorExpression = assignmentOperatorExpression;
    }

    @Override
    public void execute() {
        assignmentOperatorExpression.eval();
    }

    @Override
    public String toString() {
        return assignmentOperatorExpression.toString();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}