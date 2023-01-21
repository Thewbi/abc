package de.abc.scopes;

import java.util.ArrayList;
import java.util.List;

import org.antlrfun.InitDeclarator;

public class DefaultScope implements Scope {

	private Scope parentScope;

	private List<InitDeclarator> initDeclarators = new ArrayList<>();

	@Override
	public Scope getParentScope() {
		return parentScope;
	}
	
	@Override
	public void setParentScope(Scope parentScope) {
		this.parentScope = parentScope;
	}

	@Override
	public boolean hasParentScope() {
		return parentScope != null;
	}

	@Override
	public boolean hasNoParentScope() {
		return parentScope == null;
	}

	@Override
	public List<InitDeclarator> getInitDeclarators() {
		return initDeclarators;
	}

	@Override
	public void setInitDeclarators(final List<InitDeclarator> initDeclarators) {
		this.initDeclarators = initDeclarators;
	}

	@Override
	public void addInitDeclaration(final InitDeclarator initDeclarator) {
		initDeclarators.add(initDeclarator);
	}

	

}
