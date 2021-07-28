package br.com.brito.cliente.escola.gradecurricular.handler;

import br.com.brito.cliente.escola.gradecurricular.model.ErrorMapResponse;
import br.com.brito.cliente.escola.gradecurricular.model.ErrorResponse;
import br.com.brito.cliente.escola.gradecurricular.model.ErrorMapResponse.ErrorMapResponseBuilder;
import br.com.brito.cliente.escola.gradecurricular.model.ErrorResponse.ErrorResponseBuilder;
import br.com.brito.cliente.escola.gradecurricular.exception.MateriaException;
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
    public ResponseEntity<ErrorMapResponse> MethodArgumentNotValidException(MethodArgumentNotValidException materiaException){
        ErrorResponseBuilder error = ErrorResponse.builder();

        Map<String, String> erros = new HashMap<>();

        materiaException.getBindingResult().getAllErrors().forEach(erro ->{
            String campo = ((FieldError)erro).getField();
            String message = erro.getDefaultMessage();
            erros.put(campo, message);
        });

        ErrorMapResponseBuilder errorMap = ErrorMapResponse.builder();
        errorMap.erros(erros)
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .timeStamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap.build());
    }

    @ExceptionHandler(MateriaException.class)
    public ResponseEntity<ErrorResponse> handlerMateriaException(MateriaException materiaException){
        ErrorResponseBuilder error = ErrorResponse.builder();

        error.httpStatus(materiaException.getHttpStatus().value());
        error.message(materiaException.getMessage());
        error.timeStamp(System.currentTimeMillis());

        return ResponseEntity.status(materiaException.getHttpStatus()).body(error.build());
    }
}
