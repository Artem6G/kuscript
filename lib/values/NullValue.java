package lib.values;

import lib.Value;

public class NullValue implements Value {
    @Override
    public Number asNumber() {
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
