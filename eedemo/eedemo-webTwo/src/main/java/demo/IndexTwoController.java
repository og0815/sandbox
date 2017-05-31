/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author olive
 */
@Named
@ApplicationScoped
public class IndexTwoController implements Serializable {

    @Inject
    private SingletonBean single;

    @Inject
    private StatefulBean state;

    public void singleAdd() {
        single.add();
    }

    public int getSingleCount() {
        return single.getCount();
    }

    public void setSingleName(String name) {
        single.setName(name);
    }

    public String getSingleName() {
        return single.getName();
    }

    public void stateAdd() {
        state.add();
    }

    public int getStateCount() {
        return state.getCount();
    }

    public void setStateName(String name) {
        state.setName(name);
    }

    public String getStateName() {
        return state.getName();
    }

}
