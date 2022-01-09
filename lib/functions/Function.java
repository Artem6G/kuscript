package lib.functions;

import lib.Value;
import java.util.List;

public interface Function {
    Value execute(List<Value> values);
}