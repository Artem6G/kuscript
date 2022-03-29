package lib;

import java.util.HashMap;

public class Module {
    public HashMap<String, Value> variables;

    public Module(HashMap<String, Value> variables) {
        this.variables = variables;
    }
}
