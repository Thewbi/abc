package de.compiling.handler;

import java.io.IOException;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlrfun.ASTWalker;
import org.antlrfun.Expression;
import org.antlrfun.InitDeclarator;
import org.antlrfun.NodeWalker;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cgrammar.CParser;
import org.cgrammar.CParser.InitDeclaratorContext;
import org.cgrammar.CParser.InitDeclaratorListContext;

public class InitDeclaratorListRuleHandler implements RuleHandler<CParser.InitDeclaratorListContext> {

	private static final Logger LOG = LogManager.getLogger(InitDeclaratorListRuleHandler.class);

	@Override
	public void processEnter(final InitDeclaratorListContext ctx, final Map<String, Object> properties,
			final ASTWalker astWalker) throws IOException {

		final NodeWalker nodeWalker = new NodeWalker();
		nodeWalker.setName("?");

		final InitDeclaratorRuleHandler initDeclaratorRuleHandler = new InitDeclaratorRuleHandler();
		nodeWalker.getRuleHandlers().put(CParser.InitDeclaratorContext.class, initDeclaratorRuleHandler);

		for (int i = 0; i < ctx.getChildCount(); i++) {

			final ParseTree child = ctx.getChild(i);

			if (child instanceof InitDeclaratorContext) {
				nodeWalker.walk(child, 0);

//				final InitDeclarator initDeclarator = nodeWalker.getInitDeclarators().get(0);
//				astWalker.getInitDeclarators().add(initDeclarator);

				final InitDeclarator initDeclarator = new InitDeclarator();

				initDeclarator.setName(nodeWalker.getVariableName());

				if (CollectionUtils.isNotEmpty(nodeWalker.getExpressionList())) {
					final Expression expression = nodeWalker.getExpressionList().get(0);
					initDeclarator.setExpression(expression);
				}

				astWalker.getInitDeclarators().add(initDeclarator);
			}

			nodeWalker.getInitDeclarators().clear();

		}

	}

	@Override
	public void processExit(final InitDeclaratorListContext ctx, final Map<String, Object> properties,
			final ASTWalker astWalker) throws IOException {
		// TODO Auto-generated method stub

	}

}
