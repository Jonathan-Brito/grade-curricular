package br.com.brito.cliente.escola.gradecurricular.handler;

import br.com.brito.cliente.escola.gradecurricular.exception.MateriaException;
import br.com.brito.cliente.escola.gradecurricular.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Map<String, String>>> MethodArgumentNotValidException(MethodArgumentNotValidException materiaException){

        Map<String, String> erros = new HashMap<>();

        materiaException.getBindingResult().getAllErrors().forEach(erro ->{
            String campo = ((FieldError)erro).getField();
            String message = erro.getDefaultMessage();
            erros.put(campo, message);
        });

        Response<Map<String, String>> response = new Response<>();
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setData(erros);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MateriaException.class)
    public ResponseEntity<Response<String>> handlerMateriaException(MateriaException materiaException){
        Response<String> response = new Response<>();

        response.setStatusCode(materiaException.getHttpStatus().value());
        response.setData(materiaException.getMessage());


        return ResponseEntity.status(materiaException.getHttpStatus()).body(response);
    }
}
