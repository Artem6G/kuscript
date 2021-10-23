package parser.ast.statements;

import parser.ast.Statement;
import parser.ast.Visitor;

public class LabelStatement implements Statement {

    public final String name;
    public final Statement statement;

    public LabelStatement(String name, Statement statement) {
        this.name = name;
        this.statement = statement;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void execute() {
        try {
            statement.execute();
        } catch (BreakLabelStatement breakLabelStatement) {
            if (!breakLabelStatement.name.equals(name))
                throw breakLabelStatement;
        }
    }

    @Override
    public String toString() {
        return name + ":" + statement;
    }
}
