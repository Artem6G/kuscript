package lib.values;

import lib.Value;
import lib.functions.Function;

public class FunctionValue implements Value {
    public final Function function;

    public FunctionValue(Function function) {
        this.function = function;
    }

    @Override
    public Number asNumber() {
        throw new RuntimeException("");
    }

    @Override
    public String asString() {
        return "function" + function.hashCode();
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
        return this;
    }

    @Override
    public ClassValue asClass() {
        throw new RuntimeException("");
    }
}
