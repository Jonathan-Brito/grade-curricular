package br.com.brito.cliente.escola.gradecurricular.handler;

import br.com.brito.cliente.escola.gradecurricular.domain.model.ErrorResponse;
import br.com.brito.cliente.escola.gradecurricular.domain.model.ErrorResponse.ErrorResponseBuilder;
import br.com.brito.cliente.escola.gradecurricular.exception.MateriaException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerHandler {

    @ExceptionHandler(MateriaException.class)
    public ResponseEntity<ErrorResponse> handlerMateriaException(MateriaException materiaException){
        ErrorResponseBuilder error = ErrorResponse.builder();

        error.httpStatus(materiaException.getHttpStatus().value());
        error.message(materiaException.getMessage());
        error.timeStamp(System.currentTimeMillis());

        return ResponseEntity.status(materiaException.getHttpStatus()).body(error.build());
    }
}
