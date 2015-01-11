package hotchemi.com.github;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link hotchemi.com.github.LruCache}.
 *
 * @author Shintaro Katafuchi
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class LruCacheTest {

    @Test
    public void testSomeLibraryMethod() {
        assertTrue("someLibraryMethod should return 'true'", true);
    }


}
