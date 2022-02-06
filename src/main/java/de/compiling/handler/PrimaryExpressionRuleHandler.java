package de.compiling.handler;

import java.io.IOException;
import java.util.Map;

import org.antlrfun.ASTWalker;
import org.antlrfun.Expression;
import org.cgrammar.CParser;
import org.cgrammar.CParser.PrimaryExpressionContext;

public class PrimaryExpressionRuleHandler implements RuleHandler<CParser.PrimaryExpressionContext> {

	@Override
	public void processEnter(final PrimaryExpressionContext ctx, final Map<String, Object> properties,
			final ASTWalker astWalker) throws IOException {

		System.out.println("PrimaryExpressionRuleHandler: " + ctx.getText());

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
