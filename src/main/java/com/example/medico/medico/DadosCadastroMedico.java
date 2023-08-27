package com.example.medico.medico;

import com.example.medico.endereco.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroMedico(
       @NotBlank // Poderia ser NotNull, mas NotBlank ja verifica se nao é nem nulo nem vazio
        String nome,
        @NotBlank @Email
        String email,
        @NotBlank
        String telefone,
        @NotBlank @Pattern(regexp = "\\d{4,6}") // de 4 a 6 caracteres!
        String crm,
        @NotNull //NotBLank é só para Strings!
        Especialidade especialidade,
        @NotNull @Valid //Avisa ao spring que endereco possui um DTO que precisa ser validado!
        DadosEndereco endereco) {
}
