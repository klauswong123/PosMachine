package pos.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<SingleItem> itemsWithDetail = convertToItems(barcodes);
        int totalPrice = calculateTotal(itemsWithDetail);
        String result = formatOutput(itemsWithDetail, totalPrice);
        System.out.println(result);
        return result;
    }
    private Integer calculateTotal(List<SingleItem> itemsWithDetail){
        int totalPrice = 0;
        for (SingleItem item : itemsWithDetail) {
            totalPrice += item.getSubTotal();
        }
        return totalPrice;
    }
    private List<ItemInfo> loadAllItemsInfo(){
        return ItemDataLoader.loadAllItemInfos();
    }
    private String formatOutput(List<SingleItem> itemsWithDetail, Integer totalPrice){
        String result = "***<store earning no money>Receipt***\n";
        for (SingleItem itemInfo : itemsWithDetail) {
            result = result+"Name: "+itemInfo.getName()+", Quantity: "+itemInfo.getQuantity()+", Unit price: "+itemInfo.getUnitPrice()+" (yuan), Subtotal: "+itemInfo.getSubTotal()+" (yuan)\n";
        }
        result = result+"----------------------\n"+"Total: "+totalPrice+" (yuan)\n**********************";
        return result;
    }
    private List<SingleItem> convertToItems(List<String> barcodes) {
        List<ItemInfo> itemDetailList = loadAllItemsInfo();
        List<SingleItem> itemList = new ArrayList<>();
        List<String> distinctBarcodes = barcodes.stream()
                .distinct()
                .collect(Collectors.toList());
        for (String barcode : distinctBarcodes) {
            for (ItemInfo itemInfo : itemDetailList) {
                if (barcode.equals(itemInfo.getBarcode())) {
                    int quantity = Collections.frequency(barcodes, barcode);
                    int unitPrice = itemInfo.getPrice();
                    int subTotal = calculateSubtotal(quantity, unitPrice);
                    SingleItem item = new SingleItem(itemInfo.getName(), quantity, unitPrice, subTotal);
                    itemList.add(item);
                }
            }
        }
        return itemList;
    }

    private int calculateSubtotal(int quantity, int unitPrice) {
        return quantity * unitPrice;
    }
}