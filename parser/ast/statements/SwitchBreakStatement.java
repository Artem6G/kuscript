package parser.ast.statements;

import lib.Value;
import parser.ast.Expression;
import parser.ast.Statement;
import parser.ast.Visitor;
import parser.ast.expressions.BinaryExpression;

import java.util.LinkedHashMap;

public class SwitchBreakStatement extends SwitchStatement implements Statement {

    public SwitchBreakStatement(parser.ast.Expression valueExpression, LinkedHashMap<Expression, Statement> conditions, Statement defaultStatement) {
        super(valueExpression, conditions, defaultStatement);
    }

    @Override
    public void execute() {
        Value value = valueExpression.eval();

        for (int i = 0; i < size; i++) {
            if (BinaryExpression.correspondence(expressions.get(i).eval(), value).asBoolean()) {
                try {
                    statements.get(i).execute();
                } catch (BreakStatement breakStatement) {
                    return;
                }
                return;
            }
        }

        defaultStatement.execute();

    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("switch break %s", valueExpression));

        for (int i = 0; i < size; i++)
            stringBuilder.append(String.format("\ncase %s %s", expressions.get(i), statements.get(i)));

        if (defaultStatement != null)
            stringBuilder.append(String.format("\ndefault %s", defaultStatement));

        return stringBuilder.toString();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}