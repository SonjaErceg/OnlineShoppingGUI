/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guishoppingprogram;

import com.sun.jdi.connect.spi.Connection;
import java.beans.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.derby.iapi.sql.ResultSet;

/**
 *
 * @author sonja
 */
public class ProductDAO {
    private static final String URL = "jdbc:derby:GUIShopping;create=true";

    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery("SELECT NAME, PRICE FROM PRODUCT")) {

            while (rs.next()) {
                products.add(new Product(rs.getString("NAME"), rs.getDouble("PRICE")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}

