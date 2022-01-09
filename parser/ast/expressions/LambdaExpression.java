package parser.ast.expressions;

import lib.Value;
import lib.arguments.Arguments;
import lib.functions.DefineFunction;
import lib.values.FunctionValue;
import parser.ast.Expression;
import parser.ast.Visitor;
import parser.ast.statements.ReturnStatement;

public class LambdaExpression implements Expression {
    public final Arguments arguments;
    public final Expression expression;

    public LambdaExpression(Arguments arguments, Expression expression) {
        this.arguments = arguments;
        this.expression = expression;
    }

    @Override
    public Value eval() {
        return new FunctionValue(new DefineFunction(arguments, new ReturnStatement(expression)));
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("(lambda %s: %s)", arguments, expression);
    }
}
