package parser.ast.statements;

import lib.Value;
import lib.values.ArrayValue;
import lib.values.DataType;
import lib.variables.Variables;
import parser.ast.Expression;
import parser.ast.Statement;
import parser.ast.Visitor;
import parser.ast.expressions.VariableExpression;

public class FullBypassForEachStatement extends ForEachStatement implements Statement {

    public FullBypassForEachStatement(VariableExpression variableExpression, Expression expression, Statement statement) {
        super(variableExpression, expression, statement);
    }

    @Override
    public void execute() {
        round(expression.eval().asArray().values);
    }

    public void round(Value[] values) {
        for (Value value : values)
            try {
                if (DataType.type(value) == DataType.ARRAY) {
                    round(((ArrayValue) value).values);
                    continue;
                }
                Variables.setVariable(variableExpression.WORD, value);
                statement.execute();
            } catch (BreakStatement breakStatement) {
                if (breakStatement.getMessage() != null)
                    throw breakStatement;
            } catch (ContinueStatement continueStatement) {
                if (continueStatement.getMessage() != null)
                    throw continueStatement;
            }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("for %s *: %s %s", variableExpression, expression, statement);
    }
}
