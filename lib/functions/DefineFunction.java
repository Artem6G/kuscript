package lib.functions;

import lib.Value;
import lib.arguments.Arguments;
import lib.values.NoneValue;
import lib.variables.Variables;
import parser.ast.Statement;
import parser.ast.statements.ReturnStatement;

import java.util.List;

public class DefineFunction implements Function {
    private final Arguments arguments;
    public final Statement body;
    public static final Value DEFAULT_VALUE = new NoneValue();

    public DefineFunction(Arguments arguments, Statement body) {
        this.arguments = arguments;
        this.body = body;
    }

    @Override
    public Value execute(List<Value> values) {
        if (arguments.size() != values.size())
            throw new RuntimeException("");

        try {
            Variables.push();
            for (int i = 0; i < arguments.size(); i++) {
                Variables.setVariable(arguments.getArgument(i).getName(), values.get(i));
            }
            body.execute();
            return DEFAULT_VALUE;
        } catch (ReturnStatement returnStatement) {
            return returnStatement.value;
        } finally {
            Variables.pop();
        }
    }

    @Override
    public String toString() {
        return String.format("def%s %s", arguments, body);
    }
}
