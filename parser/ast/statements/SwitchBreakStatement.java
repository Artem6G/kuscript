package parser.ast.statements;

import lib.Value;
import lib.variables.Variables;
import parser.ast.Statement;
import parser.ast.Visitor;
import parser.ast.expressions.BinaryExpression;
import parser.ast.expressions.ConstantExpression;

import java.util.LinkedHashMap;

public class SwitchBreakStatement extends SwitchStatement implements Statement {

    public SwitchBreakStatement(parser.ast.Expression valueExpression, LinkedHashMap<ConstantExpression, Statement> conditions, Statement defaultStatement) {
        super(valueExpression, conditions, defaultStatement);
    }

    @Override
    public void execute() {
        Value value = valueExpression.eval();

        for (int i = 0; i < size; i++) {
            if (BinaryExpression.correspondence(expressions.get(i).eval(), value).asBoolean()) {
                Variables.push();
                try {
                    statements.get(i).execute();
                } catch (BreakStatement breakStatement) {
                    return;
                } finally {
                    Variables.pop();
                }
                return;
            }
        }

        Variables.push();
        defaultStatement.execute();
        Variables.pop();

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