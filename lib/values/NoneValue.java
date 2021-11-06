package lib.values;

import lib.Value;

public class NoneValue implements Value {
    @Override
    public Number asNumber() {
        throw new RuntimeException("");
    }

    @Override
    public String asString() {
        return "none";
    }

    @Override
    public boolean asBoolean() {
        throw new RuntimeException("");
    }

    @Override
    public ArrayValue asArray() {
        throw new RuntimeException("");
    }

    @Override
    public FunctionValue asFunction() {
        throw new RuntimeException("");
    }

    @Override
    public String toString() {
        return asString();
    }
}
