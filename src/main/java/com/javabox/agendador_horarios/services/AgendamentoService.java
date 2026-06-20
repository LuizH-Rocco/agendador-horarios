package com.javabox.agendador_horarios.services;


import com.javabox.agendador_horarios.infrastructure.entity.Agendamento;
import com.javabox.agendador_horarios.infrastructure.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor

public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    public Agendamento salvarAgendamento(Agendamento agendamento) {

        LocalDateTime horaAgendamento = agendamento.getDataHoraAgendamento();
        LocalDateTime horaFim = agendamento.getDataHoraAgendamento().plusMinutes(30);

        Agendamento agendados = agendamentoRepository.findByServicoAndDataHoraAgendamentoBetween(agendamento.getServico(), horaAgendamento, horaFim);


        if (Objects.nonNull(agendados)) {
            throw new RuntimeException("Horario ja esta preenchido");
        }
        return agendamentoRepository.save(agendamento);
    }

    public void deletarAgendamento(LocalDateTime dataHoraAgendamento, String cliente) {

        agendamentoRepository.deleteByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);
    }

    public java.util.List<Agendamento> buscarAgendamentosDia(LocalDate data) {
        LocalDateTime primeiraHoraDia = data.atStartOfDay();
        LocalDateTime horaFinal = data.atTime(23, 59, 59);

        return agendamentoRepository.findAll().stream()
                .filter(a -> !a.getDataHoraAgendamento().isBefore(primeiraHoraDia) && !a.getDataHoraAgendamento().isAfter(horaFinal))
                .collect(java.util.stream.Collectors.toList());
    }

    public Agendamento alterarAgendmento(Agendamento agendamento, String cliente, LocalDateTime dataHoraAgendamento) {
        Agendamento agenda = agendamentoRepository.findByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);

        if (Objects.isNull(agenda)) {
            throw new RuntimeException("Horario nao esta preenchido");
        }

        agendamento.setId(agenda.getId());
        return agendamentoRepository.save(agendamento);

    }
}