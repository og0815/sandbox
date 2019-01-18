/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.pojo.two;

import java.util.Objects;

/**
 *
 * @author oliver.guenther
 */
public class Song {

    private String title;

    private String interpred;

    private int runtime;

    public Song() {
    }

    public Song(String title, String interpred, int runtime) {
        this.title = title;
        this.interpred = interpred;
        this.runtime = runtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInterpred() {
        return interpred;
    }

    public void setInterpred(String interpred) {
        this.interpred = interpred;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.title);
        hash = 19 * hash + Objects.hashCode(this.interpred);
        hash = 19 * hash + this.runtime;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Song other = (Song) obj;
        if (this.runtime != other.runtime) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.interpred, other.interpred)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" + "title=" + title + ", interpred=" + interpred + ", runtime=" + runtime + '}';
    }

    public String toModuleInfo() {
        Module module = this.getClass().getModule();
        return this.getClass().getName() + "{module=" + module + ", name=" + module.getName() + ", isNamed=" + module.isNamed() + ", descriptor=" + module.getDescriptor() + "}";
    }

    public static void main(String[] args) {
        var s = new Song("Bells of Hell", "Hells Kitchen", 123);
        System.out.println(s);
        System.out.println(s.toModuleInfo());
    }

}
