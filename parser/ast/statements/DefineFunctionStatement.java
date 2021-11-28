package parser.ast.statements;

import lib.arguments.Arguments;
import lib.functions.DefineFunction;
import lib.variables.Variables;
import parser.ast.Statement;
import parser.ast.Visitor;

public class DefineFunctionStatement implements Statement{
    public final String name;
    public final Arguments arguments;
    public final Statement body;

    public DefineFunctionStatement(String name, Arguments arguments, Statement body) {
        this.name = name;
        this.arguments = arguments;
        this.body = body;
    }

    @Override
    public void execute() {
        Variables.setVariable(name, DefineFunction.define(arguments, body));
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("def %s%s %s", name, arguments, body);
    }
}