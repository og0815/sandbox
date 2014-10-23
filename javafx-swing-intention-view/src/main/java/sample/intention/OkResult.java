package sample.intention;

/**
 *
 * @author oliver.guenther
 */
public class OkResult<T> {

    T payload;
    boolean ok;

    // Not run in the Ui Thread.... Good or Bad?
    public void onOk(Listener<T> listener) {
        if (!ok) return;
        listener.listen(payload);
    }
}
