/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.ltux.lldp.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.util.List;

/**
 *
 * @author oliver.guenther
 */
public class LldpMapping {

    public static class Value {

        public String type;

        public String value;

        public Boolean enabled;

        @Override
        public String toString() {
            return "Value{" + "type=" + type + ", value=" + value + ", enabled=" + enabled + '}';
        }

    }

    public static class Lldp {

        public static class Interface {

            public static class Chassis {

                public List<Value> id;

                public List<Value> name;

                public List<Value> descr;

                @JsonAlias("mgmt-ip")
                public List<Value> mgmtIp;

                @JsonAlias("mgmt-iface")
                public List<Value> mgmtIface;

                @Override
                public String toString() {
                    return "Chassis{" + "id=" + id + ", name=" + name + ", descr=" + descr + ", mgmtIp=" + mgmtIp + ", mgmtIface=" + mgmtIface + '}';
                }

            }

            public static class Port {

                public List<Value> id;

                public List<Value> descr;

                public List<Value> ttl;

                @Override
                public String toString() {
                    return "Port{" + "id=" + id + ", descr=" + descr + ", ttl=" + ttl + '}';
                }                
                
            }

            public String name;

            public String via;

            public String rid;

            public String age;

            public List<Chassis> chassis;
            
            public List<Port> port;

            @Override
            public String toString() {
                return "Interface{" + "name=" + name + ", via=" + via + ", rid=" + rid + ", age=" + age + ", chassis=" + chassis + ", port=" + port + '}';
            }            

        }

        @JsonAlias("interface")
        public List<Interface> interfaces;

        @Override
        public String toString() {
            return "Lldp{" + "interfaces=" + interfaces + '}';
        }

    }

    public List<Lldp> lldp;

    @Override
    public String toString() {
        return "LldpMapping{" + "lldp=" + lldp + '}';
    }

}
