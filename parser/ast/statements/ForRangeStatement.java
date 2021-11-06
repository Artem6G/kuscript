package parser.ast.statements;

import lib.values.IntegerValue;
import lib.variables.Variables;
import parser.ast.Expression;
import parser.ast.Statement;
import parser.ast.Visitor;
import parser.ast.expressions.VariableExpression;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ForRangeStatement implements Statement {

    public final Expression expression;
    public final ArrayList<Expression> expressions;
    public final Statement statement;
    public final Statement elseStatement;

    public ForRangeStatement (Expression expression, ArrayList<Expression> expressions, Statement statement, Statement elseStatement) {
        this.expression = expression;
        this.expressions = expressions;
        this.statement = statement;
        this.elseStatement = elseStatement;
    }

    @Override
    public void execute() {
        if (!(expression instanceof VariableExpression))
            throw new RuntimeException();

        String word = ((VariableExpression) expression).WORD;

        int start, stop, step;
        start = 0;
        step = 1;

        if (expressions.size() == 1)
            stop = expressions.get(0).eval().asNumber().intValue();
        else if (expressions.size() == 2) {
            start = expressions.get(0).eval().asNumber().intValue();
            stop = expressions.get(1).eval().asNumber().intValue();
            if (stop < start) {
                int temp = start;
                start = stop;
                stop = temp;
            }
        } else if (expressions.size() == 3) {
            start = expressions.get(0).eval().asNumber().intValue();
            stop = expressions.get(1).eval().asNumber().intValue();
            step = expressions.get(2).eval().asNumber().intValue();
        } else {
            throw new RuntimeException("");
        }

        for (int i = start; i < stop; i += step)
            try {
                Variables.setVariable(word, new IntegerValue(i));
                statement.execute();
            } catch (BreakStatement breakStatement) {
                return;
            } catch (ContinueStatement ignored) {}

        elseStatement.execute();
    }

    @Override
    public String toString() {
        return String.format("for %s : range(%s) %s", expression, expressions.stream().map(Object::toString).collect(Collectors.joining(", ")), statement);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}