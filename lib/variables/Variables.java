package lib.variables;

import lib.Value;
import lib.values.NoneValue;

import java.util.HashMap;
import java.util.Stack;

public final class Variables {

    private static final Stack<HashMap<String, Variable>> STACK;
    private static HashMap<String, Variable> variables;

    static {
        STACK = new Stack<>();
        variables = new HashMap<>();
    }

    public static boolean isExists(String key) {
        return variables.containsKey(key);
    }

    public static void push() {
        STACK.push(new HashMap<>(variables));
    }

    public static void pop() {
        variables = STACK.pop();
    }

    public static Value get(String key) {
        if (!isExists(key)) throw new RuntimeException("");

        return variables.get(key).getValue();
    }

    public static void setVariable(String key, Value value) {
        if (value instanceof NoneValue)
            throw new RuntimeException("");

        setVariable(key, value, false);
    }

    public static void setVariable(String key, Value value, boolean isConst) {
        if (!isExists(key)) {
            variables.put(key, new Variable(value, isConst));
        }
        else if(!isConst)
            variables.get(key).setValue(value);
        else
            throw new RuntimeException("");
    }

    public static boolean isConstVariable(String key) {
        return variables.get(key).isConst;
    }
}