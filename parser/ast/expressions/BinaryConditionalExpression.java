package parser.ast.expressions;

import lib.Value;
import lib.values.BooleanValue;
import parser.ast.Expression;
import parser.ast.Visitor;

public class BinaryConditionalExpression implements Expression {
    public final Expression EXPR1, EXPR2, EXPR3;
    private final BinaryExpression.OPERATORS OPERATION1;
    private final BinaryExpression.OPERATORS OPERATION2;

    public BinaryConditionalExpression(BinaryExpression.OPERATORS operation1, BinaryExpression.OPERATORS operation2, Expression expr1, Expression expr2, Expression expr3) {
        this.EXPR1 = expr1;
        this.EXPR2 = expr2;
        this.EXPR3 = expr3;
        this.OPERATION1 = operation1;
        this.OPERATION2 = operation2;
    }

    @Override
    public Value eval() {
        Value secondValue = EXPR2.eval();

        if (!compare(OPERATION1, EXPR1.eval(), secondValue))
            return new BooleanValue(false);

        return new BooleanValue(compare(OPERATION2, secondValue, EXPR3.eval()));
    }

    private boolean compare(BinaryExpression.OPERATORS operator, Value value1, Value value2) {
        return new BinaryExpression(operator, new ValueExpression(value1), new ValueExpression(value2)).eval().asBoolean();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s", EXPR1, OPERATION1, EXPR2, OPERATION2, EXPR3);
    }
}
