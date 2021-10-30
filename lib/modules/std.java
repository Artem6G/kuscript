package lib.modules;

import lexer.Lexer;
import lib.FunctionInit;
import lib.Value;
import lib.values.*;
import parser.Parser;
import parser.ast.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class std {
    @FunctionInit
    private static NoneValue $print(List<Value> args) {
        System.out.print(argsToString(args));
        return new NoneValue();
    }

    @FunctionInit
    private static NoneValue $println(List<Value> args) {
        System.out.println(argsToString(args));
        return new NoneValue();
    }

    @FunctionInit
    private static Value $eval(List<Value> args) {
        if (args.size() != 1)
            throw new RuntimeException("zero argument");

        return new Parser(new Lexer(args.get(0).asString()).lex()).expression().eval();
    }

    @FunctionInit
    private static NoneValue $exec(List<Value> args) {
        if (args.size() != 1)
            throw new RuntimeException("zero argument");

        new Parser(new Lexer(args.get(0).asString()).lex()).parse().forEach(Statement::execute);

        return new NoneValue();
    }

    @FunctionInit
    private static StringValue $input(List<Value> args) {
        return new StringValue(new Scanner(System.in).nextLine());
    }

    @FunctionInit
    private static StringValue type(List<Value> args) {
        if (args.size() != 1)
            throw new RuntimeException("zero argument");

        return new StringValue(DataType.type(args.get(0)).toString().toLowerCase(Locale.ROOT));
    }

    @FunctionInit
    private static IntegerValue $int(List<Value> args) {
        if (args.size() != 1)
            throw new RuntimeException("zero argument");

        switch (DataType.type(args.get(0))) {
            case INT:
                return (IntegerValue) args.get(0);
            case DOUBLE:
                return new IntegerValue(args.get(0).asInteger());
            case BOOLEAN:
                return new IntegerValue(args.get(0).asBoolean() ? 1 : 0);
            case STRING:
                return new IntegerValue(Integer.parseInt(args.get(0).asString()));
        }

        throw new RuntimeException(String.format("%s cannot be cast to int", DataType.type(args.get(0))));
    }

    @FunctionInit
    private static BooleanValue $boolean(List<Value> args) {
        if (args.size() != 1)
            throw new RuntimeException("zero argument");

        switch (DataType.type(args.get(0))) {
            case BOOLEAN:
                return (BooleanValue) args.get(0);
            case INT:
                return new BooleanValue(args.get(0).asInteger() != 0);
            case STRING:
                return new BooleanValue(Boolean.parseBoolean(args.get(0).asString()));
        }

        throw new RuntimeException(String.format("%s cannot be cast to boolean", DataType.type(args.get(0))));
    }

    @FunctionInit
    private static DoubleValue $double(List<Value> args) {
        if (args.size() != 1)
            throw new RuntimeException("zero argument");

        switch (DataType.type(args.get(0))) {
            case DOUBLE:
                return (DoubleValue) args.get(0);
            case INT:
                return new DoubleValue(args.get(0).asInteger());
            case STRING:
                return new DoubleValue(Double.parseDouble(args.get(0).asString()));
        }

        throw new RuntimeException(String.format("%s cannot be cast to double", DataType.type(args.get(0))));
    }

    @FunctionInit
    private static StringValue $string(List<Value> args) {
        if (args.size() != 1)
            throw new RuntimeException("zero argument");

        switch (DataType.type(args.get(0))) {
            case STRING:
                return (StringValue) args.get(0);
            case DOUBLE:
            case INT:
            case BOOLEAN:
            case ARRAY:
                return new StringValue(args.get(0).asString());
        }

        throw new RuntimeException(String.format("%s cannot be cast to string", DataType.type(args.get(0))));
    }

    @FunctionInit
    private static Value $fill(List<Value> args) {
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