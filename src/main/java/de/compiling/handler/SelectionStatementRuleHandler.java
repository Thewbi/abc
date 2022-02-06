package de.compiling.handler;

import java.io.IOException;
import java.util.Map;

import org.antlrfun.ASTWalker;
import org.antlrfun.CWalker;
import org.antlrfun.NodeWalker;
import org.cgrammar.CParser;
import org.cgrammar.CParser.FunctionDefinitionContext;
import org.cgrammar.CParser.SelectionStatementContext;

/**
 * if statements
 *
 */
public class SelectionStatementRuleHandler implements RuleHandler<CParser.SelectionStatementContext> {

	@Override
	public void processEnter(final SelectionStatementContext ctx, final Map<String, Object> properties,
			final ASTWalker astWalker) throws IOException {

		System.out.println(ctx.getText());

		final NodeWalker nodeWalker = new NodeWalker();
		nodeWalker.setName("IF");
//		nodeWalker.getRuleHandlers().put(CParser.SelectionStatementContext.class, selectionStatementRuleHandler);
//		nodeWalker.getRuleHandlers().put(CParser.FunctionDefinitionContext.class, functionDefinitionhandler);
//		nodeWalker.getRuleHandlers().put(CParser.StatementContext.class, statementRuleHandler);
//		nodeWalker.getRuleHandlers().put(CParser.JumpStatementContext.class, jumpStatementRuleHandler);

		final EqualityExpressionRuleHandler equalityExpressionRuleHandler = new EqualityExpressionRuleHandler();
		nodeWalker.getRuleHandlers().put(CParser.EqualityExpressionContext.class, equalityExpressionRuleHandler);

//		final AdditiveExpressionRuleHandler additiveExpressionRuleHandler = new AdditiveExpressionRuleHandler();
//		nodeWalker.getRuleHandlers().put(CParser.AdditiveExpressionContext.class, additiveExpressionRuleHandler);

//		final PrimaryExpressionRuleHandler primaryExpressionRuleHandler = new PrimaryExpressionRuleHandler();
//		nodeWalker.getRuleHandlers().put(CParser.PrimaryExpressionContext.class, primaryExpressionRuleHandler);

		// TODO: descend into the condition expression
		nodeWalker.walk(ctx.getChild(2), 0);
		System.out.println("CONDITION: " + nodeWalker.getExpressionList());

		// TODO: descend into the statement expression
		nodeWalker.getExpressionList().clear();
		nodeWalker.walk(ctx.getChild(4), 0);
		System.out.println("BODY: " + nodeWalker.getExpressionList());

	}

	@Override
	public void processExit(final SelectionStatementContext ctx, final Map<String, Object> properties,
			final ASTWalker astWalker) throws IOException {
		// TODO Auto-generated method stub

	}

}
