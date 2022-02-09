package de.abc;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlrfun.Hello;
import org.antlrfun.NodeWalker;
import org.cgrammar.CLexer;
import org.cgrammar.CParser;

import de.abc.scopes.DefaultScopeController;
import de.compiling.handler.DeclarationRuleHandler;
import de.compiling.handler.DefaultHandlerFactory;
import de.compiling.handler.RuleHandler;
import de.compiling.handler.SelectionStatementRuleHandler;

public abstract class AbstractParsingTest {

	public void parseTestData(final String testData, final DefaultScopeController scopeController,
			final String dotFileName) throws Exception {

		final ANTLRInputStream antlrInputStream = new ANTLRInputStream(testData);
		final CLexer lexer = new CLexer(antlrInputStream);
		final CommonTokenStream tokens = new CommonTokenStream(lexer);
		final CParser parser = new CParser(tokens);

		//
		// parse
		//

		final ParseTree tree = parser.compilationUnit();

		//
		// DEBUG
		//

		Hello.outputDotFile(tree, dotFileName);

		//
		// process
		//

		final DefaultHandlerFactory handlerFactory = new DefaultHandlerFactory();
		handlerFactory.setScopeController(scopeController);

		final NodeWalker nodeWalker = new NodeWalker();
		nodeWalker.setName(RuleHandler.at());

		final SelectionStatementRuleHandler selectionStatementRuleHandler = handlerFactory
				.createSelectionStatementRuleHandler();
		nodeWalker.getRuleHandlers().put(CParser.SelectionStatementContext.class, selectionStatementRuleHandler);

		final DeclarationRuleHandler declarationRuleHandler = handlerFactory.createDeclarationRuleHandler();
		nodeWalker.getRuleHandlers().put(CParser.DeclarationContext.class, declarationRuleHandler);

		nodeWalker.walk(tree, 0);
	}

}
