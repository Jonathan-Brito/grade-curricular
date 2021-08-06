package br.com.brito.cliente.escola.gradecurricular.controller;

import br.com.brito.cliente.escola.gradecurricular.dto.MateriaDto;
import br.com.brito.cliente.escola.gradecurricular.entities.Curso;
import br.com.brito.cliente.escola.gradecurricular.model.CursoModel;
import br.com.brito.cliente.escola.gradecurricular.model.Response;
import br.com.brito.cliente.escola.gradecurricular.service.ICursoService;
import br.com.brito.cliente.escola.gradecurricular.service.IMateriaService;
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
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private ICursoService cursoService;

    /*
     * Cadastro curso, passando a os códigos das matérias a serem cadastradas
     */

    @PostMapping
    public ResponseEntity<Response<Boolean>> cadastrarCurso(@Valid @RequestBody CursoModel curso) {

        Response<Boolean> response = new Response<>();

        response.setData(cursoService.cadastrar(curso));
        response.setStatusCode(HttpStatus.OK.value());

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    /*
     * Listar cursos
     */

    @GetMapping
    public ResponseEntity<Response<List<Curso>>> listarCurso() {
        Response<List<Curso>> response = new Response<>();
        response.setData(this.cursoService.listar());
        response.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /*
     * Consultar curso por código do curso
     */

    @GetMapping("/{codCurso}")
    public ResponseEntity<Response<Curso>> consultarCursoPorMateria(@PathVariable String codCurso) {
        Response<Curso> response = new Response<>();
        response.setData(this.cursoService.consultarPorCodigo(codCurso));
        response.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /*
     * Atualizar curso
     */
    @PutMapping
    public ResponseEntity<Response<Boolean>> atualizarCurso(@Valid @RequestBody CursoModel curso) {
        Response<Boolean> response = new Response<>();
        response.setData(cursoService.atualizar(curso));
        response.setStatusCode(HttpStatus.OK.value());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /*
     * Excluir curso
     */
    @DeleteMapping("/{cursoId}")
    public ResponseEntity<Response<Boolean>> excluirCurso( @PathVariable Long cursoId) {
        Response<Boolean> response = new Response<>();
        response.setData(cursoService.excluir(cursoId));
        response.setStatusCode(HttpStatus.OK.value());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
