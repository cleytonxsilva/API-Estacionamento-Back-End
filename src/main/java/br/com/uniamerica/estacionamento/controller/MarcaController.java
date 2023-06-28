package br.com.uniamerica.estacionamento.controller;


import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import br.com.uniamerica.estacionamento.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin("*")
@RequestMapping(value = "/api/marca")
public class MarcaController {
    @Autowired
    private MarcaService marcaService;
    @Autowired
    private ModeloRepository modeloRepository;

    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id") final Long id){
        final Marca marca = this.marcaService.findById(id).orElse(null);
        return marca == null
                ? ResponseEntity.badRequest().body("Nenhum valor encontrado")
                : ResponseEntity.ok(marca);
    }
    @GetMapping("/lista")
    public ResponseEntity<?> listaCompleta(){
        return ResponseEntity.ok(this.marcaService.findAll());
    }

    @GetMapping("/ativos")
    public ResponseEntity<?> findByAtivoTrue(){
        final List<Marca> ativosMarca = this.marcaService.findByAtivoTrue();
        return ResponseEntity.ok(ativosMarca);
    }
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Marca marca) {
        try {
            this.marcaService.cadastrar(marca);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Erro: " + e.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Marca marca){
        try{
            final Marca marcaBanco = this.marcaService.findById(id).orElse(null);

            if(marcaBanco == null || !marcaBanco.getId().equals(marca.getId()))
            {
                throw new RuntimeException("Não foi possível identificar o registro informado");
            }

            this.marcaService.cadastrar(marca);
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
            final Marca marca = this.marcaService.findById(id).orElse(null);
            if(marca == null){
                throw new Exception("Registro inexistente");
            }
            marcaService.excluir(id);
            return ResponseEntity.ok("Registro excluido com sucesso");
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
