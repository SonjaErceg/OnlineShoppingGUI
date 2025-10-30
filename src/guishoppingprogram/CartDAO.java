/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guishoppingprogram;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author sonja
 */
public class CartDAO {
    
    public void addCartItem(CartItem item){
        String sql = "INSERT INTO OrderItems (product_name, quantity, price) VALUES (?, ?, ?)";
                try (Connection conn = DBConnection.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)){
                    
                    stmt.setString(1, item.getProduct().getName());
                    stmt.setInt(2, item.getQuantity());
                    stmt.setDouble(3, item.getProduct().getPrice());
                    stmt.executeUpdate();
                    
                    } catch (SQLException e) {
                     e.printStackTrace();    
                    } 
                } 


public List<CartItem> getAllCartItems(){
List<CartItem> items = new ArrayList<>();
String sql = "SELECT * FROM CART";

try (Connection conn = DBConnection.getConnection();
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery(sql)) {

while (rs.next()) {
String productName = rs.getString("PRODUCT_NAME");
double price = rs.getDouble("PRICE");
int quantity = rs.getInt("QUANTITY");

Product product = ProductDAO.getProductByName(productName);
items.add(new CartItem(product, quantity));

}

 } catch (SQLException e) {
            e.printStackTrace();
        }

return items;
}

public void clearCart(){
String sql = "DELETE FROM CART";
try (Connection conn = DBConnection.getConnection();
Statement stmt = conn.createStatement()) {

stmt.executeUpdate(sql);

} catch (SQLException e) {
            e.printStackTrace();
        }
}
}

