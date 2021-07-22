package br.com.brito.cliente.escola.gradecurricular.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/materias")
public class MateriaController {

    @GetMapping("/")
    public ResponseEntity<String> hello(){
        return ResponseEntity.status(HttpStatus.OK).body("Olá mundo!!!!!!1");
    }
}
