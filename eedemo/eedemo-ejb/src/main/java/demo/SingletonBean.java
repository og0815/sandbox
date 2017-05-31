package demo;

import javax.ejb.Singleton;

/**
 *
 * @author martin
 */
@Singleton
public class SingletonBean {

    private int count = 0;

    private String name = "";

    public void add() {
        count++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

}
