package parser.ast.statements;

import parser.ast.Statement;
import parser.ast.Visitor;
import parser.ast.expressions.ElementArrayExpression;
import parser.ast.Expression;

public class ElementAssignmentArrayStatement implements Statement {

    public final ElementArrayExpression elementArrayExpression;
    public final Expression expression;

    public ElementAssignmentArrayStatement(ElementArrayExpression elementArrayExpression, Expression expression) {
        this.elementArrayExpression = elementArrayExpression;
        this.expression = expression;
    }

    @Override
    public void execute() {
        elementArrayExpression.getArray().set(elementArrayExpression.lastIndex(), expression.eval());
    }

    @Override
    public String toString() {
        return String.format("%s = %s", elementArrayExpression, expression);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}