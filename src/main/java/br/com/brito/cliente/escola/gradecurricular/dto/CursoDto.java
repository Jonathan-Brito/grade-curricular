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

    @NotBlank(message = "Informe o nome do curso corretamente.")
    private String nome;

    @NotBlank(message = "Informe o código do curso corretamente.")
    private String codCurso;

}
