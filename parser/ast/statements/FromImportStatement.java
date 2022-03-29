package parser.ast.statements;

import lib.ModuleImport;

import parser.ast.Statement;
import parser.ast.Visitor;

import java.util.List;

public class FromImportStatement implements Statement {

    public final String name;
    public final List<String> subjects;

    public FromImportStatement(String name, List<String> subjects) {
        this.name = name;
        this.subjects = subjects;
    }

    @Override
    public void execute() {
        ModuleImport.importModule(name, subjects);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "from " + name + " import " + subjects.toString();
    }
}
