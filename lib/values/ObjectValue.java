package lib.values;

import lib.Value;

public class ObjectValue implements Value {
    public final ClassValue classValue;

    public ObjectValue(ClassValue classValue) {
        this.classValue = classValue;
    }

    @Override
    public Number asNumber() {
        throw new RuntimeException("");
    }

    @Override
    public String asString() {
        return "object" + this.hashCode();
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
    public ClassValue asClass() {
        throw new RuntimeException("");
    }

    @Override
    public ModuleValue asModule() {
        throw new RuntimeException("");
    }
}
