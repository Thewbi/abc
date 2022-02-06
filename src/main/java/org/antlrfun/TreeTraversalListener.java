package org.antlrfun;

import de.common.Node;
import de.common.NodeException;

public interface TreeTraversalListener<T extends Node> {

	/**
	 * Called once before all other listener methods.
	 *
	 * @param userData
	 */
	void iterationStart(Object[] userData);

	/**
	 * @param node
	 * @param userData some user defined object that is put into each call of this
	 *                 interface
	 * @return true to keep the tree walking going, false to abort tree walking for
	 *         that subtree
	 * @throws MedhubException
	 */
	void preOrder(T node, Object... userData) throws NodeException;

	/**
	 * @param node
	 * @param userData some user defined object that is put into each call of this
	 *                 interface
	 * @return true to keep the tree walking going, false to abort tree walking for
	 *         that subtree
	 * @throws MedhubException
	 */
	void inOrder(T node, Object... userData) throws NodeException;

	/**
	 * @param node
	 * @param userData some user defined object that is put into each call of this
	 *                 interface
	 * @throws MedhubException
	 */
	void postOrder(T node, Object... userData) throws NodeException;

	/**
	 * @param userData some user defined object that is put into each call of this
	 *                 interface Called after the iteration is done.
	 */
	void iterationDone(Object... userData);

}
