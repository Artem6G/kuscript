package parser.ast.statements;

import parser.ast.Statement;
import parser.ast.Visitor;

public class PassStatement implements Statement {
    @Override
    public void execute() {

    }

    @Override
    public String toString() {
        return "pass";
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
