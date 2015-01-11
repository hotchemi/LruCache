package hotchemi.com.github;

import android.graphics.Bitmap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static android.graphics.Bitmap.Config.ALPHA_8;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for {@link BitmapLruCache}.
 *
 * @author Shintaro Katafuchi
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class BitmapLruCacheTest {

    private static final Bitmap A = Bitmap.createBitmap(1, 1, ALPHA_8);

    private static final Bitmap B = Bitmap.createBitmap(2, 2, ALPHA_8);

    private static final Bitmap C = Bitmap.createBitmap(3, 3, ALPHA_8);

    private static final Bitmap D = Bitmap.createBitmap(4, 4, ALPHA_8);

    private static final Bitmap E = Bitmap.createBitmap(5, 5, ALPHA_8);

    private BitmapLruCache cache;

    @Before
    public void setUp() {
        cache = new BitmapLruCache(3);
    }

    @After
    public void tearDown() {
        cache.clear();
        cache = null;
    }

    @Test
    public void getClassName() {
        assertThat(cache.getClassName(), is(BitmapLruCache.class.getName()));
    }

    @Test
    public void getBitmapSize() {
        assertThat(cache.getValueSize(A), is(1));
        assertThat(cache.getValueSize(B), is(4));
        assertThat(cache.getValueSize(C), is(9));
        assertThat(cache.getValueSize(D), is(16));
        assertThat(cache.getValueSize(E), is(25));
    }

}
