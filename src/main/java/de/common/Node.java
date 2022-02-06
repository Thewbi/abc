package de.common;

import java.util.List;

public interface Node { // NOPMD

	/**
	 * Output undirected dot graph.
	 *
	 * <ul>
	 * <li />http://www.webgraphviz.com/
	 * <li />https://dreampuf.github.io/GraphvizOnline
	 * </ul>
	 *
	 * @param stringBuffer the string buffer
	 */
	void outputDot(StringBuffer stringBuffer);

	/**
	 * Output directed dot graph.
	 *
	 * <ul>
	 * <li />http://www.webgraphviz.com/
	 * <li />https://dreampuf.github.io/GraphvizOnline
	 * </ul>
	 *
	 * @param stringBuffer the string buffer
	 */
	void outputDigraphDot(StringBuffer stringBuffer);

	int getId();

	void setId(int nextId);

	List<Node> getChildren();

	Node getParent();

	void setParent(Node parentNode);

	int getType();

	void setType(int type);

	String getLabel();

	void setLabel(String label);

	String getRawLabel();

	void setRawLabel(String rawLabel);

	boolean isRepeatNode();

	boolean isLeaf();

	int getChildLeafCount();

}
