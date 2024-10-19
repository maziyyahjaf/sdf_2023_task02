package shop;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class LoadingData {

    private static List<Product> productList;
    

    public LoadingData() {
       
    }

    public static void initProductInfo(String fileName) {
        try {
            productList = loading(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List<Product> loading(String fileName) throws FileNotFoundException, IOException {
        
        productList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            
            String currentLine;
            boolean first = true;
            while ((currentLine = br.readLine()) != null) {
                String[] detailed = currentLine.split(",");
                if (first) {
                    first = false;
                    continue;
                }
                
                Product product = new Product();
                product.setId(detailed[0]);
                product.setTitle(detailed[1]);
                product.setPrice(Integer.parseInt(detailed[2]));
                product.setRating(Integer.parseInt(detailed[3]));
                productList.add(product);

            }

        } catch ( IllegalArgumentException | IOException ioe) {
            ioe.printStackTrace();
        }

        // for (Product productInList : productList) {
        //     System.out.println(productInList);
        // }

      
        // System.out.println(productList);
    
        return productList;
    
    }

}
