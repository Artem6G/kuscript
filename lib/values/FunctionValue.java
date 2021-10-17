package lib.values;

import lib.Value;
import lib.functions.Function;

public class FunctionValue implements Value {

    public final Function function;

    public FunctionValue(Function function) {
        this.function = function;
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
        return null;
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
        return this;
    }
}
