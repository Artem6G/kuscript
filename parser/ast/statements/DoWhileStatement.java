package parser.ast.statements;

import parser.ast.Expression;
import parser.ast.Statement;
import parser.ast.Visitor;

public class DoWhileStatement extends WhileStatement implements Statement {
    public DoWhileStatement (Expression expression, Statement statement, Statement elseStatement) {
        super(expression, statement, elseStatement);
    }

    @Override
    public void execute() {
        do {
            try {
                statement.execute();
            } catch (BreakStatement breakStatement) {
                return;
            } catch (ContinueStatement ignored) {}
        } while (expression.eval().asBoolean());

        elseStatement.execute();
    }

    @Override
    public String toString() {
        return String.format("while %s %s else %s", statement, expression, elseStatement);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
