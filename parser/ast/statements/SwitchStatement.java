package parser.ast.statements;

import lib.Value;
import parser.ast.Expression;
import parser.ast.Statement;
import parser.ast.Visitor;
import parser.ast.expressions.BinaryExpression;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SwitchStatement implements Statement {

    public final parser.ast.Expression valueExpression;
    public final ArrayList<Expression> expressions;
    public final ArrayList<Statement> statements;
    public final Statement defaultStatement;
    protected final int size;

    public SwitchStatement(parser.ast.Expression valueExpression, LinkedHashMap<Expression, Statement> conditions, Statement defaultStatement) {
        size = conditions.size();
        this.valueExpression = valueExpression;
        this.expressions = new ArrayList<>(conditions.keySet());
        this.statements =  new ArrayList<>(conditions.values());
        this.defaultStatement = defaultStatement;
    }

    @Override
    public void execute() {
        Value value = valueExpression.eval();

        boolean bool = false;

        for (int i = 0; i < size; i++) {
            if (!bool)
                bool = BinaryExpression.correspondence(expressions.get(i).eval(), value).asBoolean();

                if (bool) {
                    try {
                        statements.get(i).execute();
                    } catch (BreakStatement breakStatement) {
                        return;
                    }
                }
        }

        defaultStatement.execute();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("switch %s", valueExpression));

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