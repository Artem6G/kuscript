package lib;

import lexer.Lexer;
import lib.functions.Functions;
import lib.variables.Variables;
import parser.Parser;
import parser.ast.Statement;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ModuleImport {
    private final String name;

    public ModuleImport(String name) {
        this.name = name;
    }

    public void importModule() {
        try {
            Class<?> aClass = Class.forName("lib.modules." + name);
            for (final Method method : aClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(FunctionInit.class)) {
                    method.setAccessible(true);
                    Functions.set(method.getName(), values -> {
                        try {
                            return (Value) method.invoke(null, values);
                        } catch (InvocationTargetException | IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }

            for (Field field : aClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(FieldInit.class)) {
                    field.setAccessible(true);
                    Variables.setConst(field.getName(), (Value) field.get(null));
                }
            }
        } catch (ClassNotFoundException ign) {
            try {
                statementsExecute(name);
            } catch (IOException ignored) {
                throw new RuntimeException("file not found");
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void statementsExecute(String path) throws IOException {
        for (Statement statement : new Parser(new Lexer(new String(Files.readAllBytes(Paths.get(path)))).lex()).parse())
            statement.execute();
    }
}