package br.com.uniamerica.estacionamento.service;


import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class MarcaService {
    @Autowired
    private MarcaRepository marcaRepository;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Marca marca) {
        if(marca.getMarca() == null){
            throw new RuntimeException("O campo Marca não pode ser nulo!");
        }
        Optional <Marca> marcaBanco = this.marcaRepository.findByMarca(marca.getMarca());
        if(marcaBanco.isPresent()){
            throw new RuntimeException("Marca já cadastrada");
        }

        if(marca.getMarca().length() > 50){
            throw new RuntimeException("Limite máximo de 50 caracteres");
        }
        //marca.setAtivo(false);
        marcaRepository.save(marca);
    }

    public Optional<Marca> findById(Long id) {
        return marcaRepository.findById(id);
    }

    public List<Marca> findAll() {
        return marcaRepository.findAll();
    }

    public List<Marca> findByAtivoTrue() {
        return marcaRepository.findByAtivoTrue();
    }

    @Transactional(rollbackFor = Exception.class)
    public void excluir (final Long id){
        Optional<Marca> excluirMarca = marcaRepository.findById(id);
        if(excluirMarca.isEmpty()){
            throw new RuntimeException("Marca não encontrada");
        }
        if(excluirMarca.get().isAtivo()){
            throw new IllegalStateException("Não é possível excluir esta marca pois existem modelos cadastrados com ela.");
        }

        marcaRepository.deleteById(id);
    }

}

