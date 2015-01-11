package hotchemi.com.github;

import android.graphics.Bitmap;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.HONEYCOMB_MR1;

/**
 * @author Shintaro Katafuchi
 */
final class Utils {

    private Utils() {
    }

    /**
     * Returns the number of bytes in {@link android.graphics.Bitmap}.
     *
     * @param bitmap bitmap
     * @return the number of bytes
     */
    static int getBitmapSize(Bitmap bitmap) {
        int bytes = SDK_INT >= HONEYCOMB_MR1 ? bitmap.getByteCount() : getByteCount(bitmap);
        if (bytes < 0) {
            throw new IllegalStateException("Bitmap size is negative. Size=" + bitmap);
        }
        return bytes;
    }

    static int getByteCount(Bitmap bitmap) {
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

}
