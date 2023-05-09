package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CondutorRepository extends JpaRepository<Condutor, Long>{

    List<Condutor> findByAtivo(boolean ativo);
    @Query(value = "select u from Condutor u where u.cpf = :#{#condutor.cpf}", nativeQuery = true)
    Condutor findByCpf (@Param("condutor") Condutor condutor);

}
