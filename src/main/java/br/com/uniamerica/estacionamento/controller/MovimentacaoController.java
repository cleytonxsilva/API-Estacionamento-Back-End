package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/movimentacao")
public class MovimentacaoController {
    @Autowired
    private MovimentacaoService movimentacaoService;

    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id") final Long id){
        final Movimentacao movimentacao = this.movimentacaoService.findById(id).orElse(null);
        return movimentacao == null
                ? ResponseEntity.badRequest().body("Nenhum valor encontrado")
                : ResponseEntity.ok(movimentacao);
    }
    @GetMapping("/lista")
    public ResponseEntity<?> listaCompleta(){
        return ResponseEntity.ok(this.movimentacaoService.findAll());
    }

//    @GetMapping("/abertas")
//    public ResponseEntity<?> findByAberta(){
//        final boolean movimentacoes = this.movimentacaoService.findBySaidaIsNull();
//        return ResponseEntity.ok(movimentacoes);
//    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Movimentacao movimentacao) {
        try {
            this.movimentacaoService.cadastrar(movimentacao);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body(e.getCause().getCause().getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Movimentacao movimentacao){
        try{
            final Movimentacao movimentacaoBanco = this.movimentacaoService.findById(id).orElse(null);

            if(movimentacaoBanco == null)
            {
                throw new RuntimeException("Não foi possível identificar o registro informado");
            }

            this.movimentacaoService.editar(movimentacao);
            return ResponseEntity.ok("Registro editado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body(e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> excluir(@RequestParam("id") final Long id){
        try {
            final Movimentacao movimentacao = this.movimentacaoService.findById(id).orElse(null);
            if(movimentacao == null){
                throw new Exception("Registro inexistente");
            }

            this.movimentacaoService.excluir(id);
            return ResponseEntity.ok("Registro excluido");
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
