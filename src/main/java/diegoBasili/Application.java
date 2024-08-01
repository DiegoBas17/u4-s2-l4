package diegoBasili;

import com.github.javafaker.Faker;
import diegoBasili.entities.Customer;
import diegoBasili.entities.Order;
import diegoBasili.entities.Product;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {
        Random random = new Random();
        Supplier<Product> productSupplierBook = () -> {
            Faker f = new Faker(Locale.ITALY);
            return new Product(f.book().title(), "Books", random.nextDouble(0, 1000));
        };
        Supplier<Product> productSupplierBaby = () -> {
            Faker f = new Faker(Locale.ITALY);
            return new Product(f.funnyName().name(), "Baby", random.nextDouble(0, 1000));
        };
        Supplier<Product> productSupplierBoys = () -> {
            Faker f = new Faker(Locale.ITALY);
            return new Product(f.pokemon().name(), "Boys", random.nextDouble(0, 1000));
        };

        Customer gabriel = new Customer("Gabriel", 1);
        Customer arianna = new Customer("Arianna", 2);
        Customer eddy = new Customer("Eddy", 1);
        List<Product> productsGabriel = new ArrayList<>();
        for (int i = 0; i < random.nextInt(0, 10); i++) {
            productsGabriel.add(productSupplierBook.get());
            productsGabriel.add(productSupplierBoys.get());
        }
        List<Product> productsArianna = new ArrayList<>();
        for (int i = 0; i < random.nextInt(0, 10); i++) {
            productsArianna.add(productSupplierBook.get());
            productsArianna.add(productSupplierBaby.get());
        }
        List<Product> productsEddy = new ArrayList<>();
        for (int i = 0; i < random.nextInt(0, 10); i++) {
            productsEddy.add(productSupplierBaby.get());
            productsEddy.add(productSupplierBook.get());
            productsEddy.add(productSupplierBoys.get());
        }
        Order gabrielShop = new Order("inviato", productsGabriel, gabriel);
        Order gabrielShop1 = new Order("inviato", productsGabriel, gabriel);
        Order gabrielShop2 = new Order("inviato", productsGabriel, gabriel);
        Order ariannaShop = new Order("inviato", productsArianna, arianna);
        Order ariannaShop1 = new Order("inviato", productsArianna, arianna);
        Order ariannaShop2 = new Order("inviato", productsArianna, arianna);
        Order eddyShop = new Order("inviato", productsEddy, eddy);
        Order eddyShop1 = new Order("inviato", productsEddy, eddy);
        Order eddyShop2 = new Order("inviato", productsEddy, eddy);

        List<Order> totalOrdersList = new ArrayList<>();
        totalOrdersList.add(gabrielShop);
        totalOrdersList.add(ariannaShop);
        totalOrdersList.add(eddyShop);
        totalOrdersList.add(gabrielShop1);
        totalOrdersList.add(gabrielShop2);
        totalOrdersList.add(ariannaShop1);
        totalOrdersList.add(ariannaShop2);
        totalOrdersList.add(eddyShop1);
        totalOrdersList.add(eddyShop2);

        System.out.println("-----------------------------------------------------es1-------------------------------------------");
        Map<Customer, List<Order>> listaOrdiniPerCliente = totalOrdersList.stream().collect(Collectors.groupingBy(Order::getCustomer));
        listaOrdiniPerCliente.forEach((customer, orders) -> System.out.println("Cliente: " + customer.getName() + " : " + orders));

        System.out.println("-----------------------------------------------------es2-------------------------------------------");
        Map<Customer, Double> totaleVedite = totalOrdersList.stream().collect(Collectors.groupingBy(Order::getCustomer, Collectors.summingDouble(order -> order.getProducts().stream()
                        .mapToDouble(Product::getPrice)
                        .sum()
                )
        ));
        totaleVedite.forEach((costumer, totaleacquistato) -> System.out.println("Cliente: " + costumer.getName() + " : " + totaleacquistato));

        System.out.println("-----------------------------------------------------es3-------------------------------------------");
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            products.add(productSupplierBaby.get());
            products.add(productSupplierBook.get());
            products.add(productSupplierBoys.get());
        }
        List<Product> prodottiCostosi = products.stream().sorted(Comparator.comparingDouble(Product::getPrice)).limit(3).toList();
        prodottiCostosi.forEach(System.out::println);
        salvaProdottiSuDisco(prodottiCostosi);

        System.out.println("-----------------------------------------------------es4-------------------------------------------");
        OptionalDouble mediaValoreOrdini = totalOrdersList.stream()
                .mapToDouble(order -> order.getProducts().stream()
                        .mapToDouble(Product::getPrice)
                        .sum()
                )
                .average();
        System.out.println("La media dei prodotti è: " + mediaValoreOrdini);

        System.out.println("-----------------------------------------------------es5-------------------------------------------");
        Map<String, Double> sommaValoreProdottiPerCategoria = products.stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.summingDouble(Product::getPrice)));
        sommaValoreProdottiPerCategoria.forEach((category, totalValue) -> System.out.println(category + ": " + Math.round(totalValue * 100.0) / 100.0));
    }

    public static void salvaProdottiSuDisco(List<Product> listaProdotti) {
        File file = new File("src/info.txt");
        try {
            FileUtils.writeStringToFile(file, listaProdotti.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
