package br.com.brito.cliente.escola.gradecurricular.service;

import br.com.brito.cliente.escola.gradecurricular.constante.Mensagens;
import br.com.brito.cliente.escola.gradecurricular.controller.MateriaController;
import br.com.brito.cliente.escola.gradecurricular.dto.CursoDto;
import br.com.brito.cliente.escola.gradecurricular.dto.MateriaDto;
import br.com.brito.cliente.escola.gradecurricular.entities.Curso;
import br.com.brito.cliente.escola.gradecurricular.entities.Materia;
import br.com.brito.cliente.escola.gradecurricular.exception.CursoException;
import br.com.brito.cliente.escola.gradecurricular.exception.MateriaException;
import br.com.brito.cliente.escola.gradecurricular.model.CursoModel;
import br.com.brito.cliente.escola.gradecurricular.repositories.ICursoRepository;
import br.com.brito.cliente.escola.gradecurricular.repositories.IMateriaRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CacheConfig(cacheNames = "curso")
@Service
public class CursoService implements ICursoService {

    private ICursoRepository cursoRepository;

    private IMateriaRepository materiaRepository;

    @Autowired
    public CursoService(ICursoRepository cursoRepository, IMateriaRepository materiaRepository) {
        this.cursoRepository = cursoRepository;
        this.materiaRepository = materiaRepository;
    }

    @Override
    public Boolean cadastrar(CursoModel cursoModel) {
        try {
            /*
             * O id não pode ser informado no cadastro
             */

            if (cursoModel.getId() != null) {
                throw new CursoException(Mensagens.ERRO_ID_INFORMADO.getValor(), HttpStatus.BAD_REQUEST);
            }

            /*
             * Não permite fazer cadastro de cursos com mesmos códigos.
             */
            if (this.cursoRepository.findCursoByCodigo(cursoModel.getCodCurso()) != null) {
                throw new CursoException(Mensagens.ERRO_CURSO_CADASTRADO_ANTERIORMENTE.getValor(), HttpStatus.BAD_REQUEST);
            }
            return this.cadastrarOuAtualizar(cursoModel);

        }catch (CursoException c) {
            throw c;
        }
        catch (Exception e) {
            throw new CursoException(Mensagens.ERRO_GENERICO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Boolean atualizar(CursoModel cursoModel) {
        try {
            this.consultarPorCodigo(cursoModel.getCodCurso());
            return this.cadastrarOuAtualizar(cursoModel);
        } catch (CursoException c) {
            throw c;
        } catch (Exception e) {
            throw e;
        }
    }

    @CachePut(key = "#codCurso")
    @Override
    public Curso consultarPorCodigo(String codCurso) {

        try {
            Curso curso = this.cursoRepository.findCursoByCodigo(codCurso);

            if (curso == null) {
                throw new CursoException(Mensagens.ERRO_CURSO_NAO_ENCONTRADO.getValor(), HttpStatus.NOT_FOUND);
            }
            return curso;

        } catch (CursoException c) {
            throw c;
        } catch (Exception e) {
            throw new CursoException(Mensagens.ERRO_GENERICO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CachePut(unless = "#result.size()<3")
    @Override
    public List<Curso> listar() {
        return this.cursoRepository.findAll();
    }

    @Override
    public Boolean excluir(Long cursoId) {
        try {
            if(this.cursoRepository.findById(cursoId).isPresent()) {
                this.cursoRepository.deleteById(cursoId);
                return Boolean.TRUE;
            }
            throw new CursoException(Mensagens.ERRO_CURSO_NAO_ENCONTRADO.getValor(), HttpStatus.NOT_FOUND);
        }catch (CursoException c) {
            throw c;
        }catch (Exception e) {
            throw new CursoException(Mensagens.ERRO_GENERICO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * O cadastrar e atualizar tem comportamentos semelhantes então centralizamos
     * esss comportamento.
     */

    private Boolean cadastrarOuAtualizar(CursoModel cursoModel) {
        List<Materia> listMateriaEntity = new ArrayList<>();

        if (!cursoModel.getMaterias().isEmpty()) {

            cursoModel.getMaterias().forEach(materia -> {
                if (this.materiaRepository.findById(materia).isPresent())
                    listMateriaEntity.add(this.materiaRepository.findById(materia).get());
            });
        }

        Curso curso = new Curso();
        if(cursoModel.getId()!=null) {
            curso.setId(cursoModel.getId());
        }
        curso.setCodCurso(cursoModel.getCodCurso());
        curso.setNome(cursoModel.getNome());
        curso.setMaterias(listMateriaEntity);

        this.cursoRepository.save(curso);

        return Boolean.TRUE;
    }

    
}
