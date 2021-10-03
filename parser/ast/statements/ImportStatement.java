package parser.ast.statements;

import lib.ModuleImport;

import parser.ast.Statement;
import parser.ast.Visitor;
import parser.ast.expressions.ConstantExpression;

public class ImportStatement implements Statement {

    public final ConstantExpression constantExpression;

    public ImportStatement(ConstantExpression constantExpression) {
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
