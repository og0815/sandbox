/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.sql.nativ;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author oliver.guenther
 */
public class ConnectDb {

    public static void main(String[] args) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://blackout/test2?user=hugo&password=demo");

        int id = 2;
//        ResultSet result = con.createStatement().executeQuery("Select * from heino where id > " + id);
        PreparedStatement pst = con.prepareStatement("Select * from heino where id > ?");
        pst.setInt(1, id);
        ResultSet result = pst.executeQuery();

        while (result.next()) {
            System.out.print(result.getInt(1));
            System.out.print(" - ");
            System.out.println(result.getString(2));
        }
        result.close();
        con.close();
    }

}
