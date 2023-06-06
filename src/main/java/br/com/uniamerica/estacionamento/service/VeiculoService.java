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
    public void cadastrar(final Veiculo veiculo) {

        if(veiculo.getId() == null){
            throw new RuntimeException("O campo Id-Veiculo não pode ser nulo!");
        }
        Optional <Veiculo> veiculoBanco = this.veiculoRepository.findByVeiculo(veiculo.getVeiculo());
        if(veiculoBanco.isPresent()){
            throw new RuntimeException("Veiculo já cadastrado com esse nome!");
        }

        if(veiculo.getVeiculo().length() > 50){
            throw new RuntimeException("Limite máximo de 50 caracteres");
        }
        veiculoRepository.save(veiculo);
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