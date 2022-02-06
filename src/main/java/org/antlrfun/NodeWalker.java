package org.antlrfun;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import de.compiling.handler.RuleHandler;

public class NodeWalker implements ASTWalker {

	private String name;

//	private Expression expression;

	private final List<Expression> expressionList = new ArrayList<>();

	private final Map<Class<?>, RuleHandler> ruleHandlers = new HashMap<>();

	private String variableName;

	private final List<InitDeclarator> initDeclarators = new ArrayList<>();

//	public void walk(final ParseTreeListener listener, final ParseTree t) {
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
				ruleHandler.processEnter((ParserRuleContext) ruleNode, null, this);

			} else {

				// recurse further
				final int n = ruleNode.getChildCount();
				for (int i = 0; i < n; i++) {

					final ParseTree child = ruleNode.getChild(i);

//					// DEBUG
//					System.out.print("[" + name + "]");
//					for (int j = 0; j < indent; j++) {
//						System.out.print("  ");
//					}
//					System.out.println(child.getClass().getSimpleName() + " \"" + child.getText() + "\"");

					// walk(listener, r.getChild(i));
					walk(child, i + 1);
				}
			}
		}
//		exitRule(listener, r);
	}

	// /**
//	 * The discovery of a rule node, involves sending two events: the generic
//	 * {@link ParseTreeListener#enterEveryRule} and a {@link RuleContext}-specific
//	 * event. First we trigger the generic and then the rule specific. We to them in
//	 * reverse order upon finishing the node.
//	 */
//	protected void enterRule(final ParseTreeListener listener, final RuleNode r) {
//		final ParserRuleContext ctx = (ParserRuleContext) r.getRuleContext();
//		listener.enterEveryRule(ctx);
//		ctx.enterRule(listener);
//	}
//
//	protected void exitRule(final ParseTreeListener listener, final RuleNode r) {
//		final ParserRuleContext ctx = (ParserRuleContext) r.getRuleContext();
//		ctx.exitRule(listener);
//		listener.exitEveryRule(ctx);
//	}

	public Map<Class<?>, RuleHandler> getRuleHandlers() {
		return ruleHandlers;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

//	@Override
//	public Expression getExpression() {
//		return expression;
//	}
//
//	@Override
//	public void setExpression(final Expression expression) {
//		this.expression = expression;
//	}

	@Override
	public List<Data> getDataList() {
		// TODO Auto-generated method stub
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
