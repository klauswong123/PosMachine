package pos.machine;

//rename class
public class ItemWithDetail {
    private final String name;
    private final int quantity;
    private final int unitPrice;
    private final int subTotal;

    public ItemWithDetail(String name, int quantity, int unitPrice, int subTotal) {
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subTotal = subTotal;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public int getSubTotal() {
        return subTotal;
    }
}