package hotchemi.com.github;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Unit tests for {@link hotchemi.com.github.LruCache}.
 *
 * @author Shintaro Katafuchi
 */
public class LruCacheTest {

    private static final String A = "A";

    private static final String B = "B";

    private static final String C = "C";

    private static final String D = "D";

    private static final String E = "E";

    private LruCache<String, String> cache;

    private static void assertMiss(LruCache<String, String> cache, String key) {
        assertNull(cache.get(key));
    }

    private static void assertHit(LruCache<String, String> cache, String key, String value) {
        assertThat(cache.get(key), is(value));
    }

    private static void assertSnapshot(LruCache<String, String> cache, String... keysAndValues) {
        List<String> actualKeysAndValues = new ArrayList<>();
        for (Map.Entry<String, String> entry : cache.snapshot().entrySet()) {
            actualKeysAndValues.add(entry.getKey());
            actualKeysAndValues.add(entry.getValue());
        }
        assertEquals(Arrays.asList(keysAndValues), actualKeysAndValues);
    }

    @Before
    public void setUp() {
        cache = new LruCache<>(3);
    }

    @After
    public void tearDown() {
        cache.clear();
        cache = null;
    }

    @Test
    public void defaultMemorySize() {
        assertThat(cache.getMaxMemorySize(), is(3 * 1024 * 1024));
    }

    @Test
    public void logic() {
        cache.put("a", A);
        assertHit(cache, "a", A);
        cache.put("b", B);
        assertHit(cache, "a", A);
        assertHit(cache, "b", B);
        assertSnapshot(cache, "a", A, "b", B);

        cache.put("c", C);
        assertHit(cache, "a", A);
        assertHit(cache, "b", B);
        assertHit(cache, "c", C);
        assertSnapshot(cache, "a", A, "b", B, "c", C);

        cache.put("d", D);
        assertMiss(cache, "a");
        assertHit(cache, "b", B);
        assertHit(cache, "c", C);
        assertHit(cache, "d", D);
        assertHit(cache, "b", B);
        assertHit(cache, "c", C);
        assertSnapshot(cache, "d", D, "b", B, "c", C);

        cache.put("e", E);
        assertMiss(cache, "d");
        assertMiss(cache, "a");
        assertHit(cache, "e", E);
        assertHit(cache, "b", B);
        assertHit(cache, "c", C);
        assertSnapshot(cache, "e", E, "b", B, "c", C);
    }

    @Test
    public void constructorDoesNotAllowZeroCacheSize() {
        try {
            new LruCache(0);
            fail();
        } catch (IllegalArgumentException expected) {
            //nothing
        }
    }

    @Test
    public void cannotPutNullKey() {
        try {
            cache.put(null, "a");
            fail();
        } catch (NullPointerException expected) {
            // nothing
        }
    }

    @Test
    public void cannotPutNullValue() {
        try {
            cache.put("a", null);
            fail();
        } catch (NullPointerException expected) {
            // nothing
        }
    }

    @Test
    public void evictionWithSingletonCache() {
        LruCache<String, String> cache = new LruCache<>(1);
        cache.put("a", A);
        cache.put("b", B);
        assertSnapshot(cache, "b", B);
    }

    @Test
    public void removeOneItem() {
        LruCache<String, String> cache = new LruCache<>(1);
        cache.put("a", A);
        cache.put("b", B);
        assertNull(cache.remove("a"));
        assertSnapshot(cache, "b", B);
    }

    @Test
    public void cannotRemoveNullKey() {
        try {
            cache.remove(null);
            fail();
        } catch (NullPointerException expected) {
            // nothing
        }
    }

    /**
     * Replacing the value for a key doesn't cause an eviction but it does bring the replaced entry to
     * the front of the queue.
     */
    @Test
    public void putCauseEviction() {
        cache.put("a", A);
        cache.put("b", B);
        cache.put("c", C);
        cache.put("b", D);
        assertSnapshot(cache, "a", A, "c", C, "b", D);
    }

    @Test
    public void throwsWithNullKey() {
        try {
            cache.get(null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            // nothing
        }
    }

    @Test
    public void clear() {
        cache.put("a", "a");
        cache.put("b", "b");
        cache.put("c", "c");
        cache.clear();
        assertThat(cache.snapshot().size(), is(0));
    }

}