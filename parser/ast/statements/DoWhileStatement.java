package parser.ast.statements;

import parser.ast.Expression;
import parser.ast.Statement;
import parser.ast.Visitor;

public class DoWhileStatement implements Statement {

    public final Statement statement;
    public final Expression expression;

    public DoWhileStatement (Statement statement, Expression expression) {
        this.statement = statement;
        this.expression = expression;
    }

    @Override
    public void execute() {
        do {
            try {
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
        } while (expression.eval().asBoolean());
    }

    @Override
    public String toString() {
        return String.format("while %s %s", statement, expression);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
