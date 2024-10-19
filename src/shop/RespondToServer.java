package shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class RespondToServer {

    private Integer budget;
    private Product[] products;
    private List<Product> selectedProducts;
    private List<String> selectedProductId;
    private Integer totalSpent;
    private Integer remainingBudget;

    public RespondToServer(Product[] sendToUser, Integer budget) {
        this.products = sendToUser;
        this.budget = budget;
        this.selectedProducts = new ArrayList<>();
        this.selectedProductId = new ArrayList<>();
        this.totalSpent = 0;
        this.remainingBudget = budget;

    }

    public Product[] rankingProducts() {
        Product[] sortedProducts = Arrays.stream(products)
                .sorted(Comparator.comparingInt(Product::getRating).reversed() // Sort by rating (descending)
                        .thenComparing(Comparator.comparingInt(Product::getPrice).reversed())) // Sort by
                                                                                               // price(descending)
                .toArray(Product[]::new);

        Arrays.stream(sortedProducts).forEach(System.out::println);

        return sortedProducts;
    }

    public List<Product> selectingProductsFromSorted(Product[] sortedProducts, Integer budget) {

        // check if sortedProducts is null or empty
        if (sortedProducts == null || sortedProducts.length == 0) {
            System.out.println("No products available to select.");
            return new ArrayList<>(); // return empty list if no products
        }

        Integer remainingBudget = budget;

        System.out.println("budget before selecting:" + budget);

        for (Product product : sortedProducts) {
            if (product.getPrice() > remainingBudget) {
                continue;
            } else {
                selectedProducts.add(product);
                remainingBudget -= product.getPrice();
            }

        }

        System.out.println("budget after selecting:" + remainingBudget);
        System.out.println("selected products: " + selectedProducts);

        // Update class variables for totalSpent and remaining budget
        this.totalSpent = budget - remainingBudget;
        this.remainingBudget = remainingBudget;

        return selectedProducts;

    }

    public List<String> listingProductId(List<Product> selectedProducts) {

        selectedProductId.clear();  // Clear any previous entries

        for (Product product : selectedProducts) {
            selectedProductId.add(product.getId());
        }

        System.out.println(selectedProductId);

        return selectedProductId;
    }

    public Integer spent(List<Product> selectedProducts) {

        totalSpent = 0; // Reset total spent before calculating

        for (Product product : selectedProducts) {
            totalSpent += product.getPrice();
        }

        System.out.println("spent: " + totalSpent);
        return totalSpent;
    }

    public Integer remaining() {
        return remainingBudget;
    }

}

