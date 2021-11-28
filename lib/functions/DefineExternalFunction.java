package lib.functions;

import lib.Value;
import lib.arguments.Arguments;
import lib.values.NoneValue;
import lib.variables.Variables;
import parser.ast.Statement;
import parser.ast.statements.ReturnStatement;

import java.util.List;

public class DefineExternalFunction implements Function {
    private final Arguments arguments;
    public final Statement body;
    public static final Value DEFAULT_VALUE = new NoneValue();

    public DefineExternalFunction(Arguments arguments, Statement body) {
        this.arguments = arguments;
        this.body = body;
    }

    @Override
    public Value execute(List<Value> values) {
        if (arguments.size() != values.size())
            throw new RuntimeException("");

        DefineFunction.isExternal = false;

        try {
            Variables.push();

            for (int i = 0; i < arguments.size(); i++) {
                Variables.setVariable(arguments.getArgument(i).getName(), values.get(i));
                DefineFunction.variables.put(arguments.getArgument(i).getName(), values.get(i));
            }

            body.execute();
            return DEFAULT_VALUE;
        } catch (ReturnStatement returnStatement) {
            return returnStatement.value;
        } finally {
            DefineFunction.isExternal = true;
            DefineFunction.variables.clear();
            Variables.pop();
        }
    }

    @Override
    public String toString() {
        return String.format("def%s %s", arguments, body);
    }
}
