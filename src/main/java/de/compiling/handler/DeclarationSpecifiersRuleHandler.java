package de.compiling.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlrfun.ASTWalker;
import org.antlrfun.InitDeclarator;
import org.cgrammar.CParser;
import org.cgrammar.CParser.DeclarationSpecifiersContext;
import org.cgrammar.CParser.StorageClassSpecifierContext;
import org.cgrammar.CParser.TypeSpecifierContext;
import org.cgrammar.CParser.TypedefNameContext;

public class DeclarationSpecifiersRuleHandler implements RuleHandler<CParser.DeclarationSpecifiersContext> {

	@Override
	public void processEnter(final DeclarationSpecifiersContext ctx, final Map<String, Object> properties,
			final ASTWalker astWalker) throws IOException {

		System.out.println(ctx.getText());

		final InitDeclarator initDeclarator = new InitDeclarator();

		for (int i = 0; i < ctx.getChildCount(); i++) {

			final ParseTree child = ctx.getChild(i);

//			System.out.println(child.getText() + " isStorageClassSpecifier: " + isStorageClassSpecifier(child));
//			System.out.println(child.getText() + " isTypeSpecifier: " + isTypeSpecifier(child));
//			System.out.println(child.getText() + " isTypedefName: " + isTypedefName(child));

			final boolean isStorageClassSpecifier = isStorageClassSpecifier(child);
			final boolean typeSpecifier = isTypeSpecifier(child);
			final boolean typedefName = isTypedefName(child);

			if (isStorageClassSpecifier) {
				initDeclarator.setStorageClassSpecifier(child.getText());
			} else if (typeSpecifier && !typedefName) {
				initDeclarator.getValueList().add(child.getText());
			} else if (typedefName) {
				initDeclarator.setName(child.getText());
			}
		}

		System.out.println(initDeclarator);

		astWalker.getInitDeclarators().add(initDeclarator);
	}

	private boolean isStorageClassSpecifier(final ParseTree parseTree) {
		return ASTNodeUtils.lookForChild(parseTree, StorageClassSpecifierContext.class) != null;
	}

	private boolean isTypeSpecifier(final ParseTree parseTree) {
		return ASTNodeUtils.lookForChild(parseTree, TypeSpecifierContext.class) != null;
	}

	private boolean isTypedefName(final ParseTree parseTree) {
		return ASTNodeUtils.lookForChild(parseTree, TypedefNameContext.class) != null;
	}

	@Override
	public void processExit(final DeclarationSpecifiersContext ctx, final Map<String, Object> properties,
			final ASTWalker astWalker) throws IOException {

	}

}
