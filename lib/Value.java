package lib;

import lib.values.ArrayValue;
import lib.values.FunctionValue;

public interface Value {
    double asDouble();
    int asInteger();
    String asString();
    boolean asBoolean();
    ArrayValue asArray();
    FunctionValue asFunction();
}
