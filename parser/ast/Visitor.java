package parser.ast;

import parser.ast.expressions.*;
import parser.ast.statements.*;

public interface Visitor {
    void visit(ArrayExpression arrayExpression);
    void visit(BinaryExpression binaryExpression);
    void visit(ElementArrayExpression elementArrayExpression);
    void visit(TernaryExpression ternaryExpression);
    void visit(UnaryExpression unaryExpression);
    void visit(ValueExpression valueExpression);
    void visit(VariableExpression variableExpression);
    void visit(BlockStatement blockStatement);
    void visit(BreakStatement breakStatement);
    void visit(DoWhileStatement doWhileStatement);
    void visit(ElementAssignmentArrayStatement elementAssignmentArrayStatement);
    void visit(ForRangeStatement forRangeStatement);
    void visit(ConditionalStatement ifElseStatement);
    void visit(ContinueStatement continueStatement);
    void visit(MultiplyAssignmentStatement multiplyAssignmentStatement);
    void visit(PassStatement passStatement);
    void visit(SwitchBreakStatement switchBreakStatement);
    void visit(SwitchStatement switchStatement);
    void visit(UnaryStatement unaryStatement);
    void visit(WhileStatement whileStatement);
    void visit(ReturnStatement returnStatement);
    void visit(DefineFunctionStatement defineFunctionStatement);
    void visit(ForEachStatement forEachStatement);
    void visit(BinaryConditionalExpression binaryConditionalExpression);
    void visit(ImportStatement importStatement);
    void visit(AssignmentExpression assignmentExpression);
    void visit(AssignmentOperatorExpression assignmentOperatorExpression);
    void visit(ElementValueArrayExpression elementValueArrayExpression);
    void visit(FunctionCallValueExpression elementFunctionArrayExpression);
    void visit(ExpressionStatement expressionStatement);
    void visit(LabelStatement labelStatement);
    void visit(BreakLabelStatement breakLabelStatement);
}