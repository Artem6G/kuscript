package parser.ast.statements;

import parser.ast.Statement;
import parser.ast.Visitor;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class RawBlockStatement implements Statement {

    public final ArrayList<Statement> statements;

    public RawBlockStatement(ArrayList<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public void execute() {
        for (Statement statement : statements)
                statement.execute();
    }

    @Override
    public String toString() {
        return String.format("{\n%s\n}", statements.stream().map(Object::toString).collect(Collectors.joining("\n")));
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
