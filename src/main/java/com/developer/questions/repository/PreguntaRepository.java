package com.developer.questions.repository;

import com.developer.questions.model.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta,Long> {

    List<Pregunta> findByEncuestaId(Long encuestaId);

}
