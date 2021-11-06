package parser.ast.expressions;

import lib.values.ArrayValue;
import lib.values.IntegerValue;
import lib.Value;
import lib.variables.Variables;
import parser.ast.Expression;
import parser.ast.Visitor;

import java.util.List;
import java.util.stream.Collectors;

public class ElementArrayExpression implements Expression {

    private final String variable;
    public final List<Expression> expressionIndexes;

    public ElementArrayExpression(String variable, List<Expression> expressionIndexes) {
        this.variable = variable;
        this.expressionIndexes = expressionIndexes;
    }

    @Override
    public Value eval() {
        return getArray().get(lastIndex());
    }

    public ArrayValue getArray() {
        ArrayValue array = consumeArray(Variables.get(variable));
        final int last = expressionIndexes.size() - 1;
        for (int i = 0; i < last; i++)
            array = consumeArray(array.get(index(i)));
        return array;
    }

    public int lastIndex() {
        return index(expressionIndexes.size() - 1);
    }

    private int index(int index) {
        final Value ind = expressionIndexes.get(index).eval();

        if (!(ind instanceof IntegerValue))
            throw new RuntimeException("");

        return ind.asNumber().intValue();
    }

    private ArrayValue consumeArray(Value value) {
        if (value instanceof ArrayValue)
            return (ArrayValue) value;
        else
            throw new RuntimeException("");
    }

    @Override
    public String toString() {
        return String.format("(%s[%s])", variable, expressionIndexes.stream().map(Object::toString).collect(Collectors.joining("][")));
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}