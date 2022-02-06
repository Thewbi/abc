package org.antlrfun;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cgrammar.CBaseListener;
import org.cgrammar.CParser;
import org.cgrammar.CParser.AssignmentExpressionContext;
import org.cgrammar.CParser.AssignmentOperatorContext;
import org.cgrammar.CParser.DeclarationSpecifierContext;
import org.cgrammar.CParser.DeclarationSpecifiersContext;
import org.cgrammar.CParser.DeclaratorContext;
import org.cgrammar.CParser.InitDeclaratorContext;
import org.cgrammar.CParser.InitializerContext;
import org.cgrammar.CParser.TypedefNameContext;

import de.compiling.handler.RuleHandler;

public class CWalker extends CBaseListener implements ASTWalker {

	private static final Logger LOG = LogManager.getLogger(CWalker.class);

	private static final int IDENTIFIER_TOKEN_TYPE = 105;

	private static final int CONSTANT_TOKEN_TYPE = 106;

	private static final int STRING_LITERAL_TOKEN_TYPE = 108;

	private final Stack<String> declaractionName = new Stack<String>();

	private final Map<Class<?>, RuleHandler> ruleHandlers = new HashMap<>();

	private final List<Argument> argumentList = new ArrayList<>();

	private final List<Data> dataList = new ArrayList<>();

	final BufferedWriter bufferedWriter;

	private List<String> typeSpecifier = new ArrayList<String>();

	private final Stack<List<String>> typeSpecifierStack = new Stack<List<String>>();

	private String variableName;

	/**
	 * The initializer is used to store an idividual value in a declaration like
	 * this: int a = 1;
	 */
	private String initializer;

	/**
	 * initializerList exists because of value lists like this. int x[] = {1, 2, 3};
	 * The initializerList is supposed to store the values 1, 2, 3
	 */
	private List<String> initializerList;

	private String typedefName;

	private int arrayDimensions = -1;

	private String storageClassSpecifier;

	private final List<InitDeclarator> initDeclarators = new ArrayList<>();

//	private final Stack<String> expressionStack = new Stack<>();

	private final Map<ParserRuleContext, String> dtMap = new HashMap<>();

	private int tempCounter;

	private String currentVariable;

//	private Expression expression;

	private final List<Expression> expressionList = new ArrayList<>();

