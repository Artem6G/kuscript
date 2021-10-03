package parser.ast.expressions;

import lib.Value;
import parser.ast.Expression;
import parser.ast.Visitor;

public class TernaryExpression implements Expression {
    public final Expression condition;
    public final Expression trueExpr, falseExpr;

    public TernaryExpression(Expression condition, Expression trueExpr, Expression falseExpr) {
        this.condition = condition;
        this.trueExpr = trueExpr;
        this.falseExpr = falseExpr;
    }

    @Override
    public Value eval() {
        return condition.eval().asBoolean() ? trueExpr.eval() : falseExpr.eval();
    }

    @Override
    public String toString() {
        return String.format("%s ? %s : %s", condition, trueExpr, falseExpr);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}