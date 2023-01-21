package de.compiling.handler;

import java.io.IOException;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlrfun.ASTWalker;

import de.abc.exceptions.ScopeException;

public interface RuleHandler<T extends ParserRuleContext> {

	void processEnter(T ctx, Map<String, Object> properties, ASTWalker astWalker) throws IOException, ScopeException;

	void processExit(T ctx, Map<String, Object> properties, ASTWalker astWalker) throws IOException, ScopeException;

	default void dumpContext(final T ctx, final int indent) {

		// perform indentation
		for (int i = 0; i < indent; i++) {
			System.out.print("  ");
		}
		System.out.println(ctx.getClass() + " " + ctx.getText());
//		if (indent > 0) {
//			System.out.println("");
//		}

		// output the childern
		for (int i = 0; i < ctx.getChildCount(); i++) {

			final ParseTree child = ctx.getChild(i);
//			System.out.println(child.getClass() + " " + child.getText());
			dumpParseTree(child, indent + 1);
		}
	}

	default void dumpParseTree(final ParseTree parseTree, final int indent) {

		// perform indentation
		for (int i = 0; i < indent; i++) {
			System.out.print("  ");
		}
		System.out.println(parseTree.getClass() + " " + parseTree.getText());

		// output the childern
		for (int i = 0; i < parseTree.getChildCount(); i++) {

			final ParseTree child = parseTree.getChild(i);
//					System.out.println(child.getClass() + " " + child.getText());
			dumpParseTree(child, indent + 1);
		}
	}

	public static int lineNumber() {
		return Thread.currentThread().getStackTrace()[2].getLineNumber();
	}

	/**
	 * The string returned describes where in the stack trace, i.e. which class
	 * code is currently executed for easier debuggin.
	 * 
	 * @return
	 */
	public static String at() {
		final StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		final String where = ste.getClassName() + " " + ste.getMethodName() + " " + ste.getLineNumber() + " ";
		return where;
	}

}
