package parser.ast.statements;

import lib.Value;
import lib.variables.Variables;
import parser.ast.Expression;
import parser.ast.Statement;
import parser.ast.Visitor;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MultiplyAssignmentStatement  implements Statement {

    public final ArrayList<String> variables;
    public final ArrayList<Expression> expressions;
    private final AssignmentOperatorStatement.ASSIGNMENT_OPERATORS assignment_operator;
    private final int expressionsSize;

    public MultiplyAssignmentStatement(ArrayList<String> variables, ArrayList<Expression> expressions, AssignmentOperatorStatement.ASSIGNMENT_OPERATORS assignment_operator) {
        this.assignment_operator = assignment_operator;
        this.expressionsSize = expressions.size();
        this.variables = variables;
        this.expressions = expressions;
    }

    @Override
    public void execute() {
        if (expressionsSize != variables.size())
            throw new RuntimeException("");

        ArrayList<Value> values = new ArrayList<>();

        for(int i = 0; i < expressionsSize; i++) {
            values.add(expressions.get(i).eval());
        }

        if (assignment_operator == null)
            for (int i = 0; i < expressionsSize; i++)
                Variables.setVariable(variables.get(i), values.get(i));
        else
            for (int i = 0; i < expressionsSize; i++) {
                final String variable = variables.get(i);
                Variables.setVariable(variable, AssignmentOperatorStatement.receive(assignment_operator, variable, values.get(i)));
            }
    }

    @Override
    public String toString() {
        if (assignment_operator == null)
            return String.format("%s = %s", variables.stream().map(Object::toString).collect(Collectors.joining(", ")), expressions.stream().map(Object::toString).collect(Collectors.joining(", ")));
        else
            return String.format("%s %s %s", variables.stream().map(Object::toString).collect(Collectors.joining(", ")), assignment_operator, expressions.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}