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
     * @return image
     */
    V get(K key);

    /**
     * Puts an value in the cache for the specified {@code key}.
     *
     * @param key   key
     * @param value image
     * @return previous value
     */
    V put(K key, V value);

    /**
     * Clears all the elements in the cache.
     */
    void clear();

    /**
     * Returns the current memory size of the cache.
     *
     * @return current memory size
     */
    int getMemorySize();

}
