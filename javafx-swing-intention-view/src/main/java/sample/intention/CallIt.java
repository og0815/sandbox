package sample.intention;

/**
 * Like {@link java.util.concurrent.Callable } , but with a parameter.
 *
 * @author oliver.guenther
 * @param <T> type of the argument
 * @param <V> type of the result.
 */
public interface CallIt<T, V> {

    public V call(T t) throws Exception;

}
