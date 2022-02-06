package de.compiling.handler;

import java.io.IOException;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlrfun.ASTWalker;
import org.antlrfun.Expression;
import org.antlrfun.InitDeclarator;
import org.antlrfun.NodeWalker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cgrammar.CParser;
import org.cgrammar.CParser.InitDeclaratorContext;

public class InitDeclaratorRuleHandler implements RuleHandler<CParser.InitDeclaratorContext> {

	private static final Logger LOG = LogManager.getLogger(InitDeclaratorRuleHandler.class);

	@Override
	public void processEnter(final InitDeclaratorContext ctx, final Map<String, Object> properties,
			final ASTWalker astWalker) throws IOException {

		LOG.info("InitDeclarator: " + ctx.getText());

		final InitDeclarator initDeclarator = new InitDeclarator();

		// retrieve variable name
		final ParseTree child0 = ctx.getChild(0);
		final String varName = child0.getText();
		astWalker.setVariableName(varName);
		initDeclarator.setName(varName);

		final ParseTree child2 = ctx.getChild(2);

		// retrieve initialization value if any
		if (child2 != null) {

			final NodeWalker nodeWalker = new NodeWalker();
			nodeWalker.setName("?");

			final EqualityExpressionRuleHandler equalityExpressionRuleHandler = new EqualityExpressionRuleHandler();
			nodeWalker.getRuleHandlers().put(CParser.EqualityExpressionContext.class, equalityExpressionRuleHandler);

			nodeWalker.walk(child2, 0);

			final Expression expression = nodeWalker.getExpressionList().get(0);

			LOG.info("VarName: " + varName + " is initialized with: " + expression);

			// send data to parent
			astWalker.getExpressionList().add(expression);

			initDeclarator.setExpression(expression);
			astWalker.getInitDeclarators().add(initDeclarator);
		}
	}

	@Override
	public void processExit(final InitDeclaratorContext ctx, final Map<String, Object> properties,
			final ASTWalker astWalker) throws IOException {
		// TODO Auto-generated method stub

	}

}
