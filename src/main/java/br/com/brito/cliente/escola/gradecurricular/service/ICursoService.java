package br.com.brito.cliente.escola.gradecurricular.service;

import br.com.brito.cliente.escola.gradecurricular.entities.Curso;
import br.com.brito.cliente.escola.gradecurricular.model.CursoModel;

import java.util.List;

public interface ICursoService {

    Boolean cadastrar(CursoModel cursoModel);

    Boolean atualizar(CursoModel cursoModel);

    Boolean excluir(Long cursoId);

    Curso consultarPorCodigo(String codCurso);

    List<Curso> listar();
}
