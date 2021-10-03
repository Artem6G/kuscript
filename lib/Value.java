package lib;

import lib.values.ArrayValue;

public interface Value {
    double asDouble();
    int asInteger();
    String asString();
    boolean asBoolean();
    char asChar();
    ArrayValue asArray();
}
