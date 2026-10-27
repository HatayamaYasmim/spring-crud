package com.example.springcrud.service;

import com.example.springcrud.entity.Product;
import com.example.springcrud.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

private final ProductRepository repository;

public ProductService(ProductRepository repository) {
    this.repository = repository;
}

@Transactional
    public Product create(Product product) {
    return repository.save(product);
}

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
    return repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));
}



}