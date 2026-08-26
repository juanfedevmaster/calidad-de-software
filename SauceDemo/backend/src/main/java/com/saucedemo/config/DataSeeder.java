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
                new Product("mochila", "Mochila resistente para el dia a dia.", 119900.0, "Images/mochila.png"),
                new Product("lampara", "Luz LED para bicicleta.", 39900.0, "Images/lampara.png"),
                new Product("camiseta", "Camiseta de algodon 100%.", 59900.0, "Images/camiseta.png"),
                new Product("chaqueta", "Chaqueta abrigada para clima frio.", 189900.0, "Images/chaqueta.png"),
                new Product("enterizo", "Enterizo comodo para dormir.", 79900.0, "Images/enterizo.jpg"),
                new Product("camiseta-especial", "Camiseta edicion especial color rojo.", 65900.0, "Images/camiseta-especial.jpg"),
                new Product("gafas", "Gafas de sol con proteccion UV.", 49900.0, "Images/gafas.jpg"),
                new Product("gorra", "Gorra ajustable de algodon.", 34900.0, "Images/gorra.jpg"),
                new Product("botella", "Botella termica de 750ml.", 44900.0, "Images/botella.jpg"),
                new Product("medias", "Pack de 3 pares de medias.", 24900.0, "Images/medias.jpg"),
                new Product("buso", "Buso con capota, ideal para clima templado.", 149900.0, "Images/buso.jpg"),
                new Product("bolsa", "Bolsa de tela reutilizable.", 39900.0, "Images/bolsa.jpg")
        );

        catalog.forEach(product -> {
            productRepository.findByName(product.getName()).ifPresentOrElse(
                    existing -> {
                        existing.setImageUrl(product.getImageUrl());
                        existing.setDescription(product.getDescription());
                        existing.setPrice(product.getPrice());
                        productRepository.save(existing);
                    },
                    () -> productRepository.save(product)
            );
        });
    }
}
