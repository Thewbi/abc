package de.compiling.handler;

public interface HandlerFactory {

	SelectionStatementRuleHandler createSelectionStatementRuleHandler();

	DeclarationSpecifiersRuleHandler createDeclarationSpecifiersRuleHandler();

	InitDeclaratorRuleHandler createInitDeclaratorRuleHandler();

	EqualityExpressionRuleHandler createEqualityExpressionRuleHandler();

	InitDeclaratorListRuleHandler createInitDeclaratorListRuleHandler();

	PrimaryExpressionRuleHandler createPrimaryExpressionRuleHandler();

	AdditiveExpressionRuleHandler createAdditiveExpressionRuleHandler();

}
