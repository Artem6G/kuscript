package parser.ast.statements;

import parser.ast.Expression;
import parser.ast.Statement;
import parser.ast.Visitor;

public class ForStatement implements Statement {

    public final Expression expression;
    public final Statement firstStatement;
    public final Statement secondStatement;
    public final Statement thirdStatement;

    public ForStatement (Statement firstStatement, Expression expression, Statement secondStatement, Statement thirdStatement) {
        this.expression = expression;
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
        this.thirdStatement = thirdStatement;
    }

    @Override
    public void execute() {
        for (firstStatement.execute(); expression.eval().asBoolean(); secondStatement.execute())
            try {
                thirdStatement.execute();
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
        return String.format("for %s, %s, %s %s", firstStatement, expression, secondStatement, thirdStatement);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}