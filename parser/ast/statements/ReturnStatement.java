package parser.ast.statements;

import lib.Value;
import parser.ast.Expression;
import parser.ast.Statement;
import parser.ast.Visitor;

public class ReturnStatement extends RuntimeException implements Statement {
    public final Expression expression;
    public Value value;

    public ReturnStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        value = expression.eval();
        throw this;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "return " + expression;
    }
}
