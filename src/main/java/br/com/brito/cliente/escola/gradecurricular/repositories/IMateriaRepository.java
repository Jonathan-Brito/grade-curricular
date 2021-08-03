package br.com.brito.cliente.escola.gradecurricular.repositories;

import br.com.brito.cliente.escola.gradecurricular.entities.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMateriaRepository extends JpaRepository<Materia, Long> {

    @Query("select m from Materia m where m.hours >= :hours")
    public List<Materia> findByHoraMinima(@Param("hours")int hours);

    @Query("select m from Materia m where m.frequency =:frequency")
    public List<Materia> findByFrequencia(@Param("frequency")int frequency);

    @Query("select m from Materia m where m.code =:code")
    public Materia findByCodigo(@Param("code")String code);
}
