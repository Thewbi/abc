package org.antlrfun;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.common.Node;

/**
 * The Class TreeWalkingTools.
 */
@SuppressWarnings({ "PMD.ExcessiveClassLength", "PMD.ExcessivePublicCount" })
public final class TreeWalkingTools extends NodeUtil {

	private static final int SIZE_TWO = 2;

	private static final int SIZE_THREE = 3;

	private static final int SIZE_ONE = 1;

	private static final int SPLIT_LENGTH_ONE = 1;

	private static final String OPENING_BRACKET = "(";

	private static final String ONE_SPACE_EMPTY_STRING = " ";

	public static final String ASTERISK = "*";

	private static boolean outputAllTablesIsInsideSubSelect = false; // NOPMD

	/** The Constant LOG. */
//  private static final transient Logger LOG = LoggerFactory.getLogger(TreeWalkingTools.class);

	/**
	 * Utility classes should not have a public or default constructor.
	 */
	protected TreeWalkingTools() {
		// empty
	}

//	/**
//	 * Output first expression.
//	 *
//	 * @param node the node
//	 * @return the node
//	 */
//	public static Node outputFirstExpression(final Node node) {
//
//		if (node.getType() == NodeListenerImpl.EXPR) {
//
//			return node;
//		} else {
//
//			Node expression = null;
//			for (final Node child : node.getChildren()) {
//				expression = outputFirstExpression(child);
//				if (null != expression) {
//					return expression;
//				}
//			}
//		}
//
//		return null;
//	}
//
//	/**
//	 * Process where clause.
//	 *
//	 * @param node           the node
//	 * @param objectListener the object listener
//	 * @param idService      the id service
//	 * @return the boolean node
//	 */
//	public static BooleanNode processWhereClause(final Node node, final ObjectListener objectListener,
//			final IdService idService) {
//
//		final boolean useIdInLabel = false; // NOPMD
//
//		if ((node.getType() == NodeListenerImpl.PREDICATE) && (5 == node.getChildren().size())) {
//
//			final Node firstChild = node.getChildren().get(0);
//			final BooleanNode booleanNodeA = processWhereClause(firstChild, objectListener, idService);
//
//			final Node secondChild = node.getChildren().get(1);
//			final BooleanNode booleanNodeB = processWhereClause(secondChild, objectListener, idService);
//
//			final Node thirdChild = node.getChildren().get(2);
//			final BooleanNode booleanNodeC = processWhereClause(thirdChild, objectListener, idService);
//
//			final Node fourthChild = node.getChildren().get(3);
//			final BooleanNode booleanNodeD = processWhereClause(fourthChild, objectListener, idService);
//			booleanNodeC.setChildB(booleanNodeD);
//
//			final Node fifthChild = node.getChildren().get(4);
//			final BooleanNode booleanNodeE = processWhereClause(fifthChild, objectListener, idService);
//			booleanNodeD.setChildB(booleanNodeE);
//
//			final BooleanNode booleanNode = new BooleanNode();
//			booleanNode.setId(idService.getNextId());
//			booleanNode.setType(NodeListenerImpl.PREDICATE);
//			booleanNode.setLabel(booleanNodeB.getLabel());
//			booleanNode.setChildA(booleanNodeA);
//
//			booleanNode.setChildB(booleanNodeC);
//
//			objectListener.constraintFound(booleanNode);
//
//			return booleanNode;
//
//		} else if ((node.getType() == NodeListenerImpl.PREDICATE) && (6 == node.getChildren().size())) {
//
//			final Node firstChild = node.getChildren().get(0);
//			final BooleanNode booleanNodeA = processWhereClause(firstChild, objectListener, idService);
//
//			final Node secondChild = node.getChildren().get(1);
//			final BooleanNode booleanNodeB = processWhereClause(secondChild, objectListener, idService);
//
//			final Node thirdChild = node.getChildren().get(2);
//			final BooleanNode booleanNodeC = processWhereClause(thirdChild, objectListener, idService);
//
//			final Node fourthChild = node.getChildren().get(3);
//			final BooleanNode booleanNodeD = processWhereClause(fourthChild, objectListener, idService);
//
//			final Node fifthChild = node.getChildren().get(4);
//			final BooleanNode booleanNodeE = processWhereClause(fifthChild, objectListener, idService);
//			booleanNodeC.setChildB(booleanNodeE);
//
//			final Node sixthChild = node.getChildren().get(5);
//			final BooleanNode booleanNodeF = processWhereClause(sixthChild, objectListener, idService);
//
//			booleanNodeE.setChildA(booleanNodeD);
//			booleanNodeE.setChildB(booleanNodeF);
//
//			final BooleanNode booleanNode = new BooleanNode();
//			booleanNode.setId(idService.getNextId());
//			booleanNode.setType(NodeListenerImpl.PREDICATE);
//			booleanNode.setLabel(booleanNodeB.getLabel());
//			booleanNode.setChildA(booleanNodeA);
//
//			booleanNode.setChildB(booleanNodeC);
//
//			objectListener.constraintFound(booleanNode);
//
//			return booleanNode;
//
//		} else if ((node.getType() == NodeListenerImpl.BIT_EXPR) && (3 == node.getChildren().size())) {
//
//			final BooleanNode booleanNode = new BooleanNode();
//			booleanNode.setId(idService.getNextId());
//			booleanNode.setType(NodeListenerImpl.BIT_EXPR);
//
//			// column or constant
//			final Node firstChild = node.getChildren().get(0);
//			final BooleanNode booleanNodeA = processWhereClause(firstChild, objectListener, idService);
//			booleanNode.setChildA(booleanNodeA);
//
//			// operator
//			final Node secondChild = node.getChildren().get(1);
//			final String comparator = outputFirstLeave(secondChild, objectListener);
//			booleanNode.setOperator(comparator);
//			if (useIdInLabel) {
//				booleanNode.setLabel(comparator + " (" + booleanNode.getId() + ")");
//			} else {
//				booleanNode.setLabel(comparator);
//			}
//
//			// column or constant
//			final Node thirdChild = node.getChildren().get(2);
//			final BooleanNode booleanNodeB = processWhereClause(thirdChild, objectListener, idService);
//			booleanNode.setChildB(booleanNodeB);
//
//			return booleanNode;
//
//		} else if ((node.getType() == NodeListenerImpl.SIMPLE_EXPR) && (2 == node.getChildren().size())) {
//
//			final BooleanNode booleanNode = new BooleanNode();
//			booleanNode.setId(idService.getNextId());
//			booleanNode.setType(NodeListenerImpl.SIMPLE_EXPR);
//
//			final Node firstChild = node.getChildren().get(0);
//			final BooleanNode booleanNodeA = processWhereClause(firstChild, objectListener, idService);
//			booleanNode.setLabel(booleanNodeA.getLabel());
//
//			final Node secondChild = node.getChildren().get(1);
//			final BooleanNode booleanNodeB = processWhereClause(secondChild, objectListener, idService);
//
//			booleanNode.setChildB(booleanNodeB);
//
//			return booleanNode;
//
//		} else if ((node.getType() == NodeListenerImpl.EXPR) && (4 == node.getChildren().size())) {
//
//			final BooleanNode booleanNode = new BooleanNode();
//			booleanNode.setId(idService.getNextId());
//
//			// column or constant
//			final Node firstChild = node.getChildren().get(0);
//			final BooleanNode booleanNodeA = processWhereClause(firstChild, objectListener, idService);
//			booleanNode.setChildA(booleanNodeA);
//
//			// column or constant
//			final Node secondChild = node.getChildren().get(1);
//			final BooleanNode booleanNodeB = processWhereClause(secondChild, objectListener, idService);
//			booleanNode.setLabel(booleanNodeB.getLabel());
//
//			// column or constant
//			final Node thirdChild = node.getChildren().get(2);
//			final BooleanNode booleanNodeC = processWhereClause(thirdChild, objectListener, idService);
//			booleanNode.setChildB(booleanNodeC);
//
//			final Node fourthChild = node.getChildren().get(3);
//			final BooleanNode booleanNodeD = processWhereClause(fourthChild, objectListener, idService);
//			booleanNodeC.setChildB(booleanNodeD);
//
//			objectListener.constraintFound(booleanNode);
//
//			return booleanNode;
//
//		} else if ((node.getType() == NodeListenerImpl.EXPR) && (3 == node.getChildren().size())) {
//
//			final BooleanNode booleanNode = new BooleanNode();
//			booleanNode.setId(idService.getNextId());
//
//			// column or constant
//			final Node firstChild = node.getChildren().get(0);
//			final BooleanNode booleanNodeA = processWhereClause(firstChild, objectListener, idService);
//			booleanNode.setChildA(booleanNodeA);
//
//			// operator
//			final Node secondChild = node.getChildren().get(1);
//			final String comparator = outputFirstLeave(secondChild, objectListener);
//			booleanNode.setOperator(comparator);
//			if (useIdInLabel) {
//				booleanNode.setLabel(comparator + " (" + booleanNode.getId() + ")");
//			} else {
//				booleanNode.setLabel(comparator);
//			}
//
//			// column or constant
//			final Node thirdChild = node.getChildren().get(2);
//			final BooleanNode booleanNodeB = processWhereClause(thirdChild, objectListener, idService);
//			booleanNode.setChildB(booleanNodeB);
//
//			objectListener.constraintFound(booleanNode);
//
//			return booleanNode;
//
//		} else if ((node.getType() == NodeListenerImpl.EXPR) && (2 == node.getChildren().size())) {
//
//			final BooleanNode booleanNode = new BooleanNode();
//			booleanNode.setId(idService.getNextId());
//
//			// operator
//			final Node secondChild = node.getChildren().get(0);
//			final String comparator = outputFirstLeave(secondChild, objectListener);
//			booleanNode.setOperator(comparator);
//			if (useIdInLabel) {
//				booleanNode.setLabel(comparator + " (" + booleanNode.getId() + ")");
//			} else {
//				booleanNode.setLabel(comparator);
//			}
//
//			// column or constant
//			final Node thirdChild = node.getChildren().get(1);
//			final BooleanNode booleanNodeB = processWhereClause(thirdChild, objectListener, idService);
//			booleanNode.setChildB(booleanNodeB);
//
//			return booleanNode;
//
//		} else if ((node.getType() == NodeListenerImpl.BOOLEAN_PRIMARY) && (4 == node.getChildren().size())) {
//
//			final BooleanNode booleanNode = new BooleanNode();
//			booleanNode.setId(idService.getNextId());
//
//			// column or constant
//			final Node firstChild = node.getChildren().get(0);
//			final BooleanNode booleanNodeA = processWhereClause(firstChild, objectListener, idService);
//			booleanNode.setChildA(booleanNodeA);
//
//			// column or constant
//			final Node secondChild = node.getChildren().get(1);
//			final BooleanNode booleanNodeB = processWhereClause(secondChild, objectListener, idService);
//			booleanNode.setLabel(booleanNodeB.getLabel());
//
//			// column or constant
//			final Node thirdChild = node.getChildren().get(2);
//			final BooleanNode booleanNodeC = processWhereClause(thirdChild, objectListener, idService);
//			booleanNode.setChildB(booleanNodeC);
//
//			final Node fourthChild = node.getChildren().get(3);
//			final BooleanNode booleanNodeD = processWhereClause(fourthChild, objectListener, idService);
//			booleanNodeC.setChildB(booleanNodeD);
//
//			objectListener.constraintFound(booleanNode);
//
//			return booleanNode;
//
//		} else if ((node.getType() == NodeListenerImpl.BOOLEAN_PRIMARY) && (3 == node.getChildren().size())) {
//
//			final BooleanNode booleanNode = new BooleanNode();
//			booleanNode.setId(idService.getNextId());
//
//			// column or constant
//			final Node firstChild = node.getChildren().get(0);
//			final BooleanNode booleanNodeA = processWhereClause(firstChild, objectListener, idService);
//			booleanNode.setChildA(booleanNodeA);
//
//			// operator
//			final Node secondChild = node.getChildren().get(1);
//			final String comparator = outputFirstLeave(secondChild, objectListener);
//			booleanNode.setOperator(comparator);
//			if (useIdInLabel) {
//				booleanNode.setLabel(comparator + " (" + booleanNode.getId() + ")");
//			} else {
//				booleanNode.setLabel(comparator);
//			}
//
//			// column or constant
//			final Node thirdChild = node.getChildren().get(2);
//			final BooleanNode booleanNodeB = processWhereClause(thirdChild, objectListener, idService);
//			booleanNode.setChildB(booleanNodeB);
//
//			objectListener.constraintFound(booleanNode);
//
//			return booleanNode;
//
//		} else if (node.getType() == NodeListenerImpl.PATH) {
//
//			final String firstPath = outputFirstPath(node, objectListener);
//			final BooleanNode tempBooleanNode = new BooleanNode();
//
//			tempBooleanNode.setId(idService.getNextId());
//
//			if (useIdInLabel) {
//				tempBooleanNode.setLabel(firstPath + " (" + tempBooleanNode.getId() + ")");
//			} else {
//				tempBooleanNode.setLabel(firstPath);
//			}
//			tempBooleanNode.setType(NodeListenerImpl.PATH);
//
//			return tempBooleanNode;
//
//		} else if (node.getType() == NodeListenerImpl.TERMINAL) {
//
//			final BooleanNode tempBooleanNode = new BooleanNode();
//
//			tempBooleanNode.setId(idService.getNextId());
//
//			if (useIdInLabel) {
//				tempBooleanNode.setLabel(node.getLabel() + " (" + tempBooleanNode.getId() + ")");
//			} else {
//				tempBooleanNode.setLabel(node.getLabel());
//			}
//			tempBooleanNode.setType(NodeListenerImpl.TERMINAL);
//
//			return tempBooleanNode;
//		}
//
//		for (final Node child : node.getChildren()) {
//
//			// do not process (, ) or other token, only traverse over rule nodes
//			if (child.getType() == NodeListenerImpl.TERMINAL) {
//
//				if (child.getLabel().trim().equalsIgnoreCase(OPENING_BRACKET)) {
//					continue;
//				} else if (child.getLabel().trim().equalsIgnoreCase(")")) {
//					continue;
//				}
//
//				// this might be a number for example
//				// String firstPath = outputFirstPath(node, objectListener);
//				final BooleanNode tempBooleanNode = new BooleanNode();
//
//				tempBooleanNode.setId(idService.getNextId());
//				tempBooleanNode.setType(NodeListenerImpl.TERMINAL);
//
//				if (useIdInLabel) {
//					tempBooleanNode.setLabel(child.getLabel().trim() + " (" + tempBooleanNode.getId() + ")");
//				} else {
//					tempBooleanNode.setLabel(child.getLabel().trim());
//				}
//
//				return tempBooleanNode;
//
//			} else {
//				return processWhereClause(child, objectListener, idService);
//			}
//		}
//
//		return processWhereClause(node.getChildren().get(0), objectListener, idService);
//	}
//
//	/**
//	 * Output expr.
//	 *
//	 * @param node           the node
//	 * @param objectListener the object listener
//	 * @param idService      the id service
//	 * @return the boolean node
//	 */
//	public static BooleanNode outputExpr(final Node node, final ObjectListener objectListener,
//			final IdService idService) {
//
//		if ((node.getType() == NodeListenerImpl.EXPR) && (3 == node.getChildren().size())) {
//
//			final BooleanNode booleanNode = new BooleanNode();
//			booleanNode.setId(idService.getNextId());
//
//			// column or constant
//			final Node firstChild = node.getChildren().get(0);
//			final BooleanNode booleanNodeA = outputExpr(firstChild, objectListener, idService);
//			booleanNode.setChildA(booleanNodeA);
//
//			// operator
//			final Node secondChild = node.getChildren().get(1);
//			final String comparator = outputFirstLeave(secondChild, objectListener);
//			booleanNode.setOperator(comparator);
//			booleanNode.setLabel(comparator + " (" + booleanNode.getId() + ")");
//
//			// column or constant
//			final Node thirdChild = node.getChildren().get(2);
//			final BooleanNode booleanNodeB = outputExpr(thirdChild, objectListener, idService);
//			booleanNode.setChildB(booleanNodeB);
//
//			objectListener.constraintFound(booleanNode);
//
//			return booleanNode;
//
//		} else if (node.getType() == NodeListenerImpl.BOOLEAN_PRIMARY) {
//
//			if (SIZE_THREE == node.getChildren().size()) {
//				return outputAllBooleanPrimaries(node, objectListener, idService);
//			} else {
//				return outputExpr(node.getChildren().get(0), objectListener, idService);
//			}
//
//		} else if (node.getType() == NodeListenerImpl.PATH) {
//
//			final String firstPath = outputFirstPath(node, objectListener);
//			final BooleanNode tempBooleanNode = new BooleanNode();
//			tempBooleanNode.setLabel(firstPath);
//
//			return tempBooleanNode;
//
//		} else {
//			return outputExpr(node.getChildren().get(0), objectListener, idService);
//		}
//	}
//
//	/**
//	 * Output all boolean primaries.
//	 *
//	 * @param node           the node
//	 * @param objectListener the object listener
//	 * @param idService      the id service
//	 * @return the boolean node
//	 */
//	public static BooleanNode outputAllBooleanPrimaries(final Node node, final ObjectListener objectListener,
//			final IdService idService) {
//
//		if (node.getType() == NodeListenerImpl.BOOLEAN_PRIMARY) {
//
//			if (SIZE_THREE == node.getChildren().size()) {
//
//				final BooleanNode booleanNode = new BooleanNode();
//				booleanNode.setId(idService.getNextId());
//
//				// column or constant
//				final Node firstChild = node.getChildren().get(0);
//				final BooleanNode booleanNodeA = outputExpr(firstChild, objectListener, idService);
//				if (null == booleanNodeA) {
//					final String firstPath = outputFirstPath(firstChild, objectListener);
//					if (null == firstPath) {
//						final String literal = outputFirstLeave(firstChild, objectListener);
//						booleanNode.setConstA(literal);
//					} else {
//						booleanNode.setColumnA(firstPath);
//					}
//				} else {
//					booleanNode.setChildA(booleanNodeA);
//				}
//
//				// operator
//				final Node secondChild = node.getChildren().get(1);
//				final String comparator = outputFirstLeave(secondChild, objectListener);
//				booleanNode.setOperator(comparator);
//				booleanNode.setLabel(comparator + " (" + booleanNode.getId() + ")");
//
//				// column or constant
//				final Node thirdChild = node.getChildren().get(2);
//				final BooleanNode booleanNodeB = outputExpr(thirdChild, objectListener, idService);
//				if (null == booleanNodeB) {
//					final String secondPath = outputFirstPath(thirdChild, objectListener);
//					if (null == secondPath) {
//						final String literal = outputFirstLeave(thirdChild, objectListener);
//						booleanNode.setConstB(literal);
//					} else {
//						booleanNode.setColumnB(secondPath);
//					}
//				} else {
//					booleanNode.setChildB(booleanNodeB);
//				}
//
//				objectListener.constraintFound(booleanNode);
//
//				return booleanNode;
//			}
//		}
//
//		for (final Node child : node.getChildren()) {
//
//			BooleanNode booleanNode = null;
//
//			booleanNode = outputExpr(child, objectListener, idService);
//
//			if (null != booleanNode) {
//				return booleanNode;
//			}
//
//			booleanNode = outputAllBooleanPrimaries(child, objectListener, idService);
//
//			if (null != booleanNode) {
//				return booleanNode;
//			}
//		}
//
//		return null;
//	}
//
//	/**
//	 * Output first leave.
//	 *
//	 * @param node           the node
//	 * @param objectListener the object listener
//	 * @return the string
//	 */
//	public static String outputFirstLeave(final Node node, final ObjectListener objectListener) {
//
//		if (0 == node.getChildren().size()) {
//			return node.getLabel();
//		}
//
//		return outputFirstLeave(node.getChildren().get(0), objectListener);
//	}
//
//	/**
//	 * walks the entire tree depth first and performs an action on all
//	 * (enterSelect_expr) SELECT_EXPR nodes.
//	 *
//	 * @param node                        the node
//	 * @param objectListener              the object listener
//	 * @param mySQLDatasourceTableFactory the my SQL datasource table factory
//	 * @throws MedhubException
//	 */
//	public static void outputAllColumns(final Node node, final ObjectListener objectListener,
//			final MySQLDatasourceTableFactory mySQLDatasourceTableFactory) throws MedhubException {
//
//		if (node.getType() == NodeListenerImpl.SELECT_EXPRS) {
//
//			final StringBuffer aliasStringBuffer = new StringBuffer();
//
//			for (final Node child : node.getChildren()) {
//
//				if (child.getType() == NodeListenerImpl.TERMINAL) {
//
//					if (child.getLabel().equalsIgnoreCase(",")) { // NOPMD
//						// empty
//					} else if (child.getLabel().equalsIgnoreCase(ASTERISK)) {
//
//						// find the root node
//						Node parentNode = node.getParent();
//						Node childNode = node;
//						while (null != parentNode) {
//							childNode = parentNode;
//							parentNode = childNode.getParent();
//						}
//						parentNode = childNode;
//
//						final List<CSVExportColumnIdentifier> columns = retrieveColumns(parentNode, node,
//								mySQLDatasourceTableFactory);
//
//						for (final CSVExportColumnIdentifier colId : columns) {
//							objectListener.columnFound(colId.getColumnIdentifier());
//						}
//
//					} else {
//						aliasStringBuffer.append(child.getLabel());
//						aliasStringBuffer.append(ONE_SPACE_EMPTY_STRING);
//					}
//				}
//			}
//
//			if (aliasStringBuffer.length() > 0) {
//				objectListener.aliasFound(aliasStringBuffer.toString().trim());
//			}
//
//		} else if (node.getType() == NodeListenerImpl.SELECT_EXPR) {
//
//			final StringBuffer stringBuffer = new StringBuffer();
//
//			for (final Node child : node.getChildren()) {
//
//				String path = outputFirstPath(child, objectListener);
//
//				if (null == path) {
//					path = outputFirstStringLiteral(child, objectListener);
//				}
//
//				if (null == path) {
//					path = TreeWalkingTools.nodeLeavesAsString(child, true);
//				}
//
//				stringBuffer.append(path);
//			}
//
//			// output the path
//			objectListener.columnFound(stringBuffer.toString());
//
//		} else if (node.getType() == NodeListenerImpl.SUB_SELECT_IN_SELECT_CLAUSE) {
//
//			final StringBuffer stringBuffer = new StringBuffer();
//
//			// visit the entire sub tree starting from this node
//			// output all leave nodes in depth first order
//			// create a string from that statement
//
//			final StringBuffer stringBufferDepthFirstSubtree = new StringBuffer();
//			depthFirstSubTree(node, stringBufferDepthFirstSubtree);
//			final String depthFirstSubTree = stringBufferDepthFirstSubtree.toString();
//
//			// store the string as value
//			stringBuffer.append(depthFirstSubTree);
//
//			// visit the parent
//			final Node parent = node.getParent();
//
//			// the parents second child is the AS token
//			stringBuffer.append(ONE_SPACE_EMPTY_STRING).append(parent.getChildren().get(1).getLabel()); // NOPMD
//
//			// the parents third child is the alias
//			stringBuffer.append(ONE_SPACE_EMPTY_STRING).append(parent.getChildren().get(2).getLabel());
//
//			// create a static column (has no parent table) that
//			// uses the string as value and the alias as alias
//
//			// output the path
//			objectListener.subSelectColumnFound(stringBuffer.toString());
//
//			return;
//		}
//
//		// recurse further
//		for (final Node child : node.getChildren()) {
//			outputAllColumns(child, objectListener, mySQLDatasourceTableFactory);
//		}
//	}
//
//	/**
//	 * Retrieve columns.
//	 *
//	 * @param selectStatementRootNode     the select statement root node
//	 * @param enterSelectExprs            the enter select expression
//	 * @param mySQLDatasourceTableFactory the my SQL datasource table factory
//	 * @return the list
//	 * @throws MedhubException
//	 */
//	public static List<CSVExportColumnIdentifier> retrieveColumns(final Node selectStatementRootNode,
//			final Node enterSelectExprs, final MySQLDatasourceTableFactory mySQLDatasourceTableFactory)
//			throws MedhubException {
//
//		List<CSVExportColumnIdentifier> columns = new ArrayList<>();
//
//		final String rawLabel = enterSelectExprs.getChildren().get(0).getRawLabel();
//
//		if (rawLabel.equalsIgnoreCase(ASTERISK)) {
//
//			final List<String> tableNames = new ArrayList<>();
//
//			// a star is specified, determine the columns from the tables
//			// meta data
//
//			TreeWalkingTools.outputAllTables(selectStatementRootNode, new ObjectListener() {
//
//				@Override
//				public void tableFound(final String table) {
//					tableNames.add(table);
//				}
//
//				@Override
//				public void subSelectColumnFound(final String column) {
//					// empty
//				}
//
//				@Override
//				public void staticColumnFound(final String column) {
//					// empty
//				}
//
//				@Override
//				public void constraintFound(final DefaultNode booleanNode) {
//					// empty
//				}
//
//				@Override
//				public void columnFound(final String column) {
//					// empty
//				}
//
//				@Override
//				public void aliasFound(final String alias) {
//					// empty
//				}
//			});
//
//			// DEBUG
//			if (LOG.isTraceEnabled()) {
//				if (MapUtils.isNotEmpty(mySQLDatasourceTableFactory.getNameToTableMap())) {
//					for (final Map.Entry<String, DefaultTable> entry : mySQLDatasourceTableFactory.getNameToTableMap()
//							.entrySet()) {
//						LOG.trace(entry.getKey() + ONE_SPACE_EMPTY_STRING + entry.getValue());
//					}
//				}
//			}
//
//			final DefaultTable exportGUITable = mySQLDatasourceTableFactory
//					.containsTableCheckWithoutSchema(tableNames.get(0));
//
//			Preconditions.checkNotNull(exportGUITable,
//					"Could not find table \"" + tableNames.get(0) + "\" in the map of tables: \""
//							+ StringUtils.join(mySQLDatasourceTableFactory.getNameToTableMap().keySet(), ",")
//							+ "\". Make sure the table exists in the database!");
//
//			// user has mistyped a table name or the table has not been created
//			// yet
//			if (null != exportGUITable) {
//
//				final List<Column> tableColumns = exportGUITable.getColumns();
//
//				for (final Column column : tableColumns) {
//
//					final CSVExportColumnIdentifier csvExportColumnIdentifier = new CSVExportColumnIdentifier();
//					columns.add(csvExportColumnIdentifier);
//
//					csvExportColumnIdentifier.setColumnIdentifier(column.getName());
//				}
//			}
//
//		} else {
//
//			// retrieve a list of all columns, which is needed to extract data
//			// from the SQL statement
//			final CSVExportColumnListener csvExportColumnListener = new CSVExportColumnListener();
//
//			// output all columns into the object listener
//			TreeWalkingTools.outputAllColumns(selectStatementRootNode, csvExportColumnListener,
//					mySQLDatasourceTableFactory);
//
//			// if columns are specified explicitly, use those
//			columns = csvExportColumnListener.getColumns();
//		}
//
//		return columns;
//	}
//
//	public static Map<String, DefaultTable> retrieveTables(final Node selectStatementRootNode,
//			final Node enterSelectExprs, final MySQLDatasourceTableFactory mySQLDatasourceTableFactory)
//			throws MedhubException {
//
//		// find all tables in their statement.
//		// The ExportGUITableListener will contain a aliasToTable
//		// that contains all tables indexed by their alias as key.
//		// If a table has no alias in the statement, it is indexed by its fully
//		// qualified name (= schema.tablename)
//		final ExportGUITableListener exportGUITableListener = new ExportGUITableListener();
//		exportGUITableListener.setMySQLDatasourceTableFactory(mySQLDatasourceTableFactory);
//
//		TreeWalkingTools.outputAllTables(selectStatementRootNode, exportGUITableListener);
//
//		return exportGUITableListener.getAliasToTable();
//	}

//	/**
//	 * depth first leaves.
//	 *
//	 * DOES NOT RETURN BRACKETS!!!
//	 *
//	 * @param node                          the node
//	 * @param stringBufferDepthFirstSubtree the string buffer depth first subtree
//	 */
//	private static void depthFirstSubTree(final Node node, final StringBuffer stringBufferDepthFirstSubtree) {
//
//		// is this a leaf?
//		if (0 == node.getChildren().size()) {
//
//			// insert a separator
//			if (0 != stringBufferDepthFirstSubtree.length()) {
//				stringBufferDepthFirstSubtree.append(ONE_SPACE_EMPTY_STRING);
//			}
//
//			// insert the label
//			stringBufferDepthFirstSubtree.append(node.getLabel());
//
//			return;
//		}
//
//		// recurse
//		for (final Node child : node.getChildren()) {
//			depthFirstSubTree(child, stringBufferDepthFirstSubtree);
//		}
//	}
//
//	/**
//	 * Output first string literal.
//	 *
//	 * @param node           the node
//	 * @param objectListener the object listener
//	 * @return the string
//	 */
//	private static String outputFirstStringLiteral(final Node node, final ObjectListener objectListener) {
//
//		if (node.getType() == NodeListenerImpl.STRING_LITERAL) {
//			return node.getChildren().get(0).getRawLabel().toString().trim();
//		}
//
//		// recurse
//		for (final Node child : node.getChildren()) {
//			final String result = outputFirstStringLiteral(child, objectListener);
//			if (null != result) {
//				return result;
//			}
//		}
//
//		return null;
//	}
//
//	/**
//	 * Walks the entire tree depth first and performs an action on all
//	 * (enterSelect_expr) SELECT_EXPR nodes. <br />
//	 * <br />
//	 * This method does not call the listener about tables in sub selects inside the
//	 * SELECT_CLAUSE.
//	 *
//	 * @param node           the node
//	 * @param objectListener the object listener
//	 * @throws MedhubException
//	 */
//	public static void outputAllTables(final Node node, final ObjectListener objectListener) throws MedhubException {
//
//		if (node.getType() == NodeListenerImpl.TABLE_REFERENCE) {
//
//			if (outputAllTablesIsInsideSubSelect) {
//				return;
//			}
//
//			//
//			// process the alias
//			//
//
//			final StringBuffer aliasStringBuffer = new StringBuffer();
//
//			for (final Node child : node.getChildren()) {
//
//				if (child.getType() == NodeListenerImpl.TERMINAL) {
//
//					if (!child.getLabel().equalsIgnoreCase(",")) { // NOPMD
//						aliasStringBuffer.append(child.getLabel());
//						aliasStringBuffer.append(ONE_SPACE_EMPTY_STRING);
//					}
//				}
//
//			}
//
//			if (aliasStringBuffer.length() > 0) {
//				objectListener.aliasFound(aliasStringBuffer.toString().trim());
//			}
//
//			//
//			// descend until a path node is found
//			//
//
//			Node tempNode = node;
//
//			while (NodeListenerImpl.PATH != tempNode.getType()) {
//				tempNode = tempNode.getChildren().get(0);
//			}
//
//			// output the path
//			final String tableName = dumpPathNode(tempNode);
//			objectListener.tableFound(tableName);
//
//		} else if (node.getType() == NodeListenerImpl.SUB_SELECT_IN_SELECT_CLAUSE) {
//
//			// entering the sub select, do not output tables to the list of
//			// tables
//			outputAllTablesIsInsideSubSelect = true;
//		}
//
//		for (final Node child : node.getChildren()) {
//			outputAllTables(child, objectListener);
//		}
//
//		// leaving the sub select, output tables to the list of tables again
//		if (node.getType() == NodeListenerImpl.SUB_SELECT_IN_SELECT_CLAUSE) {
//			outputAllTablesIsInsideSubSelect = false;
//		}
//	}
//
//	/**
//	 * Output all paths.
//	 *
//	 * @param node           the node
//	 * @param objectListener the object listener
//	 */
//	public static void outputAllPaths(final Node node, final ObjectListener objectListener) {
//
//		for (final Node child : node.getChildren()) {
//			outputAllPaths(child, objectListener);
//		}
//	}
//
//	/**
//	 * process a node of type path.
//	 *
//	 * @param node           the node
//	 * @param objectListener the object listener
//	 * @return the string
//	 */
//	public static String outputFirstPath(final Node node, final ObjectListener objectListener) {
//
//		if (node.getType() == NodeListenerImpl.PATH) {
//			return dumpPathNode(node);
//		}
//
//		for (final Node child : node.getChildren()) {
//			final String result = outputFirstPath(child, objectListener);
//			if (null != result) {
//				return result;
//			}
//		}
//
//		return null;
//	}
//
//	/**
//	 * Dump path node.
//	 *
//	 * @param node the node
//	 * @return the string
//	 */
//	public static String dumpPathNode(final Node node) {
//
//		final StringBuffer stringBuffer = new StringBuffer();
//
//		int i = 0;
//		for (final Node child : node.getChildren()) {
//
//			// append node with index 0 which is an alias
//			// append node with index 1 which is the dot
//			if ((i == 0) || (i == 1)) {
//				stringBuffer.append(child.getLabel());
//			}
//
//			// append node with index 2, which is the column name string literal
//			if (i == SIZE_TWO) {
//				final String result = nodeLeavesAsString(child);
//				stringBuffer.append(result);
//			}
//
//			i++;
//		}
//
//		return stringBuffer.toString();
//	}
//
//	/**
//	 * Generate where clause.
//	 *
//	 * @param whereClauseRootNode the where clause root node
//	 * @return the string
//	 */
//	public static String generateWhereClause(final DefaultNode whereClauseRootNode) {
//
//		final BooleanNode booleanNode = (BooleanNode) whereClauseRootNode;
//
//		final StringBuffer stringBuffer = new StringBuffer();
//
//		if (null != booleanNode.getChildA()) {
//			stringBuffer.append(recurseGenerateWhereClause(booleanNode.getChildA()));
//		}
//
//		stringBuffer.append(booleanNode.getLabel()).append(ONE_SPACE_EMPTY_STRING);
//
//		if (null != booleanNode.getChildB()) {
//			stringBuffer.append(recurseGenerateWhereClause(booleanNode.getChildB()));
//		}
//
//		return stringBuffer.toString().trim();
//	}
//
//	/**
//	 * Recurse generate where clause.
//	 *
//	 * @param node the node
//	 * @return the string
//	 */
//	private static String recurseGenerateWhereClause(final BooleanNode node) {
//
//		if (null == node) {
//			// no text added to the buffer
//			return null;
//		}
//
//		final StringBuffer stringBuffer = new StringBuffer();
//
//		String textA = null;
//		if (null != node.getChildA()) {
//			textA = recurseGenerateWhereClause(node.getChildA());
//		} else if (null != node.getColumnA()) {
//			textA = node.getColumnA();
//		} else if (null != node.getConstA()) {
//			textA = node.getConstA();
//		}
//
//		String textB = null;
//		if (null != node.getChildB()) {
//			textB = recurseGenerateWhereClause(node.getChildB());
//		} else if (null != node.getColumnB()) {
//			textB = node.getColumnB();
//		} else if (null != node.getConstB()) {
//			textB = node.getConstB();
//		}
//
//		if ((node.getType() != NodeListenerImpl.TERMINAL) && (node.getType() != NodeListenerImpl.PATH)) {
//			stringBuffer.append("( ");
//		}
//		if (null != textA) {
//			stringBuffer.append(textA);
//		}
//		stringBuffer.append(node.getLabel()).append(ONE_SPACE_EMPTY_STRING);
//		if (null != textB) {
//			stringBuffer.append(textB);
//		}
//		if ((node.getType() != NodeListenerImpl.TERMINAL) && (node.getType() != NodeListenerImpl.PATH)) {
//			stringBuffer.append(") ");
//		}
//
//		// text has been added to the buffer
//		return stringBuffer.toString();
//	}
//
//	public static String nodeLeavesAsString(final Node parentNode) {
//		return nodeLeavesAsString(parentNode, false);
//	}
//
//	/**
//	 * Converts this node into a string by appending all the labels of all leaf
//	 * nodes in the subtree of that node.
//	 *
//	 * @param parentNode the parent node
//	 * @return the string
//	 */
//	public static String nodeLeavesAsString(final Node parentNode, final boolean noSpace) {
//
//		final List<Node> allLeaves = new ArrayList<>();
//		outputAllLeavesReturnPaths(parentNode, allLeaves);
//
//		// DEBUG
//		if (LOG.isTraceEnabled()) {
//			for (final Node node : allLeaves) {
//				LOG.trace(node.getRawLabel());
//			}
//		}
//
//		String lastRawLabel = null;
//
//		final StringBuffer stringBuffer = new StringBuffer();
//
//		for (final Node node : allLeaves) {
//
//			if (node.getRawLabel().equalsIgnoreCase("enterPath")) {
//
//				final List<Node> subAllLeaves = new ArrayList<>();
//				outputAllLeaves(node, subAllLeaves);
//
//				if (0 != stringBuffer.length() && !noSpace) {
//					stringBuffer.append(ONE_SPACE_EMPTY_STRING);
//				}
//
//				for (final Node subNode : subAllLeaves) {
//					stringBuffer.append(subNode.getRawLabel());
//				}
//
//				lastRawLabel = node.getRawLabel();
//
//				continue;
//
//			} else if (
//
//			(0 != stringBuffer.length()) && (!node.getRawLabel().equalsIgnoreCase("`"))
//					&& (!node.getRawLabel().equalsIgnoreCase("'")) && (!node.getRawLabel().equalsIgnoreCase("\""))
//					&& ((null != lastRawLabel) && (!lastRawLabel.equalsIgnoreCase("`"))
//							&& (!lastRawLabel.equalsIgnoreCase("'")) && (!lastRawLabel.equalsIgnoreCase("\"")))
//					&& !noSpace) {
//
//				stringBuffer.append(ONE_SPACE_EMPTY_STRING);
//			}
//
//			stringBuffer.append(node.getRawLabel());
//			lastRawLabel = node.getRawLabel();
//		}
//
//		if (LOG.isTraceEnabled()) {
//			LOG.trace(stringBuffer.toString());
//		}
//
//		return stringBuffer.toString();
//	}
//
//	/**
//	 * Returns true, if this alter statement node contains "add constraint".
//	 *
//	 * @param alterStatementNode the alter statement node
//	 * @return true, if is constraint
//	 */
//	public static boolean isConstraint(final Node alterStatementNode) {
//
//		// find all enterAlter_specification
//		final List<Node> enterAlter_specifications = new ArrayList<>();
//		TreeWalkingTools.findAll(alterStatementNode, "enterAlter_specification", enterAlter_specifications);
//
//		if (CollectionUtils.isEmpty(enterAlter_specifications)) {
//			return false;
//		}
//
//		for (final Node enterAlter_specification : enterAlter_specifications) {
//
//			if (CollectionUtils.isEmpty(enterAlter_specification.getChildren())) {
//				continue;
//			}
//
//			final Node child0 = enterAlter_specification.getChildren().get(0);
//			final Node child1 = enterAlter_specification.getChildren().get(1);
//
//			if (!child0.getRawLabel().equalsIgnoreCase("add")) {
//				continue;
//			}
//			if (!child1.getRawLabel().equalsIgnoreCase("constraint")) {
//				continue;
//			}
//
//			return true; // NOPMD
//		}
//
//		return false;
//	}
//
//	/**
//	 * node = enterCreate_table_statement.
//	 *
//	 * @param node   the node
//	 * @param source the source
//	 * @param schema the schema
//	 * @return the export GUI table
//	 * @throws MedhubException
//	 */
//	public static DefaultTable node2Table(final Node node, final String source, final String schema)
//			throws MedhubException {
//
//		final DefaultTable exportGUITable = new DefaultTable();
//		exportGUITable.setSource(source);
//
//		// retrieve the table name. The table name is a token which is not
//		// CREATE, not TABLE and not TEMPORARY
//		for (final Node child : node.getChildren()) {
//
//			if (child.getRawLabel().equalsIgnoreCase("CREATE") || child.getRawLabel().equalsIgnoreCase("TABLE")
//					|| child.getRawLabel().equalsIgnoreCase("TEMPORARY")) {
//				continue;
//			}
//
//			String name = null;
//
//			if (child.getType() == NodeListenerImpl.TERMINAL) {
//				name = child.getRawLabel();
//			} else if (child.getType() == NodeListenerImpl.ENTER_EVERY_RULE) {
//				name = TreeWalkingTools.nodeLeavesAsString(child);
//			}
//
//			if (StringUtils.isBlank(name)) {
//				throw new MedhubException("No name retrievable!");
//			}
//
//			splitSchemaAndName(exportGUITable, Util.unquote(name));
//
//			break; // NOPMD
//		}
//
//		if (LOG.isTraceEnabled()) {
//			LOG.trace("table name: \"" + exportGUITable.getName() + "\"");
//		}
//
//		// column definitions
//		final List<Node> columDefinitions = new ArrayList<>();
//		TreeWalkingTools.findAll(node, "enterColumn_def", columDefinitions);
//
//		for (final Node columDefinitionNode : columDefinitions) {
//
//			if (null == columDefinitionNode) {
//				continue;
//			}
//
//			final DefaultColumn column = node2Column(columDefinitionNode, source, schema);
//			exportGUITable.getColumns().add(column);
//			column.setExportGUITable(exportGUITable);
//			column.setSource(source);
//			column.setSchema(schema);
//
//		}
//
//		// TODO: table constraints, PRIMARY KEY, FOREIGN KEY
//
//		final List<Node> tableConstraintS = new ArrayList<>();
//		TreeWalkingTools.findAll(node, "enterTable_constraint", tableConstraintS);
//
//		if (!tableConstraintS.isEmpty()) {
//
//			for (final Node tableConstraint : tableConstraintS) {
//
//				final List<Node> leaves = new ArrayList<>();
//				TreeWalkingTools.outputAllLeaves(tableConstraint, leaves);
//
//				final StringBuffer stringBuffer = new StringBuffer();
//				for (final Node leaf : leaves) {
//
//					if (0 != stringBuffer.length()) {
//						stringBuffer.append(ONE_SPACE_EMPTY_STRING);
//					}
//
//					stringBuffer.append(leaf.getRawLabel());
//				}
//
//				String constraintAsString = stringBuffer.toString();
//				constraintAsString = Util.unifyString(constraintAsString);
//				exportGUITable.getConstraints().add(constraintAsString);
//			}
//		}
//
//		return exportGUITable;
//	}
//
//	/**
//	 * Split schema and name.
//	 *
//	 * @param exportGUITable the export GUI table
//	 * @param name           the name
//	 */
//	public static void splitSchemaAndName(final NameSchema exportGUITable, final String name) {
//		final String[] split = name.split("\\.");
//
//		// if no schema is contained, early out
//		if (SPLIT_LENGTH_ONE == split.length) {
//			exportGUITable.setName(split[split.length - 1]);
//			exportGUITable.setSchema(null);
//			return;
//		}
//
//		// separate schema by table name
//		final StringBuffer stringBuffer = new StringBuffer();
//		for (int i = 0; i < (split.length - 1); i++) {
//			stringBuffer.append(split[i]);
//
//			if (i != (split.length - 2)) {
//				stringBuffer.append('.');
//			}
//		}
//
//		exportGUITable.setName(split[split.length - 1]);
//		exportGUITable.setSchema(stringBuffer.toString());
//	}
//
//	/**
//	 * Transforms a node into a column.
//	 *
//	 * @param columDefinitionNode the column definition node
//	 * @param source              the source
//	 * @param schema              the schema
//	 * @return the export GUI column
//	 * @throws MedhubException
//	 */
//	public static DefaultColumn node2Column(final Node columDefinitionNode, final String source, final String schema)
//			throws MedhubException {
//
//		final DefaultColumn exportGUIColumn = new DefaultColumn();
//		exportGUIColumn.setSource(source);
//
//		// name
//		final Node nameNode = columDefinitionNode.getChildren().get(0);
//		String name = null;
//
//		if (nameNode.getType() == NodeListenerImpl.TERMINAL) {
//			name = nameNode.getRawLabel();
//		} else if (nameNode.getType() == NodeListenerImpl.ENTER_EVERY_RULE) {
//			name = TreeWalkingTools.nodeLeavesAsString(nameNode);
//		}
//
//		splitSchemaAndName(exportGUIColumn, Util.unquote(name));
//
//		if (LOG.isTraceEnabled()) {
//			LOG.trace("column " + name);
//		}
//
//		// data type
//		final List<Node> resultNodeList = new ArrayList<>();
//		TreeWalkingTools.findAll(columDefinitionNode, "enterColumn_data_type", resultNodeList);
//		final Node temp = resultNodeList.get(0);
//
//		// retrieve type name
//		String datatype = null;
//
//		try {
//			if (CollectionUtils.isNotEmpty(temp.getChildren())) {
//				datatype = temp.getChildren().get(0).getRawLabel();
//			}
//		} catch (final Exception e) { // NOPMD
//			LOG.error(e.getMessage(), e);
//		}
//
//		exportGUIColumn.setDatatype(datatype);
//
//		// size descriptor (optional) + signed or unsigned
//		if (SIZE_ONE < temp.getChildren().size()) {
//
//			final List<Node> leafNodes = new ArrayList<>();
//			TreeWalkingTools.outputAllLeaves(temp, leafNodes);
//			final StringBuffer stringBuffer = new StringBuffer();
//
//			String lastNodeAsString = null;
//
//			int nodeIndex = 0;
//			for (final Node node : leafNodes) {
//
//				String rawLabel = node.getRawLabel();
//				rawLabel = rawLabel.trim();
//
//				// unify INT to INTEGER
//				if ("INT".equalsIgnoreCase(rawLabel)) {
//					rawLabel = "INTEGER";
//				}
//
//				// for double(M,D) -> double precision(M,D)
//				if (StringUtils.isNotBlank(lastNodeAsString)) {
//					if (lastNodeAsString.trim().equalsIgnoreCase("double")
//							&& OPENING_BRACKET.equalsIgnoreCase(rawLabel)) {
//						stringBuffer.append(" precision");
//					}
//				}
//
//				// for the column hasChangedAdditionalIllnessManifestation bit
//				// default false not null,
//				// there have to be spaces inserted, otherwise bitdefaultfalse
//				// is created
//				if (StringUtils.isNotBlank(stringBuffer.toString())) {
//					stringBuffer.append(ONE_SPACE_EMPTY_STRING);
//				}
//				stringBuffer.append(rawLabel);
//
//				if ("UNSIGNED".equalsIgnoreCase(rawLabel)) {
//					fixDataType(stringBuffer);
//					stringBuffer.append(ONE_SPACE_EMPTY_STRING);
//				} else if ("PRECISION".equalsIgnoreCase(rawLabel)) {
//					stringBuffer.append(ONE_SPACE_EMPTY_STRING);
//				} else if ("BIT".equalsIgnoreCase(rawLabel)) {
//
//					final Node nextNode = leafNodes.get(nodeIndex + 1);
//					final String nextNodeRawLabel = nextNode.getRawLabel();
//
//					if (!OPENING_BRACKET.equalsIgnoreCase(nextNodeRawLabel)) {
//						// add the default data type size to the bit
//						fixDataType(stringBuffer);
//					}
//				} else if ("BIGINT".equalsIgnoreCase(rawLabel)) {
//
//					final Node nextNode = leafNodes.get(nodeIndex + 1);
//					final String nextNodeRawLabel = nextNode.getRawLabel();
//
//					// if there is no (, the size indicator is missing
//					// the size indicator is returned by tables read from a
//					// database hence the script source has to add them too
//					if (!OPENING_BRACKET.equalsIgnoreCase(nextNodeRawLabel)) {
//
//						// add the default data type size to the bit
//						fixDataType(stringBuffer);
//					}
//				}
//
//				lastNodeAsString = rawLabel;
//
//				nodeIndex++;
//			}
//
//			String dataTypeSize = stringBuffer.toString();
//			dataTypeSize = dataTypeSize.toLowerCase().trim();
//
//			if (dataTypeSize.toLowerCase().endsWith("PRECISION".toLowerCase())) {
//				dataTypeSize += "(20,2)"; // NOPMD
//			}
//
//			dataTypeSize = dataTypeSize.toLowerCase().replaceAll("decimal", "numeric");
//
//			// mask types that should not be replaced
//			dataTypeSize = dataTypeSize.toLowerCase().replaceAll("bigint\\(", "%%%111%%%\\(");
//			dataTypeSize = dataTypeSize.toLowerCase().replaceAll("tinyint\\(", "%%%222%%%\\(");
//			dataTypeSize = dataTypeSize.toLowerCase().replaceAll("smallint\\(", "%%%333%%%\\(");
//
//			dataTypeSize = dataTypeSize.toLowerCase().replaceAll("int\\(", "integer\\(");
//
//			dataTypeSize = dataTypeSize.toLowerCase().replaceAll("%%%111%%%\\(", "bigint\\(");
//			dataTypeSize = dataTypeSize.toLowerCase().replaceAll("%%%222%%%\\(", "tinyint\\(");
//			dataTypeSize = dataTypeSize.toLowerCase().replaceAll("%%%333%%%\\(", "smallint\\(");
//
//			exportGUIColumn.setDatatypeSize(dataTypeSize);
//
//		} else if (SIZE_ONE == temp.getChildren().size()) {
//
//			String dataTypeSize = temp.getChildren().get(0).getRawLabel();
//			dataTypeSize = dataTypeSize.trim().toLowerCase();
//
//			final StringBuffer stringBuffer = new StringBuffer();
//			stringBuffer.append(dataTypeSize);
//
//			fixDataType(stringBuffer);
//
//			dataTypeSize = stringBuffer.toString();
//
//			dataTypeSize = dataTypeSize.toLowerCase().replaceAll("decimal", "numeric");
//
//			// mask types that should not be replaced
//			dataTypeSize = dataTypeSize.toLowerCase().replaceAll("bigint\\(", "%%%111%%%\\(");
//			dataTypeSize = dataTypeSize.toLowerCase().replaceAll("tinyint\\(", "%%%222%%%\\(");
//			dataTypeSize = dataTypeSize.toLowerCase().replaceAll("smallint\\(", "%%%333%%%\\(");
//
//			// this replaces bigint(10) by biginteger(10) which is invalid!
//			// Therefore the masking was introduced
//			dataTypeSize = dataTypeSize.toLowerCase().replaceAll("int\\(", "integer\\(");
//
//			// unmask
//			dataTypeSize = dataTypeSize.toLowerCase().replaceAll("%%%111%%%\\(", "bigint\\(");
//			dataTypeSize = dataTypeSize.toLowerCase().replaceAll("%%%222%%%\\(", "tinyint\\(");
//			dataTypeSize = dataTypeSize.toLowerCase().replaceAll("%%%333%%%\\(", "smallint\\(");
//
//			exportGUIColumn.setDatatypeSize(dataTypeSize);
//		}
//
//		// put the data type into the size descriptor if no size descriptor
//		// could be retrieved.
//		//
//		// As the SQL datasource table factory which uses SQL meta data provides
//		// a value in the size descriptor even if
//		// no size descriptor is given for the column, this behavior is
//		// replicated for the
//		// SQL script factory here.
//		if (StringUtils.isBlank(exportGUIColumn.getDatatypeSize())) {
//			String columnDataType = exportGUIColumn.getDatatype();
//			columnDataType = MySQLDatasourceTableFactory.addDefaultDisplayWidth(columnDataType);
//
//			exportGUIColumn.setDatatypeSize(columnDataType);
//		}
//
//		// constraints
//		final List<Node> constraintNodeList = new ArrayList<>();
//		TreeWalkingTools.findAll(columDefinitionNode, "enterColumn_constraint", constraintNodeList);
//
//		final StringBuffer stringBuffer = new StringBuffer();
//		for (final Node node : constraintNodeList) {
//
//			final List<Node> leaves = new ArrayList<>();
//			TreeWalkingTools.outputAllLeaves(node, leaves);
//
//			int tickCount = 0;
//
//			for (int i = 0; i < leaves.size(); i++) {
//
//				final Node child = leaves.get(i);
//				Node lastChild = null;
//
//				if (child.getLabel().equalsIgnoreCase("'")) {
//					tickCount++;
//				}
//
//				if (i > 0) {
//					lastChild = leaves.get(i - 1);
//				}
//
//				if (0 != stringBuffer.length()) {
//					if ((null == lastChild) || (!lastChild.getLabel().equalsIgnoreCase("'"))) {
//						if (child.getLabel().equalsIgnoreCase("'") && (2 == tickCount)) { // NOPMD
//							// empty
//						} else {
//							stringBuffer.append(ONE_SPACE_EMPTY_STRING);
//						}
//					}
//				}
//
//				stringBuffer.append(child.getRawLabel());
//
//				if (SIZE_TWO == tickCount) {
//					tickCount = 0;
//				}
//			}
//		}
//
//		final String colConstraint = stringBuffer.toString();
//		exportGUIColumn.setConstraint(colConstraint);
//
//		// comment - there is a COMMENT node. The next child after that node
//		// contains the comment text
//		int commentIndex = -1;
//		boolean commentFound = false;
//		for (final Node childNode : columDefinitionNode.getChildren()) {
//			commentIndex++;
//			if (childNode.getRawLabel().equalsIgnoreCase("COMMENT")) {
//				commentFound = true;
//				break;
//			}
//		}
//
//		if (commentFound) {
//			// the next node contains the comment itself
//			commentIndex++;
//			final Node commentChildNode = columDefinitionNode.getChildren().get(commentIndex);
//			exportGUIColumn.setComment(TreeWalkingTools.nodeLeavesAsString(commentChildNode));
//		}
//
//		return exportGUIColumn;
//	}
//
//	/**
//	 * Fix data type.
//	 *
//	 * @param stringBuffer the string buffer
//	 * @throws MedhubException
//	 */
//	public static void fixDataType(final StringBuffer stringBuffer) throws MedhubException {
//
//		if (stringBuffer.toString().toLowerCase().endsWith("BIGINT".toLowerCase())) {
//			stringBuffer.append("(20)");
//		} else if (stringBuffer.toString().toLowerCase().endsWith("SMALLINT".toLowerCase())) {
//			// stringBuffer.append("(20)");
//			stringBuffer.append("(6)");
//		} else if (stringBuffer.toString().toLowerCase().endsWith("TINYINT".toLowerCase())) {
//			stringBuffer.append("(11)");
//		} else if (stringBuffer.toString().toLowerCase().endsWith("INT".toLowerCase())) {
//			stringBuffer.append("(11)");
//		} else if (stringBuffer.toString().toLowerCase().endsWith("INTEGER".toLowerCase())) {
//			stringBuffer.append("(11)");
//		} else if (stringBuffer.toString().toLowerCase().endsWith("NUMERIC".toLowerCase())) {
//			stringBuffer.append("(20)");
//		} else if (stringBuffer.toString().toLowerCase().endsWith("DECIMAL".toLowerCase())) {
//			stringBuffer.append("(20)");
//		} else if (stringBuffer.toString().toLowerCase().endsWith("FLOAT".toLowerCase())) {
//			// stringBuffer.append("(20)");
//			stringBuffer.append("(11)");
//		} else if (stringBuffer.toString().toLowerCase().endsWith("DOUBLE".toLowerCase())) {
//			// why does double not get a size description?
//			// stringBuffer.append("(20)");
//			stringBuffer.append(" precision(20,2)");
//		} else if (stringBuffer.toString().toLowerCase().endsWith("DATETIME".toLowerCase())) { // NOPMD
//			// empty
//		} else if (stringBuffer.toString().toLowerCase().endsWith("DATE".toLowerCase())) { // NOPMD
//			// empty
//		} else if (stringBuffer.toString().toLowerCase().endsWith("BIT".toLowerCase())) {
//			stringBuffer.append("(1)");
//		} else if (stringBuffer.toString().toLowerCase().endsWith("TIMESTAMP".toLowerCase())) { // NOPMD
//			// empty
//		} else if (stringBuffer.toString().toLowerCase().endsWith("BLOB".toLowerCase())) { // NOPMD
//			// empty
//		} else if (stringBuffer.toString().toLowerCase().endsWith("LONGTEXT".toLowerCase())) { // NOPMD
//			// empty
//		}
//
//		String value = stringBuffer.toString().toLowerCase();
//
//		value = value.trim();
//
//		if (value.endsWith("unsigned")) {
//			value = value.substring(0, value.length() - " unsigned".length());
//		}
//
//		// @formatter:off
//
//		// check if each type has a size declaration now
//		if (!value.endsWith(")") && !"DOUBLE".equalsIgnoreCase(value) && !"DATETIME".equalsIgnoreCase(value)
//				&& !"BIT".equalsIgnoreCase(value) && !"BIGINT".equalsIgnoreCase(value)
//				&& !"DATE".equalsIgnoreCase(value) && !"TIMESTAMP".equalsIgnoreCase(value)
//				&& !"BLOB".equalsIgnoreCase(value) && !"LONGBLOB".equalsIgnoreCase(value)
//				&& !"LONGTEXT".equalsIgnoreCase(value)) {
//			throw new MedhubException("Unknown type: \"" + stringBuffer.toString() + "\"");
//		}
//
//		// @formatter:on
//	}
//
//	/**
//	 * All the rules in this method where deduced from the SQL statement:
//	 *
//	 * alter table TumorVolumeStatics add index FKEEB88ABC98BCE87B
//	 * (osteochondral_metastasis_id), add constraint FKEEB88ABC98BCE87B foreign key
//	 * (osteochondral_metastasis_id) references osteochondral_metastasis_follow_up
//	 * (id);
//	 *
//	 * @param node the node
//	 * @return the foreign key constraint
//	 */
//	public static ForeignKeyConstraint node2ForeignKeyConstraint(final Node node) {
//
//		final ForeignKeyConstraint foreignKeyConstraint = new ForeignKeyConstraint();
//
//		foreignKeyConstraint.setName(parseName(node));
//		foreignKeyConstraint.setReferencedTableName(parseReferencedTable(node));
//		foreignKeyConstraint.setReferencedColumnName(parseReferencedColumn(node));
//
//		// set the name of the table that contains this constraint
//		foreignKeyConstraint.setTableName(parseTableName(node));
//
//		return foreignKeyConstraint;
//	}
//
//	/**
//	 * Parses the table name.
//	 *
//	 * @param node the node
//	 * @return the string
//	 */
//	private static String parseTableName(final Node node) {
//		final List<Node> enterAlter_statements = new ArrayList<>();
//		TreeWalkingTools.findAll(node, "enterAlter_statement", enterAlter_statements);
//
//		if (CollectionUtils.isNotEmpty(enterAlter_statements)) {
//			final Node enterAlter_statement = enterAlter_statements.get(0);
//			final Node child = enterAlter_statement.getChildren().get(2);
//
//			return nodeLeavesAsString(child);
//		}
//
//		return null;
//	}
//
//	/**
//	 * Parses the referenced table.
//	 *
//	 * @param node the node
//	 * @return the string
//	 */
//	private static String parseReferencedTable(final Node node) {
//		final List<Node> enterAlter_specifications = new ArrayList<>();
//		TreeWalkingTools.findAll(node, "enterAlter_specification", enterAlter_specifications);
//
//		if (CollectionUtils.isNotEmpty(enterAlter_specifications)) {
//
//			for (final Node enterAlter_specification : enterAlter_specifications) {
//
//				final Node child0 = enterAlter_specification.getChildren().get(0);
//				final Node child1 = enterAlter_specification.getChildren().get(1);
//
//				if (!child0.getRawLabel().equalsIgnoreCase("add")) {
//					continue;
//				}
//				if (!child1.getRawLabel().equalsIgnoreCase("constraint")) {
//					continue;
//				}
//
//				// find the child that has "references" as raw label as this
//				// denotes the constraints name
//				int index = 0;
//				for (final Node child : enterAlter_specification.getChildren()) {
//
//					if (child.getRawLabel().equalsIgnoreCase("references")) {
//
//						// is this correct in all situations ?
//						// +1 is deduced from the ast of the SQL-Statement in
//						// this methods comment
//						final Node referencedTableNode = enterAlter_specification.getChildren().get(index + 1);
//						return nodeLeavesAsString(referencedTableNode);
//					}
//
//					index++;
//				}
//			}
//		}
//
//		return null;
//	}
//
//	/**
//	 * Parses the referenced column.
//	 *
//	 * @param node the node
//	 * @return the string
//	 */
//	private static String parseReferencedColumn(final Node node) {
//		final List<Node> enterAlter_specifications = new ArrayList<>();
//		TreeWalkingTools.findAll(node, "enterAlter_specification", enterAlter_specifications);
//
//		if (CollectionUtils.isNotEmpty(enterAlter_specifications)) {
//
//			for (final Node enterAlter_specification : enterAlter_specifications) {
//
//				final Node child0 = enterAlter_specification.getChildren().get(0);
//				final Node child1 = enterAlter_specification.getChildren().get(1);
//
//				if (!child0.getRawLabel().equalsIgnoreCase("add")) {
//					continue;
//				}
//				if (!child1.getRawLabel().equalsIgnoreCase("constraint")) {
//					continue;
//				}
//
//				// find the child that has "references" as raw label as this
//				// denotes the constraints name
//				int index = 0;
//				for (final Node child : enterAlter_specification.getChildren()) {
//
//					if (child.getRawLabel().equalsIgnoreCase("references")) {
//
//						// is this correct in all situations ?
//						// +1 is deduced from the AST of the SQL-Statement in
//						// this methods comment
//						final Node referencedColumnNode = enterAlter_specification.getChildren().get(index + 3);
//						return referencedColumnNode.getRawLabel();
//					}
//
//					index++;
//				}
//			}
//		}
//
//		return null;
//	}
//
//	/**
//	 * Parses the name.
//	 *
//	 * @param node the node
//	 * @return the string
//	 */
//	private static String parseName(final Node node) {
//		// retrieve the name
//		final List<Node> enterAlter_specifications = new ArrayList<>();
//		TreeWalkingTools.findAll(node, "enterAlter_specification", enterAlter_specifications);
//
//		if (CollectionUtils.isNotEmpty(enterAlter_specifications)) {
//
//			for (final Node enterAlter_specification : enterAlter_specifications) {
//
//				final Node child0 = enterAlter_specification.getChildren().get(0);
//				final Node child1 = enterAlter_specification.getChildren().get(1);
//
//				if (!child0.getRawLabel().equalsIgnoreCase("add")) {
//					continue;
//				}
//				if (!child1.getRawLabel().equalsIgnoreCase("constraint")) {
//					continue;
//				}
//
//				// find the child that has "foreign" as raw label as this
//				// denotes the constraints name
//				// in the third following node (hence +3) see below
//				int index = 0;
//				for (final Node child : enterAlter_specification.getChildren()) {
//
//					if (child.getRawLabel().equalsIgnoreCase("foreign")) {
//
//						// is this correct in all situations ?
//						// +3 is deduced from the ast of the SQL-Statement in
//						// this methods comment
//						final Node nameNode = enterAlter_specification.getChildren().get(index + 3);
//						return nameNode.getRawLabel();
//					}
//
//					index++;
//				}
//			}
//		}
//
//		return null;
//	}
//
//	/**
//	 * Enriches a tree of nodes with limit nodes.
//	 *
//	 * @param node
//	 * @param currentPage
//	 * @param pageSize
//	 * @throws MedhubException
//	 */
//	public static void addOrUpdateLimit(final Node node, final int currentPage, final int pageSize,
//			final Factory<DefaultNode> nodeFactory) throws MedhubException {
//
//		final List<Node> children = node.getChildren();
//		final int size = children.size();
//
//		// if the statement ends with LIMIT a then size-2 is LIMIT
//		Node testNode = children.get(size - 2);
//
//		boolean hasSimpleLimit = false;
//		if ((NodeListenerImpl.TERMINAL == testNode.getType()) && ("LIMIT".equalsIgnoreCase(testNode.getRawLabel()))) {
//			hasSimpleLimit = Boolean.TRUE;
//		}
//
//		// if the statement ends with LIMIT a COMMA b, then size-4 is LIMIT
//		testNode = children.get(size - 4);
//		boolean hasComplexLimit = Boolean.FALSE;
//		if ((NodeListenerImpl.TERMINAL == testNode.getType()) && ("LIMIT".equalsIgnoreCase(testNode.getRawLabel()))) {
//			hasComplexLimit = Boolean.TRUE;
//		}
//
//		if (!hasSimpleLimit && !hasComplexLimit) {
//
//			final Node limitNode = nodeFactory.createObject();
//			limitNode.setRawLabel("LIMIT");
//			limitNode.setLabel("LIMIT");
//			limitNode.setType(NodeListenerImpl.TERMINAL);
//			children.add(limitNode);
//			limitNode.setParent(node);
//
//			final String limits = Integer.toString(currentPage * pageSize) + ", " + Integer.toString(pageSize);
//			final Node thresholdNode = nodeFactory.createObject();
//			thresholdNode.setRawLabel(limits);
//			thresholdNode.setLabel(limits);
//			thresholdNode.setType(NodeListenerImpl.TERMINAL);
//			children.add(thresholdNode);
//			thresholdNode.setParent(node);
//
//		} else {
//
//			if (hasSimpleLimit) {
//
//				final String limits = Integer.toString(currentPage * pageSize) + ", " + Integer.toString(pageSize);
//
//				final Node simpleLimitNode = children.get(size - 1);
//				simpleLimitNode.setRawLabel(limits);
//				simpleLimitNode.setLabel(limits);
//
//			} else if (hasComplexLimit) {
//
//				final Node currentPageNode = children.get(size - 3);
//				final Node pageSizeNode = children.get(size - 1);
//
//				currentPageNode.setRawLabel(Integer.toString(currentPage * pageSize));
//				currentPageNode.setLabel(Integer.toString(currentPage * pageSize));
//				pageSizeNode.setRawLabel(Integer.toString(pageSize));
//				pageSizeNode.setLabel(Integer.toString(pageSize));
//
//			}
//		}
//	}
//
//	/**
//	 * @param sqlQuery
//	 * @param rootNode
//	 * @param instanceId
//	 * @param instanceIdColumnName
//	 */
//	public static void addInstanceId(final SQLQuery sqlQuery, final Node rootNode, final int instanceId,
//			final String instanceIdColumnName) {
//
//		final StringBuffer instanceIdAppendix = new StringBuffer();
//		instanceIdAppendix.append(" WHERE ").append(instanceIdColumnName).append(" = ").append(instanceId);
//
//		final String query = sqlQuery.getQuery();
//		sqlQuery.setQuery(query + instanceIdAppendix);
//
//		final Node limitNode = new DefaultNode();
//		limitNode.setRawLabel(instanceIdAppendix.toString().trim());
//		limitNode.setLabel(instanceIdAppendix.toString().trim());
//		limitNode.setType(NodeListenerImpl.TERMINAL);
//		limitNode.setParent(rootNode);
//
//		// this has to go into the enterSelect select node, otherwise it is
//		// erased by the addOrUpdateLimit() method
//		final List<Node> selectResultList = new ArrayList<>();
//		TreeWalkingTools.findAll(rootNode, "enterSelect", selectResultList);
//
//		final Node node = selectResultList.get(0);
//		node.getChildren().add(limitNode);
//	}

}
