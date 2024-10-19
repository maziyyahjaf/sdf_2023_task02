package shop;


import static shop.LoadingData.initProductInfo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException {

    
        int budget = 400;

        initProductInfo("data/products.csv");
        ListProductInfo lpi = new ListProductInfo();
        int item_count = lpi.determineItemCount(); 
        System.out.println(item_count);
        Product[] sendToUser = lpi.infoSendToUser(item_count);
        
        RespondToServer rts = new RespondToServer(sendToUser, budget);
        Product[] sorted = rts.rankingProducts();
        List<Product> productList = rts.selectingProductsFromSorted(sorted, budget);
        rts.listingProductId(productList);
        rts.spent(productList);
        rts.remaining();
        



    
    }
    
}
