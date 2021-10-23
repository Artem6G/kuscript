package parser.ast.statements;

import parser.ast.Statement;
import parser.ast.Visitor;

public class ContinueStatement extends RuntimeException implements Statement {
    @Override
    public void execute() {
        throw this;
    }

    @Override
    public String toString() {
        return "continue" ;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
