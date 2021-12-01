package pos.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<SingleItem> itemsWithDetail = convertToItems(barcodes);
        int totalPrice = calculateTotal(itemsWithDetail);
        return formatOutput(itemsWithDetail, totalPrice);;
    }
    private int calculateTotal(List<SingleItem> itemsWithDetail){
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
            result = result.concat(String.format("Name: %s , Quantity: %d, Unit price: %d (yuan), Subtotal: %d (yuan)\n",
                    itemInfo.getName(),itemInfo.getQuantity(),itemInfo.getUnitPrice(),itemInfo.getSubTotal()));
        }
        result = result.concat(String.format("----------------------\nTotal:  %d(yuan)\n**********************",totalPrice));
        return result;
    }

    private List<String> uniqueBarcode(List<String> barcodes){
        return barcodes.stream()
                .distinct()
                .collect(Collectors.toList());
    }
    private List<SingleItem> convertToItems(List<String> barcodes) {
        List<ItemInfo> itemDetailList = loadAllItemsInfo();
        List<SingleItem> itemList = new ArrayList<>();
        List<String> uniqueBarcode = uniqueBarcode(barcodes);
        for (String barcode : uniqueBarcode) {
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