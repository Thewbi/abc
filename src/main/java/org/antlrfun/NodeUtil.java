package org.antlrfun;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import de.common.Node;
//import de.medhub.common.exception.NodeException;
import de.common.NodeException;

/**
 * The Class NodeUtil.
 */
public class NodeUtil {

	/** The Constant LOG. */
//  private static final transient Logger LOG = LoggerFactory.getLogger(NodeUtil.class);

	/**
	 * Utility classes should not have a public or default constructor.
	 */
	protected NodeUtil() {
		// empty
	}

	/**
	 * Output leaves.
	 *
	 * @param node the dictionary entry node
	 * @return the string
	 */
	public static String outputLeaves(final Node node) {
		return outputLeaves(node, false);
	}

	/**
	 * Output leaves.
	 *
	 * @param rootNode     the node to start from.
	 * @param outputSpaces the output spaces
	 * @return the resulting string
	 */
	public static String outputLeaves(final Node rootNode, final boolean outputSpaces) {

		final StringBuffer stringBuffer = new StringBuffer();
		for (final Node node : rootNode.getChildren()) {
			recurse(node, stringBuffer, outputSpaces);
		}

		return stringBuffer.toString().trim();
	}

	/**
	 * Output first expression.
	 *
	 * @param node the node
	 * @return the node
	 */
	public static Node outputFirstNodeOfType(final Node node, final int nodeType) {

		if (node.getType() == nodeType) {
			return node;
		} else {
			Node expression = null;

			if (CollectionUtils.isNotEmpty(node.getChildren())) {
				for (final Node child : node.getChildren()) {
					expression = outputFirstNodeOfType(child, nodeType);
					if (null != expression) {
						return expression;
					}
				}
			}
		}

		return null;
	}

	public static Node outputFirstNodeWithLabel(final Node node, final String label) {

		if (StringUtils.equalsIgnoreCase(node.getLabel(), label)) {

			return node;
		} else {

			if (CollectionUtils.isNotEmpty(node.getChildren())) {
				for (final Node child : node.getChildren()) {
					final Node childNode = outputFirstNodeWithLabel(child, label);
					if (null != childNode) {
						return childNode;
					}
				}
			}
		}

		return null;
	}

	public static void outputAllNodesOfType(final Node node, final int type, final List<Node> result) {

		if (node.getType() == type) {
			result.add(node);
		}

		if (CollectionUtils.isNotEmpty(node.getChildren())) {

			result.addAll(node.getChildren().stream().filter(n -> n.getType() == type).collect(Collectors.toList()));
		}
	}

	/**
	 * Output first leave.
	 *
	 * @param node the node
	 * @return the string
	 */
	public static Node outputFirstLeave(final Node node) {

		if (CollectionUtils.isEmpty(node.getChildren())) {
			return node;
		}

		return outputFirstLeave(node.getChildren().get(0));
	}

	/**
	 * Signals a listener about node pre, in and postorder for trees with multiple
	 * children after the last child is considered in order for the parent. That
	 * means after the last child, the parent gets notified about in order and then
	 * immediately post order.
	 *
	 * Does not use recursion but uses two stacks
	 *
	 * @param node
	 * @param listener
	 * @throws MedhubException
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Node> void traverse(final T node, final TreeTraversalListener<T> listener,
			final Object... userData) throws NodeException {

//		Preconditions.checkNotNull(node, "Node is null!");
//		Preconditions.checkNotNull(listener, "Listener is null!");

		try {

			final Deque<T> stack = new ArrayDeque<>();
			final Deque<T> parentStack = new ArrayDeque<>();

			// put the first node onto the stack
			stack.push(node);

			listener.iterationStart(userData);

			while (!stack.isEmpty()) {

				// a parent node is reached again
				if (parentStack.peek() == stack.peek()) {

					if (parentStack.peek().isRepeatNode()) {

						final T repeatNode = parentStack.peek();

						listener.preOrder(repeatNode, userData);

						if (repeatNode.isRepeatNode()) {

							// push all children in the correct order
							if (CollectionUtils.isNotEmpty(repeatNode.getChildren())) {

								final int size = repeatNode.getChildren().size();
								final ListIterator<?> listIterator = repeatNode.getChildren().listIterator(size);

								// iterate in reverse.
								while (listIterator.hasPrevious()) {
									stack.push((T) listIterator.previous());
								}
							}
						}

					} else {

						// child postorder
						listener.postOrder(stack.peek(), userData);

						// remove child and parent
						stack.pop();
						parentStack.pop();

						// parent inorder
						if (null != parentStack.peek()) {
							listener.inOrder(parentStack.peek(), userData);
						}
					}

				} else {

					final T tempNode = stack.peek();

					// preorder
					listener.preOrder(tempNode, userData);

					// remember current node as a parent
					parentStack.push(tempNode);

					// push all children in the correct order
					if (CollectionUtils.isNotEmpty(tempNode.getChildren())) {

						final int size = tempNode.getChildren().size();
						final ListIterator<?> listIterator = tempNode.getChildren().listIterator(size);

						// iterate in reverse.
						while (listIterator.hasPrevious()) {
							stack.push((T) listIterator.previous());
						}

					} else {

						// inorder
						listener.inOrder(tempNode, userData);

					}

				}
			}

		} finally {
			listener.iterationDone(userData);
		}
	}

	/**
	 * Signals a listener about node pre, in and postorder for trees with multiple
	 * children After the last child is considered in order for the parent. That
	 * means after the last child, the parent gets notified about in order and then
	 * immediately post order.
	 *
	 * Uses recursion and crashes the heap if the trees are too big.
	 *
	 * @param node
	 * @param listener
	 * @throws MedhubException
	 */
	public static void traverseWithRecursion(final Node node, final TreeTraversalListener<Node> listener,
			final Object... userData) throws NodeException {

//		Preconditions.checkNotNull(node, "Node is null!");
//		Preconditions.checkNotNull(listener, "Listener is null!");

		listener.iterationStart(userData);

		traverseWithRecursionInternal(node, listener, userData);

		listener.iterationDone(userData);
	}

