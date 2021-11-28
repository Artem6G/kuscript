package parser.ast.expressions;

import lib.Value;
import lib.functions.DefineExternalFunction;
import lib.functions.DefineFunction;
import lib.functions.DefineLocalFunction;
import lib.values.FunctionValue;
import parser.ast.Expression;
import parser.ast.Visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionCallValueExpression implements Expression {
    public final Expression variable;
    public final List<Expression> expressionList;

    public FunctionCallValueExpression(Expression variable, List<Expression> expressionsList) {
        this.variable = variable;
        this.expressionList = expressionsList;
    }

    @Override
    public Value eval() {
        List<Value> valueList = new ArrayList<>();
        for (Expression expression : expressionList)
            valueList.add(expression.eval());

        FunctionValue functionValue = (FunctionValue) variable.eval();
        if (functionValue.function instanceof DefineExternalFunction)
            return (functionValue).function.execute(valueList);
        else {
            DefineFunction.isExternal = false;
            try {
                return (functionValue).function.execute(valueList);
            } finally {
                DefineFunction.isExternal = true;
            }
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("(%s(%s))", variable, expressionList.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }
}
