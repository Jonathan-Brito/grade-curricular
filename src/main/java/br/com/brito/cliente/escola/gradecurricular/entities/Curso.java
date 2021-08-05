package br.com.brito.cliente.escola.gradecurricular.entities;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_curso")
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private int hours;

    @NotNull
    private String code;

    @OneToMany(fetch = FetchType.LAZY) // Um curso está para várias materias
    @JoinColumn(name = "materia_id")
    private List<Materia> materias = new ArrayList<>();
}
