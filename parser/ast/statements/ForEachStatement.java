package parser.ast.statements;

import lib.Value;
import lib.variables.Variables;
import parser.ast.Expression;
import parser.ast.Statement;
import parser.ast.Visitor;
import parser.ast.expressions.VariableExpression;

public class ForEachStatement implements Statement {

    public final VariableExpression variableExpression;
    public final Expression expression;
    public final Statement statement;
    public final Statement elseStatement;

    public ForEachStatement (VariableExpression variableExpression, Expression expression, Statement statement, Statement elseStatement) {
        this.variableExpression = variableExpression;
        this.expression = expression;
        this.statement = statement;
        this.elseStatement = elseStatement;
    }

    @Override
    public void execute() {
        String word = variableExpression.WORD;

        for (Value value : expression.eval().asArray().values)
            try {
                Variables.setVariable(word, value);
                statement.execute();
            } catch (BreakStatement breakStatement) {
                return;
            } catch (ContinueStatement ignored) {}

        elseStatement.execute();
    }

    @Override
    public String toString() {
        return String.format("for %s : %s %s", variableExpression, expression, statement);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}