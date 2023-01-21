package de.abc.scopes;

import de.abc.exceptions.ScopeException;

public class DefaultScopeController implements ScopeController {

	private Scope currentScope = new DefaultScope();

	@Override
	public Scope getCurrentScope() {
		return currentScope;
	}

	@Override
	public void ascend() throws ScopeException {
		
		if (currentScope.hasNoParentScope()) {
			throw new ScopeException("No parent scope to ascend into!");
		}
		currentScope = currentScope.getParentScope();
	}

	@Override
	public void descend() throws ScopeException {
		
		DefaultScope newScope = new DefaultScope();
		if (null != currentScope) {
			newScope.setParentScope(currentScope);
		}
		
		currentScope = newScope;
	}

}
