import lexer.Lexer;
import lexer.Token;
import parser.Parser;
import parser.ast.Statement;
import parser.ast.statements.BreakStatement;
import parser.ast.statements.ContinueStatement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Token> tokens = new Lexer(new String(Files.readAllBytes(Paths.get("test/main.ku")))).lex();

        // tokens
        tokens.forEach(System.out::print);

        System.out.println();

        List<Statement> statements = new Parser(tokens).parse();

        // ast
        statements.forEach(System.out::println);

        System.out.println("====================================");

        // answer
        try {
            statements.forEach(Statement::execute);
        } catch (BreakStatement | ContinueStatement sta) {
            throw new RuntimeException("unknown label " + sta.getMessage());
        }
    }
}