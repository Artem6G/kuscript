package lib.values;

import lib.Value;

public class CharValue implements Value {

    private final char value;

    public CharValue(char value) {
        this.value = value;
    }

    @Override
    public boolean asBoolean() {
        throw new RuntimeException("");
    }

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
        return Character.toString(value);
    }

    @Override
    public ArrayValue asArray() {
        throw new RuntimeException("");
    }

    @Override
    public char asChar() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("'%s'", asString());
    }
}
