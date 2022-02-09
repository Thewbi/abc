package de.abc;

import static org.junit.Assert.assertEquals;

import org.antlrfun.InitDeclarator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import de.abc.scopes.DefaultScopeController;
import de.abc.scopes.Scope;

public class IfElseTest extends AbstractParsingTest {

	private static final Logger LOG = LogManager.getLogger(IfElseTest.class);

	@Test
	public void test_WhenIf_NoElse_SimpleCondition() throws Exception {

		// Arrange

		final DefaultScopeController scopeController = new DefaultScopeController();
		final String testData = "#include <stdio.h>\n int main() { if (1)\n statement; }\n\n";

		// Act

		parseTestData(testData, scopeController, "src/test/test_WhenIf_NoElse_SimpleCondition.dot");

		// Assert

		final Scope currentScope = scopeController.getCurrentScope();
		final InitDeclarator initDeclarator = currentScope.getInitDeclarators().get(0);

		assertEquals("typedef", initDeclarator.getStorageClassSpecifier());

		assertEquals("unsigned", initDeclarator.getValueList().get(0));
		assertEquals("char", initDeclarator.getValueList().get(1));

		assertEquals("BYTE", initDeclarator.getName());

		LOG.info("done");
	}

}
