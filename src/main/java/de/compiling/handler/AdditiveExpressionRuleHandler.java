package de.compiling.handler;

import java.io.IOException;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlrfun.ASTWalker;
import org.antlrfun.Expression;
import org.antlrfun.NodeWalker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cgrammar.CParser;
import org.cgrammar.CParser.AdditiveExpressionContext;
import org.cgrammar.CParser.MultiplicativeExpressionContext;

public class AdditiveExpressionRuleHandler extends AbstractRuleHandler<CParser.AdditiveExpressionContext> {

	private static final Logger LOG = LogManager.getLogger(AdditiveExpressionRuleHandler.class);

	@Override
	public void processEnter(final AdditiveExpressionContext ctx, final Map<String, Object> properties,
			final ASTWalker astWalker) throws IOException {

		LOG.trace("AdditiveExpressionRuleHandler: " + ctx.getText());

		final NodeWalker nodeWalker = new NodeWalker();
		nodeWalker.setName("ADDITIVE");

		final PrimaryExpressionRuleHandler primaryExpressionRuleHandler = getHandlerFactory()
				.createPrimaryExpressionRuleHandler();
		nodeWalker.getRuleHandlers().put(CParser.PrimaryExpressionContext.class, primaryExpressionRuleHandler);

		for (int i = 0; i < ctx.getChildCount(); i++) {

			final ParseTree child = ctx.getChild(i);

			if (child instanceof MultiplicativeExpressionContext) {

				nodeWalker.walk(child, 0);

			} else {
				LOG.trace(child.getText());

				final Expression mathematicalOperatorExpression = new Expression();
				mathematicalOperatorExpression.setOperator(child.getText());
				nodeWalker.getExpressionList().add(mathematicalOperatorExpression);
			}
		}

		LOG.trace("+ " + nodeWalker.getExpressionList());

		// parent data up into parent
		astWalker.getExpressionList().addAll(nodeWalker.getExpressionList());
	}

	@Override
	public void processExit(final AdditiveExpressionContext ctx, final Map<String, Object> properties,
			final ASTWalker astWalker) throws IOException {

	}

}
