package de.abc;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlrfun.Expression;
import org.antlrfun.Hello;
import org.antlrfun.InitDeclarator;
import org.antlrfun.NodeWalker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cgrammar.CLexer;
import org.cgrammar.CParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.abc.scopes.DefaultScopeController;
import de.abc.scopes.Scope;
import de.abc.scopes.ScopeController;
import de.compiling.handler.DeclarationRuleHandler;
import de.compiling.handler.DeclarationSpecifiersRuleHandler;
import de.compiling.handler.DefaultHandlerFactory;
import de.compiling.handler.HandlerFactory;
import de.compiling.handler.RuleHandler;
import de.compiling.handler.SelectionStatementRuleHandler;

public class TypeDefinitionTest extends AbstractParsingTest {

	private static final Logger LOG = LogManager.getLogger(TypeDefinitionTest.class);

	@Test
	public void testTypeDefinition_WhenTypedefUnsignedCharBYTE() throws Exception {

		// Arrange

		final DefaultScopeController scopeController = new DefaultScopeController();
		final String testData = "#include <stdio.h>\n typedef unsigned char BYTE;\n\n";

		// Act

		parseTestData(testData, scopeController, "src/test/testTypeDefinition_WhenTypedefUnsignedCharBYTE.dot");

		// Assert

		final Scope currentScope = scopeController.getCurrentScope();
		final InitDeclarator initDeclarator = currentScope.getInitDeclarators().get(0);

		assertEquals("typedef", initDeclarator.getStorageClassSpecifier());

		assertEquals("unsigned", initDeclarator.getValueList().get(0));
		assertEquals("char", initDeclarator.getValueList().get(1));

		assertEquals("BYTE", initDeclarator.getName());

		LOG.info("done");
	}

	@Test
	public void testTypeDefinition_WhenVarDefinitionWithoutInitialization() throws Exception {

		// Arrange

		final DefaultScopeController scopeController = new DefaultScopeController();
		final String testData = "#include <stdio.h>\n int global_a;\n\n";

		// Act

		parseTestData(testData, scopeController,
				"src/test/testTypeDefinition_WhenVarDefinitionWithoutInitialization.dot");

		// Assert

		final Scope currentScope = scopeController.getCurrentScope();
		final InitDeclarator initDeclarator = currentScope.getInitDeclarators().get(0);

		assertEquals(null, initDeclarator.getStorageClassSpecifier());
		assertEquals("int", initDeclarator.getValueList().get(0));
		assertEquals("global_a", initDeclarator.getName());

		LOG.info("done");
	}

	@Test
	public void testTypeDefinition_WhenVarDefinitionWithInitialization() throws Exception {

		// Arrange

		final DefaultScopeController scopeController = new DefaultScopeController();
		final String testData = "#include <stdio.h>\n int global_a = 1;\n\n";

		// Act

		parseTestData(testData, scopeController, "src/test/testTypeDefinition_WhenVarDefinitionWithInitialization.dot");

		// Assert

		final Scope currentScope = scopeController.getCurrentScope();
		final InitDeclarator initDeclarator = currentScope.getInitDeclarators().get(0);

		assertEquals(null, initDeclarator.getStorageClassSpecifier());
		assertEquals("int", initDeclarator.getValueList().get(0));
		assertEquals("global_a", initDeclarator.getName());

		LOG.info("done");
	}

	@Test
	public void testTypeDefinition_WhenVarDefinitionWithoutInitialization_Unsigned() throws Exception {

		// Arrange

		final DefaultScopeController scopeController = new DefaultScopeController();
		final String testData = "#include <stdio.h>\n unsigned int a;\n\n";

		// Act

		parseTestData(testData, scopeController,
				"src/test/testTypeDefinition_WhenVarDefinitionWithoutInitialization_Unsigned.dot");

		// Assert

		final Scope currentScope = scopeController.getCurrentScope();
		final InitDeclarator initDeclarator = currentScope.getInitDeclarators().get(0);

		assertEquals(null, initDeclarator.getStorageClassSpecifier());
		assertEquals("unsigned", initDeclarator.getValueList().get(0));
		assertEquals("int", initDeclarator.getValueList().get(1));
		assertEquals("a", initDeclarator.getName());

		LOG.info("done");
	}

