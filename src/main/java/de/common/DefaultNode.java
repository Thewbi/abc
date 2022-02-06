package de.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * The Class DefaultNode.
 */
public class DefaultNode implements Node {

	private static final String TICK = "'";

	private static final String QUOTE = "\\\\\"";

	/** The id. */
	private int id;

	/** The label. */
	private String label;

	/** The raw label. */
	private String rawLabel;

	/** The children. */
	private List<Node> children = new ArrayList<Node>();

	/** The parent. */
	private Node parent;

	/** The type. */
	private int type;

	/** repeat this node during traversal */
	private boolean repeatNode;

	/**
	 * ctor.
	 */
	public DefaultNode() {
		id = -1;
		label = "unknown";
		parent = null;
		type = -1;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@Override
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	@Override
	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the label.
	 *
	 * @param label the new label
	 */
	@Override
	public void setLabel(final String label) {
		this.label = label;
	}

	/**
	 * Gets the children.
	 *
	 * @return the children
	 */
	@Override
	public List<Node> getChildren() {
		return children;
	}

	/**
	 * Sets the children.
	 *
	 * @param children the new children
	 */
	public void setChildren(final List<Node> children) {
		this.children = children;
	}

	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	@Override
	public Node getParent() {
		return parent;
	}

	/**
	 * Sets the parent.
	 *
	 * @param parent the new parent
	 */
	@Override
	public void setParent(final Node parent) {

		if (this == parent) {
			throw new IllegalArgumentException("A node cannot be its own parent!");
		}

		this.parent = parent;
	}

	/**
	 * Output undirected dot graph. http://www.webgraphviz.com/
	 *
	 * @param stringBuffer the string buffer
	 */
	@Override
	public void outputDot(final StringBuffer stringBuffer) {
		outputDotInternal(stringBuffer, false);
	}

	/**
	 * Output directed dot graph. http://www.webgraphviz.com/
	 *
	 * @param stringBuffer the string buffer
	 */
	@Override
	public void outputDigraphDot(final StringBuffer stringBuffer) {
		outputDotInternal(stringBuffer, true);
	}

	private void outputDotInternal(final StringBuffer stringBuffer, final boolean digraph) {

		// for indentation
		final String prefix = "  ";

		// if this is the root node, eclose it in the dot graph notation frame
		if (getParent() == null) {
			if (digraph) {
				stringBuffer.append("digraph graphname {\n\n");
			} else {
				stringBuffer.append("graph graphname {\n\n");
			}
		}

		String tempRawLabel = getRawLabel();

		// hack for PDF parsing
		// if you output binary data from a PDF stream into a dot file, the dot
		// file is most likely broken
		if (StringUtils.isNotBlank(tempRawLabel)) {
			if (tempRawLabel.trim().toLowerCase().startsWith("stream")) {
				tempRawLabel = "binary data hidden";
			} else {
				tempRawLabel = tempRawLabel.replaceAll("\"", QUOTE);
				tempRawLabel = tempRawLabel.replaceAll("`", TICK);
			}
		}

		String tempLabel = getLabel();
		if (StringUtils.isNotBlank(tempLabel)) {
			tempLabel = tempLabel.replaceAll("\"", QUOTE);
			tempLabel = tempLabel.replaceAll("`", TICK);
		}

		if (StringUtils.isNotBlank(tempRawLabel)) {

			stringBuffer.append(prefix).append(id).append("[label=\"").append(tempRawLabel).append(" (").append(id)
					.append(")\"]");

		} else if (StringUtils.isNotBlank(tempLabel)) {

			stringBuffer.append(prefix).append(id).append("[label=\"").append(tempLabel).append(" (").append(id)
					.append(")\"]");
		}

		stringBuffer.append('\n');

		if (CollectionUtils.isNotEmpty(getChildren())) {

			// relation between this node and children
			for (final Node child : children) {

				stringBuffer.append(prefix).append(id).append(digraph ? " -> " : " -- ").append(child.getId())
						.append(";\n");
			}

			// output all child nodes
			for (final Node child : children) {

				final DefaultNode childDefaultNode = ((DefaultNode) child);
				if (digraph) {
					childDefaultNode.outputDigraphDot(stringBuffer);
				} else {
					childDefaultNode.outputDot(stringBuffer);
				}
			}
		}

		// if this is the root node, eclose it in the dot graph notation frame
		if (getParent() == null) {
			stringBuffer.append("}");
		}
	}

	@Override
	public boolean isLeaf() {
		return CollectionUtils.isEmpty(getChildren());
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	@Override
	public int getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	@Override
	public void setType(final int type) {
		this.type = type;
	}

	/**
	 * Gets the raw label.
	 *
	 * @return the raw label
	 */
	@Override
	public String getRawLabel() {
		return rawLabel;
	}

	/**
	 * Sets the raw label.
	 *
	 * @param rawLabel the new raw label
	 */
	@Override
	public void setRawLabel(final String rawLabel) {
		this.rawLabel = rawLabel;
	}

	@Override
	public boolean isRepeatNode() {
		return repeatNode;
	}

	public void setRepeatNode(final boolean repeatNode) {
		this.repeatNode = repeatNode;
	}

	@Override
	public int getChildLeafCount() {

		// leaves return a value of 0 as they have no child leaf children
		if (isLeaf()) {
			return 0;
		}

		// nodes that are not leaves, request the leaf count from the subtree and
		// accumulate
		int result = 0;
		for (final Node child : children) {

			result += (child.isLeaf() ? 1 : child.getChildLeafCount());
		}

		return result;
	}

	@Override
	public String toString() {
		return rawLabel;
	}

}