	public CWalker(final BufferedWriter bufferedWriter) {
		this.bufferedWriter = bufferedWriter;
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * The default implementation does nothing.
	 */
	@Override
	public void enterEveryRule(@NotNull final ParserRuleContext ctx) {
//		System.out.println("Enter: " + ctx.getText());
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * The default implementation does nothing.
	 */
	@Override
	public void exitEveryRule(@NotNull final ParserRuleContext ctx) {
//		System.out.println("Exit: " + ctx.getClass() + " " + ctx.getText());
	}

	/**
	 * if, else {@inheritDoc}
	 * <p/>
	 * The default implementation does nothing.
	 */
	@Override
	public void exitSelectionStatement(@NotNull final CParser.SelectionStatementContext ctx) {

		LOG.info("Selection: " + ctx.getText());

		final ParseTree child0 = ctx.getChild(0);

		if (child0.getText().equals("if")) {

//			final ParseTree child2 = ctx.getChild(2);

			LOG.info("if statement found with condition: {}", expressionList);

		}
	}

	@Override
	public void exitAssignmentExpression(@NotNull final CParser.AssignmentExpressionContext ctx) {

		LOG.info("AssignmentExpression: " + ctx.getText());

		ParseTree before = null;

		for (int i = 0; i < ctx.getChildCount(); i++) {

			final String text = ctx.getChild(i).getText();
			if (text.equalsIgnoreCase("=")) {

				LOG.info(before.getText() + " = " + "temp" + tempCounter);

				dtMap.clear();
				tempCounter = 0;
			}

			before = ctx.getChild(i);
		}
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * The default implementation does nothing.
	 */
	@Override
	public void exitEqualityExpression(@NotNull final CParser.EqualityExpressionContext ctx) {

		LOG.info("exitEqualityExpression: " + ctx.getText() + " ChildCount: " + ctx.getChildCount());

		if (ctx.getChildCount() == 3) {

			final ParseTree child0 = ctx.children.get(0);
			final ParseTree child1 = ctx.children.get(1);
			final ParseTree child2 = ctx.children.get(2);

			final Expression expression = new Expression();
			expressionList.add(expression);
			expression.setLhs(child0.getText());
			expression.setOperator(child1.getText());
			expression.setRhs(child2.getText());
		}
	}

	@Override
	public void exitExpression(@NotNull final CParser.ExpressionContext ctx) {

	}

	/**
	 * detects printf
	 */
	@Override
	public void exitPrimaryExpression(@NotNull final CParser.PrimaryExpressionContext ctx) {

		final Token startToken = ctx.getStart();

//		System.out.println("PrimaryExpression: " + ctx.getText() + " Token: " + startToken);

		final Argument argument = new Argument();
		argumentList.add(argument);
		argument.setToken(startToken);
		argument.setType(startToken.getType());
		argument.setValue(startToken.getText());

//		expressionStack.push(startToken.getText());
//		dtMap.put(ctx, startToken.getText());

//		for (int i = 0; i < ctx.getChildCount(); i++) {
//			final ParseTree child = ctx.getChild(i);
//
//			expressionStack.push(child.getText());
//		}

//		// DEBUG
//		switch (startToken.getType()) {
//
//		case IDENTIFIER_TOKEN_TYPE:
//			System.out.println("Identifier: " + startToken.getText());
//			break;
//
//		case CONSTANT_TOKEN_TYPE:
//			System.out.println("Constant: " + startToken.getText());
//			break;
//
//		case STRING_LITERAL_TOKEN_TYPE:
//			System.out.println("StringLiteral: " + startToken.getText());
//			break;
//		}

	}

	@Override
	public void exitAdditiveExpression(@NotNull final CParser.AdditiveExpressionContext ctx) {
//		expressionStack.push("+");

		// TODO: generate assembler statements that perform the mathematical operation
		// and store it into the variable

		currentVariable = "temp" + tempCounter++;
		dtMap.put(ctx, currentVariable);

		// DEBUG output string
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(currentVariable + " = ");
		for (int i = 0; i < ctx.getChildCount(); i++) {

			final ParseTree child = ctx.getChild(i);
			final String text = child.getText();
			if (text.equalsIgnoreCase("+")) {
				stringBuilder.append("+");
			} else if (text.equalsIgnoreCase("-")) {
				stringBuilder.append("-");
			} else {
				final String dtText = dtMap.get(child);
				stringBuilder.append(dtText);
			}
		}
		LOG.info("exitAdditiveExpression: " + stringBuilder);

		final Expression expression = new Expression();
		expressionList.add(expression);
		expression.setLhs(currentVariable);
	}

	@Override
	public void exitMultiplicativeExpression(@NotNull final CParser.MultiplicativeExpressionContext ctx) {

		final StringBuilder stringBuilder = new StringBuilder();

		if (ctx.getChildCount() == 1) {
			dtMap.put(ctx, ctx.getText());
		} else {

			final String tempVar = "temp" + tempCounter++;

			stringBuilder.append(tempVar + " = ");

			for (int i = 0; i < ctx.getChildCount(); i++) {

				final ParseTree child = ctx.getChild(i);
				final String text = child.getText();

				if (text.equalsIgnoreCase("*")) {
					stringBuilder.append("*");
				} else if (text.equalsIgnoreCase("/")) {
					stringBuilder.append("/");
				} else {
					final String dtText = findDTText(child);
					if (StringUtils.isBlank(dtText)) {
						stringBuilder.append(text);
					} else {
						stringBuilder.append(dtText);
					}
				}
			}

			// TODO: generate to that performs the mathematical operation and stores it into
			// the variable

			LOG.info(stringBuilder);
			dtMap.put(ctx, tempVar);
		}
	}

	private String findDTText(final ParseTree parseTree) {

		if (dtMap.containsKey(parseTree)) {
			return dtMap.get(parseTree);
		}

		for (int i = 0; i < parseTree.getChildCount(); i++) {

			final ParseTree child = parseTree.getChild(i);

			final String childString = findDTText(child);
			if (StringUtils.isNotBlank(childString)) {
				return childString;
			}
		}

		return null;
	}

	@Override
	public void visitTerminal(@NotNull final TerminalNode node) {
		final String text = node.getText();

//		if (text.equalsIgnoreCase("+")) {
//			expressionStack.push("+");
//		} else if (text.equalsIgnoreCase("-")) {
//			expressionStack.push("-");
//		} else if (text.equalsIgnoreCase("*")) {
//			expressionStack.push("*");
//		} else if (text.equalsIgnoreCase("/")) {
//			expressionStack.push("/");
//		}
	}

	@Override
	public void exitAssignmentOperator(@NotNull final CParser.AssignmentOperatorContext ctx) {

		LOG.info(ctx.getText());
//		expressionStack.push("=");
		dtMap.clear();
		tempCounter = 0;
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * The default implementation does nothing.
	 */
	@Override
	public void enterFunctionDefinition(@NotNull final CParser.FunctionDefinitionContext ctx) {

		// save the type of the function in it's own typeSpecifier list, so it is not
		// overriden until exitFunctionDefinition()
		typeSpecifier = new ArrayList<>();
		typeSpecifierStack.push(typeSpecifier);

//		System.out.println("Enter: " + ctx.getText());
//
//		final RuleHandler ruleHandler = ruleHandlers.get(ctx.getClass());
////
//		ruleHandler.process(ctx);

//		final Map<String, String> properties = new HashMap<>();
//
//		// the declaration name was stored in exitDirectDeclarator()
//		properties.put("func_name", declaractionName);
//
//		final RuleHandler ruleHandler = ruleHandlers.get(ctx.getClass());
//		try {
//			ruleHandler.processEnter(ctx, properties);
//		} catch (final IOException e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * The default implementation does nothing.
	 */
	@Override
	public void exitFunctionDefinition(@NotNull final CParser.FunctionDefinitionContext ctx) {
		final RuleHandler ruleHandler = ruleHandlers.get(ctx.getClass());

		final Map<String, String> properties = new HashMap<>();

		if (!declaractionName.isEmpty()) {

			// the declaration name was stored in exitDirectDeclarator()
			properties.put("func_name", declaractionName.pop());

			// call the handler so it can generated code
			try {
				ruleHandler.processExit(ctx, properties, this);
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}

		// go back to the initial typeSpecifier list
		typeSpecifier = typeSpecifierStack.pop();
	}

	/**
	 * This one is supposed to store the name of the function that is currently
	 * declared so that name can be used in the exitFunctionDefinition() call.
	 */
	@Override
	public void exitDirectDeclarator(@NotNull final CParser.DirectDeclaratorContext ctx) {

		if (ctx.getChildCount() == 1) {

			declaractionName.push(ctx.getText());

		} else {

			for (int i = 0; i < ctx.getChildCount(); i++) {
				final ParseTree child = ctx.getChild(i);

				if (child instanceof TerminalNode) {
					if (child.getText().equals("[")) {
						LOG.info("ARRAY FOUND!");

						final String nextTokenAsString = ctx.getChild(i + 1).getText();

						if (nextTokenAsString.equals("]")) {

						} else {
							arrayDimensions = Integer.parseInt(nextTokenAsString);
						}
					}
				}
			}
		}
	}

	@Override
	public void exitDeclaration(@NotNull final CParser.DeclarationContext ctx) {

		for (final InitDeclarator initDeclarator : initDeclarators) {

			LOG.info("");

			LOG.info("[Declaration] New variable: type: '" + typeSpecifier + "' name: '" + initDeclarator.getName()
					+ "' Initializer: '" + initDeclarator.getValue() + "' InitializerList: '"
					+ initDeclarator.getValueList() + "' storageClassSpecifier: "
					+ initDeclarator.getStorageClassSpecifier() + " arrayDimensions: " + arrayDimensions);
//			LOG.info(initDeclarator);

			LOG.info("");
		}

		// reset
		initDeclarators.clear();
		typeSpecifier.clear();
		arrayDimensions = -1;
		initializer = "";
		currentVariable = "";
	}

	@Override
	public void enterCompoundStatement(@NotNull final CParser.CompoundStatementContext ctx) {

		// here add a new typeSpecifier list for the variables inside the current
		// function
		// so that the types do not mix with the type of the current function
		typeSpecifier = new ArrayList<>();
		typeSpecifierStack.push(typeSpecifier);
	}

	@Override
	public void exitCompoundStatement(@NotNull final CParser.CompoundStatementContext ctx) {
		typeSpecifier = typeSpecifierStack.pop();
	}

	@Override
	public void exitStorageClassSpecifier(@NotNull final CParser.StorageClassSpecifierContext ctx) {

//		System.out.println(ctx.getText());
		storageClassSpecifier = ctx.getText();
	}

	/**
	 * This method is only called for variable declarations with initializer:
	 *
	 * <pre>
	 * int b = 1;
	 * </pre>
	 *
	 * or for several variables:
	 *
	 * <pre>
	 * int i, j, k;
	 * </pre>
	 *
	 * For individual variables without initializer,
	 *
	 * <pre>
	 * int a;
	 * </pre>
	 *
	 * the variable is detected in exit DeclarationSpecifier() because for
	 * individual variables without initializer, no InitDeclarator rule is reduced
	 * by the parser!
	 *
	 * <pre>
	 * </pre>
	 *
	 */
	@Override
	public void exitInitDeclarator(@NotNull final CParser.InitDeclaratorContext ctx) {

		LOG.info("InitDeclarator: " + ctx.getText());

//		final String initDeclarator = declaractionName.push(ctx.getText());

		if (!declaractionName.empty()) {

			final String declarator = declaractionName.pop();

			// TODO:
			// Add a new variable into the hashmap of all variables in this scope

			LOG.info("initializer: " + initializer);
			LOG.info("initializer list: " + initializerList);
			LOG.info("currentVariable: " + currentVariable);

			final InitDeclarator initDeclarator = new InitDeclarator();
			initDeclarators.add(initDeclarator);
			initDeclarator.setName(declarator);

			if (StringUtils.isNotBlank(initializer)) {
				initializer = currentVariable;
			}

			if (initializerList != null) {
				initDeclarator.setValueList(initializerList);
				initializerList = null;
			} else {
				initDeclarator.setValue(initializer);
			}
//			initDeclarator.setValue(currentVariable);
			initDeclarator.setStorageClassSpecifier(storageClassSpecifier);

			LOG.info("New InitDeclarator added: " + initDeclarator);

			storageClassSpecifier = "";
			currentVariable = "";
		}
	}

	@Override
	public void exitTypedefName(@NotNull final CParser.TypedefNameContext ctx) {

		final InitDeclarator initDeclarator = new InitDeclarator();
		initDeclarators.add(initDeclarator);
		initDeclarator.setName(ctx.getText());
		initDeclarator.setStorageClassSpecifier(storageClassSpecifier);
//		initDeclarator.setValue(initializer);

		storageClassSpecifier = "";
	}

	@Override
	public void enterInitializer(@NotNull final CParser.InitializerContext ctx) {
		if (ctx.getStart().getText().equals("{")) {
			initializerList = new ArrayList<>();
		}
	}

	@Override
	public void exitInitializer(@NotNull final CParser.InitializerContext ctx) {
//		System.out.println("Initializer: " + ctx.getText());

		initializer = ctx.getText();

		if (ctx.getChildCount() == 1) {
			// if there is a initializer list detected, then put the symbols into a list
			// a initializer list looks like this: int x[] = {1,2,3};
			if (initializerList != null) {
				initializerList.add(ctx.getText());
			}
		}

//		final ParseTree child0 = ctx.getChild(0);
//		final ParseTree child1 = ctx.getChild(1);
//
//		System.out.println(child0.getText());
//		System.out.println(child1.getText());
	}

//	/**
//	 * detects "Hello, World!"
//	 */
//	@Override
//	public void exitUnaryExpression(@NotNull final CParser.UnaryExpressionContext ctx) {
//		System.out.println("" + ctx.getText());
//	}

	@Override
	public void exitArgumentExpressionList(@NotNull final CParser.ArgumentExpressionListContext ctx) {
//		System.out.println("ArgumentExpressionList: " + ctx.getText());
//
//		final Token start = ctx.getStart();
//		System.out.println("Token: " + start);

//		final List<TerminalNode> tokens = ctx.getTokens(108);
//		System.out.println(tokens);
	}

	@Override
	public void exitStatement(@NotNull final CParser.StatementContext ctx) {
//		System.out.println("exitStatement: " + ctx.getText());

		final String firstToken = ctx.getStart().getText();
//		System.out.println("firstToken: " + firstToken);
//		System.out.println("argumentList: " + argumentList);

		final Map<String, Object> properties = new HashMap<>();

		// the declaration name was stored in exitDirectDeclarator()
		properties.put("args", argumentList);

		final RuleHandler ruleHandler = ruleHandlers.get(ctx.getClass());
		if (ruleHandler == null) {
			LOG.info("No rule handler for: " + ctx.getClass().getSimpleName());
		} else {
			try {
				ruleHandler.processExit(ctx, properties, this);
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}

		// reset
		argumentList.clear();
	}

	@Override
	public void exitJumpStatement(@NotNull final CParser.JumpStatementContext ctx) {
		final Map<String, Object> properties = new HashMap<>();

		// the declaration name was stored in exitDirectDeclarator()
		final String firstToken = ctx.getStart().getText();
		properties.put("type", firstToken);
		properties.put("args", argumentList);

		final RuleHandler ruleHandler = ruleHandlers.get(ctx.getClass());
		try {
			ruleHandler.processExit(ctx, properties, this);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		// reset
		argumentList.clear();
	}

	/**
	 * int
	 */
	@Override
	public void exitDeclarationSpecifier(@NotNull final CParser.DeclarationSpecifierContext ctx) {
	}

	/**
	 * int i
	 */
	@Override
	public void exitTypeSpecifier(@NotNull final CParser.TypeSpecifierContext ctx) {
		final TypedefNameContext typedefName = ctx.typedefName();
		if (typedefName != null) {
			declaractionName.push(typedefName.getText());
		} else {
			typeSpecifier.add(ctx.getText());
		}
	}

	public Map<Class<?>, RuleHandler> getRuleHandlers() {
		return ruleHandlers;
	}

	public void outputDataSection() throws IOException {

		if (CollectionUtils.isEmpty(dataList)) {
			return;
		}

		bufferedWriter.write("\n\n\n");
		bufferedWriter.write("section .data\n");

		for (final Data data : dataList) {

			bufferedWriter.write("    " + data.getVarName() + ":    db      " + data.getValue() + ", 10\n");
			bufferedWriter.write("    .len:   equ     $ - " + data.getVarName() + "\n");
		}

	}

	@Override
	public List<Data> getDataList() {
		return dataList;
	}

//	@Override
//	public Expression getExpression() {
//		return expression;
//	}
//
//	@Override
//	public void setExpression(final Expression expression) {
//		// TODO Auto-generated method stub
//
//	}

	@Override
	public List<Expression> getExpressionList() {
		return expressionList;
	}

	public String getVariableName() {
		return variableName;
	}

	@Override
	public void setVariableName(final String variableName) {
		this.variableName = variableName;
	}

	@Override
	public List<InitDeclarator> getInitDeclarators() {
		return initDeclarators;
	}

//	public Stack<String> getExpressionStack() {
//		return expressionStack;
//	}

}
