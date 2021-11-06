package lib.values;

import lib.Value;

public class BooleanValue implements Value {

    private final boolean value;

    public BooleanValue(boolean value) {
        this.value = value;
    }

    @Override
    public boolean asBoolean() {
        return value;
    }

    @Override
    public Number asNumber() {
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
    public String asString() {
        return value ? "true" : "false";
    }

    @Override
    public String toString() {
        return value ? "true" : "false";
    }
}