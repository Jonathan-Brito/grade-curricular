package br.com.brito.cliente.escola.gradecurricular.controller;

import br.com.brito.cliente.escola.gradecurricular.domain.entities.Materia;
import br.com.brito.cliente.escola.gradecurricular.domain.repositories.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;

@RestController
@RequestMapping("/materias")
public class MateriaController {

    @Autowired
    private MateriaRepository materiaRepository;

    @GetMapping
    public ResponseEntity<List<Materia>> listaMaterias(){
        return ResponseEntity.status(HttpStatus.OK).body(materiaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Materia> consultaPorMateria(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(materiaRepository.findById(id).get());
    }

    @PostMapping
    public ResponseEntity<Boolean> cadastraMaterias(@RequestBody Materia materia){

        try {
            materiaRepository.save(materia);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        catch (Exception exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

    @PutMapping
    public ResponseEntity<Boolean> atualizarMaterias(@RequestBody Materia materia) {
        try {
            //buscamos pela materia que gostaríamos de atualizar
            Materia materiaAtualizada = this.materiaRepository.findById(materia.getId()).get();

            //atualizamos todos os valores
            materiaAtualizada.setName(materia.getName());
            materiaAtualizada.setHours(materia.getHours());
            materiaAtualizada.setCode(materia.getCode());
            materiaAtualizada.setFrequency(materia.getFrequency());


            //salvamos as alteracoes
            this.materiaRepository.save(materiaAtualizada);

            return ResponseEntity.status(HttpStatus.OK).body(true);

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> excluirMateria(@PathVariable Long id) {
        try {
            this.materiaRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(true);

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }
}
