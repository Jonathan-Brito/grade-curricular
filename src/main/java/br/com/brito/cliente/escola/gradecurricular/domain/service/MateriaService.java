package br.com.brito.cliente.escola.gradecurricular.domain.service;

import br.com.brito.cliente.escola.gradecurricular.domain.entities.Materia;
import br.com.brito.cliente.escola.gradecurricular.domain.repositories.MateriaRepository;
import br.com.brito.cliente.escola.gradecurricular.exception.MateriaException;
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
    public Boolean cadastrar(Materia materia) {
        try {
            this.materiaRepository.save(materia);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean atualizar(Materia materia) {
        try {
            Optional<Materia> materiaOptional = this.materiaRepository.findById(materia.getId());

            if (materiaOptional.isPresent()){

                // buscamos pela materia que gostaríamos de atualizar
                Materia materiaAtualizada = materiaOptional.get();

                // atualizamos todos os valores
                materiaAtualizada.setName(materia.getName());
                materiaAtualizada.setCode(materia.getCode());
                materiaAtualizada.setHours(materia.getHours());
                materiaAtualizada.setName(materia.getName());
                materiaAtualizada.setFrequency(materia.getFrequency());

                // salvamos as alteracoes
                this.materiaRepository.save(materiaAtualizada);

                return Boolean.TRUE;

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
