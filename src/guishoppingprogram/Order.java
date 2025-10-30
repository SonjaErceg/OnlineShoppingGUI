/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guishoppingprogram;

import java.util.Date;
import java.util.List;

/**
 *
 * @author sonja
 */
public class Order {
    private int orderId;
    private String customerName;
    private double totalPrice;
    private Date orderDate;
    private List<CartItem> items;
    
    public Order(int orderid, String customerName, double totalPrice, Date orderDate, List<CartItem> items){
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.items = items;
         
    }
    
    public Order() {}
    
    public int getOrderId() {return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    
    public String getCustomerName() {return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    
    public double getTotalPrice() {return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    
    public Date getOrderDate() {return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
    
    public List<CartItem> getItems() {return items; }
    public void setItems(List<CartItem> items) { this.items = items; }
}
