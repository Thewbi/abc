package de.abc.scopes;

import java.util.List;

import org.antlrfun.InitDeclarator;

public interface Scope {

	Scope getParentScope();

	boolean hasParentScope();

	boolean hasNoParentScope();

	List<InitDeclarator> getInitDeclarators();

	void setInitDeclarators(List<InitDeclarator> initDeclarators);

	void addInitDeclaration(InitDeclarator initDeclarator);

}
