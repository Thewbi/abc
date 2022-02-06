package org.antlrfun;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.cgrammar.CBaseListener;

import de.common.DefaultNode;
import de.common.Factory;
import de.common.Node;

public class DefaultNodeListener extends CBaseListener {

	/** The root node. */
	protected Node rootNode;

	/** The current node. */
	protected Node currentNode;

	/** If this value is set to true, the GUI Export does not work anymore. */
	protected boolean insertIdInLabel;

	protected Factory<? extends DefaultNode> nodeFactory;

	/**
	 * {@inheritDoc}
	 * <p/>
	 * The default implementation does nothing.
	 */
	@Override
	public void enterEveryRule(@NotNull final ParserRuleContext ctx) {
//		final Node newChildNode = enter(ctx.getClass().getSimpleName(), 0);
		final Node newChildNode = enter(ctx);
		decend(newChildNode);
	}

	@Override
	public void visitTerminal(@NotNull final TerminalNode node) {
		try {
			final DefaultNode childNode = createNode();
			childNode.setLabel(node.getText());
			currentNode.getChildren().add(childNode);
			childNode.setParent(currentNode);
		} catch (final Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * The default implementation does nothing.
	 */
	@Override
	public void exitEveryRule(@NotNull final ParserRuleContext ctx) {
		ascend();
	}

	private Node enter(final ParserRuleContext ctx) {
		DefaultNode node;
		try {
			node = createNode();

			final String label = ctx.getClass().getSimpleName();
			node.setLabel(label);

//			for (int i = 0; i < ctx.getChildCount(); i++) {
//
//				final ParseTree child = ctx.getChild(i);
//				if (child instanceof TerminalNode) {
//
//					final DefaultNode childNode = createNode();
//					childNode.setLabel(child.getText());
//					node.getChildren().add(childNode);
//					childNode.setParent(node);
//				}
//			}

			return node;
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Factory for nodes. The returned node is not connected to the AST tree in any
	 * way. <br />
	 * <br />
	 * Creates a new node and assigns it the id retrieved from the id service.
	 * Creates a label for the node from the specified label and the retrieved id.
	 * Sets the type of the node to the type specified. <br />
	 * <br />
	 * Returns that node.
	 *
	 * @param label the label
	 * @param type  the type
	 * @return the node
	 */
	protected Node enter(final String label, final int type) {

		try {
			final DefaultNode newChildNode = createNode();
			// newChildNode.setId(nodeFactory.getNextId());
			newChildNode.setRawLabel(label);

			if (insertIdInLabel) {
				newChildNode.setLabel(label + " (" + newChildNode.getId() + ")[" + type + "]");
			} else {
				newChildNode.setLabel(label);
			}

			newChildNode.setType(type);

			return newChildNode;

		} catch (final Exception e) {
//			LOG.error(e.getMessage(), e);
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Inserts a node into the AST as a child of the current node and makes this to
	 * the current node.
	 *
	 * @param newChildNode the new child node
	 */
	protected void decend(final Node newChildNode) {
		if (null != currentNode) {
			currentNode.getChildren().add(newChildNode);
			newChildNode.setParent(currentNode);
		}
		currentNode = newChildNode;
	}

	/**
	 * Ascend.
	 *
	 * @return the node
	 */
	protected Node ascend() {
		final Node oldNode = currentNode;
		currentNode = currentNode.getParent();
		return oldNode;
	}

	/**
	 * @return
	 * @throws MedhubException
	 */
	public DefaultNode createNode() throws Exception {
//		Preconditions.checkNotNull(nodeFactory, "nodeFactory is null!");
		try {
			return nodeFactory.createObject();
		} catch (final Exception e) {
			throw new Exception(e);
		}
	}

	public Node getRootNode() {
		return rootNode;
	}

	public void setRootNode(final Node rootNode) {
		this.rootNode = rootNode;
	}

	public Node getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(final Node currentNode) {
		this.currentNode = currentNode;
	}

	public boolean isInsertIdInLabel() {
		return insertIdInLabel;
	}

	public void setInsertIdInLabel(final boolean insertIdInLabel) {
		this.insertIdInLabel = insertIdInLabel;
	}

	public Factory<? extends DefaultNode> getNodeFactory() {
		return nodeFactory;
	}

	public void setNodeFactory(final Factory<? extends DefaultNode> nodeFactory) {
		this.nodeFactory = nodeFactory;
	}

}
