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
import br.com.brito.cliente.escola.gradecurricular.constante.Mensagens;

import java.util.List;
import java.util.Optional;

@CacheConfig(cacheNames = "materia")
@Service
public class MateriaService implements IMateriaService {

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
            return this.cadastrarOuAtualizar(materia);
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
            throw new MateriaException(Mensagens.ERRO_MATERIA_NAO_ENCONTRADA.getValor(), HttpStatus.NOT_FOUND);
        } catch (MateriaException m) {
            throw m;
        } catch (Exception e) {
            throw new MateriaException(Mensagens.ERRO_GENERICO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CachePut(unless = "#result.size()<3")
    @Override
    public List<MateriaDto> listar() {
        try {
            List<MateriaDto> materiaDto = this.mapper.map(this.materiaRepository.findAll(),
                    new TypeToken<List<MateriaDto>>() {
                    }.getType());

            materiaDto.forEach(materia ->
                    materia.add(WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).consultaMateria(materia.getId()))
                            .withSelfRel())
            );

            return materiaDto;

        } catch (Exception e) {
            throw new MateriaException(Mensagens.ERRO_GENERICO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Boolean cadastrar(MateriaDto materia) {
        try {
            if(materia.getId() != null) {
                throw new MateriaException(Mensagens.ERRO_ID_INFORMADO.getValor(),
                        HttpStatus.BAD_REQUEST);
            }

            if (this.materiaRepository.findByCodigo(materia.getCode()) != null) {
                throw new MateriaException(Mensagens.ERRO_MATERIA_CADASTRADA_ANTERIORMENTE.getValor(),
                        HttpStatus.BAD_REQUEST);
            }
            return this.cadastrarOuAtualizar(materia);
        } catch (MateriaException m) {
            throw m;
        } catch (Exception e) {
            throw new MateriaException(Mensagens.ERRO_GENERICO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<MateriaDto> listarPorHorarioMinimo(int horaMinima) {
        return this.mapper.map(this.materiaRepository.findByHoraMinima(horaMinima), new TypeToken<List<MateriaDto>>() {
        }.getType());
    }

    @Override
    public List<MateriaDto> listarPorFrequencia(int frequencia) {
        return this.mapper.map(this.materiaRepository.findByFrequencia(frequencia), new TypeToken<List<MateriaDto>>() {
        }.getType());
    }

    private Boolean cadastrarOuAtualizar(MateriaDto materia) {
        Materia materiaEnt = this.mapper.map(materia, Materia.class);
        this.materiaRepository.save(materiaEnt);
        return Boolean.TRUE;
    }

}
