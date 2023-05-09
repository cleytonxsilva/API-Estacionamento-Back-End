package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long>{

    void findBySaidaIsNull();

    @Query("select m from Movimentacao m where m.veiculo = :#{#movimentacao.veiculo}")
    Movimentacao findByVeiculo (@Param("movimentacao") Movimentacao movimentacao);

}
