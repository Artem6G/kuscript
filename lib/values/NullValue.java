package lib.values;

import lib.Value;

public class NullValue implements Value {
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
        return "null";
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
    public FunctionValue asFunction() {
        return null;
    }

    @Override
    public String toString() {
        return asString();
    }
}
