package lib.modules;

import lib.FunctionInit;
import lib.Value;
import lib.values.DoubleValue;

import java.util.List;

public class time {
    @FunctionInit
    private static Value time(List<Value> args) {
        if (args.size() != 0)
            throw new RuntimeException();

        return new DoubleValue(System.currentTimeMillis());
    }
}
