package parser.ast.expressions;

import lib.Value;
import lib.functions.Functions;
import parser.ast.Expression;
import parser.ast.Visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionCallExpression implements Expression {
    private final String name;
    public final List<Expression> expressionList;

    public FunctionCallExpression(String name, List<Expression> expressionsList) {
        this.name = name;
        this.expressionList = expressionsList;
    }

    @Override
    public Value eval() {
        List<Value> valueList = new ArrayList<>();
        for (Expression expression : expressionList)
            valueList.add(expression.eval());
        return Functions.get(name).execute(valueList);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("(%s(%s))", name, expressionList.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }
}
