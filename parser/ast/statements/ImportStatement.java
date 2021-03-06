package parser.ast.statements;

import lib.ModuleImport;

import parser.ast.Statement;
import parser.ast.Visitor;

public class ImportStatement implements Statement {

    public final String name;

    public ImportStatement(String name) {
        this.name = name;
    }

    @Override
    public void execute() {
        ModuleImport.importModule(name, null);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "import " + name;
    }
}
