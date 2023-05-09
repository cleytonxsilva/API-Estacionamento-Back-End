package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class VeiculoService {
    @Autowired
    public VeiculoRepository veiculoRepository;
    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Veiculo modelo) {
        if(isNull(modelo.getModelo())){
            throw new RuntimeException("campo Veiculo não pode ser nulo");
        }
        Modelo findByModelo = veiculoRepository.findByModelo(modelo);
        if(!isNull(findByModelo)){
            throw new RuntimeException("CPF já cadastrado");
        }
        veiculoRepository.save(modelo);
    }

    public Optional<Veiculo> findById(Long id){
        return veiculoRepository.findById(id);
    }
    public List<Veiculo> findAll() {
        return veiculoRepository.findAll();
    }
    public List<Veiculo> findByAtivo(boolean ativo) {
        return veiculoRepository.findByAtivo(true);
    }

    @Transactional(rollbackFor = Exception.class)
    public void excluir (final Long id){
        Optional<Veiculo> excluirVeiculo = veiculoRepository.findById(id);
        if(isNull(excluirVeiculo)){
            throw new RuntimeException("Veículo não encontrado");
        }
        veiculoRepository.deleteById(id);
    }
}