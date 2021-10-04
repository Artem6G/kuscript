package parser.ast.expressions;

import lib.Value;
import lib.variables.Variables;
import parser.ast.Expression;
import parser.ast.Visitor;

public class VariableExpression implements Expression {

    public final String WORD;

    public VariableExpression(String world) {
        this.WORD = world;
    }

    @Override
    public Value eval() {
        return Variables.get(WORD);
    }

    @Override
    public String toString() {
        return String.format("(%s)", WORD);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
