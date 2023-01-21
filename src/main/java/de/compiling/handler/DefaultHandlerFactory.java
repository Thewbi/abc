package de.compiling.handler;

import de.abc.scopes.ScopeController;
import de.backend.Backend;

public class DefaultHandlerFactory implements HandlerFactory {

	private ScopeController scopeController;

	private Backend backend;

	@Override
	public SelectionStatementRuleHandler createSelectionStatementRuleHandler() {

		final SelectionStatementRuleHandler selectionStatementRuleHandler = new SelectionStatementRuleHandler();
		selectionStatementRuleHandler.setScopeController(scopeController);
		selectionStatementRuleHandler.setHandlerFactory(this);
		selectionStatementRuleHandler.setBackend(backend);

		return selectionStatementRuleHandler;
	}

	@Override
	public DeclarationSpecifiersRuleHandler createDeclarationSpecifiersRuleHandler() {

		final DeclarationSpecifiersRuleHandler declarationSpecifiersRuleHandler = new DeclarationSpecifiersRuleHandler();
		declarationSpecifiersRuleHandler.setScopeController(scopeController);
		declarationSpecifiersRuleHandler.setHandlerFactory(this);
		declarationSpecifiersRuleHandler.setBackend(backend);

		return declarationSpecifiersRuleHandler;
	}

	@Override
	public InitDeclaratorRuleHandler createInitDeclaratorRuleHandler() {

		final InitDeclaratorRuleHandler initDeclaratorRuleHandler = new InitDeclaratorRuleHandler();
		initDeclaratorRuleHandler.setScopeController(scopeController);
		initDeclaratorRuleHandler.setHandlerFactory(this);
		initDeclaratorRuleHandler.setBackend(backend);

		return initDeclaratorRuleHandler;
	}

	@Override
	public EqualityExpressionRuleHandler createEqualityExpressionRuleHandler() {

		final EqualityExpressionRuleHandler equalityExpressionRuleHandler = new EqualityExpressionRuleHandler();
		equalityExpressionRuleHandler.setScopeController(scopeController);
		equalityExpressionRuleHandler.setHandlerFactory(this);
		equalityExpressionRuleHandler.setBackend(backend);

		return equalityExpressionRuleHandler;
	}

	@Override
	public InitDeclaratorListRuleHandler createInitDeclaratorListRuleHandler() {

		final InitDeclaratorListRuleHandler initDeclaratorListRuleHandler = new InitDeclaratorListRuleHandler();
		initDeclaratorListRuleHandler.setScopeController(scopeController);
		initDeclaratorListRuleHandler.setHandlerFactory(this);
		initDeclaratorListRuleHandler.setBackend(backend);

		return initDeclaratorListRuleHandler;
	}

	@Override
	public PrimaryExpressionRuleHandler createPrimaryExpressionRuleHandler() {

		final PrimaryExpressionRuleHandler primaryExpressionRuleHandler = new PrimaryExpressionRuleHandler();
		primaryExpressionRuleHandler.setScopeController(scopeController);
		primaryExpressionRuleHandler.setHandlerFactory(this);
		primaryExpressionRuleHandler.setBackend(backend);

		return primaryExpressionRuleHandler;
	}

	public DeclarationRuleHandler createDeclarationRuleHandler() {

		final DeclarationRuleHandler declarationRuleHandler = new DeclarationRuleHandler();
		declarationRuleHandler.setScopeController(scopeController);
		declarationRuleHandler.setHandlerFactory(this);
		declarationRuleHandler.setBackend(backend);

		return declarationRuleHandler;
	}

	@Override
	public AdditiveExpressionRuleHandler createAdditiveExpressionRuleHandler() {

		final AdditiveExpressionRuleHandler additiveExpressionRuleHandler = new AdditiveExpressionRuleHandler();
		additiveExpressionRuleHandler.setScopeController(scopeController);
		additiveExpressionRuleHandler.setHandlerFactory(this);
		additiveExpressionRuleHandler.setBackend(backend);

		return additiveExpressionRuleHandler;
	}

	public ScopeController getScopeController() {
		return scopeController;
	}

	public void setScopeController(final ScopeController scopeController) {
		this.scopeController = scopeController;
	}

	public Backend getBackend() {
		return backend;
	}

	public void setBackend(final Backend backend) {
		this.backend = backend;
	}

}
