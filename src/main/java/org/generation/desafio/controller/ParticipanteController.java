package org.generation.desafio.controller;

import org.generation.desafio.entity.Participante;
import org.generation.desafio.repository.ParticipanteRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"api/v1/participante"})
public class ParticipanteController {

    private final ParticipanteRepository participanteRepository;

    public ParticipanteController(ParticipanteRepository participanteRepository) {
        this.participanteRepository = participanteRepository;
    }

    @GetMapping
    public List<Participante> getParticipantes() {
        return participanteRepository.findAll();
    }

}
