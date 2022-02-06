package de.common;

/**
 * Creates objects so that their internal state is set up correctly.
 *
 * @param <T> the generic type parameter.
 */
public interface Factory<T> {

	/**
	 * Objects are created.
	 *
	 * <ul>
	 * <li />They are not null.
	 * <li />Their id is not null.
	 * <li />They have a negative dummy id.
	 * <li />Two created objects do not share the same negative dummy id.
	 * </ul>
	 *
	 * @param args the arguments, that the factory can use to create objects.
	 * @return the object created.
	 */
	T createObject(Object... args) throws Exception;

	/**
	 *
	 */
	void reset();

}
