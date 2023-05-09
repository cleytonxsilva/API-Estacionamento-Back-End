package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class MovimentacaoService {
    @Autowired
    public MovimentacaoRepository movimentacaoRepository;
    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Movimentacao movimentacao) {
        if(isNull(movimentacao.getCondutor()) || isNull((movimentacao.getVeiculo())) || isNull(movimentacao.getEntrada())){
            throw new RuntimeException("Condutor, Veiculo e Horário de entrada são campos obrigatórios");
        }
        Movimentacao findByVeiculo = movimentacaoRepository.findByVeiculo(movimentacao);
        if(!isNull(findByVeiculo)){
            throw new RuntimeException("Veiculo já cadastrado");
        }
        movimentacaoRepository.save(movimentacao);
    }

    public Optional<Movimentacao> findById(Long id) {
        return movimentacaoRepository.findById(id);
    }
    public List<Movimentacao> findAll() {
        return movimentacaoRepository.findAll();
    }

    public boolean findBySaidaIsNull() {
         movimentacaoRepository.findBySaidaIsNull();
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    public void excluir (final Long id){
        Optional<Movimentacao> excluirMovimentacao = movimentacaoRepository.findById(id);
        if(findBySaidaIsNull()){
            throw new RuntimeException("Movimentacao não encontrada");
        }
        movimentacaoRepository.deleteById(id);
    }
}