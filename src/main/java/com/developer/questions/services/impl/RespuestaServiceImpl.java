package com.developer.questions.services.impl;

import com.developer.questions.model.Pregunta;
import com.developer.questions.model.Respuesta;
import com.developer.questions.repository.PreguntaRepository;
import com.developer.questions.repository.RespuestaRepository;
import com.developer.questions.services.RespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RespuestaServiceImpl implements RespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private PreguntaRepository preguntaRepository;


    @Override
    public Respuesta agregarRespuestaAPregunta(Long preguntaId, String contenido) {
        Optional<Pregunta> preguntaOptional = preguntaRepository.findById(preguntaId);
        return preguntaOptional.map(pregunta -> {
            Respuesta respuesta = new Respuesta(null,contenido,pregunta);
            pregunta.getRespuestas().add(respuesta);

            preguntaRepository.save(pregunta);
            return respuesta;
        }).orElse(null);
    }

    @Override
    public List<Respuesta> obtenerRespuestasPorPregunta(Long preguntaId) {
        return respuestaRepository.findByPreguntaId(preguntaId);
    }

    @Override
    public Optional<Respuesta> obtenerDetallesRespuesta(Long respuestaId) {
        return respuestaRepository.findById(respuestaId);
    }

    @Override
    public Respuesta actualizarRespuesta(Long respuestaId, String nuevoContenido) {
        return respuestaRepository.findById(respuestaId)
                .map(respuesta -> {
                    respuesta.setContenido(nuevoContenido);
                    return respuestaRepository.save(respuesta);
                }).orElse(null);
    }

    @Override
    public void eliminarRespuesta(Long respuestaId) {
        respuestaRepository.findById(respuestaId).ifPresent(respuesta -> {
            Pregunta pregunta = respuesta.getPregunta();
            pregunta.getRespuestas().remove(respuesta);

            respuestaRepository.deleteById(respuestaId);
            preguntaRepository.save(pregunta);
        });
    }
}
