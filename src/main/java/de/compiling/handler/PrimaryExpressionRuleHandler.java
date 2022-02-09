package de.compiling.handler;

import java.io.IOException;
import java.util.Map;

import org.antlrfun.ASTWalker;
import org.antlrfun.Expression;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cgrammar.CParser;
import org.cgrammar.CParser.PrimaryExpressionContext;

public class PrimaryExpressionRuleHandler extends AbstractRuleHandler<CParser.PrimaryExpressionContext> {

	private static final Logger LOG = LogManager.getLogger(PrimaryExpressionRuleHandler.class);

	@Override
	public void processEnter(final PrimaryExpressionContext ctx, final Map<String, Object> properties,
			final ASTWalker astWalker) throws IOException {

		LOG.info("PrimaryExpressionRuleHandler: " + ctx.getText());

		final Expression expression = new Expression();
		expression.setRhs(ctx.getText());

//		astWalker.setExpression(expression);
		astWalker.getExpressionList().add(expression);
	}

	@Override
	public void processExit(final PrimaryExpressionContext ctx, final Map<String, Object> properties,
			final ASTWalker ASTWalker) throws IOException {
		// TODO Auto-generated method stub

	}

}
