package de.abc;

//import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlrfun.Hello;
import org.antlrfun.NodeWalker;
import org.cgrammar.CLexer;
import org.cgrammar.CParser;

import de.abc.scopes.DefaultScopeController;
import de.backend.DummyBackend;
import de.compiling.handler.DeclarationRuleHandler;
import de.compiling.handler.DefaultHandlerFactory;
import de.compiling.handler.RuleHandler;
import de.compiling.handler.SelectionStatementRuleHandler;

public abstract class AbstractParsingTest {

	protected DefaultScopeController scopeController;

	protected DefaultHandlerFactory handlerFactory;

	protected DummyBackend backend;

	/**
	 * Converts the ParseTree/AST that antlr has created into a dot file.
	 * The tree will contain all the nodes that the grammar defines via it's production rules.
	 * It is the raw unprocessed parsing output without any further processing applied.
	 * After the dot file is output, the processing of the AST starts.
	 * A node walker is first loaded with handlers for productions.
	 * When the walker arrives at a node that matches the handler, the walker will
	 * feed the node into the handler, so that the handler can process the node.
	 * The output of the tree walk is???
	 * 
	 * @param testData
	 * @param dotFileName
	 * @throws Exception
	 */
	public void parseTestData(final String testData/*, final DefaultScopeController scopeController*/,
			final String dotFileName) throws Exception {

//		final ANTLRInputStream antlrInputStream = new ANTLRInputStream(testData);
		CharStream antlrInputStream = CharStreams.fromString(testData);
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

		final NodeWalker nodeWalker = new NodeWalker();
		nodeWalker.setName(RuleHandler.at());

		// create handlers and add them to the node walker
		final SelectionStatementRuleHandler selectionStatementRuleHandler = handlerFactory
				.createSelectionStatementRuleHandler();
		nodeWalker.getRuleHandlers().put(CParser.SelectionStatementContext.class, selectionStatementRuleHandler);

		final DeclarationRuleHandler declarationRuleHandler = handlerFactory.createDeclarationRuleHandler();
		nodeWalker.getRuleHandlers().put(CParser.DeclarationContext.class, declarationRuleHandler);

		// start the tree walk
		nodeWalker.walk(tree, 0);
	}

}
