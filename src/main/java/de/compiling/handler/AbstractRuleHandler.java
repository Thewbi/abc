package de.compiling.handler;

import org.antlr.v4.runtime.ParserRuleContext;

import de.abc.scopes.ScopeController;

public abstract class AbstractRuleHandler<T extends ParserRuleContext> implements RuleHandler<T> {

	private ScopeController scopeController;

	private HandlerFactory handlerFactory;

	public ScopeController getScopeController() {
		return scopeController;
	}

	public void setScopeController(final ScopeController scopeController) {
		this.scopeController = scopeController;
	}

	public HandlerFactory getHandlerFactory() {
		return handlerFactory;
	}

	public void setHandlerFactory(final HandlerFactory handlerFactory) {
		this.handlerFactory = handlerFactory;
	}

}
