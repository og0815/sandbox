package eu.ggnet.saft.core.all;

import eu.ggnet.saft.core.aux.CallableA1;
import eu.ggnet.saft.core.aux.Title;

import java.awt.Desktop;
import java.io.File;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * Util is
 *
 * @author oliver.guenther
 */
public class UiUtil {

    public static <V, R> Callable<R> onOk(CallableA1<V, R> function, OnceCaller<OkCancelResult<V>> before) {
        return () -> {
            if (before.ifPresentIsNull()) return null; // Chainbreaker
            OkCancelResult<V> result = before.get();
            if (!result.ok) return null;  // Break Chain on demand
            return function.call(result.value);
        };
    }

    public static <T> Callable<Void> osOpen(OnceCaller<T> before) {
        return () -> {
            if (before.ifPresentIsNull()) return null; // Chainbreaker
            T beforeResult = before.get();
            if (beforeResult == null) return null; // Null result is also useless here.
            if (beforeResult instanceof File) {
                Desktop.getDesktop().open((File) beforeResult);
            } else {
                throw new IllegalArgumentException("No Os support for Object Type: " + beforeResult.getClass());
            }
            return null;
        };
    }

    /**
     * Returns the Value of {@link Title} if set on the parameter, otherwise empty.
     *
     * @param o the element to extract from.
     * @return the Value of {@link Title} if set on the parameter, otherwise empty.
     */
    public static Optional<String> extractTitle(Object o) {
        Title annotation = o.getClass().getAnnotation(Title.class);
        if (annotation == null) return Optional.empty();
        return Optional.ofNullable(annotation.value());
    }

}