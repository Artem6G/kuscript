package lib;

import lexer.Lexer;
import lib.values.FunctionValue;
import lib.variables.Variables;
import parser.Parser;
import parser.ast.Statement;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ModuleImport {
    public static void importModule(String name) {
        try {
            Class<?> aClass = Class.forName("lib.modules." + name);
            for (final Method method : aClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(FunctionInit.class)) {
                    method.setAccessible(true);
                    Variables.setVariable(method.getName().replace("$", ""), new FunctionValue(values -> {
                        try {
                            return (Value) method.invoke(null, values);
                        } catch (InvocationTargetException | IllegalAccessException e) {
                            throw new RuntimeException(method.getName().replace("$", "") + " | " + e.getCause().getMessage());
                        }
                    }));
                }
            }

            for (Field field : aClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(FieldInit.class)) {
                    field.setAccessible(true);
                    Variables.setVariable(field.getName().replace("$", ""), (Value) field.get(null));
                }
            }
        } catch (ClassNotFoundException ign) {
            try {
                statementsExecute(name);
            } catch (IOException ignored) {
                throw new RuntimeException("file not found");
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getCause().getMessage());
        }
    }

    private static void statementsExecute(String path) throws IOException {
        List<Statement> statements = new Parser(new Lexer(new String(Files.readAllBytes(Paths.get(path)))).lex()).parse();
        statements.forEach(Statement::execute);
    }
}