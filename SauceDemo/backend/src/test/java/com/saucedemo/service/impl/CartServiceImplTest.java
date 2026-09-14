package com.saucedemo.service.impl;

import com.saucedemo.model.CartItem;
import com.saucedemo.model.Product;
import com.saucedemo.repository.CartItemRepository;
import com.saucedemo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("mochila", "Mochila Urban Trek", "desc", 100.0, "img");
        product.setId(1L);
    }

    @Test
    void getCart_returnsItemsForSession() {
        CartItem item = new CartItem("session-1", product, 2);
        when(cartItemRepository.findBySessionId("session-1")).thenReturn(List.of(item));

        List<CartItem> result = cartService.getCart("session-1");

        assertThat(result).containsExactly(item);
        verify(cartItemRepository).findBySessionId("session-1");
    }

    @Test
    void addToCart_whenProductDoesNotExist_throwsAndDoesNotSave() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cartService.addToCart("session-1", 1L, 1))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("1");
        verify(cartItemRepository, never()).save(any());
    }

    @Test
    void addToCart_whenItemIsNotInCart_createsNewItem() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(cartItemRepository.findBySessionIdAndProductId("session-1", 1L)).thenReturn(Optional.empty());
        when(cartItemRepository.save(any(CartItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CartItem result = cartService.addToCart("session-1", 1L, 3);

        ArgumentCaptor<CartItem> captor = ArgumentCaptor.forClass(CartItem.class);
        verify(cartItemRepository).save(captor.capture());
        CartItem saved = captor.getValue();
        assertThat(saved.getSessionId()).isEqualTo("session-1");
        assertThat(saved.getProduct()).isSameAs(product);
        assertThat(saved.getQuantity()).isEqualTo(3);
        assertThat(result).isSameAs(saved);
    }

    @Test
    void addToCart_whenItemAlreadyInCart_accumulatesQuantity() {
        CartItem existing = new CartItem("session-1", product, 2);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(cartItemRepository.findBySessionIdAndProductId("session-1", 1L)).thenReturn(Optional.of(existing));
        when(cartItemRepository.save(existing)).thenReturn(existing);

        CartItem result = cartService.addToCart("session-1", 1L, 5);

        assertThat(result).isSameAs(existing);
        assertThat(result.getQuantity()).isEqualTo(7);
        verify(cartItemRepository).save(existing);
    }

    @Test
    void updateQuantity_whenItemDoesNotExist_throwsAndDoesNotSave() {
        when(cartItemRepository.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cartService.updateQuantity(10L, 4))
                .isInstanceOf(NoSuchElementException.class);
        verify(cartItemRepository, never()).save(any());
    }

    @Test
    void updateQuantity_whenItemExists_updatesAndSaves() {
        CartItem existing = new CartItem("session-1", product, 2);
        when(cartItemRepository.findById(10L)).thenReturn(Optional.of(existing));
        when(cartItemRepository.save(existing)).thenReturn(existing);

        CartItem result = cartService.updateQuantity(10L, 9);

        assertThat(result.getQuantity()).isEqualTo(9);
        verify(cartItemRepository).save(existing);
    }

    @Test
    void removeItem_delegatesToRepository() {
        cartService.removeItem(10L);

        verify(cartItemRepository).deleteById(10L);
    }
}
