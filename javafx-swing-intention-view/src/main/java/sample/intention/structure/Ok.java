package sample.intention.structure;

import java.util.function.Function;

/**
 *
 * @author oliver.guenther
 */
public interface Ok<T, R> {

    public void onOk(Function<T, R> listener);

//    public void onOkRun(RunIt<T> run);
//    public <V> Done<V> onOkCall(CallIt<T, V> callIt);
}
