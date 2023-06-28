package br.com.uniamerica.estacionamento.controller;


import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import br.com.uniamerica.estacionamento.service.ModeloService;
import br.com.uniamerica.estacionamento.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin("*")
@RequestMapping(value = "/api/modelo")
public class ModeloController {
    @Autowired
    private ModeloService modeloService;
    @Autowired
    private VeiculoService veiculoService;
    @Autowired
    private ModeloRepository modeloRepository;

    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id") final Long id){
        final Modelo modelo = this.modeloService.findById(id).orElse(null);
        return modelo == null
            ? ResponseEntity.badRequest().body("Nenhum valor encontrado")
            : ResponseEntity.ok(modelo);
    }
    @GetMapping("/lista")
    public ResponseEntity<?> listaCompleta(){
        return ResponseEntity.ok(this.modeloService.findAll());
    }

    @GetMapping("/ativos")
    public ResponseEntity<?> findByAtivoTrue(){
        final List<Modelo> ativosModelo = this.modeloService.findByAtivoTrue();
        return ResponseEntity.ok(ativosModelo);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Modelo modelo) {
        try {
            this.modeloService.cadastrar(modelo);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Modelo modelo){
        try{
            final Modelo modeloBanco = this.modeloService.findById(id).orElse(null);

            if(modeloBanco == null || !modeloBanco.getId().equals(modelo.getId()))
            {
                throw new RuntimeException("Não foi possível identificar o registro informado");
            }

            this.modeloService.cadastrar(modelo);
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
            final Modelo modelo = this.modeloService.findById(id).orElse(null);
            if(modelo == null){
                throw new Exception("Registro inexistente");
            }
            modeloService.excluir(id);
            return ResponseEntity.ok("Registro excluido com sucesso");
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
