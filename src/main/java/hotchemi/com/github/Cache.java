package hotchemi.com.github;

/**
 * A memory cache interface.
 *
 * @author Shintaro Katafuchi
 */
public interface Cache<K, V> {

    /**
     * Gets an value for the specified {@code key} or return {@code null}.
     *
     * @param key key
     * @return the value or {@code null}.
     */
    V get(K key);

    /**
     * Puts an value in the cache for the specified {@code key}.
     *
     * @param key   key
     * @param value image
     * @return the previous value.
     */
    V put(K key, V value);

    /**
     * Removes the entry for {@code key} if it exists or return {@code null}.
     *
     * @return the previous value or @{code null}.
     */
    V remove(K key);

    /**
     * Clears all the entries in the cache.
     */
    void clear();

    /**
     * Returns the max memory size of the cache.
     *
     * @return max memory size.
     */
    int getMaxMemorySize();

    /**
     * Returns the current memory size of the cache.
     *
     * @return current memory size.
     */
    int getMemorySize();

}
