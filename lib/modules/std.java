package lib.modules;

import lib.FunctionInit;
import lib.Value;
import lib.values.ArrayValue;
import lib.values.NoneValue;
import lib.values.NullValue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class std {
    @FunctionInit
    private static Value print(List<Value> args) {
        System.out.print(argsToString(args));
        return new NoneValue();
    }

    @FunctionInit
    private static Value println(List<Value> args) {
        System.out.println(argsToString(args));
        return new NoneValue();
    }

    @FunctionInit
    private static Value fill(List<Value> args) {
        if (args.size() == 0)
            throw new RuntimeException("zero argument");

        List<Integer> sizes = new ArrayList<>();

        for (Value value : args)
            sizes.add(value.asInteger());

        return fill(sizes, 0);
    }

    private static ArrayValue fill(List<Integer> sizes, int n) {
        final int size = sizes.get(n);
        final int last = sizes.size() - 1;
        ArrayValue array = new ArrayValue(size);
        if (n == last) {
            array = new ArrayValue(size);
            for (int i = 0; i < size; i++) {
                array.values[i] = new NullValue();
            }
        } else if (n < last) {
            for (int i = 0; i < size; i++) {
                array.values[i] = fill(sizes, n + 1);
            }
        }
        return array;
    }

    private static String argsToString(List<Value> args) {
        return args.stream().map(Value::asString).collect(Collectors.joining(" "));
    }
}