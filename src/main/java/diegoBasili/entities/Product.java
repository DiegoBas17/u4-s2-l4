package diegoBasili.entities;

import java.util.Random;

public class Product {
    private Long id;
    private String name;
    private String category;
    private Double price;

    /*COSTRUTTORI*/
    public Product(String name, String category, Double price) {
        Random random = new Random();
        this.id = random.nextLong(1000000000);
        this.name = name;
        this.category = category;
        this.price = price;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double setDiscount() {
        return this.price = this.price * 0.10;
    }


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }
}
