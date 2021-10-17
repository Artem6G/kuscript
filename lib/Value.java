package lib;

import lib.values.ArrayValue;
import lib.values.FunctionValue;

public interface Value {
    double asDouble();
    int asInteger();
    String asString();
    boolean asBoolean();
    char asChar();
    ArrayValue asArray();
    FunctionValue asFunction();
}
