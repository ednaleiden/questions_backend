package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Encuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    //encuesta tendra muchas preguntas
    @OneToMany(mappedBy = "encuesta", cascade = CascadeType.ALL) //si se elimina la encuenta se elimina las preguntas
    private List<Pregunta> preguntas = new ArrayList<>();
}
