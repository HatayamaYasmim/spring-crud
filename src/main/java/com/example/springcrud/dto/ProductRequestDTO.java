package com.example.springcrud.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequestDTO (

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 150, message = "Nome deve ter até 150 caracteres")
    String name,

    @Size(max = 500, message = "Descrição deve ter até 500 caracteres")
    String description,

    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    @Digits(integer = 8, fraction = 2)
    BigDecimal price,

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 0, message = "Quantidade não pode ser negativa")
    Integer quantity
) {}
