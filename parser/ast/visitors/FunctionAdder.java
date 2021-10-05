package parser.ast.visitors;

import lib.functions.DefineFunction;
import lib.functions.Functions;
import parser.ast.statements.DefineFunctionStatement;

public class FunctionAdder extends AbstractVisitor{
    @Override
    public void visit(DefineFunctionStatement defineFunctionStatement) {
        Functions.set(defineFunctionStatement.name, new DefineFunction(defineFunctionStatement.arguments, defineFunctionStatement.body));
        defineFunctionStatement.body.accept(this);
    }
}
