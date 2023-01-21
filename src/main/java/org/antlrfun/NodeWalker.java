package org.antlrfun;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.abc.exceptions.ScopeException;
import de.compiling.handler.RuleHandler;

/**
 * Traverses all nodes by depth-first traversal over all children.
 * If a handler is registered for the node class, then the node is 
 * fed into that handler and the node walker itself will then not
 * descend into the child.
 * If no handler is registered, the node walker will descend into the
 * child.<br /><br />
 * This NodeWalker will not produce any output. The output is
 * produced by the registered handlers. The handlers will create
 * output and load that output into the handlers member variables.
 * E.g. a handler might create an Expression object and store that
 * object into the NodeWalkers expressionList member variable.
 * The handlers will also inform the backend about language
 * constructs that they have found. E.g. a handler for if-statements
 * will call the backend whenever it has processed an if-statement.
 */
public class NodeWalker implements ASTWalker {
	
	private static final Logger LOG = LogManager.getLogger(CWalker.class);

	private String name;

	private final List<Expression> expressionList = new ArrayList<>();

	private final Map<Class<?>, RuleHandler<?>> ruleHandlers = new HashMap<>();

	private String variableName;

	private final List<InitDeclarator> initDeclarators = new ArrayList<>();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void walk(final ParseTree t, final int indent) throws IOException {

		if (t instanceof ErrorNode) {
			
//			listener.visitErrorNode((ErrorNode) t);

		} else if (t instanceof TerminalNode) {
			
//			listener.visitTerminal((TerminalNode) t);

//			// DEBUG
//			System.out.print("[" + name + "]");
//			for (int i = 0; i < indent; i++) {
//				System.out.print("  ");
//			}
//			System.out.println(t.getText());

		} else {

			final RuleNode ruleNode = (RuleNode) t;

			if (ruleHandlers.containsKey(ruleNode.getClass())) {

				final RuleHandler ruleHandler = ruleHandlers.get(ruleNode.getClass());
				try {
					ruleHandler.processEnter((ParserRuleContext) ruleNode, null, this);
				} catch (IOException | ScopeException e) {
					LOG.error(e.getMessage(), e);
					System.exit(-1);
				}

			} else {

				// recurse further, descend into the child node
				final int n = ruleNode.getChildCount();
				for (int i = 0; i < n; i++) {

					final ParseTree child = ruleNode.getChild(i);

//					// DEBUG
//					System.out.print("[" + name + "]");
//					for (int j = 0; j < indent; j++) {
//						System.out.print("  ");
//					}
//					System.out.println(child.getClass().getSimpleName() + " \"" + child.getText() + "\"");

					walk(child, i + 1);
				}
			}
		}
//		exitRule(listener, r);
	}

	public Map<Class<?>, RuleHandler<?>> getRuleHandlers() {
		return ruleHandlers;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public List<Data> getDataList() {
		return null;
	}

	@Override
	public List<Expression> getExpressionList() {
		return expressionList;
	}

	public String getVariableName() {
		return variableName;
	}

	@Override
	public void setVariableName(final String variableName) {
		this.variableName = variableName;
	}

	@Override
	public List<InitDeclarator> getInitDeclarators() {
		return initDeclarators;
	}

}
