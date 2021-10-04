package lib.variables;

import lib.Value;

public class Variable {
    public final boolean isConst;
    private Value value;

    public Variable (Value value, boolean isConst) {
        this.isConst = isConst;
        this.value = value;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        if (isConst)
            throw new RuntimeException("");
        this.value = value;
    }
}
