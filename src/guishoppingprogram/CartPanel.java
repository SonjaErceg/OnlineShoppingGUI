/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guishoppingprogram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CartPanel extends JPanel {

    private MainFrame mainFrame;

    public CartPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        buildCart();
    }

    private void buildCart() {
        removeAll();

        if (mainFrame.getCart().isEmpty()) {
            JLabel emptyLabel = new JLabel("Your cart is empty.", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(Box.createVerticalStrut(20));
            add(emptyLabel);
            return;
        }

        for (CartItem item : mainFrame.getCart().getItems()) {
            add(createCartItemPanel(item));
            add(Box.createVerticalStrut(10));
        }

        // Total at bottom
        JLabel totalLabel = new JLabel("Total: $" + mainFrame.getCart().getTotal(), SwingConstants.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(20));
        add(totalLabel);

        // Checkout button
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkoutButton.addActionListener(e -> checkout());
        add(Box.createVerticalStrut(10));
        add(checkoutButton);

        revalidate();
        repaint();
    }

    private JPanel createCartItemPanel(CartItem item) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        JLabel nameLabel = new JLabel(item.getProduct().getName(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel priceLabel = new JLabel("$" + item.getProduct().getPrice() + " x " + item.getQuantity() + " = $" + item.getTotalPrice(), SwingConstants.CENTER);
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton removeButton = new JButton("\uD83D\uDDD1"); // trash bin icon
        removeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeButton.addActionListener(e -> {
            mainFrame.getCart().getItems().remove(item);
            mainFrame.updateCartBadge();
            buildCart(); // refresh cart
        });

        panel.add(Box.createVerticalStrut(5));
        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(priceLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(removeButton);
        panel.add(Box.createVerticalStrut(5));

        return panel;
    }

    private void checkout() {
    if (mainFrame.getCart().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Your cart is empty!", "Checkout", JOptionPane.WARNING_MESSAGE);
    } else {
        mainFrame.mainPanel.removeAll();
        CheckoutPanel checkout = new CheckoutPanel(mainFrame);
        mainFrame.mainPanel.add(checkout, BorderLayout.CENTER);
        mainFrame.mainPanel.revalidate();
        mainFrame.mainPanel.repaint();

        // Hide side cart
        if (mainFrame.isCartVisible) {
            mainFrame.slideOutCart();
            mainFrame.isCartVisible = false;
        }
    }
}

    }

