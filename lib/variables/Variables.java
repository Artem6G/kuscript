package lib.variables;

import lib.Value;
import lib.values.NoneValue;

import java.util.HashMap;
import java.util.Stack;

public final class Variables {

    private static final Stack<HashMap<String, Value>> STACK;
    private static HashMap<String, Value> variables;

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

        return variables.get(key);
    }

    public static void setVariable(String key, Value value) {
        if (value instanceof NoneValue)
            throw new RuntimeException("");

        variables.put(key, value);
    }

}