package lib;

import lexer.Lexer;
import lib.values.FunctionValue;
import lib.values.ModuleValue;
import lib.variables.Variables;
import parser.Parser;
import parser.ast.Statement;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleImport {
    public static Path currentPath = null;

    public static void importModule(String name, List<String> subjects) {
        boolean isAll = false;
        if (subjects != null)
            isAll = subjects.contains("*all*");
        HashMap<String, Value> variables = null;
        try {
            Class<?> aClass = Class.forName("lib.modules." + name);
            if (subjects == null)
                variables = new HashMap<>();

            for (final Method method : aClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(FunctionInit.class)) {
                    method.setAccessible(true);

                    if(variables != null)
                        variables.put(method.getName().replace("$", ""), functionValue(method));
                    else
                        if (isAll | subjects.contains(method.getName().replace("$", "")))
                            Variables.setVariable(method.getName().replace("$", ""), functionValue(method));
                }
            }
            for (Field field : aClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(FieldInit.class)) {
                    field.setAccessible(true);

                    if(variables != null)
                        variables.put(field.getName().replace("$", ""), (Value) field.get(null));
                    else
                        if (isAll | subjects.contains(field.getName().replace("$", "")))
                            Variables.setVariable(field.getName().replace("$", ""), (Value) field.get(null));
                }
            }

            if(variables != null)
                Variables.setVariable(name, new ModuleValue(new Module(variables)));
        } catch (ClassNotFoundException ign) {
            try {
                statementsExecute(name, subjects);
            } catch (IOException ignored) {
                throw new RuntimeException("file not found");
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getCause().getMessage());
        }
    }

    private static FunctionValue functionValue(Method method) {
        return new FunctionValue(values -> {
            try {
                return (Value) method.invoke(null, values);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(method.getName().replace("$", "") + " | " + e.getCause().getMessage());
            }
        });
    }

    private static void statementsExecute(String name, List<String> subjects) throws IOException {
        List<Statement> statements = new Parser(new Lexer(new String(Files.readAllBytes(Paths.get(currentPath.getParent() + "/" + name + ".ku")))).lex()).parse();
        Variables.push();
        statements.forEach(Statement::execute);
        HashMap<String, Value> variables = new HashMap<>(Variables.difference.get(Variables.lvl));
        Variables.pop();
        if (subjects == null)
            Variables.setVariable(name, new ModuleValue(new Module(variables)));
        else {
            boolean isAll = subjects.contains("*all*");
            for (Map.Entry<String, Value> entry : variables.entrySet())
                if (isAll | subjects.contains(entry.getKey()))
                    Variables.setVariable(entry.getKey(), entry.getValue());
        }
    }
}