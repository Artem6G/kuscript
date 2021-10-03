package lib.variables;

import lib.Value;
import lib.values.NoneValue;

public class Variable {
    public final boolean isConst;
    private Value value;

    public Variable (Value value, boolean isConst) {
        if (value instanceof NoneValue)
            throw new RuntimeException("");

        this.isConst = isConst;
        this.value = value;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        if (isConst)
            throw new RuntimeException("");
        if (value instanceof NoneValue)
            throw new RuntimeException("");
        this.value = value;
    }
}
