package parser.ast.statements;

import lib.values.BooleanValue;
import lib.variables.Variables;
import parser.ast.Expression;
import parser.ast.Statement;
import parser.ast.Visitor;
import parser.ast.expressions.VariableExpression;

public class ForBooleanStatement implements Statement {

    public final Expression expression;
    public final Statement statement;

    public ForBooleanStatement (Expression expression, Statement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public void execute() {
        if (!(expression instanceof VariableExpression))
            throw new RuntimeException();

        String word = ((VariableExpression) expression).WORD;

        Variables.push();
        for (byte i = 0; i < 2; i++)
            try {
                Variables.setVariable(word, new BooleanValue(i != 0));
                statement.execute();
            } catch (BreakStatement breakStatement) {
                if (breakStatement.getMessage() == null)
                    break;
                else
                    throw breakStatement;
            } catch (ContinueStatement continueStatement) {
                if (continueStatement.getMessage() != null)
                    throw continueStatement;
            }
        Variables.pop();
    }

    @Override
    public String toString() {
        return String.format("for %s boolean %s", expression, statement);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
