package de.abc.scopes;

import java.util.List;

import org.antlrfun.InitDeclarator;

public interface Scope {

	Scope getParentScope();
	
	void setParentScope(Scope parentScope);

	boolean hasParentScope();

	boolean hasNoParentScope();

	List<InitDeclarator> getInitDeclarators();

	void setInitDeclarators(List<InitDeclarator> initDeclarators);

	void addInitDeclaration(InitDeclarator initDeclarator);

}
