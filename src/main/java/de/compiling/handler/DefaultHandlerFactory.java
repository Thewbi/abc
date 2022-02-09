package de.compiling.handler;

import de.abc.scopes.ScopeController;

public class DefaultHandlerFactory implements HandlerFactory {

	private ScopeController scopeController;

	@Override
	public SelectionStatementRuleHandler createSelectionStatementRuleHandler() {

		final SelectionStatementRuleHandler selectionStatementRuleHandler = new SelectionStatementRuleHandler();
		selectionStatementRuleHandler.setScopeController(scopeController);
		selectionStatementRuleHandler.setHandlerFactory(this);

		return selectionStatementRuleHandler;
	}

	@Override
	public DeclarationSpecifiersRuleHandler createDeclarationSpecifiersRuleHandler() {

		final DeclarationSpecifiersRuleHandler declarationSpecifiersRuleHandler = new DeclarationSpecifiersRuleHandler();
		declarationSpecifiersRuleHandler.setScopeController(scopeController);
		declarationSpecifiersRuleHandler.setHandlerFactory(this);

		return declarationSpecifiersRuleHandler;
	}

	@Override
	public InitDeclaratorRuleHandler createInitDeclaratorRuleHandler() {

		final InitDeclaratorRuleHandler initDeclaratorRuleHandler = new InitDeclaratorRuleHandler();
		initDeclaratorRuleHandler.setScopeController(scopeController);
		initDeclaratorRuleHandler.setHandlerFactory(this);

		return initDeclaratorRuleHandler;
	}

	@Override
	public EqualityExpressionRuleHandler createEqualityExpressionRuleHandler() {

		final EqualityExpressionRuleHandler equalityExpressionRuleHandler = new EqualityExpressionRuleHandler();
		equalityExpressionRuleHandler.setScopeController(scopeController);
		equalityExpressionRuleHandler.setHandlerFactory(this);

		return equalityExpressionRuleHandler;
	}

	@Override
	public InitDeclaratorListRuleHandler createInitDeclaratorListRuleHandler() {

		final InitDeclaratorListRuleHandler initDeclaratorListRuleHandler = new InitDeclaratorListRuleHandler();
		initDeclaratorListRuleHandler.setScopeController(scopeController);
		initDeclaratorListRuleHandler.setHandlerFactory(this);

		return initDeclaratorListRuleHandler;
	}

	@Override
	public PrimaryExpressionRuleHandler createPrimaryExpressionRuleHandler() {

		final PrimaryExpressionRuleHandler primaryExpressionRuleHandler = new PrimaryExpressionRuleHandler();
		primaryExpressionRuleHandler.setScopeController(scopeController);
		primaryExpressionRuleHandler.setHandlerFactory(this);

		return primaryExpressionRuleHandler;
	}

	public DeclarationRuleHandler createDeclarationRuleHandler() {

		final DeclarationRuleHandler declarationRuleHandler = new DeclarationRuleHandler();
		declarationRuleHandler.setScopeController(scopeController);
		declarationRuleHandler.setHandlerFactory(this);

		return declarationRuleHandler;
	}

	@Override
	public AdditiveExpressionRuleHandler createAdditiveExpressionRuleHandler() {

		final AdditiveExpressionRuleHandler additiveExpressionRuleHandler = new AdditiveExpressionRuleHandler();
		additiveExpressionRuleHandler.setScopeController(scopeController);
		additiveExpressionRuleHandler.setHandlerFactory(this);

		return additiveExpressionRuleHandler;
	}

	public ScopeController getScopeController() {
		return scopeController;
	}

	public void setScopeController(final ScopeController scopeController) {
		this.scopeController = scopeController;
	}

}
