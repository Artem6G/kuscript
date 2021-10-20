package parser.ast.statements;

import lib.ModuleImport;

import lib.Value;
import lib.values.ArrayValue;
import parser.ast.Expression;
import parser.ast.Statement;
import parser.ast.Visitor;

public class ImportStatement implements Statement {

    public final Expression expression;

    public ImportStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        Value value = expression.eval();
        if (value instanceof ArrayValue)
            for(Value val : ((ArrayValue)value).values)
                importModule(val);
        else
            importModule(expression.eval());
    }

    private void importModule(Value value) {
        ModuleImport.importModule(value.asString());
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "import " + expression;
    }
}
