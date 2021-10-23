package parser.ast.statements;

import parser.ast.Statement;
import parser.ast.Visitor;

public class BreakLabelStatement extends RuntimeException implements Statement {

    public final String name;

    public BreakLabelStatement(String name) {
        this.name = name;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void execute() {
        throw this;
    }
}
