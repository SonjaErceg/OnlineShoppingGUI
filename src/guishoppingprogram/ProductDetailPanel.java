/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guishoppingprogram;

import javax.swing.*;
import java.awt.*;

public class ProductDetailPanel extends AbstractPanel {
    private Product product;
    private JLabel nameLabel, priceLabel;
    private JSpinner quantitySpinner;

    public ProductDetailPanel(MainFrame mainFrame) {
        super(mainFrame);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    protected void buildUI() {
        nameLabel = new JLabel();
        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        priceLabel = new JLabel();
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        quantitySpinner.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton addButton = new JButton("Add to Cart");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.addActionListener(e -> {
            if (product != null) { // null check
                mainFrame.getCart().addProduct(product, (int) quantitySpinner.getValue());
                mainFrame.updateCartBadge();
            } else {
                JOptionPane.showMessageDialog(this,
                    "No product selected!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> mainFrame.showHome());

        add(Box.createVerticalStrut(50));
        add(nameLabel);
        add(Box.createVerticalStrut(20));
        add(priceLabel);
        add(Box.createVerticalStrut(20));
        add(quantitySpinner);
        add(Box.createVerticalStrut(20));
        add(addButton);
        add(Box.createVerticalStrut(10));
        add(backButton);
    }

    public void setProduct(Product product) {
        this.product = product;
        if (product != null) {
            nameLabel.setText(product.getName());
            priceLabel.setText("$" + product.getPrice());
        }
    }
}
