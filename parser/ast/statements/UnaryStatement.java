package parser.ast.statements;

import parser.ast.Statement;
import parser.ast.Visitor;
import parser.ast.expressions.UnaryExpression;

public class UnaryStatement implements Statement {

    public final UnaryExpression unaryExpression;

    public UnaryStatement (UnaryExpression unaryExpression) {
        this.unaryExpression = unaryExpression;
    }

    @Override
    public void execute() {
        unaryExpression.eval();
    }

    @Override
    public String toString() {
        return unaryExpression.toString();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
