package parser.ast.expressions;

import lib.values.ArrayValue;
import lib.Value;
import parser.ast.Visitor;

import java.util.List;
import java.util.stream.Collectors;

public final class ArrayExpression implements parser.ast.Expression {

    public final List<ConstantExpression> expressions;

    public ArrayExpression(List<ConstantExpression> expressions) {
        this.expressions = expressions;
    }

    @Override
    public Value eval() {
        final int size = expressions.size();
        final ArrayValue array = new ArrayValue(size);

        for (int i = 0; i < size; i++) {
            Value value = expressions.get(i).eval();
            array.set(i, value);
        }

        return array;
    }

    @Override
    public String toString() {
        return String.format("{%s}", expressions.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
