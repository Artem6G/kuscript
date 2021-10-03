package lib.modules;

import lib.FieldInit;
import lib.FunctionInit;
import lib.Value;
import lib.values.DoubleValue;

import java.util.List;

public class math {
    @FieldInit
    private static DoubleValue PI = new DoubleValue(Math.PI);
    @FieldInit
    private static DoubleValue E = new DoubleValue(Math.E);

    @FunctionInit
    private static Value sin(List<Value> args) {
        if (args.size() != 1)
            throw new RuntimeException();

        return new DoubleValue(Math.sin(args.get(0).asDouble()));
    }

    @FunctionInit
    private static Value cos(List<Value> args) {
        if (args.size() != 1)
            throw new RuntimeException();

        return new DoubleValue(Math.cos(args.get(0).asDouble()));
    }

    @FunctionInit
    private static Value tan(List<Value> args) {
        if (args.size() != 1)
            throw new RuntimeException();

        return new DoubleValue(Math.tan(args.get(0).asDouble()));
    }
}
