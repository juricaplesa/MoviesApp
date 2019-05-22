package android.text;

/**
 * Created by Jurica Pleša
 * <p>
 * Required for unit testing TextUtils class
 * http://tools.android.com/tech-docs/unit-testing-support#TOC-Method-...-not-mocked.-
 * We are aware that the default behavior is problematic when using classes like
 * Log or TextUtils and will evaluate possible solutions in future releases.
 */
public class TextUtils {
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }
}