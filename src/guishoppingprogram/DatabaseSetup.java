/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guishoppingprogram;

/**
 *
 * @author sonja
 */
import java.sql.*;

import java.sql.*;

public final class DatabaseSetup {
    public static void init() {
        try (Connection c = DBConnection.getConnection();
             Statement s = c.createStatement()) {

            // Create PRODUCT table
            s.executeUpdate("CREATE TABLE PRODUCT (ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, NAME VARCHAR(100), DESCRIPTION VARCHAR(500), PRICE DOUBLE, STOCK INT)");

            // Create ORDER table
            s.executeUpdate("CREATE TABLE ORDERS (ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, TOTAL DOUBLE, ORDER_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

            // Create ORDER_ITEM table
            s.executeUpdate("CREATE TABLE ORDER_ITEM (ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, ORDER_ID INT, PRODUCT_NAME VARCHAR(100), QUANTITY INT, PRICE DOUBLE)");

            insertSampleProducts(c);

        } catch (SQLException e) {
            if (!"X0Y32".equals(e.getSQLState())) e.printStackTrace();
        }
    }

    private static void insertSampleProducts(Connection c) throws SQLException {
        String[] names = {"Apples", "Bananas", "Milk", "Bread", "Eggs", "Cheese", "Orange Juice", "Chicken", "Salmon", "Yogurt"};
        double[] prices = {3.99, 2.49, 4.29, 3.49, 6.99, 5.79, 4.50, 12.99, 15.99, 5.50};

        PreparedStatement ps = c.prepareStatement("INSERT INTO PRODUCT (NAME, DESCRIPTION, PRICE, STOCK) VALUES (?, ?, ?, ?)");
        for (int i = 0; i < names.length; i++) {
            ps.setString(1, names[i]);
            ps.setString(2, names[i] + " description");
            ps.setDouble(3, prices[i]);
            ps.setInt(4, 50); // default stock
            ps.executeUpdate();
        }
    }
}
