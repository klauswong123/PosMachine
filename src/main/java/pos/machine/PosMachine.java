package pos.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<SingleItem> itemsWithDetail = convertToItems(barcodes);
        int totalPrice = calculateTotal(itemsWithDetail);
        System.out.println(totalPrice);
        return null;
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