	private static void traverseWithRecursionInternal(final Node node, final TreeTraversalListener<Node> listener,
			final Object... userData) throws NodeException {
		// preorder
		listener.preOrder(node, userData);

		if (CollectionUtils.isNotEmpty(node.getChildren())) {

			for (final Node child : node.getChildren()) {

				// recurse
				traverseWithRecursionInternal(child, listener, userData);

				// inorder
				listener.inOrder(node, userData);
			}
		} else {
			// inorder
			listener.inOrder(node, userData);
		}

		// postorder
		listener.postOrder(node, userData);
	}

	/**
	 * Depth first recursion. Adds all rawLabels of leaf nodes into a stringbuffer.
	 * A leaf node is a node that has no children.
	 *
	 * @param node         the node
	 * @param stringBuffer the string buffer
	 * @param outputSpaces the output spaces
	 */
	public static void recurse(final Node node, final StringBuffer stringBuffer, final boolean outputSpaces) {

		if (CollectionUtils.isEmpty(node.getChildren())) {

			if (outputSpaces && stringBuffer.length() > 0) {
				stringBuffer.append(' ');
			}

			final String rawLabel = node.getRawLabel();
			stringBuffer.append(rawLabel);

			return;
		}

		for (final Node child : node.getChildren()) {
			recurse(child, stringBuffer, outputSpaces);
		}
	}

	/**
	 * Traverse subtree and put all nodes having that raw label into the result
	 * list.
	 *
	 * @param node   the node
	 * @param label  the raw label
	 * @param result the result
	 */
	public static void findAll(final Node node, final String label, final List<Node> result) {

		if (null == node) {
			return;
		}

		if (StringUtils.isNotBlank(node.getRawLabel()) && node.getRawLabel().equalsIgnoreCase(label.trim())) {
			result.add(node);
		}

		if (CollectionUtils.isEmpty(node.getChildren())) {
			return;
		}

		for (final Node childNode : node.getChildren()) {
			findAll(childNode, label, result);
		}
	}

	public static void findAllByLabel(final Node node, final String label, final List<Node> result) {

		if (null == node) {
			return;
		}

		if (StringUtils.isNotBlank(node.getLabel()) && node.getLabel().equalsIgnoreCase(label.trim())) {
			result.add(node);
		}

		if (CollectionUtils.isEmpty(node.getChildren())) {
			return;
		}

		for (final Node childNode : node.getChildren()) {
			findAllByLabel(childNode, label, result);
		}
	}

	/**
	 * Find all.
	 *
	 * @param node   the node
	 * @param labels the labels
	 * @param result the result
	 */
	public static void findAll(final Node node, final String[] labels, final List<Node> result) {

		if (null == node) {
			return;
		}

		for (final String label : labels) {
			if (node.getRawLabel().equalsIgnoreCase(label.trim())) {
				result.add(node);
			}
		}

		if (null == node.getChildren()) {
			return;
		}

		for (final Node childNode : node.getChildren()) {
			findAll(childNode, labels, result);
		}
	}

	/**
	 * Output all leaves.
	 *
	 * @param node   the node
	 * @param leaves the leaves
	 */
	public static void outputAllLeaves(final Node node, final List<Node> leaves) {

		if (CollectionUtils.isEmpty(node.getChildren())) {
			leaves.add(node);

			return;
		}

		for (final Node child : node.getChildren()) {
			outputAllLeaves(child, leaves);
		}
	}

	/**
	 * Output all leaves and nodes that have "enterPath" as their raw label.
	 *
	 * @param node   the rootnode of the subtree to test
	 * @param leaves the leaves
	 */
	public static void outputAllLeavesReturnPaths(final Node node, final List<Node> leaves) {

		if (CollectionUtils.isEmpty(node.getChildren())) {

			leaves.add(node);
			return;

		}

		if (StringUtils.isNotBlank(node.getRawLabel()) && node.getRawLabel().equalsIgnoreCase("enterPath")) {

			leaves.add(node);

			// stop the recursion for some reason
			return;

		}

		for (final Node child : node.getChildren()) {
			outputAllLeavesReturnPaths(child, leaves);
		}
	}

	/**
	 * view dot files using the ZGR viewer.
	 *
	 * @param node     the node
	 * @param filename the filename
	 */
	@SuppressWarnings("PMD")
	public static void dumpDot(final Node node, final String filename) {

		final StringBuffer stringBuffer = new StringBuffer();
		node.outputDot(stringBuffer);

		PrintWriter printWriter;
		try {
			printWriter = new PrintWriter(filename);

			printWriter.println(stringBuffer.toString());
			printWriter.flush();
			printWriter.close();
		} catch (final FileNotFoundException e) {
//			LOG.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

}
