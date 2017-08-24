package deadlock;

import org.junit.Test;

/**
 *
 * @author oliver.guenther
 */
public class SimpleTest {

    @Test
    public void single() {
        Tresen t = new Tresen(5);

        BestellungHans b1 = new BestellungHans(t, 1);
        b1.run();

        BestellungMax m1 = new BestellungMax(t, 1);
        m1.run();

    }
}
