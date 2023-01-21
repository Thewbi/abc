package org.antlrfun;

import org.apache.commons.text.StringEscapeUtils;

import de.common.DefaultNode;
import de.common.Factory;
import de.common.Node;

/**
 * The listener interface for receiving abstractParseTree events. The class that
 * is interested in processing a abstractParseTree event implements this
 * interface, and the object created with that class is registered with a
 * component using the component's <code>addAbstractParseTreeListener</code>
 * method. When the abstractParseTree event occurs, that object's appropriate
 * method is invoked.
 *
 * @see AbstractParseTreeEvent
 */
public abstract class AbstractParseTreeListener<T extends DefaultNode> {

//  /** The Constant LOG. */
//  private static final transient Logger LOG =
//      LoggerFactory.getLogger(AbstractParseTreeListener.class);

	/** The root node. */
	protected Node rootNode;

	/** The current node. */
	protected Node currentNode;

	/** If this value is set to true, the GUI Export does not work anymore. */
	protected boolean insertIdInLabel;

	protected Factory<? extends DefaultNode> nodeFactory;

	/**
	 * Log.
	 *
	 * @param msg the msg
	 */
	protected void log(final String msg) {
//    if (LOG.isTraceEnabled()) {
//      LOG.trace(msg);
//    }
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
	 * If the data string contains linebreaks, those linebreaks will cause the dot
	 * file format to be violated. Hence this method, encodes linebreaks.
	 *
	 * @param terminalText
	 * @return
	 */
	protected String getSaveLabel(final String terminalText) {
		return StringEscapeUtils.escapeJava(terminalText);
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

	/**
	 * Gets the current node.
	 *
	 * @return the current node
	 */
	public Node getCurrentNode() {
		return currentNode;
	}

	/**
	 * Sets the current node.
	 *
	 * @param currentNode the new current node
	 */
	public void setCurrentNode(final Node currentNode) {
		this.currentNode = currentNode;
	}

	/**
	 * Gets the root node.
	 *
	 * @return the root node
	 */
	public Node getRootNode() {
		return rootNode;
	}

	/**
	 * Sets the root node.
	 *
	 * @param rootNode the new root node
	 */
	public void setRootNode(final Node rootNode) {
		this.rootNode = rootNode;
	}

	/**
	 * Checks if is insert id in label.
	 *
	 * @return true, if is insert id in label
	 */
	public boolean isInsertIdInLabel() {
		return insertIdInLabel;
	}

	/**
	 * Sets the insert id in label.
	 *
	 * @param insertIdInLabel the new insert id in label
	 */
	public void setInsertIdInLabel(final boolean insertIdInLabel) {
		this.insertIdInLabel = insertIdInLabel;
	}

	/**
	 * @return
	 */
	public Factory<? extends DefaultNode> getNodeFactory() {
		return nodeFactory;
	}

	/**
	 * @param nodeFactory
	 */
//	@Required
	public void setNodeFactory(final Factory<? extends DefaultNode> nodeFactory) {
		this.nodeFactory = nodeFactory;
	}

}
