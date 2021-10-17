package parser.ast.visitors;

import lib.functions.DefineFunction;
import lib.values.FunctionValue;
import lib.variables.Variables;
import parser.ast.statements.DefineFunctionStatement;

public class FunctionAdder extends AbstractVisitor{
    @Override
    public void visit(DefineFunctionStatement defineFunctionStatement) {
        Variables.setVariable(defineFunctionStatement.name, new FunctionValue(new DefineFunction(defineFunctionStatement.arguments, defineFunctionStatement.body)));
        defineFunctionStatement.body.accept(this);
    }
}
