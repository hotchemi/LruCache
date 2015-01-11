package hotchemi.com.github;

import android.graphics.Bitmap;

import java.util.Map;
import java.util.Objects;

import static hotchemi.com.github.Utils.getBitmapSize;

/*
 * A memory cache implementation which uses a LRU policy.
 * <p>
 * This implementation is thread safe.
 *
 * @author Shintaro Katafuchi
 */
public final class LruCache implements Cache<String, Bitmap> {

    private static final String TAG = LruCache.class.getName();

    /**
     * Evict all entries flag.
     */
    private static final int EVICT_ALL = -1;

    /**
     * Default cache entry size.
     */
    private static final int DEFAULT_LIMIT_SIZE = 10;

    /**
     * Max entry count is 10.
     */
    private final Map<String, Bitmap> map;

    /**
     * Max memory size.
     */
    private final int maxMemorySize;

    /**
     * Current memory size.
     */
    private int memorySize;

    public LruCache() {
        this.map = new LruHashMap<>(DEFAULT_LIMIT_SIZE);
        maxMemorySize = DEFAULT_LIMIT_SIZE * 1024 * 1024;
    }

    public LruCache(int limitSize) {
        this.map = new LruHashMap<>(limitSize);
        maxMemorySize = limitSize * 1024 * 1024;
    }

    @Override
    public Bitmap get(String key) {
        Objects.requireNonNull(key, "key == null");
        synchronized (this) {
            Bitmap value = map.get(key);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    @Override
    public Bitmap put(String key, Bitmap value) {
        Objects.requireNonNull(key, "key == null");
        Objects.requireNonNull(value, "value == null");
        Bitmap previous;
        synchronized (this) {
            previous = map.put(key, value);
            memorySize += getBitmapSize(value);
            if (previous != null) {
                memorySize -= getBitmapSize(previous);
            }
            trimToSize(maxMemorySize);
        }
        return previous;
    }

    @Override
    public synchronized void clear() {
        trimToSize(EVICT_ALL);
    }

    @Override
    public synchronized int getMemorySize() {
        return memorySize;
    }

    /**
     * Trim items in the cache.
     * <p>
     * <em>Note:</em> This method has to be called in synchronized block.
     *
     * @param maxSize maxSize
     */
    private void trimToSize(int maxSize) {
        while (true) {
            if (memorySize < 0 || (map.isEmpty() && memorySize != 0)) {
                throw new IllegalStateException(TAG + ".sizeOf() is reporting inconsistent results");
            }
            if (memorySize <= maxSize || map.isEmpty()) {
                break;
            }
            Map.Entry<String, Bitmap> entry = map.entrySet().iterator().next();
            map.remove(entry.getKey());
            memorySize -= getBitmapSize(entry.getValue());
        }
    }

}