	@Test
	public void testTypeDefinition_WhenVarDefinitionWithoutInitialization_Multiple() throws Exception {

		// Arrange

		final DefaultScopeController scopeController = new DefaultScopeController();
		final String testData = "#include <stdio.h>\n unsigned int i, j, k;\n\n";

		// Act

		parseTestData(testData, scopeController,
				"src/test/testTypeDefinition_WhenVarDefinitionWithoutInitialization_Multiple.dot");

		// Assert

		final Scope currentScope = scopeController.getCurrentScope();

		InitDeclarator initDeclarator = currentScope.getInitDeclarators().get(0);
		assertEquals(null, initDeclarator.getStorageClassSpecifier());
		assertEquals("unsigned", initDeclarator.getValueList().get(0));
		assertEquals("int", initDeclarator.getValueList().get(1));
		assertEquals("i", initDeclarator.getName());

		initDeclarator = currentScope.getInitDeclarators().get(1);
		assertEquals(null, initDeclarator.getStorageClassSpecifier());
		assertEquals("unsigned", initDeclarator.getValueList().get(0));
		assertEquals("int", initDeclarator.getValueList().get(1));
		assertEquals("j", initDeclarator.getName());

		initDeclarator = currentScope.getInitDeclarators().get(2);
		assertEquals(null, initDeclarator.getStorageClassSpecifier());
		assertEquals("unsigned", initDeclarator.getValueList().get(0));
		assertEquals("int", initDeclarator.getValueList().get(1));
		assertEquals("k", initDeclarator.getName());

		LOG.info("done");
	}

	@Test
	public void testTypeDefinition_WhenVarDefinitionWithInitialization_Multiple() throws Exception {

		// Arrange

		final DefaultScopeController scopeController = new DefaultScopeController();
		final String testData = "#include <stdio.h>\n unsigned int i=1, j=2, k=3;\n\n";

		// Act

		parseTestData(testData, scopeController,
				"src/test/testTypeDefinition_WhenVarDefinitionWithInitialization_Multiple.dot");

		// Assert

		final Scope currentScope = scopeController.getCurrentScope();

		InitDeclarator initDeclarator = currentScope.getInitDeclarators().get(0);
		assertEquals(null, initDeclarator.getStorageClassSpecifier());
		assertEquals("unsigned", initDeclarator.getValueList().get(0));
		assertEquals("int", initDeclarator.getValueList().get(1));
		assertEquals("i", initDeclarator.getName());
		Expression expression = initDeclarator.getExpressionList().get(0);
		assertEquals("1", expression.getRhs());

		initDeclarator = currentScope.getInitDeclarators().get(1);
		assertEquals(null, initDeclarator.getStorageClassSpecifier());
		assertEquals("unsigned", initDeclarator.getValueList().get(0));
		assertEquals("int", initDeclarator.getValueList().get(1));
		assertEquals("j", initDeclarator.getName());
		expression = initDeclarator.getExpressionList().get(0);
		assertEquals("2", expression.getRhs());

		initDeclarator = currentScope.getInitDeclarators().get(2);
		assertEquals(null, initDeclarator.getStorageClassSpecifier());
		assertEquals("unsigned", initDeclarator.getValueList().get(0));
		assertEquals("int", initDeclarator.getValueList().get(1));
		assertEquals("k", initDeclarator.getName());
		expression = initDeclarator.getExpressionList().get(0);
		assertEquals("3", expression.getRhs());

		LOG.info("done");
	}

	@Test
	public void testTypeDefinition_WhenVarDefinitionWithInitialization_ByFunction() throws Exception {

		// Arrange

		final DefaultScopeController scopeController = new DefaultScopeController();
		final String testData = "#include <stdio.h>\n int a = func();\n\n";

		// Act

		parseTestData(testData, scopeController,
				"src/test/testTypeDefinition_WhenVarDefinitionWithInitialization_ByFunction.dot");

		// Assert

		final Scope currentScope = scopeController.getCurrentScope();

		final InitDeclarator initDeclarator = currentScope.getInitDeclarators().get(0);
		assertEquals(null, initDeclarator.getStorageClassSpecifier());
		assertEquals("int", initDeclarator.getValueList().get(0));
		assertEquals("a", initDeclarator.getName());
		final Expression expression = initDeclarator.getExpressionList().get(0);
		assertEquals("func", expression.getRhs());
	}

}
