/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guishoppingprogram;

/**
 *
 * @author sonja
 */
public class OrderDAO {
    
    public void insertOrder(Order order) {
        String inseertOrderSQL = "INSERT INTO Orders (order_id, customer_name, total_price, order_date) VALUES (?, ?, ?, ?)";
        String inseertItemSQL = "INSERT INTO OrderItems (order_id, product_id, product_name, price, quantity) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement orderStmt = conn.prepareStatement(insertOrderSQL);
                PreparedStatement itemStmt = conn.prepareStatement(insertOrderSQL)) {
            
            conn.setAutoCommit(false);
            
            orderStmt.setInt(1, order.getOrderId());
            orderStmt.setString(2, order.getCustomerName());
            orderStmt.setDouble(3, order.getTotalPrice());
            orderStmt.setTimestamp(4, new Timestamp(order.getOrderDate().getTime()));
            OrderStmt.executeUpdate();
            
            for (CartItem item : order.getItems()) {
                itemStmt.setInt(1, order.getOrderId());
                itemStmt.setInt(2, item.getProduct().getId());
                itemStmt.setString(3, item.getProduct().getName());
                itemStmt.setString(4, item.getProduct().getPrice());
                itemStmt.setInt(5, order.getQuantity());
                itemStmt.addBatch();
            }
            
            itemStmt.executeBatch();
            conn.commit();
            
        } catch (SQLExeception e){
            e.printStackTrace();
        }
    }
    
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql ="SELECT * FROM Orders";
        
        try (Connection conn = DBConnection.getConnection();
                PreparedStatment stmt = conn.perpareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setCustomerName(rs.getString("customer_name"));
                order.setTotalPrice(rs.getDouble("total_price"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.add(order);
                
            
            
          } catch (SQLExeception e){
            e.printStackTrace();
        }
           return orders;
        }
    
    public List<CartItem> getOrderItems(int orderId) {
        List<CartItem> items = new ArrayList<>();
        String sql = "SELECT * FROM OrderItems WHERE order_id = ?";
        
        try (Connection conn = DBConnection.getConnection());
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1,orderId);
            ResultSet rs = new.executeQuery();
            
            while (rs.next()) {
                Product product = new Product(
                rs.getInt("product_id"),
                rs.getInt("product_name"),
                rs.getInt("price"),
                null
               );
                
                CartItem item = new CartItem(product, rs.getInt("quantity"));
                items.add(item);
            }
            
             } catch (SQLExeception e){
            e.printStackTrace();
        }
            
            return items;
        }
    }
    }

