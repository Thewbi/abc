package de.compiling.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.antlrfun.ASTWalker;
import org.antlrfun.CWalker;
import org.antlrfun.Expression;
import org.antlrfun.InitDeclarator;
import org.antlrfun.NodeWalker;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cgrammar.CParser;
import org.cgrammar.CParser.DeclarationContext;
import org.cgrammar.CParser.DeclarationSpecifiersContext;
import org.cgrammar.CParser.RelationalExpressionContext;

public class DeclarationRuleHandler implements RuleHandler<CParser.DeclarationContext> {

	private static final Logger LOG = LogManager.getLogger(DeclarationRuleHandler.class);

	@Override
	public void processEnter(final DeclarationContext ctx, final Map<String, Object> properties,
			final ASTWalker astWalker) throws IOException {

		LOG.info("Declaration: " + ctx.getText());

		final NodeWalker nodeWalker = new NodeWalker();
		nodeWalker.setName("DECL");

		final DeclarationSpecifiersRuleHandler declarationSpecifiersRuleHandler = new DeclarationSpecifiersRuleHandler();
		nodeWalker.getRuleHandlers().put(CParser.DeclarationSpecifiersContext.class, declarationSpecifiersRuleHandler);

//		final InitDeclaratorRuleHandler initDeclaratorRuleHandler = new InitDeclaratorRuleHandler();
//		nodeWalker.getRuleHandlers().put(CParser.InitDeclaratorContext.class, initDeclaratorRuleHandler);

		final InitDeclaratorListRuleHandler initDeclaratorListRuleHandler = new InitDeclaratorListRuleHandler();
		nodeWalker.getRuleHandlers().put(CParser.InitDeclaratorListContext.class, initDeclaratorListRuleHandler);

		for (int i = 0; i < ctx.getChildCount(); i++) {

			LOG.info("Declaration: " + ctx.getText());

			final ParseTree child = ctx.getChild(i);
			nodeWalker.walk(child, 0);

//			if (child instanceof RelationalExpressionContext) {
//
//				nodeWalker.walk(child, 0);
//
//			} else if (child instanceof TerminalNodeImpl) {
//
//				System.out.println(child.getText());
//
//				final Expression mathematicalOperatorExpression = new Expression();
//				mathematicalOperatorExpression.setOperator(child.getText());
//				nodeWalker.getExpressionList().add(mathematicalOperatorExpression);
//			}
		}

		// first initDeclarator contains the type
		final InitDeclarator initDeclarator0 = nodeWalker.getInitDeclarators().get(0);
		initDeclarator0.setName(nodeWalker.getVariableName());

		if (CollectionUtils.isNotEmpty(nodeWalker.getExpressionList())) {
			initDeclarator0.setValue(nodeWalker.getExpressionList().get(0).getRhs());
		}

		// combine the type from the first initDeclarator with the rest of the
		// initDeclarators
		// which are added for each variable
		for (int i = 1; i < nodeWalker.getInitDeclarators().size(); i++) {

			final InitDeclarator initDeclarator = nodeWalker.getInitDeclarators().get(i);
			initDeclarator.getValueList().addAll(initDeclarator0.getValueList());

			LOG.info(initDeclarator);

			// TODO: generate code for this
			astWalker.getInitDeclarators().add(initDeclarator);
		}

//		final List<InitDeclarator> initDeclarators = new ArrayList<>();
//
//		for (final InitDeclarator initDeclarator : initDeclarators) {
//
//			LOG.info("");
//
//			LOG.info("[Declaration] New variable: type: '" + typeSpecifier + "' name: '" + initDeclarator.getName()
//					+ "' Initializer: '" + initDeclarator.getValue() + "' InitializerList: '"
//					+ initDeclarator.getValueList() + "' storageClassSpecifier: "
//					+ initDeclarator.getStorageClassSpecifier() + " arrayDimensions: " + arrayDimensions);
////			LOG.info(initDeclarator);
//
//			LOG.info("");
//		}
	}

	@Override
	public void processExit(final DeclarationContext ctx, final Map<String, Object> properties,
			final ASTWalker astWalker) throws IOException {
		// TODO Auto-generated method stub

	}

}
