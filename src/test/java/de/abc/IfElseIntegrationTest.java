package de.abc;

import static org.junit.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import de.abc.scopes.DefaultScopeController;
import de.abc.scopes.Scope;
import de.backend.DummyBackend;
import de.compiling.handler.DefaultHandlerFactory;

public class IfElseIntegrationTest extends AbstractParsingTest {
	
	private static final Logger LOG = LogManager.getLogger(IfElseIntegrationTest.class);
	
	@Before
	public void before() {
		
		scopeController = new DefaultScopeController();
		
		backend = new DummyBackend();
		backend.setScopeController(scopeController);
		

		handlerFactory = new DefaultHandlerFactory();
		handlerFactory.setScopeController(scopeController);
		handlerFactory.setBackend(backend);
	}
	
	@Test
	public void test_WhenIf_NoElse_SimpleCondition_DummyStatement() throws Exception {

		// Arrange

		final String testData = "#include <stdio.h>\n int main() { if (1)\n statement; }\n\n";

		// Act

		parseTestData(testData/*, scopeController*/, "src/test/integration_test_WhenIf_NoElse_SimpleCondition_DummyStatement.dot");

		// Assert

		final Scope currentScope = scopeController.getCurrentScope();
		assertEquals(0, currentScope.getInitDeclarators().size());

		

		LOG.info("done");
	}
}
