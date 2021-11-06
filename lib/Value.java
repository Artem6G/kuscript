package lib;

import lib.values.ArrayValue;
import lib.values.FunctionValue;

public interface Value {
    Number asNumber();
    String asString();
    boolean asBoolean();
    ArrayValue asArray();
    FunctionValue asFunction();
}