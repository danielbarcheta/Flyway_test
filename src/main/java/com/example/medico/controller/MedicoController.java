package com.example.medico.controller;

import com.example.medico.medico.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("medicos")
@RestController
public class MedicoController {

    @Autowired
    private MedicoRepository repository;   /*Sabemos que repository é uma interface, e não pode ser INSTANCIADA.
                                           No entanto, o próprio Spring irá instanciar automaticamente uma classe concreta com os métodos que iremos utilizar
                                           a partir dessa interface para o controlador. Essa é a principal funcionalidade do Spring,
                                           chamada INJEÇÃO DE DEPENDENCIAS. Para isso, utilizamos a notação AUTOWIRED. */

    @PostMapping
    @Transactional       // RequestBody faz com que o Spring MAPEIE o conteudo do corpo da requisicao (Os dados passados) para o objeto (no caso, o DTO DadosCadastroMedico)
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uribuilder) { //Obs; ID: null, pois será gerado automaticamente!
                                                    // uribuilder é usado para armazenar o endereco da api atual
        repository.save(new Medico(dados));
        var medico = new Medico(dados);

        //Por padrao, devemos enviar o codigo 201 no cadastro
        // e tambem devolver esses dados cadastrados e a referencia para que o front possa acessa-lo:

        var uri = uribuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri(); //ID acrescentado na uri(URL da api)

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico)); //mensagem final: cod201 + ID + dados Medico criado

    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(Pageable paginacao) {
        var page = repository.findAll(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        // Vamos criar um DTO para devolver os dados atualizados(criar outro mais completo):
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")                                       // o parametro dinamico (ID a ser excluido) vem na URL. No caso, /medicos/id
    @Transactional                                               // toda operacao de escrita precisa de um @transactional
    public ResponseEntity excluir(@PathVariable Long id) {      //PathVariable avisa que id vem da URL {id}
    var medico = repository.getReferenceById(id);
    medico.excluir(id);
        return ResponseEntity.noContent().build();            //Sucesso codigo 204 (No content)
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {      //PathVariable avisa que id vem da URL {id}
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

}
