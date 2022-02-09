package de.abc.scopes;

public interface ScopeController {

	Scope getCurrentScope();

	void ascend() throws Exception;

}
