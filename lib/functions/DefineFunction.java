package lib.functions;

import lib.Value;
import lib.arguments.Arguments;
import lib.values.FunctionValue;
import parser.ast.Statement;

import java.util.HashMap;

public class DefineFunction {
    public static boolean isExternal = true;
    public static HashMap<String, Value> variables = new HashMap<>();
    
    public static Value define(Arguments arguments, Statement body) {
        if (isExternal)
            return new FunctionValue(new DefineExternalFunction(arguments, body));
        else
            return new FunctionValue(new DefineLocalFunction(arguments, new HashMap<>(variables), body));
    }
}
