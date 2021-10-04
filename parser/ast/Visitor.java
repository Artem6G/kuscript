package parser.ast;

import parser.ast.expressions.*;
import parser.ast.expressions.ConstantExpression;
import parser.ast.statements.*;

public interface Visitor {
    void visit(ArrayExpression arrayExpression);
    void visit(BinaryExpression binaryExpression);
    void visit(ElementArrayExpression elementArrayExpression);
    void visit(NullValueExpression nullValueExpression);
    void visit(TernaryExpression ternaryExpression);
    void visit(UnaryExpression unaryExpression);
    void visit(ValueExpression valueExpression);
    void visit(VariableExpression variableExpression);
    void visit(ConstantExpression constantExpression);
    void visit(AssignmentOperatorStatement assignmentOperatorStatement);
    void visit(AssignmentStatement assignmentStatement);
    void visit(BlockStatement blockStatement);
    void visit(BreakStatement breakStatement);
    void visit(CultivatedStatement cultivatedStatement);
    void visit(DoWhileStatement doWhileStatement);
    void visit(ElementAssignmentArrayStatement elementAssignmentArrayStatement);
    void visit(ForBooleanStatement forBooleanStatement);
    void visit(ForRangeStatement forRangeStatement);
    void visit(ForStatement forStatement);
    void visit(IfElseStatement ifElseStatement);
    void visit(ContinueStatement continueStatement);
    void visit(IfStatement ifStatement);
    void visit(MultiplyAssignmentStatement multiplyAssignmentStatement);
    void visit(PassStatement passStatement);
    void visit(RawBlockStatement rawBlockStatement);
    void visit(SwitchBreakStatement switchBreakStatement);
    void visit(SwitchStatement switchStatement);
    void visit(UnaryStatement unaryStatement);
    void visit(WhileStatement whileStatement);
    void visit(ReturnStatement returnStatement);
    void visit(DefineFunctionStatement defineFunctionStatement);
    void visit(FunctionCallExpression functionCallExpression);
    void visit(FunctionCallStatement functionCallStatement);
    void visit(ForEachStatement forEachStatement);
    void visit(BinaryConditionalExpression binaryConditionalExpression);
    void visit(ImportStatement importStatement);
    void visit(AssignmentExpression assignmentExpression);
    void visit(AssignmentOperatorExpression assignmentOperatorExpression);
}