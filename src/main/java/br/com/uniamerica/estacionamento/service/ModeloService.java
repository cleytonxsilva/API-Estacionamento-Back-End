package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
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

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Modelo modelo) {
        if(isNull(modelo.getModelo())){
            throw new RuntimeException("O campo Modelo não pode ser nulo!");
        }
        Marca findByModelo = modeloRepository.findByModelo(modelo);
        if(!isNull(findByModelo)){
            throw new RuntimeException("Modelo já cadastrado");
        }
        modeloRepository.save(modelo);
    }

    public Optional<Modelo> findById(Long id) {
        return modeloRepository.findById(id);
    }

    public List<Modelo> findAll() {
        return modeloRepository.findAll();
    }

    public List<Modelo> findByAtivo(boolean ativo) {
        return modeloRepository.findByAtivo(true);
    }

    @Transactional(rollbackFor = Exception.class)
    public void excluir (final Long id){
        Optional<Modelo> excluirModelo = modeloRepository.findById(id);
        if(isNull(excluirModelo)){
            throw new RuntimeException("Modelo não encontrado");
        }
        modeloRepository.deleteById(id);
    }
}
