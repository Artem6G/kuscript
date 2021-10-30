package parser.ast.statements;

import parser.ast.Expression;
import parser.ast.Statement;
import parser.ast.Visitor;

public class WhileStatement implements Statement {

    public final Statement elseStatement;
    public final Expression expression;
    public final Statement statement;

    public WhileStatement (Expression expression, Statement statement, Statement elseStatement) {
        this.expression = expression;
        this.statement = statement;
        this.elseStatement = elseStatement;
    }

    @Override
    public void execute() {
         while (expression.eval().asBoolean())
             try {
                 statement.execute();
             } catch (BreakStatement breakStatement) {
                     return;
             } catch (ContinueStatement ignored) {}

         elseStatement.execute();
    }

    @Override
    public String toString() {
        return String.format("while %s %s else %s", expression, statement, elseStatement);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
