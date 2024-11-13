package com.developer.questions.repository;

import com.developer.questions.model.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta,Long> {

    List<Respuesta> findByPreguntaId(Long preguntaId);

}
