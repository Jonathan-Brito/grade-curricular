package br.com.brito.cliente.escola.gradecurricular.service;

import br.com.brito.cliente.escola.gradecurricular.controller.MateriaController;
import br.com.brito.cliente.escola.gradecurricular.dto.MateriaDto;
import br.com.brito.cliente.escola.gradecurricular.entities.Materia;
import br.com.brito.cliente.escola.gradecurricular.repositories.IMateriaRepository;
import br.com.brito.cliente.escola.gradecurricular.exception.MateriaException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CacheConfig(cacheNames = "materia")
@Service
public class MateriaService implements IMateriaService {

    private static final String MENSAGEM_ERRO = "Erro interno identificado. Contate o suporte";
    private static final String MATERIA_NAO_ENCONTRADA = "Matéria não encontrada";
    private IMateriaRepository materiaRepository;
    private ModelMapper mapper;

    @Autowired
    public MateriaService(IMateriaRepository materiaRepository) {
        this.mapper = new ModelMapper();
        this.materiaRepository = materiaRepository;
    }

    @Override
    public Boolean atualizar(MateriaDto materia) {
        try {
            this.consultar(materia.getId());
            Materia materiaEntityAtualizada = this.mapper.map(materia, Materia.class);

            this.materiaRepository.save(materiaEntityAtualizada);

            return Boolean.TRUE;

        } catch (MateriaException m) {
            throw m;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Boolean excluir(Long id) {
        try {
            this.consultar(id);
            this.materiaRepository.deleteById(id);
            return Boolean.TRUE;
        } catch (MateriaException m) {
            throw m;
        } catch (Exception e) {
            throw e;
        }
    }

    @CachePut(key = "#id")
    @Override
    public MateriaDto consultar(Long id) {
        try {
            Optional<Materia> materiaOptional = this.materiaRepository.findById(id);
            if (materiaOptional.isPresent()) {
                return this.mapper.map(materiaOptional.get(), MateriaDto.class);
            }
            throw new MateriaException(MATERIA_NAO_ENCONTRADA, HttpStatus.NOT_FOUND);
        } catch (MateriaException m) {
            throw m;
        } catch (Exception e) {
            throw new MateriaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CachePut(unless = "#result.size()<3")
    @Override
    public List<MateriaDto> listar() {
        try {

            List<MateriaDto> materiaDto = this.mapper.map(this.materiaRepository.findAll(),
                    new TypeToken<List<MateriaDto>>() {
                    }.getType());

            materiaDto.forEach(materia -> {
                materia.add(WebMvcLinkBuilder
                        .linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).consultaMateria(materia.getId()))
                        .withSelfRel());
            });

            return materiaDto;

        } catch (Exception e) {
            throw new MateriaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Boolean cadastrar(MateriaDto materia) {
        try {
            Materia materiaEnt = this.mapper.map(materia, Materia.class);
            this.materiaRepository.save(materiaEnt);
            return Boolean.TRUE;
        } catch (Exception e) {
            throw new MateriaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
