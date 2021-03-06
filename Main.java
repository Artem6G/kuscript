import lexer.Lexer;
import lexer.Token;
import lib.ModuleImport;
import parser.Parser;
import parser.ast.Statement;
import parser.ast.statements.BreakLabelStatement;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        ModuleImport.currentPath = Paths.get("test/main.ku");
        ArrayList<Token> tokens = new Lexer(new String(Files.readAllBytes(Paths.get(String.valueOf(ModuleImport.currentPath))))).lex();
        // tokens
        tokens.forEach(System.out::print);
        System.out.println();
        List<Statement> statements = new Parser(tokens).parse();
        // ast
        statements.forEach(System.out::println);
        System.out.println("====================================");
        ModuleImport.importModule("std", List.of("*all*"));
        // answer
        try {
            statements.forEach(Statement::execute);
        } catch (BreakLabelStatement breakLabelStatement) {
            throw new RuntimeException("unknown label " + breakLabelStatement.name);
        }
    }
}