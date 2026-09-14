package com.saucedemo.repository;

import com.saucedemo.model.CartItem;
import com.saucedemo.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CartItemRepositoryTest {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        product = productRepository.save(new Product("mochila", "Mochila Urban Trek", "desc", 100.0, "img"));
    }

    @Test
    void findBySessionId_returnsOnlyItemsOfThatSession() {
        cartItemRepository.save(new CartItem("session-1", product, 1));
        cartItemRepository.save(new CartItem("session-2", product, 5));

        List<CartItem> result = cartItemRepository.findBySessionId("session-1");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getSessionId()).isEqualTo("session-1");
        assertThat(result.get(0).getQuantity()).isEqualTo(1);
    }

    @Test
    void findBySessionId_whenNoItems_returnsEmptyList() {
        assertThat(cartItemRepository.findBySessionId("session-x")).isEmpty();
    }

    @Test
    void findBySessionIdAndProductId_whenMatch_returnsItem() {
        cartItemRepository.save(new CartItem("session-1", product, 2));

        Optional<CartItem> found = cartItemRepository.findBySessionIdAndProductId("session-1", product.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getQuantity()).isEqualTo(2);
        assertThat(found.get().getProduct().getId()).isEqualTo(product.getId());
    }

    @Test
    void findBySessionIdAndProductId_whenNoMatch_returnsEmpty() {
        assertThat(cartItemRepository.findBySessionIdAndProductId("session-1", product.getId())).isEmpty();
    }
}
