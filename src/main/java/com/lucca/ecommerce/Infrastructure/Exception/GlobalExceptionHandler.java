package com.lucca.ecommerce.Infrastructure.Exception;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

/**
 * Centralizador de exceções da API.
 * A anotação @RestControllerAdvice permite interceptar erros em todos os @RestControllers
 * do projeto, fornecendo uma resposta padronizada para o cliente.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura erros de validação de dados (Bean Validation).
     * Disparado quando o Spring encontra inconsistências em objetos anotados com @Valid.
     * * @param ex Exceção contendo a lista de campos que falharam na validação.
     * @return Um Map contendo o nome do campo e a respectiva mensagem de erro, 
     * acompanhado do status HTTP 400 (Bad Request).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        // Extrai a lista detalhada de todos os erros encontrados pelo Spring
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        Map<String, String> errorMap = new HashMap<>();

        // Itera sobre a lista de erros para mapear Campo -> Mensagem Amigável
        for (FieldError wrong : errors) {
            errorMap.put(wrong.getField(), wrong.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errorMap);
    }

    /**
     * Captura exceções de status HTTP disparadas manualmente no código.
     * Utilizado principalmente para tratar recursos não encontrados (404 Not Found).
     * * @param ex Exceção que carrega o status HTTP e a mensagem de motivo (reason).
     * @return Uma resposta com o status definido na origem do erro e a mensagem explicativa.
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleNotFound(ResponseStatusException ex) {
        // Retorna o status dinâmico (ex: 404) e o motivo definido no Controller
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
    }
}