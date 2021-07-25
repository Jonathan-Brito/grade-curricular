package br.com.brito.cliente.escola.gradecurricular.domain.service;

import br.com.brito.cliente.escola.gradecurricular.domain.entities.Materia;

import java.util.List;

public interface IMateriaService {

    public Boolean atualizar(final Materia materia);

    public Boolean excluir(final Long id);

    /*
     * LISTAR todas matérias.
     */
    public List<Materia> listar();

    /*
     * CONSULTA uma matéria a partir do ID.
     */
    public Materia consultar(final Long id);

    /*
     * CADASTRAR uma matéria.
     */
    public Boolean cadastrar(final Materia materia);
}
