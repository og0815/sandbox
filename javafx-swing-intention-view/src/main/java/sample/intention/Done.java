package sample.intention;

/**
 *
 * @author oliver.guenther
 */
public class Done<V> {

    private Listener<V> listener;

    public void done(Listener<V> listener) {
        this.listener = listener;
    }

    void executeDone(V v) {
        if (listener != null) {
            listener.listen(v);
        }
    }

}
