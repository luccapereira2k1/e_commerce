package com.lucca.ecommerce.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.lucca.ecommerce.Repository.ProductRepository;
import com.lucca.ecommerce.domain.model.Product;

import jakarta.annotation.PostConstruct;

import com.lucca.ecommerce.Repository.ProductRepository;

@RestController // Essa anotação combina o "@Controller" e o "@ResponseBody". Isso significa que ela diz ao spring que os metodos retornarão um JSON diretamente na resposta HTTP.
@RequestMapping("/products") // Define a rota, ou seja tudo que fizermos irá começar com "http://localhost:9090/products".
public class ProductController {
    // Essa classe é o ponto de entrada da API, para gerenciar os recursos de produtos.

    // Estamos fazendo uma injeção de dependência via Spring. Pois não se usa "new ProductRepository()" em Spring.
    private final ProductRepository repository;

    // Mais seguro que o "Autowired", pois podemos deixar o atributo como "final" sendo assim imutavel.
    public ProductController(ProductRepository repository){ this.repository = repository; }

    /**
     * Endpoint para listagem de produtos.
     * Realiza uma operação de leitura (GET) para retornar todos os registros 
     * da tabela de produtos convertidos em formato JSON.
     */
    @GetMapping 
    public List<Product> listAll(){
        return repository.findAll();

    }

    /**
     * @RequestBody está dentro dos parametros para sinalizar que estará recebendo um JSON no corpo da requisição, e irá preencher um objeto da classe Product.
     * repository.save(product) sinaliza que estará salvando com o objeto que acabou de receber.
     * por baixo dos panos o JPA fará a seguinte ação no banco de dados "INSERT INTO products"
     * @return é uma boa pratica no padrão REST, retornarmos o produto foi criado para o usuario. 
     */
    
    @PostMapping
    public Product save(@RequestBody Product product){
        return repository.save(product);
    }

    /**
     * @GetMapping As chaves indicam que aquele trecho da URL é um Template Variable. O Spring entende que qualquer valor digitado após /products/ deve ser tratado como o parâmetro id do meu método. 
     * @PathVariable server para o spring pegar o ID da URL e colocar dentro do metodo, por isso está nos parametros.
     * @return a ideia de utilizar o Optional<Product> é para caso o usuario digite um id inexistente ele não irá retornar o produto direto.
     */
    @GetMapping("/{id}")
    public Optional<Product> searchId(@PathVariable UUID id){
        return repository.findById(id);
    }

    /**
     * Remove um produto do banco de dados.
     * @DeleteMapping indica uma operação de exclusão.
     * @param id O identificador único (UUID) do produto a ser removido.
     * Retorna o status 204 (No Content) após a execução bem-sucedida.
     */

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Para retornar 204, pois o método é void, não retorna nada. Sendo no padrão REST, quando não retorna nada mas foi bem sucedido devemos retornar 204 No Content.
    public void delete(@PathVariable UUID id){
        repository.deleteById(id);
    }

    /**
     * Atualiza integralmente os dados de um produto existente.
     * * @param id O ID extraído da URL para garantir a integridade da operação.
     * @param product Os novos dados do produto recebidos no corpo da requisição.
     * @return O produto atualizado e persistido no banco de dados.
     * * Nota: O método save() do JPA realiza um "merge". Se o objeto contiver um ID
     * já presente no banco, o Spring Data JPA executa um UPDATE em vez de um INSERT.
     */
    @PutMapping("/{id}")
    public Product update(@PathVariable UUID id, @RequestBody Product product) {
        product.setId(id); // Garante que o ID da URL tenha precedência sobre o ID do corpo (segurança).
        return repository.save(product);
    }

}
