package lib.values;

import lib.Value;
public class StringValue implements Value {

    private final String value;

    public StringValue(String value) {
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
        return value;
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
    public char asChar() {
        throw new RuntimeException("");
    }

    @Override
    public String toString() {
        return String.format("\"%s\"", asString());
    }
}
