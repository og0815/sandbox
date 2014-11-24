package eu.ggnet.saft.core.aux;

/**
 * Interface to supply a title, which gets produced at runtime.
 *
 * @author oliver.guenther
 */
// HINT: Yes, we know a Annotation would be more "up to date", but this is clearly enought for simple cases.
public interface DynamicTitle {

    String title();

}
