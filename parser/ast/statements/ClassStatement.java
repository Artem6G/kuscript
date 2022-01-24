package parser.ast.statements;

import lib.classes.Class;
import lib.values.ClassValue;
import lib.variables.Variables;
import parser.ast.Statement;
import parser.ast.Visitor;

public class ClassStatement implements Statement {
    public final Statement statement;
    public final String name;

    public ClassStatement(String name, Statement statement) {
        this.name = name;
        this.statement = statement;
    }

    @Override
    public void execute() {
        Variables.push();
        statement.execute();
        Class aClass = new Class(Variables.difference.get(Variables.lvl));
        Variables.pop();
        Variables.setVariable(name, new ClassValue(aClass));
    }

    @Override
    public String toString() {
        return String.format("class %s %s", name, statement);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}