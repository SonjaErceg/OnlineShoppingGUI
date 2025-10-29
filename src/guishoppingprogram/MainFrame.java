/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guishoppingprogram;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private Cart cart;
    JPanel mainPanel;
    private JButton cartButton;
    private JLabel cartBadge;
    private JPanel cartSidePanel;
    boolean isCartVisible = false;
    private JTextField searchField;

    public MainFrame() {
        cart = new Cart();
        setTitle("Online Shopping System");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setupBannerAndSearch();
        setupMainPanel();
        setupCartSidePanel();
        setupCartButtonAction();

        showHome();
    }

    private void setupBannerAndSearch() {
        JPanel banner = new JPanel(null);
        banner.setBackground(new Color(40, 110, 255));
        banner.setPreferredSize(new Dimension(900, 100));

        JLabel title = new JLabel("Welcome to GUI Shopping!");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBounds(10, 30, 400, 30);
        banner.add(title);

        cartButton = new JButton("Cart");
        cartButton.setBounds(780, 30, 80, 30);
        banner.add(cartButton);

        cartBadge = new JLabel();
        cartBadge.setForeground(Color.WHITE);
        cartBadge.setBackground(Color.RED);
        cartBadge.setOpaque(true);
        cartBadge.setFont(new Font("Arial", Font.BOLD, 12));
        cartBadge.setHorizontalAlignment(SwingConstants.CENTER);
        cartBadge.setBounds(850, 25, 20, 20);
        updateCartBadge();
        banner.add(cartBadge);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setOpaque(false);
        searchField = new JTextField(20);
        searchPanel.add(searchField);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel.add(banner);
        northPanel.add(searchPanel);

        add(northPanel, BorderLayout.NORTH);
    }

    private void setupMainPanel() {
        mainPanel = new JPanel(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
    }

    private void setupCartSidePanel() {
        cartSidePanel = new JPanel(new BorderLayout());
        cartSidePanel.setBackground(Color.WHITE);
        cartSidePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        cartSidePanel.setPreferredSize(new Dimension(300, getHeight()));
        cartSidePanel.setVisible(false);
        getLayeredPane().add(cartSidePanel, JLayeredPane.POPUP_LAYER);
        cartSidePanel.setBounds(getWidth(), 0, 300, getHeight()); // start off-screen
    }

    private void setupCartButtonAction() {
        cartButton.addActionListener(e -> toggleCart());
    }

    void toggleCart() {
        if (isCartVisible) {
            slideOutCart();
        } else {
            slideInCart();
        }
        isCartVisible = !isCartVisible;
    }

    private void slideInCart() {
        // Rebuild cart panel each time cart opens
        cartSidePanel.removeAll();
        CartPanel cartPanel = new CartPanel(this);
        cartSidePanel.add(cartPanel, BorderLayout.CENTER);

        cartSidePanel.setVisible(true);

        new Thread(() -> {
            for (int x = getWidth(); x >= getWidth() - 300; x -= 20) {
                cartSidePanel.setBounds(x, 0, 300, getHeight());
                sleep(10);
            }
        }).start();
    }

    void slideOutCart() {
        new Thread(() -> {
            for (int x = getWidth() - 300; x <= getWidth(); x += 20) {
                cartSidePanel.setBounds(x, 0, 300, getHeight());
                sleep(10);
            }
            cartSidePanel.setVisible(false);
        }).start();
    }

    private void sleep(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }

    public Cart getCart() { return cart; }

    public void updateCartBadge() {
        int totalItems = cart.getItems().size();
        if (totalItems > 0) {
            cartBadge.setText(String.valueOf(totalItems));
            cartBadge.setVisible(true);
        } else {
            cartBadge.setVisible(false);
        }
    }

    public JTextField getSearchField() { return searchField; }
    public String getSearchText() { return searchField.getText(); }

    public void showHome() {
        if (isCartVisible) {
            slideOutCart();
            isCartVisible = false;
        }

        mainPanel.removeAll();

        ProductPanel products = new ProductPanel(this);
        JScrollPane scrollPane = new JScrollPane(products);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void showDetailPanel(ProductDetailPanel detailPanel) {
        if (isCartVisible) {
            slideOutCart();
            isCartVisible = false;
        }

        mainPanel.removeAll();
        mainPanel.add(detailPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
