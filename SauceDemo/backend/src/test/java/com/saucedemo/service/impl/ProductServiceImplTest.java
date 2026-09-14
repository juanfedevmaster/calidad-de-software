package com.saucedemo.service.impl;

import com.saucedemo.model.Product;
import com.saucedemo.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void getAllProducts_returnsRepositoryResult() {
        List<Product> products = List.of(
                new Product("mochila", "Mochila Urban Trek", "desc", 100.0, "img"),
                new Product("gorra", "Gorra Classic Fit", "desc", 50.0, "img"));
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertThat(result).isSameAs(products);
        verify(productRepository).findAll();
    }

    @Test
    void getProductById_whenExists_returnsProduct() {
        Product product = new Product("mochila", "Mochila Urban Trek", "desc", 100.0, "img");
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProductById(1L);

        assertThat(result).containsSame(product);
        verify(productRepository).findById(1L);
    }

    @Test
    void getProductById_whenNotExists_returnsEmpty() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Product> result = productService.getProductById(99L);

        assertThat(result).isEmpty();
        verify(productRepository).findById(99L);
    }
}
