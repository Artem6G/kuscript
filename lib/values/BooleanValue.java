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
    public double asDouble() {
        throw new RuntimeException("");
    }

    @Override
    public int asInteger() {
        return value ? 1 : 0;
    }

    @Override
    public ArrayValue asArray() {
        throw new RuntimeException("");
    }

    @Override
    public FunctionValue asFunction() {
        return null;
    }

    @Override
    public String asString() {
        return value ? "true" : "false";
    }

    @Override
    public char asChar() {
        throw new RuntimeException("");
    }

    @Override
    public String toString() {
        return value ? "true" : "false";
    }
}