package lib;

import lib.values.ArrayValue;
import lib.values.ClassValue;
import lib.values.FunctionValue;
import lib.values.ModuleValue;

public interface Value {
    Number asNumber();
    String asString();
    boolean asBoolean();
    ArrayValue asArray();
    FunctionValue asFunction();
    ClassValue asClass();
    ModuleValue asModule();
}