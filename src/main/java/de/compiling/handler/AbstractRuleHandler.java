package de.compiling.handler;

import org.antlr.v4.runtime.ParserRuleContext;

import de.abc.scopes.ScopeController;
import de.backend.Backend;

public abstract class AbstractRuleHandler<T extends ParserRuleContext> implements RuleHandler<T> {

	private ScopeController scopeController;

	private HandlerFactory handlerFactory;

	private Backend backend;

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

	public Backend getBackend() {
		return backend;
	}

	public void setBackend(final Backend backend) {
		this.backend = backend;
	}

}
