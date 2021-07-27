package br.com.brito.cliente.escola.gradecurricular.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class MateriaDto {

    private Long id;

    @NotBlank(message = "Informe o nome da materia corretamente.")
    private String name;

    @Min(value = 34, message = "Permitido o mínimo de 34 horas nessa matéria.")
    @Max(value = 50, message = "Permitido o máximo de 50 horas nessa matéria.")
    private int hours;

    @NotBlank(message = "Informe o código da materia corretamente.")
    private String code;

    @Min(value = 1, message = "Permitido o mínimo de 1 vez ao ano.")
    @Max(value = 2, message = "Permitido o máximo de 2 vezes ao ano.")
    private int frequency;
}
