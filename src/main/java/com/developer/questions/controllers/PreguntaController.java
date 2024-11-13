package com.developer.questions.controllers;

import com.developer.questions.model.Pregunta;
import com.developer.questions.services.PreguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/preguntas")
public class PreguntaController {

    @Autowired
    private PreguntaService preguntaService;

    @PostMapping("/agregar/{encuestaId}")
    public ResponseEntity<Pregunta> agregarPreguntaAEncuesta(@PathVariable Long encuestaId, @RequestBody Pregunta pregunta) throws URISyntaxException {
        Pregunta nuevaPregunta = preguntaService.agregarPreguntaAEncuesta(encuestaId,pregunta.getContenido());
        return ResponseEntity.created(new URI("/api/preguntas" + nuevaPregunta.getId())).body(nuevaPregunta);
    }

    @GetMapping("/por-encuesta/{encuestaId}")
    public List<Pregunta> obtenerPreguntasPorEncuesta(@PathVariable Long encuestaId){
        return preguntaService.obtenerPreguntasPorEncuesta(encuestaId);
    }

    @GetMapping("/{preguntaId}")
    public ResponseEntity<Pregunta> obtenerDetallesPregunta(@PathVariable Long preguntaId){
        return preguntaService.obtenerDetallesPregunta(preguntaId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{preguntaId}/encuesta/{encuestaId}")
    public ResponseEntity<Pregunta> actulizarPregunta(@PathVariable Long preguntaId,@RequestBody Pregunta pregunta,@PathVariable Long encuestaId){
        Pregunta preguntaActulizada = preguntaService.actualizarPregunta(preguntaId,pregunta.getContenido(),encuestaId);
        return preguntaActulizada != null ? ResponseEntity.ok(preguntaActulizada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{preguntaId}")
    public ResponseEntity<Void> eliminarPregunta(@PathVariable Long preguntaId){
        preguntaService.eliminarPregunta(preguntaId);
        return ResponseEntity.noContent().build();
    }
}
