package br.com.brito.cliente.escola.gradecurricular.constante;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HyperLinkConstant {

    ATUALIZAR("UPDATE"),
    EXCLUIR("DELETE"),
    LISTAR("GET_ALL"),
    CONSULTAR("GET");

    private final String valor;
}
