package parser.ast.expressions;

import lib.Value;
import lib.variables.Variables;
import parser.ast.Expression;
import parser.ast.Visitor;
import parser.ast.visitors.AbstractVisitor;

public class ConstantExpression implements Expression {
    public Expression expression;

    public ConstantExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Value eval() {
        accept(new ConstVisitor());
        return expression.eval();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("(%s)", expression.toString());
    }

    private static class ConstVisitor extends AbstractVisitor {
        public void visit(ConstantExpression constantExpression) {
            constantExpression.expression.accept(this);
        }

        public void visit(VariableExpression variableExpression) {
            if (!Variables.isConstVariable(variableExpression.WORD))
                throw new RuntimeException("");
        }
    }
}
