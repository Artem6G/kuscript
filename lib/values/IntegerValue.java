package lib.values;

import lib.Value;

public class IntegerValue implements Value {
    private final int value;

    public IntegerValue(int value) {
        this.value = value;
    }

    @Override
    public boolean asBoolean() {
        return value != 0;
    }

    @Override
    public int asInteger () {
        return value;
    }

    @Override
    public double asDouble () {
        return value;
    }

    @Override
    public String asString() {
        return String.valueOf(value);
    }

    @Override
    public ArrayValue asArray() {
        throw new RuntimeException("");
    }

    @Override
    public char asChar() {
        throw new RuntimeException("");
    }

    @Override
    public String toString() {
        return asString();
    }
}