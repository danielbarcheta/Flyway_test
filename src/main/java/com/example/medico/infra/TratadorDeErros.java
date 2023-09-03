package com.example.medico.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {


    // MOTIVAÇÃO: ao consultar um registro, a API retornava erro 500(server error) e o correto seria erro 404!
    @ExceptionHandler(EntityNotFoundException.class) //Indica para qual erro esse método deve ser chamado

    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex){ //Devemos receber a Exception como parametro para enviar ao cliente qual campo foi enviado errado
        var erros = ex.getFieldErrors();

        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }



    //Vamos crar um DTO aqui mesmo

    public record DadosErroValidacao(String campo, String mensagem) {

        public DadosErroValidacao(FieldError erro){
           this(erro.getField(), erro.getDefaultMessage());
        }


    }


}
