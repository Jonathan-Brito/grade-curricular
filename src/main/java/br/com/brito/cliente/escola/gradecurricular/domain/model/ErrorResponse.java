package br.com.brito.cliente.escola.gradecurricular.domain.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {

    private String message;
    private int httpStatus;
    private long timeStamp;
}
