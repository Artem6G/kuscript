package parser.ast.visitors;

import parser.ast.Expression;
import parser.ast.Statement;
import parser.ast.Visitor;
import parser.ast.expressions.*;
import parser.ast.statements.*;

public class AbstractVisitor implements Visitor {
    @Override
    public void visit(NullValueExpression nullValueExpression) {

    }

    @Override
    public void visit(ValueExpression valueExpression) {

    }

    @Override
    public void visit(VariableExpression variableExpression) {

    }

    @Override
    public void visit(BreakStatement breakStatement) {

    }


    @Override
    public void visit(ContinueStatement continueStatement) {

    }

    @Override
    public void visit(PassStatement passStatement) {

    }

    @Override
    public void visit(ArrayExpression arrayExpression) {
        for (parser.ast.Expression expression : arrayExpression.expressions)
            expression.accept(this);
    }

    @Override
    public void visit(BinaryExpression binaryExpression) {
        binaryExpression.EXPR1.accept(this);
        binaryExpression.EXPR2.accept(this);
    }

    @Override
    public void visit(ElementArrayExpression elementArrayExpression) {
        for (parser.ast.Expression expression : elementArrayExpression.expressionIndexes)
            expression.accept(this);
    }

    @Override
    public void visit(TernaryExpression ternaryExpression) {
        ternaryExpression.falseExpr.accept(this);
        ternaryExpression.trueExpr.accept(this);
        ternaryExpression.condition.accept(this);
    }

    @Override
    public void visit(UnaryExpression unaryExpression) {
        unaryExpression.EXPR1.accept(this);
    }

    @Override
    public void visit(ReturnStatement returnStatement) {
        returnStatement.expression.accept(this);
    }

    @Override
    public void visit(DefineFunctionStatement defineFunctionStatement) {
        defineFunctionStatement.body.accept(this);
    }

    @Override
    public void visit(ForEachStatement forEachStatement) {
        forEachStatement.variableExpression.accept(this);
        forEachStatement.expression.accept(this);
        forEachStatement.statement.accept(this);
    }

    @Override
    public void visit(FullBypassForEachStatement fullBypassForEachStatement) {
        fullBypassForEachStatement.variableExpression.accept(this);
        fullBypassForEachStatement.expression.accept(this);
        fullBypassForEachStatement.statement.accept(this);
    }

    @Override
    public void visit(BinaryConditionalExpression binaryConditionalExpression) {
        binaryConditionalExpression.EXPR1.accept(this);
        binaryConditionalExpression.EXPR2.accept(this);
        binaryConditionalExpression.EXPR3.accept(this);
    }

    @Override
    public void visit(ImportStatement importStatement) {
        importStatement.expression.accept(this);
    }

    @Override
    public void visit(AssignmentExpression assignmentExpression) {
        assignmentExpression.expression.accept(this);
    }

    @Override
    public void visit(AssignmentOperatorExpression assignmentOperatorExpression) {
        assignmentOperatorExpression.EXPRESSION.accept(this);
    }

    @Override
    public void visit(ElementValueArrayExpression elementValueArrayExpression) {
        elementValueArrayExpression.expressionIndex.accept(this);
        elementValueArrayExpression.expression.accept(this);
    }

    @Override
    public void visit(FunctionCallValueExpression elementFunctionArrayExpression) {
        elementFunctionArrayExpression.variable.accept(this);
        for (Expression expression : elementFunctionArrayExpression.expressionList)
            expression.accept(this);
    }

    @Override
    public void visit(ExpressionStatement expressionStatement) {
        expressionStatement.expression.accept(this);
    }

    @Override
    public void visit(BlockStatement blockStatement) {
        for (Statement statement : blockStatement.statements)
            statement.accept(this);
    }

    @Override
    public void visit(CultivatedStatement cultivatedStatement) {
        cultivatedStatement.statement.accept(this);
    }

    @Override
    public void visit(DoWhileStatement doWhileStatement) {
        doWhileStatement.statement.accept(this);
        doWhileStatement.expression.accept(this);
    }

    @Override
    public void visit(ElementAssignmentArrayStatement elementAssignmentArrayStatement) {
        elementAssignmentArrayStatement.elementArrayExpression.accept(this);
        elementAssignmentArrayStatement.expression.accept(this);
    }

    @Override
    public void visit(ForRangeStatement forRangeStatement) {
        for (parser.ast.Expression expression : forRangeStatement.expressions)
            expression.accept(this);
        forRangeStatement.statement.accept(this);
        forRangeStatement.expression.accept(this);
    }

    @Override
    public void visit(ForStatement forStatement) {
        forStatement.firstStatement.accept(this);
        forStatement.secondStatement.accept(this);
        forStatement.thirdStatement.accept(this);
        forStatement.expression.accept(this);
    }

    @Override
    public void visit(IfElseStatement ifElseStatement) {
        ifElseStatement.elseStatement.accept(this);
        for (parser.ast.Expression expression : ifElseStatement.expressions)
            expression.accept(this);
        for (Statement statement : ifElseStatement.statements)
            statement.accept(this);
    }

    @Override
    public void visit(IfStatement ifStatement) {
        for (parser.ast.Expression expression : ifStatement.expressions)
            expression.accept(this);
        for (Statement statement : ifStatement.statements)
            statement.accept(this);
    }

    @Override
    public void visit(MultiplyAssignmentStatement multiplyAssignmentStatement) {
        for (parser.ast.Expression expression : multiplyAssignmentStatement.expressions)
            expression.accept(this);
    }

    @Override
    public void visit(RawBlockStatement rawBlockStatement) {
        for (Statement statement : rawBlockStatement.statements)
            statement.accept(this);
    }

    @Override
    public void visit(SwitchBreakStatement switchBreakStatement) {
        switchBreakStatement.defaultStatement.accept(this);
        for (parser.ast.Expression expression : switchBreakStatement.expressions)
            expression.accept(this);
        for (Statement statement : switchBreakStatement.statements)
            statement.accept(this);
    }

    @Override
    public void visit(SwitchStatement switchStatement) {
        switchStatement.defaultStatement.accept(this);
        for (parser.ast.Expression expression : switchStatement.expressions)
            expression.accept(this);
        for (Statement statement : switchStatement.statements)
            statement.accept(this);
    }

    @Override
    public void visit(UnaryStatement unaryStatement) {
        unaryStatement.unaryExpression.accept(this);
    }

    @Override
    public void visit(WhileStatement whileStatement) {
        whileStatement.expression.accept(this);
        whileStatement.statement.accept(this);
    }

}
