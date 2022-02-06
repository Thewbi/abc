package de.common;

/**
 * The Class IdService.
 */
public class IdService {

	/** The next. */
	private int next;

	/**
	 * Gets the next id.
	 *
	 * @return the next id
	 */
	public int getNextId() {
		final int result = next;

		next++;

		return result;
	}

	/**
	 * Reset.
	 */
	public void reset() {
		next = 0;
	}

}
