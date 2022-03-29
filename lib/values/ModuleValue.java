package lib.values;

import lib.Module;
import lib.Value;

public class ModuleValue implements Value {
    public final Module module;

    public ModuleValue(Module module) {
        this.module = module;
    }

    @Override
    public Number asNumber() {
        throw new RuntimeException("");
    }

    @Override
    public String asString() {
        return "module" + module.hashCode();
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
        return this;
    }
}
