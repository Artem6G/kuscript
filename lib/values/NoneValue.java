package lib.values;

import lib.Value;

public class NoneValue implements Value {
    @Override
    public double asDouble() {
        throw new RuntimeException("");
    }

    @Override
    public int asInteger() {
        throw new RuntimeException("");
    }

    @Override
    public String asString() {
        throw new RuntimeException("");
    }

    @Override
    public boolean asBoolean() {
        throw new RuntimeException("");
    }

    @Override
    public char asChar() {
        throw new RuntimeException("");
    }

    @Override
    public ArrayValue asArray() {
        throw new RuntimeException("");
    }

    @Override
    public String toString() {
        return "none";
    }
}
