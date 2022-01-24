package parser.ast.expressions;

import lib.Value;
import lib.values.BooleanValue;
import parser.ast.Expression;
import parser.ast.Visitor;

import java.util.ArrayList;

public class BinaryConditionalExpression implements Expression {
    public final ArrayList<Expression> expressions;
    private final ArrayList<BinaryExpression.OPERATORS> operators;

    public BinaryConditionalExpression(ArrayList<BinaryExpression.OPERATORS> operators, ArrayList<Expression> expressions) {
        this.operators = operators;
        this.expressions = expressions;
    }

    @Override
    public Value eval() {
        Value temp = null;
        for (int i = 0; i < operators.size(); i++) {
            if (i % 2 == 0) {
                temp = expressions.get(i + 1).eval();
                if (!compare(operators.get(i), expressions.get(i).eval(), temp))
                    return new BooleanValue(false);
            }
            else
                if (!compare(operators.get(i), temp, expressions.get(i + 1).eval()))
                    return new BooleanValue(false);
        }

        return new BooleanValue(true);
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
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < expressions.size(); i++)
            res.append(expressions.get(i)).append(i == expressions.size() - 1 ? "" : operators.get(i));
        return String.format("(%s)", res);
    }
}
