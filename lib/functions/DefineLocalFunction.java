package lib.functions;

import lib.Value;
import lib.arguments.Arguments;
import lib.values.NoneValue;
import lib.variables.Variables;
import parser.ast.Statement;
import parser.ast.statements.ReturnStatement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefineLocalFunction implements Function {
    private final Arguments arguments;
    private final HashMap<String, Value> variables;
    public final Statement body;
    public static final Value DEFAULT_VALUE = new NoneValue();

    public DefineLocalFunction(Arguments arguments, HashMap<String, Value> variables, Statement body) {
        this.arguments = arguments;
        this.variables = variables;
        this.body = body;
    }

    @Override
    public Value execute(List<Value> values) {
        if (arguments.size() != values.size())
            throw new RuntimeException("");

        try {
            Variables.push();

            for(Map.Entry<String, Value> entry: variables.entrySet()) {
                Variables.setVariable(entry.getKey(), entry.getValue());
                DefineFunction.variables.put(entry.getKey(), entry.getValue());
            }

            for (int i = 0; i < arguments.size(); i++) {
                Variables.setVariable(arguments.getArgument(i).getName(), values.get(i));
                DefineFunction.variables.put(arguments.getArgument(i).getName(), values.get(i));
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
