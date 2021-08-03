package br.com.brito.cliente.escola.gradecurricular.service;

import br.com.brito.cliente.escola.gradecurricular.dto.MateriaDto;
import br.com.brito.cliente.escola.gradecurricular.entities.Materia;

import java.util.List;

public interface IMateriaService {

    public Boolean atualizar(final MateriaDto materia);

    public Boolean excluir(final Long id);

    /*
     * LISTAR todas matérias.
     */
    public List<MateriaDto> listar();

    /*
     * CONSULTA uma matéria a partir do ID.
     */
    public MateriaDto consultar(final Long id);

    /*
     * CADASTRAR uma matéria.
     */
    public Boolean cadastrar(final MateriaDto materia);

    public List<MateriaDto> listarPorHorarioMinimo(int hours);

    public List<MateriaDto> listarPorFrequencia(int frequency);
}
