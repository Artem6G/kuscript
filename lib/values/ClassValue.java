package lib.values;

import lib.Value;
import lib.classes.Class;

public class ClassValue implements Value {
    public final Class aClass;

    public ClassValue(Class aClass) {
        this.aClass = aClass;
    }

    @Override
    public Number asNumber() {
        throw new RuntimeException("");
    }

    @Override
    public String asString() {
        return "class" + this.hashCode();
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

    public ClassValue asClass() {
        return this;
    }
}
