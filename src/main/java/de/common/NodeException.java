package de.common;

public class NodeException extends Exception {

	private static final long serialVersionUID = 1L;

	public NodeException(final String msg) {
		super(msg);
	}

	public NodeException(final String msg, final Throwable t) {
		super(msg, t);
	}

	public NodeException(final Exception e) {
		super(e);
	}

}
