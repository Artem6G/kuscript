package parser.ast.statements;

import parser.ast.Expression;
import parser.ast.Statement;
import parser.ast.Visitor;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class IfElseStatement implements Statement {

    public final ArrayList<Expression> expressions;
    public final ArrayList<Statement> statements;
    public final Statement elseStatement;
    private final int size;

    public IfElseStatement(LinkedHashMap<Expression, Statement> conditions, Statement elseStatement) {
        size = conditions.size();
        this.expressions = new ArrayList<>(conditions.keySet());
        this.statements =  new ArrayList<>(conditions.values());
        this.elseStatement = elseStatement;
    }

    @Override
    public void execute() {
        for (int i = 0; i < size; i++) {
            if (expressions.get(i).eval().asBoolean()) {
                statements.get(i).execute();
                return;
            }
        }

        elseStatement.execute();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("if %s %s", expressions.get(0), statements.get(0)));

        for (int i = 1; i < size; i++)
            stringBuilder.append(String.format("\nelif %s %s", expressions.get(i), statements.get(i)));

        stringBuilder.append(String.format("\nelse %s", elseStatement));

        return stringBuilder.toString();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
