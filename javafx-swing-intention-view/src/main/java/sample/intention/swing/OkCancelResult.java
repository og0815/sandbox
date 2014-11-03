package sample.intention.swing;

/**
 *
 * @author oliver.guenther
 */
public class OkCancelResult<V> {

    public final V value;
    public final boolean ok;

    public OkCancelResult(V value, boolean ok) {
        this.value = value;
        this.ok = ok;
    }

}
