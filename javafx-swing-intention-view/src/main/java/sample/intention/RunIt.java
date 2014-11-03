package sample.intention;

/**
 * Like {@link java.lang.Runnable} but with an Argument and may throw exception, which are handled somethere else.
 *
 * @author oliver.guenther
 */
public interface RunIt<T> {

    public void run(T parameter) throws Exception;

}
