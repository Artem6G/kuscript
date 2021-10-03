package parser.ast.statements;

import lib.variables.Variables;
import parser.ast.Statement;
import parser.ast.Visitor;

public class CultivatedStatement implements Statement {

    public final Statement statement;

    public CultivatedStatement(Statement statement) {
        this.statement = statement;
    }

    @Override
    public void execute() {
        Variables.push();
        statement.execute();
        Variables.pop();
    }

    @Override
    public String toString() {
        return statement.toString();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
