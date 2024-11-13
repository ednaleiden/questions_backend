package com.developer.questions.services.impl;


import com.developer.questions.model.Encuesta;
import com.developer.questions.model.Pregunta;
import com.developer.questions.repository.EncuestaRepository;
import com.developer.questions.repository.PreguntaRepository;
import com.developer.questions.services.PreguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PreguntaServiceImpl implements PreguntaService {

    @Autowired
    private PreguntaRepository preguntaRepository;

    @Autowired
    private EncuestaRepository encuestaRepository;


    @Override
    public Pregunta agregarPreguntaAEncuesta(Long encuestaId, String contenido) {
        Optional<Encuesta> optionalEncuesta = encuestaRepository.findById(encuestaId);
        return optionalEncuesta.map(encuesta -> {
            Pregunta pregunta = new Pregunta(null,contenido,encuesta,new ArrayList<>());
            encuesta.getPreguntas().add(pregunta);

            encuestaRepository.save(encuesta);
            return pregunta;
        }).orElse(null);
    }

    @Override
    public List<Pregunta> obtenerPreguntasPorEncuesta(Long encuestaId) {
        return preguntaRepository.findByEncuestaId(encuestaId);
    }

    @Override
    public Optional<Pregunta> obtenerDetallesPregunta(Long preguntaId) {
        return preguntaRepository.findById(preguntaId);
    }

    @Override
    public Pregunta actualizarPregunta(Long preguntaId, String nuevoContenido, Long encuestaId) {
        return preguntaRepository.findById(preguntaId)
                .map(pregunta -> {
                    pregunta.setContenido(nuevoContenido);
                    Optional<Encuesta> optionalEncuesta = encuestaRepository.findById(encuestaId);
                    optionalEncuesta.ifPresent(pregunta::setEncuesta);
                    return preguntaRepository.save(pregunta);
                }).orElse(null);
    }

    @Override
    public void eliminarPregunta(Long preguntaId) {
        preguntaRepository.findById(preguntaId).ifPresent(pregunta -> {
            Encuesta encuesta = pregunta.getEncuesta();
            encuesta.getPreguntas().remove(pregunta);

            preguntaRepository.deleteById(preguntaId);
            encuestaRepository.save(encuesta);
        });
    }
}
