package parser.ast.statements;

import lib.values.BooleanValue;
import lib.variables.Variables;
import parser.ast.Expression;
import parser.ast.Statement;
import parser.ast.Visitor;
import parser.ast.expressions.VariableExpression;

import java.util.List;

public class ForBooleanStatement implements Statement {

    public final List<Expression> expressions;
    public final Statement statement;
    private int size;

    public ForBooleanStatement (List<Expression> expressions, Statement statement) {
        this.expressions = expressions;
        this.statement = statement;
    }

    @Override
    public void execute() {
        size = expressions.size();
        setVariables(size);
    }

    private void setVariables(int n) {
        for (byte i = 0; i < 2; i++)
            try {
                Variables.setVariable(((VariableExpression) expressions.get(size - n)).WORD, new BooleanValue(i != 0));

                if (n != 1) {
                    setVariables(n - 1);
                } else {
                    statement.execute();
                }

            } catch (BreakStatement breakStatement) {
                if (breakStatement.getMessage() == null)
                    break;
                else
                    throw breakStatement;
            } catch (ContinueStatement continueStatement) {
                if (continueStatement.getMessage() != null)
                    throw continueStatement;
            }
    }

    @Override
    public String toString() {
        return String.format("for boolean %s %s", expressions, statement);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
