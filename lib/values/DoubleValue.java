package lib.values;

import lib.Value;

public class DoubleValue implements Value {

    private final double value;

    public DoubleValue(double value) {
        this.value = value;
    }

    @Override
    public boolean asBoolean() {
        throw new RuntimeException("");
    }

    @Override
    public Number asNumber() {
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
    public FunctionValue asFunction() {
        throw new RuntimeException("");
    }

    @Override
    public ClassValue asClass() {
        throw new RuntimeException("");
    }

    @Override
    public ModuleValue asModule() {
        throw new RuntimeException("");
    }

    @Override
    public String toString() {
        return asString();
    }
}
