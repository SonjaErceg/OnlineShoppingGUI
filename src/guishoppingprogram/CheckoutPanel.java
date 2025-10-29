/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guishoppingprogram;

import javax.swing.*;
import java.awt.*;

public class CheckoutPanel extends JPanel {

    private MainFrame mainFrame;

    public CheckoutPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        buildUI();
    }

    private void buildUI() {
        add(Box.createVerticalStrut(50));

        JLabel title = new JLabel("Checkout", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);

        add(Box.createVerticalStrut(20));

        // Display cart items
        for (CartItem item : mainFrame.getCart().getItems()) {
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
            itemPanel.setBackground(Color.WHITE);
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

            JLabel nameLabel = new JLabel(item.getProduct().getName(), SwingConstants.CENTER);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
            JLabel priceLabel = new JLabel("$" + item.getProduct().getPrice() + " x " + item.getQuantity() + " = $" + item.getTotalPrice(), SwingConstants.CENTER);
            priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));

            itemPanel.add(Box.createVerticalStrut(5));
            itemPanel.add(nameLabel);
            itemPanel.add(priceLabel);
            itemPanel.add(Box.createVerticalStrut(5));

            add(itemPanel);
            add(Box.createVerticalStrut(10));
        }

        // Total
        JLabel totalLabel = new JLabel("Total: $" + mainFrame.getCart().getTotal(), SwingConstants.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 20));
        totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(20));
        add(totalLabel);

        add(Box.createVerticalStrut(20));

        // Confirm Button
        JButton confirmButton = new JButton("Confirm Purchase");
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Thank you for your purchase!\nTotal: $" + mainFrame.getCart().getTotal());
            mainFrame.getCart().getItems().clear();
            mainFrame.updateCartBadge();
            mainFrame.showHome();
        });
        add(confirmButton);

        add(Box.createVerticalStrut(20));

        // Back Button
        JButton backButton = new JButton("Back to Cart");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> mainFrame.toggleCart()); // back to slide-in cart
        add(backButton);
    }
}
