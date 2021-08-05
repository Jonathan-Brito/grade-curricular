package br.com.brito.cliente.escola.gradecurricular.repositories;

import br.com.brito.cliente.escola.gradecurricular.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ICursoRepository extends JpaRepository<Curso, Long> {

    @Query("select c from Curso c where c.code =:code")
    public Curso findCursoByCodigo(@Param("code")String code);
}
