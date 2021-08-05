package br.com.brito.cliente.escola.gradecurricular.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CursoDto extends RepresentationModel<CursoDto> {

    private Long id;

    @NotBlank(message = "Informe o nome da materia corretamente.")
    private String name;

    @Min(value = 34, message = "Permitido o mínimo de 34 horas nessa matéria.")
    @Max(value = 50, message = "Permitido o máximo de 50 horas nessa matéria.")
    private int hours;

    @NotBlank(message = "Informe o código da materia corretamente.")
    private String code;

}
