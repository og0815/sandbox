package sample.primefaces;

import java.io.Serializable;
import java.util.*;
import javax.annotation.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author oliver.guenther
 */
@Named
@ManagedBean
@ViewScoped
public class AnalyticsController implements Serializable {

    private String customer;

    private final static List<String> names = Arrays.asList("Max Mustermann", "Max Malström", "Moritz Mossman", "Mika Miesmuschel", "Hugo Holstein");

    public List<String> completeCustomer(String query) {
        System.out.println("Hohoh"); // TODo: Bug somethere somehow.
        List<String> result = new ArrayList<>();
        for (String name : names) {
            if (name.startsWith(query)) result.add(name);
        }
        return result;

        //     return names.stream().filter(t -> t.startsWith(query)).collect(Collectors.toList());
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getMessage() {
        System.out.println("Horst im Wald");
        return "Huhu 4 : " + new Date();
    }

}
