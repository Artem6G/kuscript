package lib.values;

import lib.Value;

public enum DataType {
    INT,
    DOUBLE,
    BOOLEAN,
    STRING,
    NULL,
    FUNCTION,
    CLASS,
    MODULE,
    ARRAY;

    public static DataType type(Value value) {
        if (value instanceof IntegerValue)
            return INT;
        else if (value instanceof DoubleValue)
            return DOUBLE;
        else if (value instanceof BooleanValue)
            return BOOLEAN;
        else if (value instanceof StringValue)
            return STRING;
        else if (value instanceof ArrayValue)
            return ARRAY;
        else if (value instanceof NullValue)
            return NULL;
        else if (value instanceof FunctionValue)
            return FUNCTION;
        else if (value instanceof ModuleValue)
            return MODULE;
        else if (value instanceof ClassValue)
            return CLASS;

        throw new RuntimeException("");
    }
}