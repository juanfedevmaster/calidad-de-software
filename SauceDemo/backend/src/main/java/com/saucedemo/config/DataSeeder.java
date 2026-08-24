package com.saucedemo.config;

import com.saucedemo.model.Product;
import com.saucedemo.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DataSeeder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        List<Product> catalog = List.of(
                new Product("Sauce Labs Backpack", "Mochila resistente para el dia a dia.", 119900.0, "backpack.jpg"),
                new Product("Sauce Labs Bike Light", "Luz LED para bicicleta.", 39900.0, "bike-light.jpg"),
                new Product("Sauce Labs Bolt T-Shirt", "Camiseta de algodon 100%.", 59900.0, "bolt-shirt.jpg"),
                new Product("Sauce Labs Fleece Jacket", "Chaqueta abrigada para clima frio.", 189900.0, "fleece-jacket.jpg"),
                new Product("Sauce Labs Onesie", "Enterizo comodo para dormir.", 79900.0, "onesie.jpg"),
                new Product("Test.allTheThings() T-Shirt (Red)", "Camiseta edicion especial color rojo.", 65900.0, "red-shirt.jpg"),
                new Product("Sauce Labs Sunglasses", "Gafas de sol con proteccion UV.", 49900.0, "sunglasses.jpg"),
                new Product("Sauce Labs Cap", "Gorra ajustable de algodon.", 34900.0, "cap.jpg"),
                new Product("Sauce Labs Water Bottle", "Botella termica de 750ml.", 44900.0, "water-bottle.jpg"),
                new Product("Sauce Labs Socks", "Pack de 3 pares de medias.", 24900.0, "socks.jpg"),
                new Product("Sauce Labs Hoodie", "Buso con capota, ideal para clima templado.", 149900.0, "hoodie.jpg"),
                new Product("Sauce Labs Tote Bag", "Bolsa de tela reutilizable.", 39900.0, "tote-bag.jpg")
        );

        catalog.forEach(product -> {
            if (productRepository.findByName(product.getName()).isEmpty()) {
                productRepository.save(product);
            }
        });
    }
}
