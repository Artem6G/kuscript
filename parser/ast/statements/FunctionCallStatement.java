package parser.ast.statements;

import parser.ast.Statement;
import parser.ast.Visitor;
import parser.ast.expressions.FunctionCallValueExpression;

public class FunctionCallStatement implements Statement {
    public final FunctionCallValueExpression functionCallExpression;

    public FunctionCallStatement(FunctionCallValueExpression functionCallExpression) {
        this.functionCallExpression = functionCallExpression;
    }

    @Override
    public void execute() {
        functionCallExpression.eval();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("%s", functionCallExpression);
    }
}
