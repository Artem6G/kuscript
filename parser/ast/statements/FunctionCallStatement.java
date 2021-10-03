package parser.ast.statements;

import parser.ast.Statement;
import parser.ast.Visitor;
import parser.ast.expressions.FunctionCallExpression;

public class FunctionCallStatement implements Statement {
    public final FunctionCallExpression functionCallExpression;

    public FunctionCallStatement(FunctionCallExpression functionCallExpression) {
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
