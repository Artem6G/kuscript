package lib.functions;

import lib.variables.Variables;

import java.util.HashMap;

public class Functions {
    private static final HashMap<String, Function> functions;

    static {
        functions = new HashMap<>();
    }

    public static boolean isExists(String key) {
        return functions.containsKey(key);
    }

    public static Function get(String key) {
            Variables.push();
            if (!isExists(key)) throw new RuntimeException("");
            return functions.get(key);
    }

    public static void set(String key, Function function) {
        functions.put(key, function);
    }
}
