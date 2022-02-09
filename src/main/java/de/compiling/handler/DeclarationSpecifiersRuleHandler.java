package de.compiling.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlrfun.ASTWalker;
import org.antlrfun.InitDeclarator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cgrammar.CParser;
import org.cgrammar.CParser.DeclarationSpecifiersContext;
import org.cgrammar.CParser.StorageClassSpecifierContext;
import org.cgrammar.CParser.TypeSpecifierContext;
import org.cgrammar.CParser.TypedefNameContext;

public class DeclarationSpecifiersRuleHandler extends AbstractRuleHandler<CParser.DeclarationSpecifiersContext> {

	private static final Logger LOG = LogManager.getLogger(DeclarationSpecifiersRuleHandler.class);

	@Override
	public void processEnter(final DeclarationSpecifiersContext ctx, final Map<String, Object> properties,
			final ASTWalker astWalker) throws IOException {

		LOG.info(ctx.getText());

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

		LOG.info(initDeclarator);

		// The DeclarationSpecifiers rule handler will not put data into the current
		// scope! It passes the data up to the declaration rule handler instead which
		// inserts
		// the declaration into the current scope! The declaration rule handler will
		// insert the declaration for
		// both variable declarations with and without initialization
//		getScopeController().getCurrentScope().addInitDeclaration(initDeclarator);

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
