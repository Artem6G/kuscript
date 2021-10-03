package parser.ast.statements;

import parser.ast.Statement;
import parser.ast.Visitor;

public class BreakStatement extends RuntimeException implements Statement {

    private String word;

    public BreakStatement(String word) {
        this.word = word;
    }

    public BreakStatement() {

    }

    @Override
    public void execute() {
        throw this;
    }

    @Override
    public String toString() {
        if (word == null)
            return "break";
        else
            return "break " + word;
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