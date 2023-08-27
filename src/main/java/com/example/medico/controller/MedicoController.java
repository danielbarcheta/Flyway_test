package com.example.medico.controller;

import com.example.medico.medico.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("medicos")
@RestController
public class MedicoController {

    @Autowired
    private MedicoRepository repository;   /*Sabemos que repository é uma interface, e não pode ser INSTANCIADA.
                                           No entanto, o próprio Spring irá instanciar automaticamente uma classe concreta com os métodos que iremos utilizar
                                           a partir dessa interface para o controlador. Essa é a principal funcionalidade do Spring,
                                           chamada INJEÇÃO DE DEPENDENCIAS. Para isso, utilizamos a notação AUTOWIRED. */

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody  @Valid DadosCadastroMedico dados){
       // repository.save(medico) <- é o que queremos fazer!
        // No entanto, recebemos os dados por meio de um DTO (DadosCadastroMedico). Precisamos converter!



        repository.save(new Medico(dados));

        /* Opcao 2: salvar dessa forma ->
        Da pra enxugar os parametros -> Criamos um  construtor na classe medico para receber esses dados!*/
        //   repository.save(new Medico(null, dados.nome(), dados.email(), dados.crm(), new Endereco(dados.endereco())) );
        //    Obs; ID: null, pois será gerado automaticamente!
    }

    @GetMapping
    public Page<DadosListagemMedico> listar(Pageable paginacao) {
    return repository.findAll(paginacao).map(DadosListagemMedico::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody  @Valid DadosAtualizacaoMedico dados){
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

    }
    @DeleteMapping("/{id}")     // o parametro dinamico (ID a ser excluido) vem na URL. No caso, /medicos/id
    @Transactional // toda operacao de escrita precisa de um @transactional
    public void excluir(@PathVariable Long id){      //PathVariable avisa que id vem da URL {id}


    }
}
