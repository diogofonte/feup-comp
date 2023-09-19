// Generated from comp2023/grammar/Javamm.g4 by ANTLR 4.12.0

    package pt.up.fe.comp2023;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link JavammParser}.
 */
public interface JavammListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link JavammParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(JavammParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavammParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(JavammParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavammParser#importDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterImportDeclaration(JavammParser.ImportDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavammParser#importDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitImportDeclaration(JavammParser.ImportDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavammParser#importAppend}.
	 * @param ctx the parse tree
	 */
	void enterImportAppend(JavammParser.ImportAppendContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavammParser#importAppend}.
	 * @param ctx the parse tree
	 */
	void exitImportAppend(JavammParser.ImportAppendContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavammParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(JavammParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavammParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(JavammParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavammParser#extendClass}.
	 * @param ctx the parse tree
	 */
	void enterExtendClass(JavammParser.ExtendClassContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavammParser#extendClass}.
	 * @param ctx the parse tree
	 */
	void exitExtendClass(JavammParser.ExtendClassContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavammParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclaration(JavammParser.VarDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavammParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclaration(JavammParser.VarDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mainMethodDeclaration}
	 * labeled alternative in {@link JavammParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMainMethodDeclaration(JavammParser.MainMethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mainMethodDeclaration}
	 * labeled alternative in {@link JavammParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMainMethodDeclaration(JavammParser.MainMethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code otherMethodDeclaration}
	 * labeled alternative in {@link JavammParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterOtherMethodDeclaration(JavammParser.OtherMethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code otherMethodDeclaration}
	 * labeled alternative in {@link JavammParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitOtherMethodDeclaration(JavammParser.OtherMethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavammParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(JavammParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavammParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(JavammParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavammParser#parameterAppend}.
	 * @param ctx the parse tree
	 */
	void enterParameterAppend(JavammParser.ParameterAppendContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavammParser#parameterAppend}.
	 * @param ctx the parse tree
	 */
	void exitParameterAppend(JavammParser.ParameterAppendContext ctx);
	/**
	 * Enter a parse tree produced by the {@code typeArray}
	 * labeled alternative in {@link JavammParser#type}.
	 * @param ctx the parse tree
	 */
	void enterTypeArray(JavammParser.TypeArrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code typeArray}
	 * labeled alternative in {@link JavammParser#type}.
	 * @param ctx the parse tree
	 */
	void exitTypeArray(JavammParser.TypeArrayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code typeBoolean}
	 * labeled alternative in {@link JavammParser#type}.
	 * @param ctx the parse tree
	 */
	void enterTypeBoolean(JavammParser.TypeBooleanContext ctx);
	/**
	 * Exit a parse tree produced by the {@code typeBoolean}
	 * labeled alternative in {@link JavammParser#type}.
	 * @param ctx the parse tree
	 */
	void exitTypeBoolean(JavammParser.TypeBooleanContext ctx);
	/**
	 * Enter a parse tree produced by the {@code typeInt}
	 * labeled alternative in {@link JavammParser#type}.
	 * @param ctx the parse tree
	 */
	void enterTypeInt(JavammParser.TypeIntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code typeInt}
	 * labeled alternative in {@link JavammParser#type}.
	 * @param ctx the parse tree
	 */
	void exitTypeInt(JavammParser.TypeIntContext ctx);
	/**
	 * Enter a parse tree produced by the {@code typeChar}
	 * labeled alternative in {@link JavammParser#type}.
	 * @param ctx the parse tree
	 */
	void enterTypeChar(JavammParser.TypeCharContext ctx);
	/**
	 * Exit a parse tree produced by the {@code typeChar}
	 * labeled alternative in {@link JavammParser#type}.
	 * @param ctx the parse tree
	 */
	void exitTypeChar(JavammParser.TypeCharContext ctx);
	/**
	 * Enter a parse tree produced by the {@code typeString}
	 * labeled alternative in {@link JavammParser#type}.
	 * @param ctx the parse tree
	 */
	void enterTypeString(JavammParser.TypeStringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code typeString}
	 * labeled alternative in {@link JavammParser#type}.
	 * @param ctx the parse tree
	 */
	void exitTypeString(JavammParser.TypeStringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code typeVoid}
	 * labeled alternative in {@link JavammParser#type}.
	 * @param ctx the parse tree
	 */
	void enterTypeVoid(JavammParser.TypeVoidContext ctx);
	/**
	 * Exit a parse tree produced by the {@code typeVoid}
	 * labeled alternative in {@link JavammParser#type}.
	 * @param ctx the parse tree
	 */
	void exitTypeVoid(JavammParser.TypeVoidContext ctx);
	/**
	 * Enter a parse tree produced by the {@code typeObject}
	 * labeled alternative in {@link JavammParser#type}.
	 * @param ctx the parse tree
	 */
	void enterTypeObject(JavammParser.TypeObjectContext ctx);
	/**
	 * Exit a parse tree produced by the {@code typeObject}
	 * labeled alternative in {@link JavammParser#type}.
	 * @param ctx the parse tree
	 */
	void exitTypeObject(JavammParser.TypeObjectContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bracketStatement}
	 * labeled alternative in {@link JavammParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterBracketStatement(JavammParser.BracketStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bracketStatement}
	 * labeled alternative in {@link JavammParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitBracketStatement(JavammParser.BracketStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifStatement}
	 * labeled alternative in {@link JavammParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(JavammParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifStatement}
	 * labeled alternative in {@link JavammParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(JavammParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileStatement}
	 * labeled alternative in {@link JavammParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(JavammParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileStatement}
	 * labeled alternative in {@link JavammParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(JavammParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code forStatement}
	 * labeled alternative in {@link JavammParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(JavammParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code forStatement}
	 * labeled alternative in {@link JavammParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(JavammParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionStatement}
	 * labeled alternative in {@link JavammParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(JavammParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionStatement}
	 * labeled alternative in {@link JavammParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(JavammParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignment}
	 * labeled alternative in {@link JavammParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(JavammParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignment}
	 * labeled alternative in {@link JavammParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(JavammParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code incAssignment}
	 * labeled alternative in {@link JavammParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterIncAssignment(JavammParser.IncAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code incAssignment}
	 * labeled alternative in {@link JavammParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitIncAssignment(JavammParser.IncAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayAssignment}
	 * labeled alternative in {@link JavammParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterArrayAssignment(JavammParser.ArrayAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayAssignment}
	 * labeled alternative in {@link JavammParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitArrayAssignment(JavammParser.ArrayAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parentheses}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParentheses(JavammParser.ParenthesesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parentheses}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParentheses(JavammParser.ParenthesesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code identifier}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(JavammParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identifier}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(JavammParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binaryOp}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBinaryOp(JavammParser.BinaryOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binaryOp}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBinaryOp(JavammParser.BinaryOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code false}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFalse(JavammParser.FalseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code false}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFalse(JavammParser.FalseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code this}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterThis(JavammParser.ThisContext ctx);
	/**
	 * Exit a parse tree produced by the {@code this}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitThis(JavammParser.ThisContext ctx);
	/**
	 * Enter a parse tree produced by the {@code length}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLength(JavammParser.LengthContext ctx);
	/**
	 * Exit a parse tree produced by the {@code length}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLength(JavammParser.LengthContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalAnd}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAnd(JavammParser.LogicalAndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalAnd}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAnd(JavammParser.LogicalAndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code integer}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterInteger(JavammParser.IntegerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code integer}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitInteger(JavammParser.IntegerContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relationalOp}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterRelationalOp(JavammParser.RelationalOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relationalOp}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitRelationalOp(JavammParser.RelationalOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayCreation}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArrayCreation(JavammParser.ArrayCreationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayCreation}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArrayCreation(JavammParser.ArrayCreationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code true}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterTrue(JavammParser.TrueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code true}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitTrue(JavammParser.TrueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayAccess}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArrayAccess(JavammParser.ArrayAccessContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayAccess}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArrayAccess(JavammParser.ArrayAccessContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryIncDec}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryIncDec(JavammParser.UnaryIncDecContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryIncDec}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryIncDec(JavammParser.UnaryIncDecContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalOr}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOr(JavammParser.LogicalOrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalOr}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOr(JavammParser.LogicalOrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code objectCreation}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterObjectCreation(JavammParser.ObjectCreationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code objectCreation}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitObjectCreation(JavammParser.ObjectCreationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryNot}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryNot(JavammParser.UnaryNotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryNot}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryNot(JavammParser.UnaryNotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code methodCall}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMethodCall(JavammParser.MethodCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code methodCall}
	 * labeled alternative in {@link JavammParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMethodCall(JavammParser.MethodCallContext ctx);
}