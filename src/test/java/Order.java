class Order {
    private String name;
    private String menuItem;
    private int quantity;

    public Order(String name, String menuItem, int quantity) {
        this.name = name;
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(String menuItem) {
        this.menuItem = menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Menu Item: " + menuItem + ", Quantity: " + quantity;
    }
}
