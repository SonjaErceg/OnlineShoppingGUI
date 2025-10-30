/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guishoppingprogram;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;


/**
 *
 * @author sonja
 */
public class OrderDAO {
    
    public void insertOrder(Order order) {
        String insertOrderSQL = "INSERT INTO Orders (total_price, order_date) VALUES (?, ?)";
        String insertItemSQL = "INSERT INTO OrderItems (order_id, product_name, quantity, price) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement orderStmt = conn.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement itemStmt = conn.prepareStatement(insertItemSQL)) {
            
            conn.setAutoCommit(false);
            
          
            orderStmt.setDouble(1, order.getTotalPrice());
            orderStmt.setTimestamp(2, new Timestamp(order.getOrderDate().getTime()));
            orderStmt.executeUpdate();
            
            ResultSet keys = orderStmt.getGeneratedKeys();
            int orderId = 0;
            if (keys.next()){
                orderId = keys.getInt(1);
            }
            
            for (CartItem item : order.getItems()) {
                itemStmt.setInt(1, orderId);
                itemStmt.setString(2, item.getProduct().getName());
                itemStmt.setInt(3, item.getQuantity());
                itemStmt.setDouble(4, item.getProduct().getPrice());
                itemStmt.addBatch();
            }
            
            itemStmt.executeBatch();
            conn.commit();
            
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql ="SELECT * FROM Orders";
        
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setTotalPrice(rs.getDouble("total_price"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                orders.add(order);
            } 
            
            
          } catch (SQLException e){
            e.printStackTrace();
        }
           return orders;
        }
    
    public List<CartItem> getOrderItems(int orderId) {
        List<CartItem> items = new ArrayList<>();
        String sql = "SELECT * FROM OrderItems WHERE order_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1,orderId);
            ResultSet rs = stmt.executeQuery();
            
            ProductDAO productDAO = new ProductDAO();
            while (rs.next()) {
                Product product = productDAO.getProductByName(rs.getString("product_name"));
                
                CartItem item = new CartItem(product, rs.getInt("quantity"));
                items.add(item);
            }
            
             } catch (SQLException e){
            e.printStackTrace();
        }
            
            return items;
        }
    }
    

