package de.compiling.handler;

import java.io.IOException;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.antlrfun.ASTWalker;
import org.antlrfun.Expression;
import org.antlrfun.NodeWalker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cgrammar.CParser;
import org.cgrammar.CParser.EqualityExpressionContext;
import org.cgrammar.CParser.MultiplicativeExpressionContext;
import org.cgrammar.CParser.RelationalExpressionContext;

public class EqualityExpressionRuleHandler extends AbstractRuleHandler<CParser.EqualityExpressionContext> {

	private static final Logger LOG = LogManager.getLogger(EqualityExpressionRuleHandler.class);

	@Override
	public void processEnter(final EqualityExpressionContext ctx, final Map<String, Object> properties,
			final ASTWalker astWalker) throws IOException {

		LOG.info("EqualityExpressionRuleHandler: " + ctx.getText());

		final NodeWalker nodeWalker = new NodeWalker();
		nodeWalker.setName("EQ");

		final AdditiveExpressionRuleHandler additiveExpressionRuleHandler = getHandlerFactory()
				.createAdditiveExpressionRuleHandler();
		nodeWalker.getRuleHandlers().put(CParser.AdditiveExpressionContext.class, additiveExpressionRuleHandler);

//		final PrimaryExpressionRuleHandler primaryExpressionRuleHandler = new PrimaryExpressionRuleHandler();
//		nodeWalker.getRuleHandlers().put(CParser.PrimaryExpressionContext.class, primaryExpressionRuleHandler);

		for (int i = 0; i < ctx.getChildCount(); i++) {

			final ParseTree child = ctx.getChild(i);

			if (child instanceof RelationalExpressionContext) {

				nodeWalker.walk(child, 0);

			} else if (child instanceof TerminalNodeImpl) {

				LOG.info(child.getText());

				final Expression mathematicalOperatorExpression = new Expression();
				mathematicalOperatorExpression.setOperator(child.getText());
				nodeWalker.getExpressionList().add(mathematicalOperatorExpression);
			}
		}

		LOG.info("+ " + nodeWalker.getExpressionList());

		// pass expressions to the parent
//		astWalker.getExpressionList().clear();
		astWalker.getExpressionList().addAll(nodeWalker.getExpressionList());

	}

	@Override
	public void processExit(final EqualityExpressionContext ctx, final Map<String, Object> properties,
			final ASTWalker astWalker) throws IOException {
		// TODO Auto-generated method stub

	}

}
