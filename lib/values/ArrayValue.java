package lib.values;

import lib.Value;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ArrayValue implements Value {
    public Value[] values;

    public ArrayValue(int size) {
        this.values = new Value[size];
    }

    public Value get(int index) {
        return values[index];
    }

    public void set(int index, Value value) {
        try {
            values[index] = value;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("");
        }
    }

    @Override
    public double asDouble() {
        throw new RuntimeException("");
    }

    @Override
    public int asInteger() {
        throw new RuntimeException("");
    }

    @Override
    public String asString() {
        return String.format("{%s}", Arrays.stream(values).map(Object::toString).collect(Collectors.joining(", ")));
    }

    @Override
    public boolean asBoolean() {
        throw new RuntimeException("");
    }

    @Override
    public char asChar() {
        throw new RuntimeException("");
    }

    @Override
    public ArrayValue asArray() {
        return this;
    }

    @Override
    public FunctionValue asFunction() {
        return null;
    }

    @Override
    public String toString() {
        return asString();
    }

}