package com.saucedemo.repository;

import com.saucedemo.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findByCode_whenMatch_returnsProduct() {
        productRepository.save(new Product("mochila", "Mochila Urban Trek", "desc", 100.0, "img"));
        productRepository.save(new Product("gorra", "Gorra Classic Fit", "desc", 50.0, "img"));

        Optional<Product> found = productRepository.findByCode("mochila");

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Mochila Urban Trek");
    }

    @Test
    void findByCode_whenNoMatch_returnsEmpty() {
        productRepository.save(new Product("mochila", "Mochila Urban Trek", "desc", 100.0, "img"));

        assertThat(productRepository.findByCode("no-existe")).isEmpty();
    }

    @Test
    void findByName_whenMatch_returnsProduct() {
        productRepository.save(new Product("mochila", "Mochila Urban Trek", "desc", 100.0, "img"));

        Optional<Product> found = productRepository.findByName("Mochila Urban Trek");

        assertThat(found).isPresent();
        assertThat(found.get().getCode()).isEqualTo("mochila");
    }

    @Test
    void findByName_whenNoMatch_returnsEmpty() {
        assertThat(productRepository.findByName("Inexistente")).isEmpty();
    }
}
