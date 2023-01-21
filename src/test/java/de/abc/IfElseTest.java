package de.abc;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.antlrfun.Expression;
import org.antlrfun.InitDeclarator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import de.abc.scopes.DefaultScopeController;
import de.abc.scopes.Scope;
import de.backend.DummyBackend;
import de.compiling.handler.DefaultHandlerFactory;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class IfElseTest extends AbstractParsingTest {

	private static final Logger LOG = LogManager.getLogger(IfElseTest.class);

	@Mock
	private DummyBackend dummyBackend;

	@Captor
	private ArgumentCaptor<List<Expression>> conditionListCaptor;

	@Captor
	private ArgumentCaptor<List<Expression>> statementBodyListCaptor;

	@Before
	public void before() {

		scopeController = new DefaultScopeController();

		handlerFactory = new DefaultHandlerFactory();
		handlerFactory.setScopeController(scopeController);
		handlerFactory.setBackend(dummyBackend);
	}

	@Test
	public void test_WhenIf_NoElse_SimpleCondition_DummyStatement() throws Exception {

		// Arrange

		final String testData = "#include <stdio.h>\n int main() { if (1)\n statement; }\n\n";

		// Act

		parseTestData(testData/*, scopeController*/, "src/test/test_WhenIf_NoElse_SimpleCondition_DummyStatement.dot");

		// Assert

		final Scope currentScope = scopeController.getCurrentScope();
		assertEquals(0, currentScope.getInitDeclarators().size());

		// retrieve the values that have been put into the mock
		verify(dummyBackend).ifStatement(conditionListCaptor.capture(), statementBodyListCaptor.capture());

		// assert the condition of the if statement
		final List<List<Expression>> conditionExpressionList = conditionListCaptor.getAllValues();
		final List<Expression> conditionExpressions = conditionExpressionList.get(0);
		final Expression conditionExpression = conditionExpressions.get(0);
		assertEquals("1", conditionExpression.getRhs());

		// assert the statements of the if statement
		final List<List<Expression>> statementBodyExpressionList = statementBodyListCaptor.getAllValues();
		final List<Expression> statementBodyList = statementBodyExpressionList.get(0);
		final Expression expression = statementBodyList.get(0);
		assertEquals("statement", expression.getRhs());

		LOG.info("done");
	}

	@Test
	public void test_WhenIf_NoElse_EqualityCondition_WithStatement() throws Exception {

		// Arrange

		final String testData = "#include <stdio.h>\n int main() { int i = 0; if (i == 0) { i++; } }\n\n";

		// Act

		parseTestData(testData/*, scopeController*/, "src/test/test_WhenIf_NoElse_EqualityCondition_WithStatement.dot");

		// Assert

		final Scope currentScope = scopeController.getCurrentScope();
		assertEquals(1, currentScope.getInitDeclarators().size());

		final InitDeclarator initDeclarator = currentScope.getInitDeclarators().get(0);
		assertEquals(null, initDeclarator.getStorageClassSpecifier());
		assertEquals("int", initDeclarator.getValueList().get(0));
		assertEquals("i", initDeclarator.getName());
		final Expression varDeclExpression = initDeclarator.getExpressionList().get(0);
		assertEquals("0", varDeclExpression.getRhs());

		// retrieve the values that have been put into the mock
		verify(dummyBackend).ifStatement(conditionListCaptor.capture(), statementBodyListCaptor.capture());

		// assert the condition of the if statement
		final List<List<Expression>> conditionExpressionList = conditionListCaptor.getAllValues();
		final List<Expression> conditionExpressions = conditionExpressionList.get(0);
		Expression conditionExpression = conditionExpressions.get(0);
		assertEquals("i", conditionExpression.getRhs());

		conditionExpression = conditionExpressions.get(1);
		assertEquals("==", conditionExpression.getOperator());

		conditionExpression = conditionExpressions.get(2);
		assertEquals("0", conditionExpression.getRhs());

		// assert the statements of the if statement
		final List<List<Expression>> statementBodyExpressionList = statementBodyListCaptor.getAllValues();
		final List<Expression> statementBodyList = statementBodyExpressionList.get(0);

		final Expression expression = statementBodyList.get(0);
		assertEquals("i", expression.getRhs());

//		TODO parse postfix expression context for the ++ operator

		LOG.info("done");
	}

}
