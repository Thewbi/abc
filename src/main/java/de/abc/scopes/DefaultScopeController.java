package de.abc.scopes;

public class DefaultScopeController implements ScopeController {

	private Scope currentScope = new DefaultScope();

	@Override
	public Scope getCurrentScope() {
		return currentScope;
	}

	@Override
	public void ascend() throws Exception {
		if (currentScope.hasNoParentScope()) {
			throw new Exception("No parent scope to ascend into!");
		}
		currentScope = currentScope.getParentScope();
	}

}
