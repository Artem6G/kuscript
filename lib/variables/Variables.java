package lib.variables;

import lib.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public final class Variables {

    public static ArrayList<HashMap<String, Value>> difference;
    private static final Stack<HashMap<String, Value>> STACK;
    private static HashMap<String, Value> variables;
    public static int lvl = -1;

    static {
        difference = new ArrayList<>();
        STACK = new Stack<>();
        variables = new HashMap<>();
    }

    public static boolean isExists(String key) {
        return variables.containsKey(key);
    }

    public static void push() {
        STACK.push(new HashMap<>(variables));

        lvl++;
        difference.add(new HashMap<>());
    }

    public static void pop() {
        variables = STACK.pop();
        difference.remove(lvl--);
    }

    public static Value get(String key) {
        if (!isExists(key)) throw new RuntimeException("");
        return variables.get(key);
    }

    public static void setVariable(String key, Value value) {
        if(lvl != -1)
            difference.get(lvl).put(key, value);

        variables.put(key, value);
    }

}