package shop;

import java.io.FileNotFoundException;
import java.io.IOException;
//import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class ListProductInfo {

    private Integer randomItemCount;
    private List<Product> productList;
    private Product[] sendToUser;
    private String fileName;
   
    public ListProductInfo() throws FileNotFoundException, IOException {
        fileName = "data/products.csv";
        productList = LoadingData.loading(fileName);
    }

    public Integer determineItemCount() throws FileNotFoundException, IOException {

        Random random = new Random();
        // need to make sure the random number of items to show is not more than
        // the number of items we have in the arraylist/db of products
        // how to pull the info from the static arrayList of products in here?

         // Ensure randomItemCount is between 1 and productList.size()
        randomItemCount = random.nextInt(productList.size()) + 1;
        // how to ensure randomItemCount cannot be 0?
        // random.nextInt(productList.size()): This generates a random integer between 0 (inclusive) and productList.size() (exclusive).
        // + 1: By adding 1, you shift the range from 0 to size-1 (default behavior) to 1 to size. This ensures that randomItemCount is always at least 1.
       
        infoSendToUser(randomItemCount);
        System.out.println("Random item count:" + randomItemCount);
        
        return randomItemCount;
        
    }

    public Product[] infoSendToUser(Integer randomItemCount) throws FileNotFoundException, IOException {

        sendToUser = new Product[randomItemCount];
        // now I have fixed array -> size depends on the randomly generated number based on
        // product arraylist size
        // now I need to populate the array with products randomly from the arraylist of products

        Random randomItem = new Random();
        
        for (int i = 0; i < sendToUser.length; i++) {
            Integer randomItemIndex = randomItem.nextInt(productList.size());
            sendToUser[i] = productList.get(randomItemIndex);
        }
        
        // System.out.println(Arrays.toString(sendToUser));
        return sendToUser;
        
    }

    public Integer getRandomItemCount() {
        return randomItemCount;
    }

    public Product[] getSendToUser() {
        return sendToUser;
    }

    

    


    
    
}
