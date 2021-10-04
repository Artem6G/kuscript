package parser.ast.expressions;

import lib.Value;
import parser.ast.Expression;
import parser.ast.Visitor;
import parser.ast.statements.AssignmentStatement;

public class AssignmentExpression implements Expression {
    public AssignmentStatement assignmentStatement;

    public AssignmentExpression(AssignmentStatement assignmentStatement) {
        this.assignmentStatement = assignmentStatement;
    }

    @Override
    public Value eval() {
        assignmentStatement.execute();
        return assignmentStatement.value;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return assignmentStatement.toString();
    }
}
