package com.lucca.ecommerce.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucca.ecommerce.Domain.Model.Product;

import java.util.UUID;

/**
 * Interface de acesso a dados para a entidade Product.
 * * Ao estender JpaRepository, o Spring Data JPA gera automaticamente as 
 * implementações de métodos CRUD (Create, Read, Update, Delete).
 * * @param Product O tipo da entidade que este repositório gerencia.
 * @param UUID O tipo da chave primária (ID) da entidade.
 */


@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>{
    // Métodos customizados podem ser adicionados aqui caso haja necessidade.
}
