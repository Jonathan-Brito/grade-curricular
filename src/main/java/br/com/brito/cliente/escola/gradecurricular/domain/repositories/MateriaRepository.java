package br.com.brito.cliente.escola.gradecurricular.domain.repositories;

import br.com.brito.cliente.escola.gradecurricular.domain.entities.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {
}
