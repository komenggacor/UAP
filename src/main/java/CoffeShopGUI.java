import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class CoffeShopGUI {
    private JFrame frame;
    private JTextField nameField, quantityField, menuField;
    private JTextArea orderListArea;
    private List<Order> orders;
    private List<String> menu;

    public CoffeShopGUI() {
        orders = new ArrayList<>();
        menu = new ArrayList<>();
        initializeMenu();

        frame = new JFrame("Coffee Shop GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.setBackground(new Color(240, 234, 214));

        JLabel titleLabel = new JLabel("Welcome to Coffee Shop", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(101, 67, 33));

        JLabel nameLabel = new JLabel("Customer Name:");
        nameField = new JTextField();

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityField = new JTextField();

        JLabel menuLabel = new JLabel("Menu Item:");
        menuField = new JTextField();

        JButton addButton = new JButton("Add Order");
        JButton updateButton = new JButton("Update Order");
        JButton deleteButton = new JButton("Delete Order");
        JButton addMenuButton = new JButton("Add Menu Item");
        JButton viewMenuButton = new JButton("View Menu");
        JButton generateNotaButton = new JButton("Generate Nota");

        styleButton(addButton);
        styleButton(updateButton);
        styleButton(deleteButton);
        styleButton(addMenuButton);
        styleButton(viewMenuButton);
        styleButton(generateNotaButton);

        orderListArea = new JTextArea();
        orderListArea.setEditable(false);
        orderListArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(orderListArea);

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(quantityLabel);
        panel.add(quantityField);
        panel.add(menuLabel);
        panel.add(menuField);
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(addMenuButton);
        panel.add(viewMenuButton);
        panel.add(generateNotaButton);

        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);

        addButton.addActionListener(new AddOrderAction());
        updateButton.addActionListener(new UpdateOrderAction());
        deleteButton.addActionListener(new DeleteOrderAction());
        addMenuButton.addActionListener(new AddMenuAction());
        viewMenuButton.addActionListener(new ViewMenuAction());
        generateNotaButton.addActionListener(new GenerateNotaAction(orders));

        frame.setVisible(true);
    }

    private void initializeMenu() {
        menu.add("Espresso");
        menu.add("Americano");
        menu.add("Latte");
        menu.add("Cappuccino");
        menu.add("Mocha");
    }

    private void refreshOrderList() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < orders.size(); i++) {
            builder.append((i + 1)).append(". ")
                    .append(orders.get(i)).append("\n");
        }
        orderListArea.setText(builder.toString());
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(101, 67, 33));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
    }

    private class AddOrderAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String name = nameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                String menuItem = menuField.getText();

                if (name.isEmpty() || quantity <= 0 || menuItem.isEmpty()) {
                    throw new IllegalArgumentException("Invalid input: All fields must be valid.");
                }

                if (!menu.contains(menuItem)) {
                    throw new IllegalArgumentException("Menu item does not exist.");
                }

                orders.add(new Order(name, menuItem, quantity));
                refreshOrderList();

                nameField.setText("");
                quantityField.setText("");
                menuField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Quantity must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class UpdateOrderAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int index = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter order number to update:")) - 1;

                if (index < 0 || index >= orders.size()) {
                    throw new IndexOutOfBoundsException("Order number does not exist.");
                }

                String newName = JOptionPane.showInputDialog(frame, "Enter new name:");
                String newMenuItem = JOptionPane.showInputDialog(frame, "Enter new menu item:");
                int newQuantity = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter new quantity:"));

                if (newName.isEmpty() || newMenuItem.isEmpty() || newQuantity <= 0) {
                    throw new IllegalArgumentException("Invalid input: All fields must be valid.");
                }

                if (!menu.contains(newMenuItem)) {
                    throw new IllegalArgumentException("Menu item does not exist.");
                }

                orders.get(index).setName(newName);
                orders.get(index).setMenuItem(newMenuItem);
                orders.get(index).setQuantity(newQuantity);
                refreshOrderList();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class DeleteOrderAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int index = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter order number to delete:")) - 1;

                if (index < 0 || index >= orders.size()) {
                    throw new IndexOutOfBoundsException("Order number does not exist.");
                }

                orders.remove(index);
                refreshOrderList();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class AddMenuAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String menuItem = JOptionPane.showInputDialog(frame, "Enter new menu item:");
            if (menuItem != null && !menuItem.isEmpty()) {
                menu.add(menuItem);
                JOptionPane.showMessageDialog(frame, "Menu item added: " + menuItem, "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Menu item cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class ViewMenuAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringBuilder menuDisplay = new StringBuilder("Current Menu:\n");
            for (String item : menu) {
                menuDisplay.append("- ").append(item).append("\n");
            }
            JOptionPane.showMessageDialog(frame, menuDisplay.toString(), "Menu", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}