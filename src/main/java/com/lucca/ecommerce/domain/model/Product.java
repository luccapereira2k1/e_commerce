package com.lucca.ecommerce.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.UUID;

// Define que esta classe é uma tabela no banco de dados PostgreSQL
@Entity
@Table(name = "products")
public class Product {

    // Para primary key utilizei UUID para IDs únicos e aleatórios (Segurança)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // Tornei o nome obrigatório tanto no Java (@NotBlank) quanto no SQL (nullable = false)
    @NotBlank
    @Column(nullable = false)
    private String name;

    // Para o preço utilizei BigDecimal para precisão financeira (evita erro de centavos)
    @NotNull
    @PositiveOrZero // Impede que o preço seja negativo na entrada de dados
    @Column(nullable = false)
    private BigDecimal price;

    // Para o estoque mapeiei com nome técnico no banco (snake_case)
    @NotNull
    @PositiveOrZero
    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    // Construtor padrão exigido pelo JPA
    public Product() {
    }

    // --- MÉTODOS GETTERS E SETTERS (Controle Manual de Acesso) ---
    // Apesar de poder ser feito via Lombock, eu optei por fazer manualmente para praticar

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}