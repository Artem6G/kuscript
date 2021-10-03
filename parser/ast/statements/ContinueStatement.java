package parser.ast.statements;

import parser.ast.Statement;
import parser.ast.Visitor;

public class ContinueStatement extends RuntimeException implements Statement {

    private String word;

    public ContinueStatement(String word) {
        this.word = word;
    }

    public ContinueStatement() {

    }

    @Override
    public void execute() {
        throw this;
    }

    @Override
    public String toString() {
        if (word == null)
            return "continue";
        else
            return "continue " + word;
    }

    @Override
    public String getMessage() {
        if (word == null)
            return null;
        else
            return word;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
