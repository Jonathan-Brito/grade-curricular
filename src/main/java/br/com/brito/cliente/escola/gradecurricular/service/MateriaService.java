package br.com.brito.cliente.escola.gradecurricular.service;

import br.com.brito.cliente.escola.gradecurricular.dto.MateriaDto;
import br.com.brito.cliente.escola.gradecurricular.entities.Materia;
import br.com.brito.cliente.escola.gradecurricular.repositories.MateriaRepository;
import br.com.brito.cliente.escola.gradecurricular.exception.MateriaException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MateriaService implements IMateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    @Override
    public List<Materia> listar() {
        try {
            return this.materiaRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Materia consultar(Long id) {
        try {
            Optional<Materia> materiaOptional = this.materiaRepository.findById(id);
            if (materiaOptional.isPresent()) {
                return materiaOptional.get();
            }
            throw new MateriaException("Matéria não encontrada", HttpStatus.NOT_FOUND);
        } catch (MateriaException materiaException) {
            throw materiaException;
        } catch (Exception exception){
            throw new MateriaException("Erro interno identificado", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public Boolean cadastrar(MateriaDto materia) {
        try {
            ModelMapper mapper = new ModelMapper();
            Materia materia1 = mapper.map(materia, Materia.class);

            this.materiaRepository.save(materia1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean atualizar(MateriaDto materia) {
        try {
            Optional<Materia> materiaOptional = this.materiaRepository.findById(materia.getId());

            if (materiaOptional.isPresent()){

                ModelMapper mapper = new ModelMapper();

                // buscamos pela materia que gostaríamos de atualizar
                Materia materiaAtualizada = mapper.map(materia, Materia.class);

                // salvamos as alteracoes
                this.materiaRepository.save(materiaAtualizada);

                return true;

            }

            return false;


        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean excluir(Long id) {
        try {
            this.consultar(id);
            this.materiaRepository.deleteById(id);
            return true;
        } catch (MateriaException materiaException) {
            throw materiaException;
        } catch (Exception exception){
            throw exception;
        }
    }
}
