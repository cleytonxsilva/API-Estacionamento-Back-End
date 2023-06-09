package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class ModeloService {
    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Modelo modelo) {

        if(modelo.getId() == null){
            throw new RuntimeException("O campo Id-Modelo não pode ser nulo!");
        }
        Optional <Modelo> modeloBanco = this.modeloRepository.findByModelo(modelo.getModelo());
        if(modeloBanco.isPresent()){
            throw new RuntimeException("Modelo já cadastrado com esse nome!");
        }

        if(modelo.getModelo().length() > 50){
            throw new RuntimeException("Limite máximo de 50 caracteres");
        }
        //modelo.setAtivo(false);
        modeloRepository.save(modelo);
    }

    public Optional<Modelo> findById(Long id) {
        return modeloRepository.findById(id);
    }

    public List<Modelo> findAll() {
        return modeloRepository.findAll();
    }

    public List<Modelo> findByAtivoTrue() {
        return modeloRepository.findByAtivoTrue();
    }

    @Transactional(rollbackFor = Exception.class)
    public void excluir (final Long id){
        Optional<Modelo> excluirModelo = modeloRepository.findById(id);
        if(excluirModelo.isEmpty()){
            throw new RuntimeException("Marca não encontrada");
        }

        modeloRepository.deleteById(id);
    }
}
