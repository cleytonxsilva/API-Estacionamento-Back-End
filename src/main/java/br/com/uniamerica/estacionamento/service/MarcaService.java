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
    public void cadastrar(final Marca nome) {
        if(isNull(nome.getNome())){
            throw new RuntimeException("O campo Marca não pode ser nulo!");
        }
        Marca findByMarca = marcaRepository.findByMarca(nome);
        if(!isNull(findByMarca)){
            throw new RuntimeException("Marca já cadastrada");
        }
        marcaRepository.save(nome);
    }

    public Optional<Marca> findById(Long id) {
        return marcaRepository.findById(id);
    }

    public List<Marca> findAll() {
        return marcaRepository.findAll();
    }

    public List<Marca> findByAtivo(boolean ativo) {
        return marcaRepository.findByAtivo(true);
    }

    @Transactional(rollbackFor = Exception.class)
    public void excluir (final Long id){
        Optional<Marca> excluirMarca = marcaRepository.findById(id);
        if(isNull(excluirMarca)){
            throw new RuntimeException("Marca não encontrada");
        }
        marcaRepository.deleteById(id);
    }

}

