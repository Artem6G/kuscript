package parser.ast.statements;

import lib.variables.Variables;
import parser.ast.Statement;
import parser.ast.Visitor;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class BlockStatement implements Statement {

    public final ArrayList<Statement> statements;
    private String label;

    public BlockStatement(ArrayList<Statement> statements) {
        this.statements = statements;
    }

    public BlockStatement(ArrayList<Statement> statements, String label) {
        this.statements = statements;
        this.label = label;
    }

    @Override
    public void execute() {
        Variables.push();
        for (Statement statement : statements)
            try {
                statement.execute();
            } catch (BreakStatement | ContinueStatement stat) {
                String lab = stat.getMessage();
                if (lab == null)
                    break;
                else if (label == null)
                    throw stat;
                else if (lab.equals(label))
                    break;
                else
                    throw stat;
            }
        Variables.pop();
    }

    @Override
    public String toString() {
        if (label == null)
            return String.format("{\n%s\n}", statements.stream().map(Object::toString).collect(Collectors.joining("\n")));
        else
            return String.format("%s: {\n%s\n}", label, statements.stream().map(Object::toString).collect(Collectors.joining("\n")));
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
