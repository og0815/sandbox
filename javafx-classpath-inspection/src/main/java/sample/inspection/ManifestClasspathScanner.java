package sample.inspection;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import static java.util.jar.Attributes.Name.CLASS_PATH;
import java.util.jar.Manifest;
import org.apache.commons.lang3.StringUtils;

/**
 * Allows the aggregated scan of all Manifests and there Classpath elements filtering and manipulating the result.
 *
 * @author oliver.guenther
 */
public class ManifestClasspathScanner {

    public static List<String> find() {
        List<String> result = new ArrayList<>();
        try {
            Enumeration<URL> resources = ManifestClasspathScanner.class.getClassLoader().getResources("META-INF/MANIFEST.MF");
            StringBuilder sb = new StringBuilder();
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                Manifest manifest = new Manifest(url.openStream());
                String classpath = manifest.getMainAttributes().getValue(CLASS_PATH);
                if (!StringUtils.isBlank(classpath)) sb.append(classpath).append(" ");
            }
            Scanner s = new Scanner(sb.toString());
            s.useDelimiter(" ");
            while (s.hasNext()) {
                String classpathElement = s.next();
                if (StringUtils.isBlank(classpathElement)) continue;
                if (result.contains(classpathElement)) continue;
                result.add(classpathElement);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Exception in " + ManifestClasspathScanner.class.getName(), ex);
        }
        return result;
    }

}
