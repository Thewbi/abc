package de.abc.scopes;

import de.abc.exceptions.ScopeException;

public interface ScopeController {

	Scope getCurrentScope();

	void ascend() throws ScopeException;
	
	void descend() throws ScopeException;

}
