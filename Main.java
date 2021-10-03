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

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Token> tokens = new Lexer(new String(Files.readAllBytes(Paths.get("test/main.ku")))).lex();

        // tokens
        for (Token t:
                tokens) {
            System.out.print(t);
        }

        System.out.println();


        // ast
        for (Statement statement : new Parser(tokens).parse()) {
            System.out.println(statement);
        }

        System.out.println("====================================");

        // answer
        for (Statement statement : new Parser(tokens).parse()) {
            try {
                statement.execute();
            } catch (BreakStatement | ContinueStatement sta) {
                throw new RuntimeException("unknown label " + sta.getMessage());
            }
        }
    }
}