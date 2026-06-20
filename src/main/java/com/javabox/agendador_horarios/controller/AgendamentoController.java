package com.javabox.agendador_horarios.controller;

import com.javabox.agendador_horarios.infrastructure.entity.Agendamento;
import com.javabox.agendador_horarios.services.AgendamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController()
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<Agendamento> salvarAgendamento(@RequestBody Agendamento agendamento){
        return ResponseEntity.ok().body(agendamentoService.salvarAgendamento(agendamento));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarAgendamento(@RequestParam String cliente,
                                                   @RequestParam LocalDateTime dataHoraAgendamento){
        agendamentoService.deletarAgendamento(dataHoraAgendamento, cliente);
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<java.util.List<com.javabox.agendador_horarios.infrastructure.entity.Agendamento>> buscarAgendamentosDia(
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok().body(agendamentoService.buscarAgendamentosDia(date));
    }

    @PutMapping
    public ResponseEntity<Agendamento> atualizarAgendamento(@RequestBody Agendamento agendamento,
                                                            @RequestParam String cliente,
                                                            @RequestParam LocalDateTime dataHoraAgendamento) {

        return ResponseEntity.accepted().body(agendamentoService.alterarAgendmento(agendamento, cliente, dataHoraAgendamento));

    }

}
