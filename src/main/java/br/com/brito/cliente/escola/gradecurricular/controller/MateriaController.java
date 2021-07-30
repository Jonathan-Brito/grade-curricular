package br.com.brito.cliente.escola.gradecurricular.controller;

import br.com.brito.cliente.escola.gradecurricular.dto.MateriaDto;
import br.com.brito.cliente.escola.gradecurricular.entities.Materia;
import br.com.brito.cliente.escola.gradecurricular.model.Response;
import br.com.brito.cliente.escola.gradecurricular.service.IMateriaService;
import br.com.brito.cliente.escola.gradecurricular.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/materias")
public class MateriaController {

    private static final String DELETE = "DELETE";

    private static final String UPDATE = "UPDATE";

    @Autowired
    private IMateriaService materiaService;

    /*
     * ALTERACAO NOS METODOS DE CONSULTA
     */

    @GetMapping
    public ResponseEntity<Response<List<MateriaDto>>> listarMaterias() {
        Response<List<MateriaDto>> response = new Response<>();
        response.setData(this.materiaService.listar());
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMaterias())
                .withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<MateriaDto>> consultaMateria(@PathVariable Long id) {
        Response<MateriaDto> response = new Response<>();
        MateriaDto materia = this.materiaService.consultar(id);
        response.setData(materia);
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).consultaMateria(id))
                .withSelfRel());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).excluirMateria(id))
                .withRel(DELETE));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).atualizarMateria(materia))
                .withRel(UPDATE));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<Boolean> cadastrarMateria(@Valid @RequestBody MateriaDto materia) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.materiaService.cadastrar(materia));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> excluirMateria(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.materiaService.excluir(id));
    }

    @PutMapping
    public ResponseEntity<Boolean> atualizarMateria(@RequestBody MateriaDto materia) {
        return ResponseEntity.status(HttpStatus.OK).body(this.materiaService.atualizar(materia));
    }


}
