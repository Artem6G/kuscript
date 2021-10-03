package parser.ast.statements;

import lib.variables.Variables;
import parser.ast.Expression;
import parser.ast.Statement;
import parser.ast.Visitor;

public class WhileStatement implements Statement {

    public final Expression expression;
    public final Statement statement;

    public WhileStatement (Expression expression, Statement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public void execute() {
        Variables.push();
         while (expression.eval().asBoolean())
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
        Variables.pop();
    }

    @Override
    public String toString() {
        return String.format("while %s %s", expression, statement);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
