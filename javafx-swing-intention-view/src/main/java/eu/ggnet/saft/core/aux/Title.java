package eu.ggnet.saft.core.aux;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *
 * @author oliver.guenther
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface Title {

    /**
     * Returns the Title
     *
     * @return the title
     */
    String value();

}
