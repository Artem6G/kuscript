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

public class DefineFunction implements Function {
    public static int function_start_lvl = -2;
    public static HashMap<String, Value> staticVariables = new HashMap<>();

    public HashMap<String, Value> variables;
    private final Arguments arguments;
    public final Statement body;
    public static final Value DEFAULT_VALUE = new NoneValue();

    public DefineFunction(Arguments arguments, Statement body) {
        if (function_start_lvl != -2) {
            variables = new HashMap<>();
            staticVariables.putAll(Variables.difference.get(Variables.difference.size() - 1));
            variables.putAll(staticVariables);
        }
        this.arguments = arguments;
        this.body = body;
    }

    @Override
    public Value execute(List<Value> values) {
        if (arguments.size() != values.size())
            throw new RuntimeException("");
        try {
            if (function_start_lvl == -2) {
                function_start_lvl = Variables.lvl;
            }

            Variables.push();
            if (variables != null)
                for (Map.Entry<String, Value> entry : variables.entrySet())
                    Variables.setVariable(entry.getKey(), entry.getValue());

            for (int i = 0; i < arguments.size(); i++)
                Variables.setVariable(arguments.getArgument(i).getName(), values.get(i));

            body.execute();
            return DEFAULT_VALUE;
        } catch (ReturnStatement returnStatement) {
            return returnStatement.value;
        } finally {
            Variables.pop();
            if (function_start_lvl == Variables.lvl) {
                staticVariables.clear();
                function_start_lvl = -2;
            }
        }
    }

    @Override
    public String toString() {
        return String.format("def%s %s", arguments, body);
    }
}
