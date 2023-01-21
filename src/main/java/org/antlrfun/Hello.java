package org.antlrfun;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cgrammar.CLexer;
import org.cgrammar.CParser;

import de.abc.scopes.DefaultScopeController;
import de.abc.scopes.ScopeController;
import de.common.DefaultNode;
import de.common.DefaultNodeFactory;
import de.common.Factory;
import de.common.IdService;
import de.common.Node;
import de.common.NodeFactory;
import de.compiling.handler.DeclarationRuleHandler;
import de.compiling.handler.DeclarationSpecifiersRuleHandler;
import de.compiling.handler.DefaultHandlerFactory;
import de.compiling.handler.FunctionDefinitionRuleHandler;
import de.compiling.handler.JumpStatementRuleHandler;
import de.compiling.handler.RuleHandler;
import de.compiling.handler.SelectionStatementRuleHandler;
import de.compiling.handler.StatementRuleHandler;

public class Hello {

	private static final Logger LOG = LogManager.getLogger(Hello.class);

	public static void main(final String[] args) throws Exception {

//        HelloLexer lexer = new HelloLexer(antlrInputStream);
//
//        CommonTokenStream tokens = new CommonTokenStream( lexer );
//        HelloParser parser = new HelloParser( tokens );
//        ParseTree tree = parser.r();
//        ParseTreeWalker walker = new ParseTreeWalker();
//        walker.walk( new HelloWalker(), tree );

		final String fileName = "src/test/resources/helloworld.c";
//		final String fileName = "src/test/resources/array.c";
//		final String fileName = "src/test/resources/arrays.c";
//		final String fileName = "src/test/resources/loop.c";
//		final String fileName = "src/test/resources/forloop.c";
//		final String fileName = "src/test/resources/expression.c";
//		final String fileName = "src/test/resources/variables.c";
//		final String fileName = "src/test/resources/ifelse.c";

//		final String fileName = "src/test/resources/ALINK.C";
//		final String fileName = "src/test/resources/ALINK.H";
//		final String fileName = "src/test/resources/COFF.C";
//		final String fileName = "src/test/resources/COFFLIB.C";
//		final String fileName = "src/test/resources/COMBINE.C";
//		final String fileName = "src/test/resources/OBJLOAD.C";
//		final String fileName = "src/test/resources/OUTPUT.C";
//		final String fileName = "src/test/resources/UTIL.C";

		// DEBUG output the script for easier debugging
		final List<String> allLines = Files.readAllLines(Paths.get(fileName));
		// System.out.println(allLines);
		allLines.stream().forEach(l -> System.out.println(l));
		System.out.println("-------------------------------------------------");

		String outFilename = fileName.substring(0, fileName.length() - ".c".length());
		outFilename += ".asm";
		final File outFile = new File(outFilename);

		FileInputStream fileInputStream = null;
		BufferedWriter bufferedWriter = null;

		try {

			// Open the input file stream
			final File inFile = new File(fileName);
			fileInputStream = new FileInputStream(inFile);

			bufferedWriter = new BufferedWriter(new FileWriter(outFile));
			bufferedWriter.write("global start\n" + "\n" + "section .text\n" + "start:");

			// Create a CharStream that reads from standard input
//			final ANTLRInputStream antlrInputStream = new ANTLRInputStream("hello world");
//			final ANTLRInputStream antlrInputStream = new ANTLRInputStream("#include <stdio.h>\n");
//			final ANTLRInputStream antlrInputStream = new ANTLRInputStream(
//					"#include <stdio.h>\ntypedef unsigned char BYTE;\n");
//			final ANTLRInputStream antlrInputStream = new ANTLRInputStream("int main() { return 0; }");
//			final ANTLRInputStream antlrInputStream = new ANTLRInputStream("return 0;");

			final ANTLRInputStream antlrInputStream = new ANTLRInputStream(fileInputStream);

			final CLexer lexer = new CLexer(antlrInputStream);
			final CommonTokenStream tokens = new CommonTokenStream(lexer);
			final CParser parser = new CParser(tokens);
			final ParseTree tree = parser.compilationUnit();

			outputDotFile(tree, "src/test/file.dot");

//			final FunctionDefinitionRuleHandler functionDefinitionhandler = new FunctionDefinitionRuleHandler(
//					bufferedWriter);
//			final StatementRuleHandler statementRuleHandler = new StatementRuleHandler(bufferedWriter);
//			final JumpStatementRuleHandler jumpStatementRuleHandler = new JumpStatementRuleHandler(bufferedWriter);

//			final CWalker cWalker = new CWalker(bufferedWriter);
//			cWalker.getRuleHandlers().put(CParser.FunctionDefinitionContext.class, functionDefinitionhandler);
//			cWalker.getRuleHandlers().put(CParser.StatementContext.class, statementRuleHandler);
//			cWalker.getRuleHandlers().put(CParser.JumpStatementContext.class, jumpStatementRuleHandler);

//			final ParseTreeWalker walker = new ParseTreeWalker();
//			walker.walk(cWalker, tree);
//
//			cWalker.outputDataSection();

			final DefaultScopeController scopeController = new DefaultScopeController();

			final DefaultHandlerFactory handlerFactory = new DefaultHandlerFactory();
			handlerFactory.setScopeController(scopeController);

			final SelectionStatementRuleHandler selectionStatementRuleHandler = handlerFactory
					.createSelectionStatementRuleHandler();
//			final DeclarationSpecifiersRuleHandler declarationSpecifiersRuleHandler = new DeclarationSpecifiersRuleHandler();
			final DeclarationRuleHandler declarationRuleHandler = handlerFactory.createDeclarationRuleHandler();

			final NodeWalker nodeWalker = new NodeWalker();
			nodeWalker.setName("main");
			nodeWalker.getRuleHandlers().put(CParser.SelectionStatementContext.class, selectionStatementRuleHandler);
			nodeWalker.getRuleHandlers().put(CParser.DeclarationContext.class, declarationRuleHandler);

//			nodeWalker.getRuleHandlers().put(CParser.DeclarationSpecifiersContext.class,
//					declarationSpecifiersRuleHandler);
//			nodeWalker.getRuleHandlers().put(CParser.FunctionDefinitionContext.class, functionDefinitionhandler);
//			nodeWalker.getRuleHandlers().put(CParser.StatementContext.class, statementRuleHandler);
//			nodeWalker.getRuleHandlers().put(CParser.JumpStatementContext.class, jumpStatementRuleHandler);

			nodeWalker.walk(tree, 0);

//			System.out.println(cWalker.getExpressionStack());

		} finally {

			if (fileInputStream != null) {
				fileInputStream.close();
				fileInputStream = null;
			}
			if (bufferedWriter != null) {
				bufferedWriter.flush();
				bufferedWriter.close();
				bufferedWriter = null;
			}

		}

	}

	public static void outputDotFile(final ParseTree tree, final String filename) throws Exception {

		LOG.info("Outputting DOT file to : \"" + filename + "\"");

		final IdService idService = new IdService();

		final DefaultNodeFactory nodeFactory = new DefaultNodeFactory();
		nodeFactory.setIdService(idService);

		final Node rootNode = convertASTToNodeTree(nodeFactory, tree);
		debugOutputDotFileForNodeTree(rootNode, filename);
	}

	private static void debugOutputDotFileForNodeTree(final Node rootNode, final String filename) throws Exception {
		TreeWalkingTools.dumpDot(rootNode, filename);
	}

	private static Node convertASTToNodeTree(final Factory<DefaultNode> nodeFactory, final ParseTree context)
			throws Exception {

		final Node rootNode = nodeFactory.createObject();
		rootNode.setLabel("root");
		rootNode.setParent(null);

		final DefaultNodeListener nodeListenerImpl = new DefaultNodeListener();
		nodeListenerImpl.setRootNode(rootNode);
		nodeListenerImpl.setCurrentNode(rootNode);
		nodeListenerImpl.setNodeFactory(nodeFactory);

		ParseTreeWalker.DEFAULT.walk(nodeListenerImpl, context);
		return rootNode;
	}

}