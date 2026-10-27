package com.example.springcrud.service;

import com.example.springcrud.entity.Product;
import com.example.springcrud.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @Test
    void shouldCreateProductSuccessfully() {
        Product product = new Product(
                null,
                "Notebook Acer",
                "Notebook gamer",
                new BigDecimal("8500.00"),
                10
        );

        Product savedProduct = new Product(
                1L,
                "Notebook Acer",
                "Notebook gamer",
                new BigDecimal("8500.00"),
                10
        );

        when(repository.save(product))
                .thenReturn(savedProduct);

        Product result = service.create(product);

        assertNotNull(result.getId());
        assertEquals(1L, result.getId());
        assertEquals("Notebook Acer", result.getName());

        verify(repository, times(1)).save(product);
    }

    @Test
    void shouldFindAllProducts(){
        Product product = new Product(
                1L,
                "Notebook Acer",
                "Notebook gamer",
                new BigDecimal("8500.00"),
                10
        );

        when(repository.findAll())
                .thenReturn(List.of(product));

        List<Product> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("Notebook Acer", result.get(0).getName());

        verify(repository).findAll();

    }

    @Test
    void shouldFindProductById() {

        Product product = new Product(
                1L,
                "Notebook Acer",
                "Notebook gamer",
                new BigDecimal("8500.00"),
                10
        );

        when(repository.findById(1L))
                .thenReturn(Optional.of(product));

        Product result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(repository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.findById(99L)
        );

        verify(repository).findById(99L);
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        Long productId = 1L;

        Product existingProduct = new Product(
                productId,
                "Notebook Acer",
                "Notebook gamer",
                new BigDecimal("8500.00"),
                10
        );

        Product updatedData = new Product(
                null,
                "Notebook Dell",
                "Notebook corporativo",
                new BigDecimal("6500.00"),
                5
        );

        when(repository.findById(productId))
                .thenReturn(Optional.of(existingProduct));

        when(repository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Product result = service.update(productId, updatedData);
        assertEquals(productId, result.getId());
        assertEquals("Notebook Dell", result.getName());
        assertEquals("Notebook corporativo", result.getDescription());
        assertEquals(new BigDecimal("6500.00"), result.getPrice());
        assertEquals(5, result.getQuantity());

        verify(repository).findById(productId);
        verify(repository).save(existingProduct);

    }


}
