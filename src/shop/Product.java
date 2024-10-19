package shop;

import java.io.Serializable;

public class Product implements Serializable{

    private String id;
    private String title;
    private Integer price;
    private Integer rating;

    

    public Product() {

    }



    public Product(String id, String title, Integer price, Integer rating) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.rating = rating;
    }



    public String getId() {
        return id;
    }



    public void setId(String id) {
        this.id = id;
    }



    public String getTitle() {
        return title;
    }



    public void setTitle(String title) {
        this.title = title;
    }



    public Integer getPrice() {
        return price;
    }



    public void setPrice(Integer price) {
        this.price = price;
    }



    public Integer getRating() {
        return rating;
    }



    public void setRating(Integer rating) {
        this.rating = rating;
    }


    // need to override the toString method so that when you print out the ArrayList of <Product>
    // it will print out the items and not the address in memory
    @Override
    public String toString() {
        return "Product [id:" + id + ", title:" + title + ", price:" + price + ", rating:" + rating + "]";
    }

    

    

    

    

    
    
}
