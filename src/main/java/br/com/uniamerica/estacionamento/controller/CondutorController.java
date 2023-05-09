package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.service.CondutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/condutor")
public class CondutorController {

    @Autowired
    private CondutorService condutorService;
    private MovimentacaoRepository movimentacaoRepository;

    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id") final Long id){
        final Condutor condutor = this.condutorService.findById(id).orElse(null);
        return condutor == null
                ? ResponseEntity.badRequest().body("Nenhum valor encontrado")
                : ResponseEntity.ok(condutor);
    }
    @GetMapping("/lista")
    public ResponseEntity<?> listaCompleta(){
        return ResponseEntity.ok(this.condutorService.findAll());
    }

    @GetMapping("/ativos")
    public ResponseEntity<?> findByAtivo(){
        final List<Condutor> condutores = this.condutorService.findByAtivo(true);
            return ResponseEntity.ok(condutores);
    }
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Condutor condutor) {
        try {
            this.condutorService.cadastrar(condutor);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        } catch (DataIntegrityViolationException e) {
            //System.out.println(e.getCause().toString());
            return ResponseEntity.internalServerError().body("Error" + e.getCause());

        }
    }
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Condutor condutor){
        try{
            final Condutor condutorBanco = this.condutorService.findById(id).orElse(null);

            if(condutorBanco == null || !condutorBanco.getId().equals(condutor.getId()))
            {
                throw new RuntimeException("Não foi possível identificar o registro informado");
            }

            this.condutorService.cadastrar(condutor);
            return ResponseEntity.ok("Registro editado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }
    }

    public ResponseEntity<?> excluir(@RequestParam("id") final Long id){
        try {
            final Condutor condutor = this.condutorService.findById(id).orElse(null);
            if(condutor == null){
                throw new Exception("Registro inexistente");
            }

            final List<Movimentacao> movimentacoes = this.movimentacaoRepository.findAll();
            for(Movimentacao movimentacao : movimentacoes){
                if(condutor.equals(movimentacao.getCondutor())){
                    condutor.setAtivo(false);
                    this.condutorService.cadastrar(condutor);
                    return ResponseEntity.ok("Registro não está ativo");
                }
            }

            if(condutor.isAtivo()){
                this.condutorService.excluir(id);
                return ResponseEntity.ok("Registro deletado com sucesso");
            }
            else{
                throw new Exception("Não foi possível excluir o registro");
            }
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Error" + e.getMessage());
        }
    }

//    @DeleteMapping
//    public ResponseEntity<?> excluir(@RequestBody final Condutor condutor){
//        this.condutorRepository.delete(condutor);
//        return ResponseEntity.ok("Registro excluido com sucesso");
//    }

}
