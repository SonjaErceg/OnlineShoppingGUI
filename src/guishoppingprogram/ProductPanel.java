/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guishoppingprogram;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductPanel extends JPanel {

    private MainFrame mainFrame;
    private List<Product> allProducts = new ArrayList<>();      // all products
    private List<Product> displayedProducts = new ArrayList<>(); // filtered products

    public ProductPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new GridLayout(0, 3, 20, 30)); // 3 columns, dynamic rows, more vertical spacing
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Load products from database
        try {
            loadProductsFromDatabase();
        } catch (Exception e) {
            System.out.println("⚠ Error loading products: " + e.getMessage());
            e.printStackTrace();
        }

        // Add extra products manually
        allProducts.add(new Product(0, "Orange Juice", 4.50, "Freshly squeezed"));
        allProducts.add(new Product(0, "Yogurt", 2.99, "Plain"));
        allProducts.add(new Product(0, "Butter", 3.79, "Unsalted"));
        allProducts.add(new Product(0, "Chicken", 12.99, "Chicken Breast"));
        allProducts.add(new Product(0, "Salmon", 15.99, "Smoked"));
        allProducts.add(new Product(0, "Rice", 5.50, "Brown Rice"));
        allProducts.add(new Product(0, "Pasta", 3.99, "Macaroni"));
        allProducts.add(new Product(0, "Cereal", 4.20, "Crunchy"));
        allProducts.add(new Product(0, "Tomatoes", 2.79, "Orange"));
        allProducts.add(new Product(0, "Lettuce", 1.99, "Fresh"));
        allProducts.add(new Product(0, "Carrots", 2.50, "Locally sourced"));
        allProducts.add(new Product(0, "Potatoes", 3.30, "Locally sourced"));
        allProducts.add(new Product(0, "Onions", 2.10, "Small"));
        allProducts.add(new Product(0, "Peanut Butter", 5.00, "Organic"));
        allProducts.add(new Product(0, "Jam", 3.60, "Cranberry Jam"));
        allProducts.add(new Product(0, "Coffee", 6.50, "Ground Coffee"));
        allProducts.add(new Product(0, "Tea", 4.00, "Green tea"));
        allProducts.add(new Product(0, "Milk Chocolate", 2.99, "Chnocolate bar"));
        allProducts.add(new Product(0, "Cookies", 3.50, "Chocolate Chips"));
        allProducts.add(new Product(0, "Ice Cream", 7.99, "Vanilla"));

        displayedProducts.addAll(allProducts);
        displayProducts(displayedProducts);

        // Search functionality
        JTextField searchField = mainFrame.getSearchField();
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterProducts(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterProducts(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterProducts(); }
        });
    }

    private void loadProductsFromDatabase() throws SQLException {
        try (Connection c = DBConnection.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery("SELECT NAME, PRICE FROM PRODUCT")) {

            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                double price = rs.getDouble("PRICE");
                String description = rs.getString("DESCRIPTION");
                allProducts.add(new Product(id, name, price, description));
            }
        }
    }

    private void displayProducts(List<Product> productsToShow) {
        removeAll();

        for (Product product : productsToShow) {
            add(createProductPanel(product));
        }

        revalidate();
        repaint();
    }

    private void filterProducts() {
        String query = mainFrame.getSearchText().trim().toLowerCase();
        displayedProducts.clear();

        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(query)) {
                displayedProducts.add(product);
            }
        }

        displayProducts(displayedProducts);
    }

    private JPanel createProductPanel(Product product) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        panel.setPreferredSize(new Dimension(150, 220)); // taller panels for vertical expansion

        JLabel nameLabel = new JLabel(product.getName(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel priceLabel = new JLabel("$" + product.getPrice(), SwingConstants.CENTER);
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton buyButton = new JButton("Buy Now");
        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyButton.addActionListener(e -> {
            ProductDetailPanel detailPanel = new ProductDetailPanel(mainFrame);
            detailPanel.setProduct(product);
            mainFrame.showDetailPanel(detailPanel);
        });

        panel.add(Box.createVerticalStrut(20));
        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(priceLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(buyButton);
        panel.add(Box.createVerticalStrut(15));

        return panel;
    }
}
