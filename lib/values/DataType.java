package lib.values;

import lib.Value;

public enum DataType {
    INT,
    DOUBLE,
    CHAR,
    BOOLEAN,
    STRING,
    NULL,
    ARRAY;

    public static DataType type(Value value) {
        if (value instanceof IntegerValue)
            return INT;
        else if (value instanceof DoubleValue)
            return DOUBLE;
        else if (value instanceof CharValue)
            return CHAR;
        else if (value instanceof BooleanValue)
            return BOOLEAN;
        else if (value instanceof StringValue)
            return STRING;
        else if (value instanceof ArrayValue)
            return ARRAY;
        else if (value instanceof NullValue)
            return NULL;

        throw new RuntimeException("");
    }
}