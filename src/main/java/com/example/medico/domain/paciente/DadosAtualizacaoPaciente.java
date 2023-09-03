package com.example.medico.domain.paciente;

import jakarta.validation.constraints.NotNull;
import com.example.medico.domain.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
