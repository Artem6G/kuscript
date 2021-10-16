package parser.ast.statements;

import lib.ModuleImport;

import parser.ast.Expression;
import parser.ast.Statement;
import parser.ast.Visitor;

public class ImportStatement implements Statement {

    public final Expression constantExpression;

    public ImportStatement(Expression constantExpression) {
        this.constantExpression = constantExpression;
    }

    @Override
    public void execute() {
        new ModuleImport(constantExpression.eval().asString()).importModule();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "import " + constantExpression;
    }
}
