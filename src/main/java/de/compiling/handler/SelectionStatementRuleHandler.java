package de.compiling.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.antlrfun.ASTWalker;
import org.antlrfun.Expression;
import org.antlrfun.NodeWalker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cgrammar.CParser;
import org.cgrammar.CParser.SelectionStatementContext;

import de.abc.exceptions.ScopeException;

/**
 * if statements
 */
public class SelectionStatementRuleHandler extends AbstractRuleHandler<CParser.SelectionStatementContext> {

	private static final Logger LOG = LogManager.getLogger(SelectionStatementRuleHandler.class);

	@Override
	public void processEnter(final SelectionStatementContext ctx, final Map<String, Object> properties,
			final ASTWalker astWalker) throws IOException, ScopeException {

		LOG.trace(ctx.getText());

		final NodeWalker nodeWalker = createNodeWalker();

		// TODO: descend into the condition expression -- add scope
		nodeWalker.walk(ctx.getChild(2), 0);
		final List<Expression> conditionExpressionsList = new ArrayList<>();
		conditionExpressionsList.addAll(nodeWalker.getExpressionList());

		LOG.trace("CONDITION: " + conditionExpressionsList);

		// TODO: descend into the statement expression -- add scope
		nodeWalker.getExpressionList().clear();
		nodeWalker.walk(ctx.getChild(4), 0);
		final List<Expression> statementExpressionsList = new ArrayList<>();
		statementExpressionsList.addAll(nodeWalker.getExpressionList());

		LOG.trace("BODY: " + statementExpressionsList);

		// inform the backend about a newly parsed if statement
		getBackend().ifStatement(conditionExpressionsList, statementExpressionsList);
	}

	private NodeWalker createNodeWalker() {
		
		final NodeWalker nodeWalker = new NodeWalker();
		nodeWalker.setName(RuleHandler.at());

		final EqualityExpressionRuleHandler equalityExpressionRuleHandler = getHandlerFactory()
				.createEqualityExpressionRuleHandler();
		nodeWalker.getRuleHandlers().put(CParser.EqualityExpressionContext.class, equalityExpressionRuleHandler);
		
		return nodeWalker;
	}

	@Override
	public void processExit(final SelectionStatementContext ctx, final Map<String, Object> properties,
			final ASTWalker astWalker) throws IOException {
		// TODO Auto-generated method stub

	}

}
