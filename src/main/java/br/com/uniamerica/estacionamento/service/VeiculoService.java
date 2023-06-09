package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class VeiculoService {
    @Autowired
    public VeiculoRepository veiculoRepository;
    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Veiculo veiculo) {

        Veiculo veiculoBanco = this.veiculoRepository.findByPlacaCarro(veiculo.getPlacaCarro());
        if (veiculoBanco != null){
            throw new RuntimeException("Veiculo já cadastrado com essa placa!");

        }

        if(veiculo.getPlacaCarro().length() > 10){
            throw new RuntimeException("Limite máximo de 10 caracteres");
        }
        veiculoRepository.save(veiculo);
    }

    public Optional<Veiculo> findById(Long id){
        return veiculoRepository.findById(id);
    }
    public List<Veiculo> findAll() {
        return veiculoRepository.findAll();
    }
    public List<Veiculo> findByAtivoTrue(boolean ativo) {
        return veiculoRepository.findByAtivoTrue();
    }

    public List<Veiculo> findByAtivoTrue() {
        return veiculoRepository.findByAtivoTrue();
    }
    @Transactional(rollbackFor = Exception.class)
    public void excluir (final Long id){
        Optional<Veiculo> excluirVeiculo = veiculoRepository.findById(id);
//        if((excluirVeiculo)){
//            throw new RuntimeException("Veículo não encontrado");
//        }
        veiculoRepository.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Long id, final Veiculo veiculo){

        Veiculo veiculoBanco = this.veiculoRepository.findByPlacaCarro(veiculo.getPlacaCarro());
        if (veiculoBanco == null){
            throw new RuntimeException("Veículo não encontrado");
        }

        veiculoRepository.save(veiculo);

    }
}