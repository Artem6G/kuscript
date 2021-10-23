package parser.ast.statements;

import parser.ast.Statement;
import parser.ast.Visitor;

public class BreakStatement extends RuntimeException implements Statement {
    @Override
    public void execute() {
        throw this;
    }

    @Override
    public String toString() {
        return "break";
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}