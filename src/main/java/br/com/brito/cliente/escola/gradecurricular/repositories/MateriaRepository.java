package br.com.brito.cliente.escola.gradecurricular.repositories;

import br.com.brito.cliente.escola.gradecurricular.entities.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {
}
