package parser.ast.statements;

import parser.ast.Visitor;

public class BreakLabelStatement extends BreakStatement {
    public final String name;

    public BreakLabelStatement(String name) {
        this.name = name;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("%s %s", super.toString(), name);
    }
}
