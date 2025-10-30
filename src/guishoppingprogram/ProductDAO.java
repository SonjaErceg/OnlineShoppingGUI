/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guishoppingprogram;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;


/**
 *
 * @author sonja
 */
public class ProductDAO {
    private static final String URL = "jdbc:derby:GUIShopping;create=true";

    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT ID, NAME, PRICE, DESCRIPTION FROM PRODUCT";
        
        
        try (Connection c = DBConnection.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {

            while (rs.next()) {
                products.add(new Product(
                rs.getInt("ID"), 
                rs.getString("NAME"),
                rs.getDouble("PRICE"),
                rs.getString("DESCRIPTION")
                        ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    
    public Product getProductByName(String name) {
        String sql = "SELECT ID, NAME, PRICE, DESCRIPTION FROM PRODUCT WHERE NAME = ?";
        try (Connection c = DBConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return new Product(
                rs.getInt("ID"), 
                rs.getString("NAME"),
                rs.getDouble("PRICE"),
                rs.getString("DESCRIPTION")
                );
            }
            
             } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
        }
    }


