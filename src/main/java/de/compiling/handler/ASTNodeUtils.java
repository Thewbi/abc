package de.compiling.handler;

import org.antlr.v4.runtime.tree.ParseTree;

public class ASTNodeUtils {

	private ASTNodeUtils() {
		// no instances of this class
	}

	public static ParseTree lookForChild(final ParseTree parseTree, final Class<?> clazz) {

		if (parseTree.getClass().equals(clazz)) {
			return parseTree;
		}

		for (int i = 0; i < parseTree.getChildCount(); i++) {
			final ParseTree lookForChild = lookForChild(parseTree.getChild(i), clazz);
			if (lookForChild != null) {
				return lookForChild;
			}
		}

		return null;
	}

}
